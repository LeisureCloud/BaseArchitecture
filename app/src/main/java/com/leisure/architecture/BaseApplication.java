package com.leisure.architecture;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.Utils;


public class BaseApplication extends Application {


    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化工具库
        Utils.init(this);
        // 主进程初始化数据
        if (ProcessUtils.isMainProcess()) {
            // 初始化数据库相关
            initDataBase();
            // 初始化未知异常拦截器
          /*  CrashUtils.init(UnifyConstant.MEDIA_DIR, new CrashUtils.OnCrashListener() {
                @Override
                public void onCrash(String crashInfo, Throwable e) {
                    ToastUtils.showLong("程序异常,即将退出...");
                    ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                    SystemClock.sleep(1500);
                    ActivityUtils.finishAllActivities();
                    System.exit(1);
                }
            });*/

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 初始化数据库
     */
    private void initDataBase() {
        // 初始化数据库
        //FlowManager.init(this);
        // 添加日志
        // FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }
}