package com.pminkov.tryguice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(Clock.class).to(ProdClock.class);
    }

    @Provides
    StatsReceiver provideStatsReceiver() {
      return new StatsReceiver();
    }
}
