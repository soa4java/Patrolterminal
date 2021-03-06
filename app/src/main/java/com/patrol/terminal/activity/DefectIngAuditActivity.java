package com.patrol.terminal.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectAuditPicAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.DefectFragmentDetailBean;
import com.patrol.terminal.bean.InAuditPostBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.CancelOrOkDialogNew;
import com.patrol.terminal.widget.ProgressDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//缺陷审核
public class DefectIngAuditActivity extends BaseActivity {

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
    @BindView(R.id.defect_line_name)
    TextView defectLineName;
    @BindView(R.id.tower_name)
    TextView towerName;
    @BindView(R.id.defect_content)
    TextView defectContent;
    @BindView(R.id.patrol_name)
    TextView patrolName;
    @BindView(R.id.defect_category_name)
    TextView defectCategoryName;
    @BindView(R.id.defect_grade_name)
    TextView defectGradeName;
    @BindView(R.id.defect_find_time)
    TextView defectFindTime;
    @BindView(R.id.defect_deal_notes)
    TextView defectDealNotes;
    @BindView(R.id.deal_user_name)
    TextView dealUserName;
    @BindView(R.id.deal_dep_name)
    TextView dealDepName;
    @BindView(R.id.deal_time)
    TextView dealTime;
    @BindView(R.id.defect_status)
    TextView defectStatus;
    @BindView(R.id.defect_find_user_name)
    TextView defectFindUserName;
    @BindView(R.id.defect_find_dep_name)
    TextView defectFindDepName;
    @BindView(R.id.defect_deadline)
    TextView defectDeadline;
    @BindView(R.id.deffect_img)
    TextView deffectImg;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;
    @BindView(R.id.reject)
    TextView reject;
    @BindView(R.id.stock_in)
    TextView stockIn;
    @BindView(R.id.review)
    TextView review;
    @BindView(R.id.turn_to_repair)
    TextView turnToRepair;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    private DefectAuditPicAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private DefectFragmentDetailBean bean;
    private String mJobType;
    private String year;
    private String month;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("data_id");
        setContentView(R.layout.activity_defect_audit);
        ButterKnife.bind(this);
        initview(id);
    }

    private void initview(String id) {
        mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            stockIn.setText("入库");
            review.setVisibility(View.VISIBLE);
            turnToRepair.setVisibility(View.VISIBLE);
        } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            stockIn.setText("通过");
            review.setVisibility(View.GONE);
            turnToRepair.setVisibility(View.GONE);
        } else {
            reject.setVisibility(View.GONE);
            stockIn.setVisibility(View.GONE);
            review.setVisibility(View.GONE);
            turnToRepair.setVisibility(View.GONE);
        }

        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        defectDeadline.setText(time);
        initdate(time);

        titleName.setText("缺陷审核");
        BaseRequest.getInstance().getService().getDefectDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DefectFragmentDetailBean>() {
                    @SuppressLint("NewApi")
                    @Override
                    protected void onSuccees(BaseResult<DefectFragmentDetailBean> t) throws Exception {
                        bean = t.getResults();

                        if(bean.getIn_status() != null){
                            defectStatus.setText(StringUtil.getDefectState(bean.getIn_status()));
                            defectStatus.setTextColor(getResources().getColor(StringUtil.getDefectColor(bean.getIn_status())));

                            if(bean.getIn_status().equals("3") || bean.getIn_status().equals("4") || bean.getIn_status().equals("5")){
                                hiddenBottomLayout();
                            }
                        }

                        if(bean.getContent() != null){
                            defectContent.setText(bean.getContent());
                        }
                        if(bean.getTower_name() != null){
                            towerName.setText(bean.getTower_name());
                        }
                        if(bean.getPatrol_name() != null){
                            patrolName.setText(bean.getPatrol_name());
                        }
                        if(bean.getFind_time() != null){
                            defectFindTime.setText(bean.getFind_time());
                        }
                        if (bean.getDeal_user_name() != null) {
                            dealUserName.setText(bean.getDeal_user_name());
                        }
                        if (bean.getDeal_dep_name() != null) {
                            dealDepName.setText(bean.getDeal_dep_name());
                        }
                        if (bean.getDeal_time() != null) {
                            dealTime.setText(bean.getDeal_time());
                        }
                        if(bean.getLine_name() != null){
                            defectLineName.setText(bean.getLine_name());
                        }
                        if(bean.getFind_user_name() != null){
                            defectFindUserName.setText(bean.getFind_user_name());
                        }
                        if (bean.getFind_dep_name() != null) {
                            defectFindDepName.setText(bean.getFind_dep_name());
                        }
                        if(bean.getCategory_name() != null){
                            defectCategoryName.setText(bean.getCategory_name());
                        }
                        if(bean.getGrade_name() != null){
                            defectGradeName.setText(bean.getGrade_name());
                        }

                        if ("一般".equals(bean.getGrade_name())) {
                            defectGradeName.setTextColor(getResources().getColor(R.color.blue));

                            Calendar c = Calendar.getInstance();//获得一个日历的实例
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            Date date = new Date(System.currentTimeMillis());
                            c.setTime(date);//设置日历时间
                            c.add(Calendar.MONTH,1);//在日历的月份上增加6个月
                            defectDeadline.setText(sdf.format(c.getTime()));
                            initdate(sdf.format(c.getTime()));
                        } else if ("严重".equals(bean.getGrade_name())) {
                            defectGradeName.setTextColor(getResources().getColor(R.color.line_point_1));

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            Date date = new Date(System.currentTimeMillis());
                            c.setTime(date);
                            c.add(Calendar.DATE,7);
                            defectDeadline.setText(sdf.format(c.getTime()));
                            initdate(sdf.format(c.getTime()));
                        } else if ("危急".equals(bean.getGrade_name())) {
                            defectGradeName.setTextColor(getResources().getColor(R.color.line_point_0));

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            Date date = new Date(System.currentTimeMillis());
                            c.setTime(date);
                            c.add(Calendar.DATE,1);
                            defectDeadline.setText(sdf.format(c.getTime()));
                            initdate(sdf.format(c.getTime()));
                        }

                        if((bean.getIn_status().equals("3") || bean.getIn_status().equals("4") || bean.getIn_status().equals("5")) && bean.getClose_time() != null){
                            String[] times = bean.getClose_time().split("-");
                            year = times[0];
                            month = times[1];
                            day = times[2];
                            defectDeadline.setText(year + "年" + month + "月" + day + "日");
                            defectDeadline.setCompoundDrawablesRelative(null, null, null, null);
                        }

                        if (bean.getDeal_notes() != null) {
                            defectDealNotes.setText(bean.getDeal_notes());
                        }

                        if (bean.getFileList() != null && bean.getFileList().size() > 0) {
                            deffectImg.setVisibility(View.VISIBLE);
                            mPicList.clear();
                            for (int i = 0; i < bean.getFileList().size(); i++) {
                                mPicList.add(BaseUrl.BASE_URL + bean.getFileList().get(i).getFile_path() + bean.getFileList().get(i).getFilename());
                            }
                            mGridViewAddImgAdapter = new DefectAuditPicAdapter(DefectIngAuditActivity.this, mPicList);
                            defectGridView.setAdapter(mGridViewAddImgAdapter);
                            defectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    viewPluImg(position);
                                }
                            });
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @OnClick({R.id.title_back, R.id.defect_deadline, R.id.reject, R.id.stock_in, R.id.review, R.id.turn_to_repair})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.defect_deadline:
                if(!bean.getIn_status().equals("3") && !bean.getIn_status().equals("4") && !bean.getIn_status().equals("5")){
                    showDay();
                }
                break;
            case R.id.reject:
                submit("4");
                break;
            case R.id.stock_in:
                if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
                    submit("3");
                } else {
                    submit("2");
                }
                break;
            case R.id.review:
                Intent intent2 = new Intent(this, ReviewTaskActivity.class);
                intent2.putExtra("DefectFragmentDetailBean", bean);
                startActivityForResult(intent2, 10);
                finish();
                break;
            case R.id.turn_to_repair:
                break;
        }
    }

    public void inAuditPOST(String in_status) {
        ProgressDialog.show(this, false, "正在加载。。。。");
        InAuditPostBean inAuditPostBean = new InAuditPostBean();
        inAuditPostBean.setId(bean.getId());
        inAuditPostBean.setIn_status(in_status);
        inAuditPostBean.setFrom_user_id(SPUtil.getUserId(this));
        inAuditPostBean.setFrom_user_name(SPUtil.getUserName(this));
        inAuditPostBean.setLine_name(bean.getLine_name());
        inAuditPostBean.setTower_name(bean.getTower_name());
        if(in_status.equals("3")){
            inAuditPostBean.setClose_time(year + "-" + month + "-" + day);
        }
        BaseRequest.getInstance().getService()
                .inAuditPOST(inAuditPostBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if(t.getCode() == 1){
                            Toast.makeText(DefectIngAuditActivity.this,"处理完成",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(DefectIngAuditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
        intent.putExtra("isDelPic", "0");
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
    }

    //初始化日期
    public void initdate(String time) {
        String[] times = time.split("年");
        String[] months = times[1].split("月");
        year = times[0];
        month = months[0];
        day = months[1].split("日")[0];
    }

    public void showDay() {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        int curMonth = Integer.parseInt(months[0]);
        int curYear = Integer.parseInt(years[0]);
        int curDay = Integer.parseInt(days[0]);
        if(curMonth == 1){
            curMonth = 12;
            curYear = curYear - 1;
        } else {
            curMonth = curMonth - 1;
        }

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(curYear, curMonth, curDay);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = DateUatil.getDay(date);
                defectDeadline.setText(time);
                initdate(time);
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

    //提交缺陷审核
    public void submit(String in_status) {
        if(in_status.equals("4")){
            CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(DefectIngAuditActivity.this, "驳回", "取消", "确定") {
                @Override
                public void ok() {
                    super.ok();
                    inAuditPOST("4");
                }

                @Override
                public void cancle() {
                    super.cancle();
                }
            };
            dialog.show();
        } else {
            if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
                CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(this, "入库", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        inAuditPOST("3");
                    }

                    @Override
                    public void cancle() {
                        super.cancle();
                    }
                };
                dialog.show();
            } else {
                CancelOrOkDialogNew dialog = new CancelOrOkDialogNew(this, "通过", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        inAuditPOST("2");
                    }

                    @Override
                    public void cancle() {
                        super.cancle();
                    }
                };
                dialog.show();
            }
        }
    }

    private void hiddenBottomLayout() {
        layoutBottom.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
        layoutParams.bottomMargin = 0;
        scrollView.setLayoutParams(layoutParams);
        titleName.setText("缺陷记录");
    }
}
