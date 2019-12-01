package com.tonynikolov.stocks;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.tonynikolov.stocks.api.StocksApi;
import com.tonynikolov.stocks.api.StocksInterceptor;
import com.tonynikolov.stocks.model.StockEntry;
import com.tonynikolov.stocks.model.response.DailyStocksResponse;
import com.tonynikolov.stocks.model.response.MonthlyStocksResponse;
import com.tonynikolov.stocks.model.response.WeeklyStocksResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

  private StocksApi api;
  private MainActivityViewModel viewModel;

  private CandleStickChart chart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initApi();
    initViewModel();
    initViews();
    setupChart();
    initObservers();

    getDailyStocks();
  }

  private void initViewModel() {
    ViewModelFactory factory =
        new ViewModelFactory(api);

    viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
  }

  private void initViews() {
    chart = findViewById(R.id.candle_stick_chart);
    View btnDaily = findViewById(R.id.btn_daily);
    View btnWeekly = findViewById(R.id.btn_weekly);
    View btnMonthly = findViewById(R.id.btn_monthly);

    btnDaily.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        getDailyStocks();
      }
    });
    btnWeekly.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        getWeeklyStocks();
      }
    });
    btnMonthly.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        getMonthlyStocks();
      }
    });
  }

  private void initApi() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.level(Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new StocksInterceptor())
        .addInterceptor(loggingInterceptor)
        .build();

    api = new Retrofit.Builder()
        .baseUrl("https://www.alphavantage.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build().create(StocksApi.class);
  }

  private void setupChart() {
    chart.setDrawBorders(true);
    chart.setBorderColor(Color.BLACK);

    YAxis rightAxis = chart.getAxisRight();
    rightAxis.setDrawGridLines(true);
    rightAxis.setTextColor(Color.BLACK);

    YAxis leftAxis = chart.getAxisLeft();
    leftAxis.setDrawGridLines(true);
    leftAxis.setDrawLabels(true);

    XAxis xAxis = chart.getXAxis();
    xAxis.setDrawGridLines(true);
    xAxis.setDrawLabels(true);
    xAxis.setGranularity(1);
    xAxis.setGranularityEnabled(true);
    xAxis.setAvoidFirstLastClipping(true);

    Legend l = chart.getLegend();
    l.setEnabled(true);
  }

  private void initObservers() {
    viewModel.getDailyStocksLiveData().observe(this, new Observer<Response<DailyStocksResponse>>() {
      @Override
      public void onChanged(Response<DailyStocksResponse> response) {
        final DailyStocksResponse body = response.body();
        if (response.isSuccessful() && body != null) {
          if (TextUtils.isEmpty(body.getNote())) {
            updateChart(body.getEntries());
          } else {
            Toast.makeText(MainActivity.this, body.getNote(), Toast.LENGTH_LONG).show();
          }
        }
      }
    });

    viewModel.getWeeklyStocksLiveData().observe(this,
        new Observer<Response<WeeklyStocksResponse>>() {
          @Override
          public void onChanged(Response<WeeklyStocksResponse> response) {
            final WeeklyStocksResponse body = response.body();
            if (response.isSuccessful() && body != null) {
              if (TextUtils.isEmpty(body.getNote())) {
                updateChart(body.getEntries());
              } else {
                Toast.makeText(MainActivity.this, body.getNote(), Toast.LENGTH_LONG).show();
              }
            }
          }
        });

    viewModel.getMonthlyStocksLiveData().observe(this,
        new Observer<Response<MonthlyStocksResponse>>() {
          @Override
          public void onChanged(Response<MonthlyStocksResponse> response) {
            final MonthlyStocksResponse body = response.body();
            if (response.isSuccessful() && body != null) {
              if (TextUtils.isEmpty(body.getNote())) {
                updateChart(body.getEntries());
              } else {
                Toast.makeText(MainActivity.this, body.getNote(), Toast.LENGTH_LONG).show();
              }
            }
          }
        });
  }

  private void getDailyStocks() {
    viewModel.getDailyStocks();
  }

  private void getWeeklyStocks() {
    viewModel.getWeeklyStocks();
  }

  private void getMonthlyStocks() {
    viewModel.getMonthlyStocks();
  }

  private void updateChart(TreeMap<String, StockEntry> stockEntries) {
    if (stockEntries.isEmpty()) {
      Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
      return;
    }
    List<CandleEntry> candleEntries = new ArrayList<>();
    List<String> dates = new ArrayList<>();
    int i = 0;
    for (Map.Entry<String, StockEntry> entry : stockEntries.entrySet()) {
      dates.add(entry.getKey());
      StockEntry stockEntry = entry.getValue();

      candleEntries.add(new CandleEntry((float) i++, stockEntry.getHigh(), stockEntry.getLow(),
          stockEntry.getOpen(), stockEntry.getClose()));
    }

    XAxis xAxis = chart.getXAxis();
    IndexAxisValueFormatter indexAxisValueFormatter = new IndexAxisValueFormatter(dates);
    xAxis.setValueFormatter(indexAxisValueFormatter);
    xAxis.setLabelCount(4);

    Description description = new Description();
    description.setText("");

    CandleDataSet set = new CandleDataSet(candleEntries, "Stock Prices");
    set.setColor(Color.rgb(80, 80, 80));
    set.setShadowColor(Color.GRAY);
    set.setShadowWidth(2);
    set.setDecreasingColor(Color.RED);
    set.setDecreasingPaintStyle(Paint.Style.FILL);
    set.setIncreasingColor(Color.GREEN);
    set.setIncreasingPaintStyle(Paint.Style.FILL);
    set.setNeutralColor(Color.LTGRAY);

    CandleData data = new CandleData(set);

    chart.setData(data);
    chart.setDescription(description);
    chart.notifyDataSetChanged();
    chart.invalidate();
  }
}
