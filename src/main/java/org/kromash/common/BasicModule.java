package org.kromash.common;

import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(InputReader.class).toInstance(new InputReader());
    }
}
