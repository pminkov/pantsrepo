package com.pminkov.tryguice;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(Clock.class).to(ProdClock.class);
    }

    @Provides
    @Singleton
    StatsReceiver provideStatsReceiver() {
      return new StatsReceiver("prefix");
    }
}
