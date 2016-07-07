package com.nucome.app.forex.helper;

import java.io.Serializable;

/**
 * Created by david on 4/29/2016.
 */
public class RateInfo implements Serializable {
    public String currency;
    public String rate;
    public String rateChartURL;

    public RateInfo(String currency, String rate, String rateChartURL) {
        this.currency = currency;
        this.rate = rate;
        this.rateChartURL = rateChartURL;
    }
}
