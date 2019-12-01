package com.tonynikolov.stocks.api;

import com.tonynikolov.stocks.model.response.DailyStocksResponse;
import com.tonynikolov.stocks.model.response.MonthlyStocksResponse;
import com.tonynikolov.stocks.model.response.WeeklyStocksResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StocksApi {
  @GET("query?function=TIME_SERIES_DAILY&symbol=MSFT")
  Call<DailyStocksResponse> getDailyStocks();

  @GET("query?function=TIME_SERIES_WEEKLY&symbol=MSFT")
  Call<WeeklyStocksResponse> getWeeklyStocks();

  @GET("query?function=TIME_SERIES_MONTHLY&symbol=MSFT")
  Call<MonthlyStocksResponse> getMonthlyStocks();
}
