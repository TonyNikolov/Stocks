package com.tonynikolov.stocks.model.response;

import com.google.gson.annotations.SerializedName;
import com.tonynikolov.stocks.model.StockEntry;
import java.util.TreeMap;

public class WeeklyStocksResponse extends BaseStocksResponse {
  @SerializedName("Weekly Time Series")
  private TreeMap<String, StockEntry> entries;

  public TreeMap<String, StockEntry> getEntries() {
    return entries;
  }

  public void setEntries(
      TreeMap<String, StockEntry> entries) {
    this.entries = entries;
  }
}
