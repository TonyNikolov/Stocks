package com.tonynikolov.stocks.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tonynikolov.stocks.model.MetaData;

public class BaseStocksResponse {
  @SerializedName("Meta Data")
  @Expose
  private MetaData metaData;
  @SerializedName("Note")
  private String note;

  public MetaData getMetaData() {
    return metaData;
  }

  public void setMetaData(MetaData metaData) {
    this.metaData = metaData;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
