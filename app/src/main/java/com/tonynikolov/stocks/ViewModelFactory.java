package com.tonynikolov.stocks;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;
import com.tonynikolov.stocks.api.StocksApi;

public class ViewModelFactory implements Factory {

  private final StocksApi api;

  public ViewModelFactory(StocksApi api) {
    this.api = api;
  }

  public <T extends ViewModel> T create(Class<T> modelClass) {
    if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
      return (T) new MainActivityViewModel(api);
    }
    throw new IllegalArgumentException("Unknown ViewModel class");
  }
}
