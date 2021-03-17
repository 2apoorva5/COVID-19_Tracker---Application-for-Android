package com.application.covid19tracker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.application.covid19tracker.Adapter.StateListAdapter
import com.application.covid19tracker.Client.Client
import com.application.covid19tracker.Client.Internet
import com.application.covid19tracker.Model.Response
import com.application.covid19tracker.Model.StatewiseItem
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_india_tracker.*
import kotlinx.coroutines.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class IndiaTrackerActivity : AppCompatActivity() {

    lateinit var stateListAdapter: StateListAdapter

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_india_tracker)

        arrow_back.setOnClickListener {
            onBackPressed()
        }

        val calendar = Calendar.getInstance()
        val currentDate: String = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        date.text = currentDate

        var text = "COVID-19INDIA"
        val spannableString = SpannableString(text)
        val foregroundColorSpan = ForegroundColorSpan(resources.getColor(R.color.colorAccent))
        spannableString.setSpan(foregroundColorSpan, 8, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        heading.text = spannableString

        YoYo.with(Techniques.Flash)
                .duration(1000)
                .repeat(1)
                .playOn(logo)

        val color1 = resources.getColor(R.color.confirmed)
        val color2 = resources.getColor(R.color.active)
        val color3 = resources.getColor(R.color.recovered)
        val color4 = resources.getColor(R.color.deaths)

        swipe_refresh_layout.setColorSchemeColors(color1, color2, color3, color4)

        swipe_refresh_layout.post {
            if (Internet.isConnectedToInternet(applicationContext)) {
                constraint_layout.visibility = View.VISIBLE
                fetchResults()
                swipe_refresh_layout.isRefreshing = false
            } else {
                constraint_layout.visibility = View.GONE
                swipe_refresh_layout.isRefreshing = false
                Toast.makeText(applicationContext, "No Internet Connection!", Toast.LENGTH_LONG).show()
            }
        }

        swipe_refresh_layout.setOnRefreshListener {
            if (Internet.isConnectedToInternet(applicationContext)) {
                constraint_layout.visibility = View.VISIBLE
                fetchResults()
                swipe_refresh_layout.isRefreshing = false
            } else {
                constraint_layout.visibility = View.GONE
                swipe_refresh_layout.isRefreshing = false
                Toast.makeText(applicationContext, "No Internet Connection!", Toast.LENGTH_LONG).show()
            }
        }

        initWorker()
        state_list.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}
            override fun onScroll(
                    view: AbsListView,
                    firstVisibleItem: Int,
                    visibleItemCount: Int,
                    totalItemCount: Int
            ) {
                if (state_list.getChildAt(0) != null) {
                    swipe_refresh_layout.isEnabled = state_list.firstVisiblePosition === 0 && state_list.getChildAt(
                            0
                    ).getTop() === 0
                }
            }
        })
    }

    private fun fetchResults() {
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) { Client.api.clone().execute() }
            if (response.isSuccessful) {
                swipe_refresh_layout.isRefreshing = false
                val data = Gson().fromJson(response.body?.string(), Response::class.java)
                launch(Dispatchers.Main) {
                    bindCombinedData(data.statewise[0])
                    bindStateWiseData(data.statewise.subList(1, data.statewise.size))
                }
            }
        }
    }

    private fun bindStateWiseData(subList: List<StatewiseItem>) {
        stateListAdapter = StateListAdapter(subList)
        state_list.adapter = stateListAdapter
    }

    private fun bindCombinedData(data: StatewiseItem) {
        val lastUpdatedTime = data.lastupdatedtime
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        last_updated.text = "Last updated ${getTimeAgo(simpleDateFormat.parse(lastUpdatedTime))}"

        confirmed.text = data.confirmed
        active.text = data.active
        recovered.text = data.recovered
        deceased.text = data.deaths
        new_confirmed.text = "[+" + data.deltaconfirmed + "]"
        new_recovered.text = "[+" + data.deltarecovered + "]"
        new_deceased.text = "[+" + data.deltadeaths + "]"
    }

    @InternalCoroutinesApi
    private fun initWorker() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationRequest>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                "JOB_TAG",
                ExistingPeriodicWorkPolicy.KEEP,
                notificationWorkRequest
        )
    }

    class NotificationRequest(
            private val context: Context,
            params: WorkerParameters
    ) : CoroutineWorker(context, params) {
        @SuppressLint("StringFormatInvalid")
        private fun showNotification(totalCount: String, time: String) {
            val intent = Intent(context, IndiaTrackerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                    context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT
            )

            val channelId = context.getString(R.string.default_notification_channel_id)
            val channelName = context.getString(R.string.default_notification_channel_name)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setSmallIcon(R.drawable.ic_stat_notification_icon)
                    .setContentTitle(context.getString(R.string.text_confirmed_cases, totalCount))
                    .setContentText(context.getString(R.string.text_last_updated, time))
                    .setStyle(NotificationCompat.BigPictureStyle()
                            .bigPicture(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.covid_19))
                            .bigLargeIcon(null))
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)

            val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                        channelId,
                        channelName,
                        NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0, notificationBuilder.build())
        }

        override suspend fun doWork(): Result = coroutineScope {
            val response = withContext(Dispatchers.IO) { Client.api.clone().execute() }
            if (response.isSuccessful) {
                val result = Gson().fromJson(response.body?.string(), Response::class.java)
                val totalDetails = result.statewise[0]

                val confirmedCases: String? = totalDetails.confirmed
                val newCases: String? = totalDetails.deltaconfirmed

                showNotification(
                        confirmedCases.plus("  [+").plus(newCases).plus("]") ?: "",
                        getLastUpdatedTime(
                                SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                        .parse(totalDetails.lastupdatedtime)
                        )
                )

                Result.success()
            } else {
                Result.retry()
            }
        }

        fun getLastUpdatedTime(past: Date): String {
            val now = Date()
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

            return when {
                seconds < 60 -> {
                    "Few seconds ago"
                }
                minutes < 60 -> {
                    "$minutes minutes ago"
                }
                hours < 24 -> {
                    "$hours hour ${minutes % 60} min ago"
                }
                else -> {
                    SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
                }
            }
        }
    }

    fun getTimeAgo(past: Date): String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds < 60 -> {
                "Few seconds ago"
            }
            minutes < 60 -> {
                "$minutes minutes ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes % 60} min ago"
            }
            else -> {
                SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
