package com.patrol.terminal.adapter;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class DepOfGroupAdapter extends BaseQuickAdapter<GroupTaskBean, BaseViewHolder> {

    public DepOfGroupAdapter(int layoutResId, @Nullable List<GroupTaskBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, GroupTaskBean item) {
        //图标
        TextView icon = viewHolder.getView(R.id.item_task_date_tv);
        AdapterUtils.setIconText(icon, item.getDep_name());
            if (item.getWork_user_name() == null) {
                viewHolder.setText(R.id.item_task_personal, "任务执行人：未指定");
            } else {
                viewHolder.setText(R.id.item_task_personal, "任务执行人：" + item.getWork_user_name());
            }
        if (item.getDuty_user_name() == null) {
            viewHolder.setText(R.id.item_task_resource, "负责人：未指定");
        } else {
            viewHolder.setText(R.id.item_task_resource, "负责人：" + item.getDuty_user_name());
        }
        viewHolder.setVisible(R.id.item_task_resource,true);
        viewHolder.setText(R.id.item_task_name, item.getLine_name() + item.getName() + "任务");

        TextView type = viewHolder.getView(R.id.item_task_type);
        AdapterUtils.setText(type, StringUtil.getTypeSign(item.getType_sign()));

        String allot_status = item.getAllot_status();
        if ("0".equals(allot_status)) {
            if ("1".equals(item.getIs_rob())) {
                viewHolder.setText(R.id.item_task_date_tv, "抢");
                viewHolder.setBackgroundRes(R.id.item_task_date_tv, R.drawable.group_red_bg);
            }
            TextView tvAllorStatus = viewHolder.getView(R.id.item_line_state);
            AdapterUtils.setText(tvAllorStatus, "分配状态：未分配");
            viewHolder.setGone(R.id.plan_progressbar_ll, false);
        } else {

            if ("1".equals(item.getIs_rob())) {
                TextView tvAllorStatus = viewHolder.getView(R.id.item_line_state);
                AdapterUtils.setText(tvAllorStatus, "分配状态：已抢单");
            } else {
                TextView tvAllorStatus = viewHolder.getView(R.id.item_line_state);
                AdapterUtils.setText(tvAllorStatus, "分配状态：已分配");
            }
            ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
            progressBar.setMax(item.getAll_num());
            progressBar.setProgress(item.getDone_num());
            viewHolder.setGone(R.id.plan_progressbar_ll, true);
            viewHolder.setText(R.id.plan_progressbar_tv, "任务进度(" + item.getDone_num() + "/" + item.getAll_num() + ")：")
                    .setText(R.id.plan_progressbar_num, (item.getDone_rate()==null?0:item.getDone_rate()) + "%");

        }

    }
}