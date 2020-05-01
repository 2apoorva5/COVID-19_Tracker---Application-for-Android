package com.application.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application.covid19tracker.Client.Internet;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

import maes.tech.intentanim.CustomIntent;

public class StatisticsActivity extends AppCompatActivity {

    ImageView logo;
    BottomNavigationView bottomNavigationView;

    TextView heading, date, newCases, totalCases, newDeaths, totalDeaths, activeCases, mildCases, criticalCases,
            closedCases, recoveredCases, deathCases, casesPerMillion, deathsPerMillion, totalTests, testsPerMillion, countriesAffected;

    SwipeRefreshLayout swipeRefreshLayout;
    ConstraintLayout trackIndia, trackCountries;
    NestedScrollView nestedScrollView;

    PieChart pieChart;

    AVLoadingIndicatorView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        logo = findViewById(R.id.logo);
        heading = findViewById(R.id.heading);
        date = findViewById(R.id.date);
        newCases = findViewById(R.id.new_cases);
        totalCases = findViewById(R.id.total_cases);
        newDeaths = findViewById(R.id.new_deaths);
        totalDeaths = findViewById(R.id.total_deaths);
        activeCases = findViewById(R.id.active_cases);
        mildCases = findViewById(R.id.mild_cases);
        criticalCases = findViewById(R.id.critical_cases);
        closedCases = findViewById(R.id.closed_cases);
        recoveredCases = findViewById(R.id.recovered_cases);
        deathCases = findViewById(R.id.death_cases);
        casesPerMillion = findViewById(R.id.cases_per_million);
        deathsPerMillion = findViewById(R.id.deaths_per_million);
        totalTests = findViewById(R.id.total_tests);
        testsPerMillion = findViewById(R.id.tests_per_million);
        countriesAffected = findViewById(R.id.affected_countries);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        trackIndia = findViewById(R.id.track_india);
        trackCountries = findViewById(R.id.track_countries);
        pieChart = findViewById(R.id.pie_chart);
        progress = findViewById(R.id.progress);
        nestedScrollView = findViewById(R.id.nested_scroll_view);

        YoYo.with(Techniques.Flash)
                .duration(1000)
                .repeat(1)
                .playOn(logo);

        String text = "COVID-19GLOBAL";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
        spannableString.setSpan(foregroundColorSpan, 8, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        heading.setText(spannableString);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDate);

        int color1 = getResources().getColor(R.color.confirmed);
        int color2 = getResources().getColor(R.color.active);
        int color3 = getResources().getColor(R.color.recovered);
        int color4 = getResources().getColor(R.color.deaths);

        swipeRefreshLayout.setColorSchemeColors(color1, color2, color3, color4);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (Internet.isConnectedToInternet(StatisticsActivity.this)) {
                    pieChart.clearChart();
                    fetchData();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    progress.show();
                    progress.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Alerter.create(StatisticsActivity.this)
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
                if (Internet.isConnectedToInternet(StatisticsActivity.this)) {
                    pieChart.clearChart();
                    fetchData();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    progress.show();
                    progress.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Alerter.create(StatisticsActivity.this)
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

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_statistics:
                        break;

                    case R.id.menu_overview:
                        startActivity(new Intent(StatisticsActivity.this, OverviewActivity.class));
                        CustomIntent.customType(StatisticsActivity.this, "fadein-to-fadeout");
                        break;

                    case R.id.menu_symptoms:
                        startActivity(new Intent(StatisticsActivity.this, SymptomsActivity.class));
                        CustomIntent.customType(StatisticsActivity.this, "fadein-to-fadeout");
                        break;

                    case R.id.menu_prevention:
                        startActivity(new Intent(StatisticsActivity.this, PreventionActivity.class));
                        CustomIntent.customType(StatisticsActivity.this, "fadein-to-fadeout");
                        break;
                }
                return false;
            }
        });
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/all/";
        progress.show();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progress.hide();
                    progress.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    JSONObject jsonObject = new JSONObject(response.toString());

                    pieChart.addPieSlice(new PieModel("Confirmed", Integer.parseInt(jsonObject.getString("cases")), getResources().getColor(R.color.confirmed)));
                    pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(jsonObject.getString("active")), getResources().getColor(R.color.active)));
                    pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(jsonObject.getString("recovered")), getResources().getColor(R.color.recovered)));
                    pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(jsonObject.getString("deaths")), getResources().getColor(R.color.deaths)));

                    pieChart.startAnimation();

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
                    String COUNTRIES_AFFECTED = jsonObject.getString("affectedCountries");

                    double deathRate = ((Double.parseDouble(TOTAL_DEATHS) / Double.parseDouble(TOTAL_CASES)) * 100);
                    double recoveryRate = ((Double.parseDouble(RECOVERED_CASES) / Double.parseDouble(TOTAL_CASES)) * 100);

                    double deathPercent = (Math.round(deathRate * 10) / 10.0);
                    double recoveryPercent = (Math.round(recoveryRate * 10) / 10.0);

                    newCases.setText(String.format("[+%s]", NEW_CASES));
                    totalCases.setText(TOTAL_CASES);
                    newDeaths.setText(String.format("[+%s]", NEW_DEATHS));
                    totalDeaths.setText(String.format("%s (%s%%)", TOTAL_DEATHS, String.valueOf(deathPercent)));
                    activeCases.setText(ACTIVE_CASES);
                    mildCases.setText(MILD_CASES);
                    criticalCases.setText(CRITICAL_CASES);
                    closedCases.setText(CLOSED_CASES);
                    recoveredCases.setText(String.format("%s (%s%%)", RECOVERED_CASES, String.valueOf(recoveryPercent)));
                    deathCases.setText(String.format("%s (%s%%)", DEATH_CASES, String.valueOf(deathPercent)));
                    casesPerMillion.setText(String.format("Cases per 1 million : %s", CASES_PER_MILLION));
                    deathsPerMillion.setText(String.format("Deaths per 1 million : %s", DEATHS_PER_MILLION));
                    totalTests.setText(String.format("Total tests : %s", TOTAL_TESTS));
                    testsPerMillion.setText(String.format("Tests per 1 million : %s", TESTS_PER_MILLION));
                    countriesAffected.setText(String.format("Countries Affected : %s", COUNTRIES_AFFECTED));

                    YoYo.with(Techniques.Flash)
                            .duration(700)
                            .repeat(1)
                            .playOn(totalCases);

                    trackIndia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(StatisticsActivity.this, IndiaTrackerActivity.class));
                            CustomIntent.customType(StatisticsActivity.this, "bottom-to-up");
                        }
                    });

                    trackCountries.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(StatisticsActivity.this, AffectedCountriesActivity.class));
                            CustomIntent.customType(StatisticsActivity.this, "bottom-to-up");
                        }
                    });

                } catch (JSONException e) {
                    progress.hide();
                    progress.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Alerter.create(StatisticsActivity.this)
                            .setText("There was some error!")
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hide();
                progress.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Alerter.create(StatisticsActivity.this)
                        .setText("There was some error!")
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
        });

        RequestQueue requestQueue = Volley.newRequestQueue(StatisticsActivity.this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
