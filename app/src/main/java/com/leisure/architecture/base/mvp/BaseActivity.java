package com.leisure.architecture.base.mvp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;


import com.leisure.architecture.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity {
    /**
     * ButterKnife绑定对象
     */
    private Unbinder mBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局
        setContentView(getContentView());
        /*if (isImmersionBar()) {
            UltimateBar.Companion.with(this)
                    .statusDark(false)                  // 状态栏灰色模式(Android 6.0+)，默认 false
                    .statusDrawable(null)           // 状态栏背景，默认 null
                    .applyNavigation(true)              // 应用到导航栏，默认 false
                    .navigationDark(false)              // 导航栏灰色模式(Android 8.0+)，默认 false
                    .navigationDrawable(null)       // 导航栏背景，默认 null
                    .create()
                    .transparentBar();
        } else {
            // 设置状态栏颜色
            setStatusBar(getStatusBarColor());
        }*/
        // 初始化注解框架
        mBinder = ButterKnife.bind(this);
        // 初始化数据
        initData(savedInstanceState);
        // 初始化布局
        initView();
        // 初始化监听
        initListener();
    }

    protected boolean isImmersionBar() {
        return false;
    }

    /**
     * 绑定布局
     */
    protected abstract int getContentView();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化监听
     */
    protected void initListener() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinder.unbind();

    }

    /**
     * Android 6.0 以上设置状态栏颜色
     */
    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
            // 如果亮色，设置状态栏文字为黑色
            if (isLightColor(color)) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 判断颜色是不是亮色
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     */
    protected @ColorInt
    int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
