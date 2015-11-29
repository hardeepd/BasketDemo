package uk.co.hd_tech.basketdemo.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Fixer response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerResponse {

    private String base;
    private String date;
    private Rate rates;

    private List<FXRate> fxRates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Rate getRates() {
        return rates;
    }

    public void setRates(Rate rates) {
        this.rates = rates;
    }

    public List<FXRate> getFxRates() {
        return fxRates;
    }

    public void setFxRates(List<FXRate> fxRates) {
        this.fxRates = fxRates;
    }
}
