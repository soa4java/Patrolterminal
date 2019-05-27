package com.patrol.terminal.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.patrol.terminal.R;

public class PopMenmuDialog extends PopupWindow {
    private View mainView;
    private TextView layoutShare, layoutCopy,all;

    public PopMenmuDialog(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2){
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.popmenu, null);
        //全部
        all = ((TextView)mainView.findViewById(R.id.all));
        //定巡定检
        layoutShare = ((TextView)mainView.findViewById(R.id.popmenmu1));
        //保电计划
        layoutCopy = (TextView)mainView.findViewById(R.id.popmenmu2);
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            layoutShare.setOnClickListener(paramOnClickListener);
            layoutCopy.setOnClickListener(paramOnClickListener);
            all.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }
}