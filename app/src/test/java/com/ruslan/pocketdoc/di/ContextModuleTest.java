package com.ruslan.pocketdoc.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModuleTest {

    private Context mContext;

    public ContextModuleTest(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContextTest() {
        return mContext;
    }
}
