package com.tonynikolov.stocks.model.response;

import com.google.gson.annotations.SerializedName;
import com.tonynikolov.stocks.model.StockEntry;
import java.util.TreeMap;

public class DailyStocksResponse extends BaseStocksResponse {
  @SerializedName("Time Series (Daily)")
  private TreeMap<String, StockEntry> entries;

  public TreeMap<String, StockEntry> getEntries() {
    return entries;
  }

  public void setEntries(
      TreeMap<String, StockEntry> entries) {
    this.entries = entries;
  }
}
