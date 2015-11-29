package uk.co.hd_tech.basketdemo.data.model;

import java.util.Currency;

/**
 * Processed rate data
 */
public class FXRate {

    private Currency currency;
    private double rate;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
