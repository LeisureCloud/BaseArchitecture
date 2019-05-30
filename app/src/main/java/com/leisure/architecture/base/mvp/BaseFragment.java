package com.leisure.architecture.base.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    /**
     * 逻辑处理类
     */
    protected P mPresenter;
    /**
     * 当前 Activity 渲染的视图 View
     */
    protected View contentView;
    /**
     * 是否进行了初始化
     */
    private boolean isInitView = false;
    /**
     * 是否对用户可见
     */
    private boolean isVisible = false;
    /**
     * 所连接的Activity对象
     */
    protected Activity mContext;

    /**
     * ButterKnife绑定对象
     */
    private Unbinder mBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = ((Activity) context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        contentView = inflater.inflate(getContentView(), container, false);
        mBinder = ButterKnife.bind(this, contentView);
        isInitView = true;
        initView();
        // 初始化加载框
        return contentView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attachView(this);
        initData();
        initListener();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if (isVisibleToUser) {
            isVisible = true;
            isCanLoadData();
        } else {
            isVisible = false;
            onDismissDialog();
        }
    }

    /**
     * 判断是否要加载数据
     */
    private void isCanLoadData() {
        // 所以条件是view初始化完成并且对用户可见
        if (isInitView && isVisible) {
            onLazyLoad();
            // 防止重复加载数据
            // isInitView = false;
            isVisible = false;
        }
    }

    /**
     * 绑定布局
     */
    protected abstract int getContentView();

    /**
     * 创建逻辑处理类
     */
    protected abstract P initPresenter();

    /**
     * 加载要显示的数据
     */
    protected abstract void onLazyLoad();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

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
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onDestroyView() {
        onDismissDialog();
        if (contentView != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }
        super.onDestroyView();
        mPresenter.detachView();
        mBinder.unbind();
    }

}
