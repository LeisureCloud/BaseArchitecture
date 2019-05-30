package com.leisure.architecture.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;


public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (getDialog() == null) {
            return;
        }
        Window window = getDialog().getWindow();
        super.onActivityCreated(savedInstanceState);
        if (window != null) {
            // 背景色透明
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            // 居中
            attributes.gravity = Gravity.CENTER;
            // 布满全局
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(attributes);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 初始化View
        View contentView = inflater.inflate(bindLayout(), container, false);
        ButterKnife.bind(this, contentView);
        // 外部点击消失
        setCancelable(cancelable());
        // 初始化布局
        initView(contentView);
        return contentView;
    }

    /**
     * 显示对话框
     *
     * @param manager 碎片管理者对象
     */
    public void showDialog(FragmentManager manager) {
        showDialog(manager, getClass().getSimpleName());
    }

    /**
     * 显示对话框
     *
     * @param manager 碎片管理者对象
     * @param tag     标识
     */
    public void showDialog(FragmentManager manager, String tag) {
        show(manager, tag);
    }

    protected abstract void initView(View parent);

    protected abstract boolean cancelable();

    protected abstract int bindLayout();

}
