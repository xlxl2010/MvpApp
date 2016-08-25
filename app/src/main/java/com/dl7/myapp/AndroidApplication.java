package com.dl7.myapp;

import android.app.Application;
import android.content.Context;

import com.dl7.myapp.api.RetrofitService;
import com.dl7.myapp.injector.components.ApplicationComponent;
import com.dl7.myapp.injector.components.DaggerApplicationComponent;
import com.dl7.myapp.injector.modules.ApplicationModule;
import com.dl7.myapp.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by long on 2016/8/19.
 * Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent mAppComponent;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        _initInjector();
        _initConfig();
    }

    /**
     * 初始化注射器
     */
    private void _initInjector() {
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }

    public static Context getContext() {
        return sContext;
    }

    /**
     * 初始化配置
     */
    private void _initConfig() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
            Logger.init("LogTAG");
        }
        RetrofitService.init();
        ToastUtils.init(this);
    }
}
