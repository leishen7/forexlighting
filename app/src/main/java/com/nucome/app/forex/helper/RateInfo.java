package com.nucome.app.forex.helper;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by david on 4/29/2016.
 */
public class RateInfo implements Serializable {
    public String currency;
    public String rate;
    public Double dailyChange;
    public String rateChartURL;

    public RateInfo(String currency, String rate, Double dailyChange, String rateChartURL) {
        this.currency = currency;
        this.rate = rate;
        this.dailyChange = dailyChange;
        this.rateChartURL = rateChartURL;
    }
}
