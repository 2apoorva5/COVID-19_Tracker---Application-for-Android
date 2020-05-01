package com.application.covid19tracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application.covid19tracker.Adapter.CountryAdapter;
import com.application.covid19tracker.Client.Internet;
import com.application.covid19tracker.Model.CountryModel;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import maes.tech.intentanim.CustomIntent;

public class AffectedCountriesActivity extends AppCompatActivity {

    ImageView back, logo, textToSpeech;
    TextView affectedCountries;
    EditText countrySearch;
    CardView searchView;
    ListView listView;

    SwipeRefreshLayout swipeRefreshLayout;

    public static List<CountryModel> countryModelList = new ArrayList<>();
    CountryModel countryModel;
    CountryAdapter countryAdapter;

    AVLoadingIndicatorView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        back = findViewById(R.id.arrow_back);
        logo = findViewById(R.id.logo);
        affectedCountries = findViewById(R.id.affected_countries);
        countrySearch = findViewById(R.id.country_search);
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view);
        progress = findViewById(R.id.progress);
        textToSpeech = findViewById(R.id.text_to_speech);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        YoYo.with(Techniques.Flash)
                .duration(1000)
                .repeat(1)
                .playOn(logo);

        KeyboardVisibilityEvent.setEventListener(AffectedCountriesActivity.this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (!isOpen) {
                    countrySearch.clearFocus();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int color1 = getResources().getColor(R.color.confirmed);
        int color2 = getResources().getColor(R.color.active);
        int color3 = getResources().getColor(R.color.recovered);
        int color4 = getResources().getColor(R.color.deaths);

        swipeRefreshLayout.setColorSchemeColors(color1, color2, color3, color4);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (Internet.isConnectedToInternet(AffectedCountriesActivity.this)) {
                    fetchData();

                    swipeRefreshLayout.setRefreshing(false);
                    countrySearch.setEnabled(true);
                    textToSpeech.setEnabled(true);

                    listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {

                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            if (listView.getChildAt(0) != null) {
                                swipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                            }
                        }
                    });

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            startActivity(new Intent(AffectedCountriesActivity.this, CountryDetailsActivity.class).putExtra("position", position));
                            CustomIntent.customType(AffectedCountriesActivity.this, "bottom-to-up");
                        }
                    });

                    countrySearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            countryAdapter.getFilter().filter(charSequence.toString());
                            countryAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    textToSpeech.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, String.format("Say your country name!"));

                            try {
                                startActivityForResult(intent, 123);
                            } catch (ActivityNotFoundException e) {
                                Alerter.create(AffectedCountriesActivity.this)
                                        .setText("There was some error!")
                                        .setTextAppearance(R.style.ErrorAlert)
                                        .setBackgroundColorRes(R.color.errorColor)
                                        .setIcon(R.drawable.error)
                                        .setDuration(3000)
                                        .enableSwipeToDismiss()
                                        .enableIconPulse(true)
                                        .enableVibration(true)
                                        .disableOutsideTouch()
                                        .enableProgress(true)
                                        .setProgressColorInt(getResources().getColor(android.R.color.white))
                                        .show();
                                return;
                            }
                        }
                    });
                } else {
                    progress.show();
                    progress.setVisibility(View.VISIBLE);
                    countrySearch.setEnabled(false);
                    textToSpeech.setEnabled(false);
                    swipeRefreshLayout.setRefreshing(false);
                    countryModelList.clear();
                    Alerter.create(AffectedCountriesActivity.this)
                            .setText("There is no internet connection!")
                            .setTextAppearance(R.style.ErrorAlert)
                            .setBackgroundColorRes(R.color.errorColor)
                            .setIcon(R.drawable.error)
                            .setDuration(3000)
                            .enableIconPulse(true)
                            .enableVibration(true)
                            .disableOutsideTouch()
                            .enableProgress(true)
                            .setProgressColorInt(getResources().getColor(android.R.color.white))
                            .show();
                    return;
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Internet.isConnectedToInternet(AffectedCountriesActivity.this)) {
                    fetchData();

                    swipeRefreshLayout.setRefreshing(false);
                    countrySearch.setEnabled(true);
                    textToSpeech.setEnabled(true);

                    listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {

                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            if (listView.getChildAt(0) != null) {
                                swipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                            }
                        }
                    });

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            startActivity(new Intent(AffectedCountriesActivity.this, CountryDetailsActivity.class).putExtra("position", position));
                            CustomIntent.customType(AffectedCountriesActivity.this, "bottom-to-up");
                        }
                    });

                    countrySearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            countryAdapter.getFilter().filter(charSequence.toString());
                            countryAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    textToSpeech.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, String.format("Say your country name!"));

                            try {
                                startActivityForResult(intent, 123);
                            } catch (ActivityNotFoundException e) {
                                Alerter.create(AffectedCountriesActivity.this)
                                        .setText("There was some error!")
                                        .setTextAppearance(R.style.ErrorAlert)
                                        .setBackgroundColorRes(R.color.errorColor)
                                        .setIcon(R.drawable.error)
                                        .setDuration(3000)
                                        .enableSwipeToDismiss()
                                        .enableIconPulse(true)
                                        .enableVibration(true)
                                        .disableOutsideTouch()
                                        .enableProgress(true)
                                        .setProgressColorInt(getResources().getColor(android.R.color.white))
                                        .show();
                                return;
                            }
                        }
                    });
                } else {
                    progress.show();
                    progress.setVisibility(View.VISIBLE);
                    countrySearch.setEnabled(false);
                    textToSpeech.setEnabled(false);
                    countryModelList.clear();
                    swipeRefreshLayout.setRefreshing(false);
                    Alerter.create(AffectedCountriesActivity.this)
                            .setText("There is no internet connection!")
                            .setTextAppearance(R.style.ErrorAlert)
                            .setBackgroundColorRes(R.color.errorColor)
                            .setIcon(R.drawable.error)
                            .setDuration(3000)
                            .enableIconPulse(true)
                            .enableVibration(true)
                            .disableOutsideTouch()
                            .enableProgress(true)
                            .setProgressColorInt(getResources().getColor(android.R.color.white))
                            .show();
                    return;
                }
            }
        });
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/countries/";
        progress.show();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progress.hide();
                    progress.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String COUNTRY_NAME = jsonObject.getString("country");
                        String CONTINENT = jsonObject.getString("continent");
                        String NEW_CASES = jsonObject.getString("todayCases");
                        String TOTAL_CASES = jsonObject.getString("cases");
                        String NEW_DEATHS = jsonObject.getString("todayDeaths");
                        String TOTAL_DEATHS = jsonObject.getString("deaths");
                        String ACTIVE_CASES = jsonObject.getString("active");
                        String MILD_CASES = String.valueOf((Integer.parseInt(jsonObject.getString("active"))) - (Integer.parseInt(jsonObject.getString("critical"))));
                        String CRITICAL_CASES = jsonObject.getString("critical");
                        String CLOSED_CASES = String.valueOf((Integer.parseInt(jsonObject.getString("recovered"))) + (Integer.parseInt(jsonObject.getString("deaths"))));
                        String RECOVERED_CASES = jsonObject.getString("recovered");
                        String DEATH_CASES = jsonObject.getString("deaths");
                        String CASES_PER_MILLION = jsonObject.getString("casesPerOneMillion");
                        String DEATHS_PER_MILLION = jsonObject.getString("deathsPerOneMillion");
                        String TOTAL_TESTS = jsonObject.getString("tests");
                        String TESTS_PER_MILLION = jsonObject.getString("testsPerOneMillion");

                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String FLAG = object.getString("flag");

                        countryModel = new CountryModel(COUNTRY_NAME, FLAG, CONTINENT, TOTAL_CASES, NEW_CASES, TOTAL_DEATHS, NEW_DEATHS,
                                ACTIVE_CASES, MILD_CASES, CRITICAL_CASES, CLOSED_CASES, RECOVERED_CASES, DEATH_CASES, CASES_PER_MILLION,
                                DEATHS_PER_MILLION, TOTAL_TESTS, TESTS_PER_MILLION);
                        countryModelList.add(countryModel);
                    }

                    countryAdapter = new CountryAdapter(AffectedCountriesActivity.this, countryModelList);
                    listView.setAdapter(countryAdapter);

                } catch (JSONException e) {
                    progress.hide();
                    progress.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Alerter.create(AffectedCountriesActivity.this)
                            .setText("There was some error!")
                            .setTextAppearance(R.style.ErrorAlert)
                            .setBackgroundColorRes(R.color.errorColor)
                            .setIcon(R.drawable.error)
                            .setDuration(3000)
                            .enableSwipeToDismiss()
                            .enableIconPulse(true)
                            .enableVibration(true)
                            .disableOutsideTouch()
                            .enableProgress(true)
                            .setProgressColorInt(getResources().getColor(android.R.color.white))
                            .show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hide();
                progress.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Alerter.create(AffectedCountriesActivity.this)
                        .setText("There was some error!")
                        .setTextAppearance(R.style.ErrorAlert)
                        .setBackgroundColorRes(R.color.errorColor)
                        .setIcon(R.drawable.error)
                        .setDuration(3000)
                        .enableSwipeToDismiss()
                        .enableIconPulse(true)
                        .enableVibration(true)
                        .disableOutsideTouch()
                        .enableProgress(true)
                        .setProgressColorInt(getResources().getColor(android.R.color.white))
                        .show();
                return;
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(AffectedCountriesActivity.this);
        requestQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 123:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    countrySearch.setText(result.get(0));
                    countrySearch.clearFocus();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(AffectedCountriesActivity.this, "up-to-bottom");
    }
}
