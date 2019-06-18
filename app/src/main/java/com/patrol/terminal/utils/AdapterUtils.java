package com.patrol.terminal.utils;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;

public class AdapterUtils {
    //设置图标文字
    public static void setIconText(TextView icon, String dep_name){
        switch (dep_name) {
            case "西固运维班":
                icon.setText("西固");
                icon.setBackgroundResource(R.drawable.plan_week_bg);
                break;
            case "兰州运维班":
                icon.setText("兰州");
                icon.setBackgroundResource(R.drawable.plan_qing_bg);
                break;
            case "新城运维班":
                icon.setText("新城");
                icon.setBackgroundResource(R.drawable.plan_yellow_bg);
                break;
            case "盐海运维班":
                icon.setText("盐海");
                icon.setBackgroundResource(R.drawable.plan_mon_bg);
                break;
            case "开永运维班":
                icon.setText("开永");
                icon.setBackgroundResource(R.drawable.plan_date_bg);
                break;
            case "和华运维班":
                icon.setText("和华");
                icon.setBackgroundResource(R.drawable.plan_red_bg);
                break;
        }
    }

    //设置内容颜色
    public static void setText(TextView textView, String text) {
        if (text.contains("定期巡视")) {
            text = text.replace("定期巡视", "<font color='#007bff'>定期巡视</font>");
        }
        if (text.contains("故障巡视")) {
            text = text.replace("故障巡视", "<font color='#6610f2'>故障巡视</font>");
        }
        if (text.contains("接地电阻检测")) {
            text = text.replace("接地电阻检测", "<font color='#6f42c1'>接地电阻检测</font>");
        }
        if (text.contains("特殊性巡视")) {
            text = text.replace("特殊性巡视", "<font color='#e83e8c'>特殊性巡视</font>");
        }
        if (text.contains("红外测温检测")) {
            text = text.replace("红外测温检测", "<font color='#d4237a'>红外测温检测</font>");
        }
        if (text.contains("杆塔倾斜检测")) {
            text = text.replace("杆塔倾斜检测", "<font color='#fd7e14'>杆塔倾斜检测</font>");
        }
        if (text.contains("保电巡视")) {
            text = text.replace("保电巡视", "<font color='#ffc107'>保电巡视</font>");
        }
        if (text.contains("缺陷消除")) {
            text = text.replace("缺陷消除", "<font color='#28a745'>缺陷消除</font>");
        }
        if (text.contains("隐患处理")) {
            text = text.replace("隐患处理", "<font color='#20c997'>隐患处理</font>");
        }
        if (text.contains("绝缘子零值检测")) {
            text = text.replace("绝缘子零值检测", "<font color='#17a2b8'>绝缘子零值检测</font>");
        }
        if (text.contains("监督性巡视")) {
            text = text.replace("监督性巡视", "<font color='#28c7a7'>监督性巡视</font>");
        }
        if (text.contains("安全监督")) {
            text = text.replace("安全监督", "<font color='#1aa2b8'>安全监督</font>");
        }
        if (text.contains("验收")) {
            text = text.replace("验收", "<font color='#0070ff'>验收</font>");
        }
        textView.setText(Html.fromHtml(text));
    }

    //设置是否可以修改
    public static void setStatus(ImageView edit,String audit_status){
        switch (audit_status) {
            case "0":
            case "4":
                edit.setVisibility(View.VISIBLE);
                break;
            default:
                edit.setVisibility(View.GONE);
                break;
        }
    }
}