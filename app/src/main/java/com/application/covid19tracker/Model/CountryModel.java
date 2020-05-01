package com.application.covid19tracker.Model;

public class CountryModel {
    String country, flag, continent, totalCases, newCases, totalDeaths, newDeaths, activeCases, mildCases, criticalCases,
            closedCases, recoveredCases, deathCases, casesPerMillion, deathsPerMillion, totalTests, testsPerMillion;

    public CountryModel() {
    }

    public CountryModel(String country, String flag, String continent, String totalCases, String newCases,
                        String totalDeaths, String newDeaths, String activeCases, String mildCases,
                        String criticalCases, String closedCases, String recoveredCases, String deathCases,
                        String casesPerMillion, String deathsPerMillion, String totalTests, String testsPerMillion) {
        this.country = country;
        this.flag = flag;
        this.continent = continent;
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.activeCases = activeCases;
        this.mildCases = mildCases;
        this.criticalCases = criticalCases;
        this.closedCases = closedCases;
        this.recoveredCases = recoveredCases;
        this.deathCases = deathCases;
        this.casesPerMillion = casesPerMillion;
        this.deathsPerMillion = deathsPerMillion;
        this.totalTests = totalTests;
        this.testsPerMillion = testsPerMillion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        this.totalCases = totalCases;
    }

    public String getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(String activeCases) {
        this.activeCases = activeCases;
    }

    public String getMildCases() {
        return mildCases;
    }

    public void setMildCases(String mildCases) {
        this.mildCases = mildCases;
    }

    public String getCriticalCases() {
        return criticalCases;
    }

    public void setCriticalCases(String criticalCases) {
        this.criticalCases = criticalCases;
    }

    public String getClosedCases() {
        return closedCases;
    }

    public void setClosedCases(String closedCases) {
        this.closedCases = closedCases;
    }

    public String getRecoveredCases() {
        return recoveredCases;
    }

    public void setRecoveredCases(String recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public String getDeathCases() {
        return deathCases;
    }

    public void setDeathCases(String deathCases) {
        this.deathCases = deathCases;
    }

    public String getCasesPerMillion() {
        return casesPerMillion;
    }

    public void setCasesPerMillion(String casesPerMillion) {
        this.casesPerMillion = casesPerMillion;
    }

    public String getDeathsPerMillion() {
        return deathsPerMillion;
    }

    public void setDeathsPerMillion(String deathsPerMillion) {
        this.deathsPerMillion = deathsPerMillion;
    }

    public String getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(String totalTests) {
        this.totalTests = totalTests;
    }

    public String getTestsPerMillion() {
        return testsPerMillion;
    }

    public void setTestsPerMillion(String testsPerMillion) {
        this.testsPerMillion = testsPerMillion;
    }
}
