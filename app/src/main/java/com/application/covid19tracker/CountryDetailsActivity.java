package com.application.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.covid19tracker.Client.Internet;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.util.Calendar;

import maes.tech.intentanim.CustomIntent;

public class CountryDetailsActivity extends AppCompatActivity {

    private int POSITION_COUNTRY;

    ImageView back, logo;
    TextView heading, date, newCases, totalCases, newDeaths, totalDeaths, activeCases, mildCases, criticalCases,
            closedCases, recoveredCases, deathCases, casesPerMillion, deathsPerMillion, totalTests, testsPerMillion;
    PieChart pieChart;
    AVLoadingIndicatorView progress;
    NestedScrollView nestedScrollView;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        Intent intent = getIntent();
        POSITION_COUNTRY = intent.getIntExtra("position", 0);

        back = findViewById(R.id.arrow_back);
        logo = findViewById(R.id.logo);
        heading = findViewById(R.id.country_name);
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
        pieChart = findViewById(R.id.pie_chart);
        progress = findViewById(R.id.progress);
        nestedScrollView = findViewById(R.id.nested_scroll_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        YoYo.with(Techniques.Flash)
                .duration(1000)
                .repeat(1)
                .playOn(logo);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDate);

        int color1 = getResources().getColor(R.color.confirmed);
        int color2 = getResources().getColor(R.color.active);
        int color3 = getResources().getColor(R.color.recovered);
        int color4 = getResources().getColor(R.color.deaths);

        swipeRefreshLayout.setColorSchemeColors(color1, color2, color3, color4);

        YoYo.with(Techniques.Flash)
                .duration(700)
                .repeat(1)
                .playOn(totalCases);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (Internet.isConnectedToInternet(CountryDetailsActivity.this)) {
                    progress.hide();
                    progress.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    progress.show();
                    progress.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Alerter.create(CountryDetailsActivity.this)
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
                if (Internet.isConnectedToInternet(CountryDetailsActivity.this)) {
                    progress.hide();
                    progress.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    progress.show();
                    progress.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Alerter.create(CountryDetailsActivity.this)
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

        String COUNTRY_NAME = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getCountry();
        String NEW_CASES = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getNewCases();
        String TOTAL_CASES = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getTotalCases();
        String NEW_DEATHS = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getNewDeaths();
        String TOTAL_DEATHS = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getTotalDeaths();
        String ACTIVE_CASES = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getActiveCases();
        String MILD_CASES = String.valueOf((Integer.parseInt(AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getActiveCases()))
                - (Integer.parseInt(AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getCriticalCases())));
        String CRITICAL_CASES = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getCriticalCases();
        String CLOSED_CASES = String.valueOf((Integer.parseInt(AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getRecoveredCases()))
                + (Integer.parseInt(AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getDeathCases())));
        String RECOVERED_CASES = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getRecoveredCases();
        String DEATH_CASES = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getDeathCases();
        String CASES_PER_MILLION = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getCasesPerMillion();
        String DEATHS_PER_MILLION = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getDeathsPerMillion();
        String TOTAL_TESTS = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getTotalTests();
        String TESTS_PER_MILLION = AffectedCountriesActivity.countryModelList.get(POSITION_COUNTRY).getTestsPerMillion();

        double deathRate = ((Double.parseDouble(TOTAL_DEATHS) / Double.parseDouble(TOTAL_CASES)) * 100);
        double recoveryRate = ((Double.parseDouble(RECOVERED_CASES) / Double.parseDouble(TOTAL_CASES)) * 100);

        double deathPercent = (Math.round(deathRate * 10) / 10.0);
        double recoveryPercent = (Math.round(recoveryRate * 10) / 10.0);

        pieChart.addPieSlice(new PieModel("Confirmed", Integer.parseInt(TOTAL_CASES), getResources().getColor(R.color.confirmed)));
        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(ACTIVE_CASES), getResources().getColor(R.color.active)));
        pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(RECOVERED_CASES), getResources().getColor(R.color.recovered)));
        pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(TOTAL_DEATHS), getResources().getColor(R.color.deaths)));

        pieChart.startAnimation();

        heading.setText(COUNTRY_NAME);
        newCases.setText("[+" + NEW_CASES + "]");
        totalCases.setText(TOTAL_CASES);
        newDeaths.setText("[+" + NEW_DEATHS + "]");
        totalDeaths.setText(TOTAL_DEATHS + " (" + deathPercent + "%)");
        activeCases.setText(ACTIVE_CASES);
        mildCases.setText(MILD_CASES);
        criticalCases.setText(CRITICAL_CASES);
        closedCases.setText(CLOSED_CASES);
        recoveredCases.setText(RECOVERED_CASES + " (" + recoveryPercent + "%)");
        deathCases.setText(DEATH_CASES + " (" + deathPercent + "%)");
        casesPerMillion.setText("Cases per 1 million : " + CASES_PER_MILLION);
        deathsPerMillion.setText("Deaths per 1 million : " + DEATHS_PER_MILLION);
        totalTests.setText("Total Tests : " + TOTAL_TESTS);
        testsPerMillion.setText("Tests per 1 million : " + TESTS_PER_MILLION);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(CountryDetailsActivity.this, "up-to-bottom");
    }
}
