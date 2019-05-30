package com.leisure.architecture.base.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public abstract class BasePresenter<V extends BaseView> {
    /**
     * View对象 - 弱引用
     */
    private WeakReference<V> mWeakView;
    /**
     * 动态代理生成的View对象
     */
    private V mProxyView;

    /**
     * 关联V层和P层
     */
    @SuppressWarnings("unchecked")
    void attachView(@NonNull V v) {
        mWeakView = new WeakReference<>(v);
        MvpViewHandler viewHandler = new MvpViewHandler(mWeakView.get());
        mProxyView = (V) Proxy.newProxyInstance(v.getClass().getClassLoader(),
                v.getClass().getInterfaces(), viewHandler);
    }

    /**
     * P层和V层是否关联.
     */
    private boolean isViewAttached() {
        return mWeakView != null && mWeakView.get() != null;
    }

    /**
     * 断开V层和P层
     */
    void detachView() {
        if (isViewAttached()) {
            mWeakView.clear();
            mWeakView = null;
        }
    }

    /**
     * 获取View对象
     */
    public V getView() {
        return mProxyView;
    }

    /**
     * View管理对象
     */
    private class MvpViewHandler implements InvocationHandler {
        private BaseView mView;

        MvpViewHandler(BaseView view) {
            mView = view;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 如果V层没被销毁, 执行V层的方法.
            if (isViewAttached()) {
                return method.invoke(mView, args);
            }
            // P层不需要关注V层的返回值
            return null;
        }
    }

}
