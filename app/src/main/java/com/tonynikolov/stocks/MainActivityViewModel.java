package com.tonynikolov.stocks;

import android.text.TextUtils;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.tonynikolov.stocks.api.StocksApi;
import com.tonynikolov.stocks.model.response.DailyStocksResponse;
import com.tonynikolov.stocks.model.response.MonthlyStocksResponse;
import com.tonynikolov.stocks.model.response.WeeklyStocksResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

  private final StocksApi api;
  private MutableLiveData<Response<DailyStocksResponse>> dailyStocksLiveData = new MutableLiveData<>();
  private MutableLiveData<Response<WeeklyStocksResponse>> weeklyStocksLiveData = new MutableLiveData<>();
  private MutableLiveData<Response<MonthlyStocksResponse>> monthlyStocksLiveData = new MutableLiveData<>();

  public MainActivityViewModel(StocksApi api) {
    this.api = api;
  }

  public MutableLiveData<Response<DailyStocksResponse>> getDailyStocksLiveData() {
    return dailyStocksLiveData;
  }

  public MutableLiveData<Response<WeeklyStocksResponse>> getWeeklyStocksLiveData() {
    return weeklyStocksLiveData;
  }

  public MutableLiveData<Response<MonthlyStocksResponse>> getMonthlyStocksLiveData() {
    return monthlyStocksLiveData;
  }

  public void getDailyStocks() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        api.getDailyStocks().enqueue(new Callback<DailyStocksResponse>() {
          @Override
          public void onResponse(Call<DailyStocksResponse> call,
              final Response<DailyStocksResponse> response) {
            dailyStocksLiveData.postValue(response);
          }

          @Override
          public void onFailure(Call<DailyStocksResponse> call, Throwable t) {

          }
        });
      }
    }).start();
  }

  public void getWeeklyStocks() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        api.getWeeklyStocks().enqueue(new Callback<WeeklyStocksResponse>() {
          @Override
          public void onResponse(Call<WeeklyStocksResponse> call,
              final Response<WeeklyStocksResponse> response) {
            weeklyStocksLiveData.postValue(response);
          }

          @Override
          public void onFailure(Call<WeeklyStocksResponse> call, Throwable t) {

          }
        });
      }
    }).start();
  }

  public void getMonthlyStocks() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        api.getMonthlyStocks().enqueue(new Callback<MonthlyStocksResponse>() {
          @Override
          public void onResponse(Call<MonthlyStocksResponse> call,
              final Response<MonthlyStocksResponse> response) {
            monthlyStocksLiveData.postValue(response);
          }

          @Override
          public void onFailure(Call<MonthlyStocksResponse> call, Throwable t) {

          }
        });
      }
    }).start();
  }
}
