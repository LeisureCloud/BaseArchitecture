package com.leisure.architecture.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    /**
     * ButterKnife绑定对象
     */
    private Unbinder mBinder;
    /**
     * 逻辑处理类
     */
    protected P mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        // 初始化注解框架
        mBinder = ButterKnife.bind(this);
        // 初始化桥梁类
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        // 初始化数据
        initData(savedInstanceState);
        // 初始化布局
        initView();
        // 初始化监听
        initListener();
    }

    /**
     * 绑定布局
     */
    protected abstract int getContentView();

    /**
     * 初始化逻辑处理类
     */
    protected abstract P createPresenter();

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
    public void onShowDialog() {
    }

    @Override
    public void onDismissDialog() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinder.unbind();
        mPresenter.detachView();
    }
}
