package com.nucome.app.forex.helper;

import java.io.Serializable;

/**
 * Created by david on 4/29/2016.
 */
public class RecommendationInfo implements Serializable {
    public String symbol;
    public String side;
    public String effectiveDays;
    public String token;

    public RecommendationInfo(String symbol, String title, String effectiveDays, String token) {
        this.symbol = symbol;
        this.side = title;
        this.effectiveDays = effectiveDays;
        this.token = token;
    }
}
