package com.ruslan.pocketdoc.di;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    Picasso providePicasso() {
        return Picasso.get();
    }
}
