package com.nucome.app.forex.helper;

/**
 * Created by david zhang on 5/6/2016.
 */
public class SignalInfo {
    public String tradeId;
  //  public String userId;
    public String side;
    public String symbol;
    public String entryPrice;
//    public String minPrice;
//    public String maxPrice;
    public String expireDate;
    public String createDate;
    public String lastPrice;
  //  public String maxGain;
  //  public String maxLose;
  //  public String avgGain;
  //  public String avgLose;
    public String profit;
    public String userNickName;
 //   public String gainPercent;
 //   public String score;
    public String chartSrc;
    public String reason;

    public SignalInfo(String tradeId,  String side, String symbol, String entryPrice,  String expireDate, String createDate, String lastPrice, String profit, String userNickName,  String reason, String chartSrc) {
        this.tradeId = tradeId;
        this.side = side;
        this.symbol = symbol;
        this.entryPrice = entryPrice;

        this.expireDate = expireDate;
        this.createDate = createDate;
        this.lastPrice = lastPrice;
        this.profit = profit;
        this.userNickName = userNickName;
        this.chartSrc = chartSrc;
        this.reason=reason;
    }
}
