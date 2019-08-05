package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.FieldAntiInspectionActivity;
import com.patrol.terminal.activity.GetToPostCheckActivity;
import com.patrol.terminal.activity.IllegalInspectionReferenceActivity;
import com.patrol.terminal.activity.InspectionRequirementsActivity;
import com.patrol.terminal.activity.MonitoringRecordActivity;
import com.patrol.terminal.activity.SituationOnSiteActivity;
import com.patrol.terminal.adapter.DriverAdapter;
import com.patrol.terminal.adapter.EqVehicleAdapter;
import com.patrol.terminal.adapter.MonitoringRecordAdapter;
import com.patrol.terminal.adapter.PlanAllotTeamAdapter;
import com.patrol.terminal.adapter.SaveCheckReqBean;
import com.patrol.terminal.adapter.TaskGroupVehicleAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DepBean;
import com.patrol.terminal.bean.DriverBean;
import com.patrol.terminal.bean.EqVehicleBean;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.TeamAndTaskBean;
import com.patrol.terminal.bean.TeamAndVehicleBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//车辆选择
public class SafeAndQulityFrgment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {


    RelativeLayout titleSetting;
    @BindView(R.id.rv_monitoring_record)
    RecyclerView rvMonitoringRecord;
    private String[] data = {"一、现场情况", "二、到岗到位检查", "三、现场反违章检查", "四、违章检查参考", "五、检查监督要求"};
    private String audit_status="0";


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitoring_record, container, false);
        return view;
    }

    @Override
    protected void initData() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        rvMonitoringRecord.setLayoutManager(manager);
        MonitoringRecordAdapter adapter = new MonitoringRecordAdapter(R.layout.item_monitoring_record, Arrays.asList(data));
        rvMonitoringRecord.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent();
                intent.setClass(getContext(), SituationOnSiteActivity.class);
                startActivity(intent);
                break;

            case 1:
                Intent intent1 = new Intent();
                intent1.putExtra("task_id", "");
                intent1.putExtra("audit_status", audit_status);
                intent1.setClass(getContext(), GetToPostCheckActivity.class);
                startActivity(intent1);
                break;

            case 2:
                Intent intent2 = new Intent();
                intent2.putExtra("task_id","");
                intent2.putExtra("audit_status",audit_status);
                intent2.setClass(getContext(), FieldAntiInspectionActivity.class);
                startActivity(intent2);
                break;

            case 3:
                Intent intent3 = new Intent();
                intent3.setClass(getContext(), IllegalInspectionReferenceActivity.class);
                startActivity(intent3);
                break;

            case 4:
                Intent intent4 = new Intent();
                intent4.setClass(getContext(), InspectionRequirementsActivity.class);
                intent4.putExtra("audit_status", audit_status);
                intent4.putExtra("task_id", "");
                startActivity(intent4);
                break;
        }
    }



}
