package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.AddDayAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.PlanWeekLineBean;
import com.patrol.terminal.bean.PlanWeekReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollListView;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddDayPlanActivity extends BaseActivity {


    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;

    @BindView(R.id.month_plan_type)
    TextView monthPlanType;
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.month_plan_line)
    TextView monthPlanLine;
    @BindView(R.id.month_plan_type_lv)
    NoScrollListView monthPlanTypeLv;
    @BindView(R.id.trouble_more)
    LinearLayout troubleMore;
    @BindView(R.id.mon_plan_type_ll)
    LinearLayout monPlanTypeLl;
    @BindView(R.id.month_yes)
    TextView monthYes;
    @BindView(R.id.add_tower_more)
    ImageView addTowerMore;
    private List<String> lineNameList = new ArrayList<>();
    private String year;
    private String month;
    private String week;
    private int type = 0;
    private String lineId;
    private List<DayOfWeekBean> selectType = new ArrayList<>();
    private AddDayAdapter adapter;
    private List<DayOfWeekBean> results;
    private List<DayOfWeekBean> linList=new ArrayList<>();
    private Disposable subscribe;
    private List<String> typeNameList = new ArrayList<>();
    private List<LineTypeBean> typeList=new ArrayList<>();
    private String type_id;
    private String time;
    private String day;
    private String lineName;
    private String typeName;
    private List<DayOfWeekBean> lineList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_week_plan);
        ButterKnife.bind(this);
        initview();

        getLineType();
        getLine();
        getWeekPlan();
    }


    private void initview() {
        week = DateUatil.getWeekNum() + "";
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        titleName.setText(time + "计划制定");
        adapter = new AddDayAdapter(this, linList);
        monthPlanTypeLv.setAdapter(adapter);

        subscribe = RxRefreshEvent.getDayObservable().subscribe(new Consumer<DayOfWeekBean>() {

            @Override
            public void accept(DayOfWeekBean type) throws Exception {
                if (selectType.size()==0){
                    type.setWeek_tower_id(type.getId());
                    type.setDay(day);
                    selectType.add(type);
                }else {
                    int isExit=0;
                    for (int i = 0; i < selectType.size(); i++) {
                        DayOfWeekBean dayOfWeekBean = selectType.get(i);
                        if (dayOfWeekBean.getId().equals(type.getId())){
                            isExit=1;
                            selectType.remove(i);
                            return;
                        }
                    }
                    if (isExit==0){
                        type.setWeek_tower_id(type.getId());
                        type.setDay(day);
                        selectType.add(type);
                    }
                }

            }
        });
    }

    @OnClick({R.id.title_back, R.id.month_plan_line, R.id.month_plan_type,R.id.month_yes, R.id.trouble_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.month_plan_line:
                showLine();
                break;
            case R.id.month_plan_type:
                showType();
                break;
            case R.id.month_yes:
                saveWeek();
                break;

            case R.id.trouble_more:
                if (type == 0) {
                    type = 1;
                    addTowerMore.setImageResource(R.mipmap.icon_newol_up);
                } else {
                    type = 0;
                    addTowerMore.setImageResource(R.mipmap.icon_newol_down);
                }
                adapter.setType(type);
                break;

        }
    }

    //线路添加选项框
    private void showLine() {// 不联动的多级选项
        if (lineNameList.size() == 0) {
            Toast.makeText(this, "没有获取到线路信息，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                selectType.clear();
                lineName = lineNameList.get(options1);
                monthPlanLine.setText(lineName);
                for (int i = 0; i < results.size(); i++) {
                    String line_name = results.get(i).getLine_name();
                    if (lineName.equals(line_name)){
                        lineId = results.get(options1).getLine_id();
                    }
                }
              getWeekPlan();
            }
        }).build();
        pvOptions.setPicker(lineNameList);
        pvOptions.show();
    }

    //类型添加选项框
    private void showType() {// 不联动的多级选项
        if (typeList.size() == 0) {
            Toast.makeText(this, "没有获取到工作类型信息，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                typeName = typeList.get(options1).getName();
                monthPlanType.setText(typeName);
                type_id = typeList.get(options1).getId();
                getWeekPlan();
            }
        }).build();
        pvOptions.setPicker(typeNameList);
        pvOptions.show();
    }

    //获取每个班组负责的线路
    public void getLine() {
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .getDayPlan(year,month, SPUtil.getDepId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        lineList = t.getResults();
                        for (int i = 0; i < lineList.size(); i++) {
                            DayOfWeekBean bean = lineList.get(i);
                                String line_name = bean.getLine_name();
                                if (lineNameList.indexOf(line_name)==-1){
                                    lineNameList.add(line_name);
                                }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
    //获取每个班组负责的线路
    public void getWeekPlan() {
        ProgressDialog.show(this,false,"正在加载。。。。");
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .getDayPlan(year,month, SPUtil.getDepId(this),type_id,lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        results = t.getResults();
                        adapter.setData(results);
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //获取工作类型
    public void getLineType() {
        typeNameList.clear();
        BaseRequest.getInstance().getService()
                .getLineType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        typeList = t.getResults();
                        for (int i = 0; i < typeList.size(); i++) {
                            LineTypeBean lineTypeBean = typeList.get(i);
                            typeNameList.add(lineTypeBean.getName());
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }


    //保存
    public void saveWeek() {

        if (selectType.size()==0){
            Toast.makeText(this,"请添加计划",Toast.LENGTH_SHORT).show();
            return;
        }



        BaseRequest.getInstance().getService()
                .addDayPlan(selectType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode()==1){
                            setResult(RESULT_OK);
                            Toast.makeText(AddDayPlanActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(AddDayPlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    public void showDay() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
//是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = DateUatil.getDay(date);
                inteDate();


            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }
    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }
}
