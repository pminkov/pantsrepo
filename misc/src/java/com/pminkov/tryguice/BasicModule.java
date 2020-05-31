package com.pminkov.tryguice;

import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(Clock.class).to(ProdClock.class);
    }
}
