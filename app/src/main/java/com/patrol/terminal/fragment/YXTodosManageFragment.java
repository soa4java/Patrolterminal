package com.patrol.terminal.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.YXTodoManageAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GroupBean;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.TaskBean;
import com.patrol.terminal.bean.TodoBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*待办管理*/
public class YXTodosManageFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
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
    @BindView(R.id.to_do_tv)
    TextView toDoTv;
    @BindView(R.id.done_tv)
    TextView doneTv;
    @BindView(R.id.frag_todo_rv)
    SwipeRecyclerView fragTodoRv;
    @BindView(R.id.frag_todo_ref)
    SwipeRefreshLayout fragTodoRef;
    private Activity mActivity;
    private YXTodoManageAdapter toDoManageAdapter;
    private static final int IS_TODO_PAGE = 0;
    private static final int IS_DONE_PAGE = 1;
    private int isTodoPage = IS_TODO_PAGE;
    private String status;
    private List<TodoBean> results = new ArrayList<>();
//    private List<PersonalTaskListBean> resultsHave=new ArrayList<>();

    private String jobType;
    private Disposable subscribe;
    private String state;
    private String dep_id;
    private String user_id;
    private String year, month, day, time;
    private String haveState;

    private String ele_user_id;
    private String check_user_id;
    private String safe_user_id;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_dos_manage_fragment, container, false);
        return view;
    }

    @Override
    protected void initData() {
        mActivity = getActivity();
        titleBack.setVisibility(View.GONE);
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("已读");
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        titleName.setText("待办管理");

        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            dep_id = SPUtil.getDepId(getContext());
            state = "2";
            haveState = "3";
        } else if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
            user_id = SPUtil.getUserId(getContext());
            state = "1";
            haveState = "2";
        }
//        getYXtodo();
//        getYXtodoHave();
//        if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
//            getWeekList();
//        }
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        fragTodoRv.setLayoutManager(manager);
        toDoManageAdapter = new YXTodoManageAdapter(R.layout.fragment_yxtodo_item, results);

        fragTodoRv.setAdapter(toDoManageAdapter);
        fragTodoRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                results.clear();
                getYXtodo();
//                    getYXtodoHave();
//                    if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
//                        getWeekList();
//                    }
            }
        });

        toDoManageAdapter.setOnItemClickListener(this);

        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("refreshTodo")) {
                    getYXtodo();
//                        getYXtodoHave();
//                    if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
//                        getWeekList();
//                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getYXtodo();
    }

    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }

    @OnClick({R.id.to_do_tv, R.id.done_tv, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.to_do_tv:
                toDoTv.setBackgroundResource(R.drawable.date_white_bg);
                doneTv.setBackgroundResource(R.drawable.date_grey_bg);
                toDoTv.setTextColor(getResources().getColor(R.color.date_color));
                doneTv.setTextColor(getResources().getColor(R.color.white));

                isTodoPage = IS_TODO_PAGE;
                toDoManageAdapter.setNewData(results);
                break;

            case R.id.done_tv:
                toDoTv.setBackgroundResource(R.drawable.date_grey_bg);
                doneTv.setBackgroundResource(R.drawable.date_white_bg);
                toDoTv.setTextColor(getResources().getColor(R.color.white));
                doneTv.setTextColor(getResources().getColor(R.color.date_color));

                isTodoPage = IS_DONE_PAGE;
//                toDoManageAdapter.setNewData(resultsHave);
                break;
            case R.id.title_setting:
                clearTodoAll();
                break;
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        if (isTodoPage ==IS_TODO_PAGE){
//             todoListBean = results.get(position);
//        }else {
//            todoListBean = resultsHave.get(position);
//        }
        clearTodo(results.get(position).getId());
        String task_id = results.get(position).getData_id();
        Intent intent = Utils.goTodo(getContext(), results.get(position));
        if (intent == null) {
            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_LONG).show();
            return;
        }

        intent.putExtra("id", results.get(position).getId());
        intent.putExtra("audit_status", results.get(position).getNode_sign());
        intent.putExtra("task_id", task_id);
//        intent.putExtra("sign", deal_type);

        if (results.get(position).getFlow_sign().equals("9")) {//待办跳转巡视记录缺 tower_id
            getTowerId(task_id, intent);
        } else {
            startActivity(intent);
        }

    }

    //清除待办
    private void clearTodo(String id) {
        BaseRequest.getInstance().getService()
                .clearTodo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        getYXtodo();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("fff", e.toString());
                    }
                });
    }

    //待办跳转巡视记录需要  tower_id
    private void getTowerId(String id, Intent intent) {
        ProgressDialog.show(mActivity);
        BaseRequest.getInstance().getService()
                .getTowerId("TASK_PERSONAL", "id,tower_id", id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GetTowerId>>(mActivity) {
                    @Override
                    protected void onSuccees(BaseResult<List<GetTowerId>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            intent.putExtra("tower_id", t.getResults().get(0).getTower_id());
                            startActivity(intent);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(mContext, "请求失败请重试", Toast.LENGTH_LONG).show();
                    }
                });
    }

    //消除所有查看类型待办
    public void clearTodoAll() {

        BaseRequest.getInstance().getService()
                .clearTodoAll(SPUtil.getUserId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TaskBean>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<TaskBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(getContext(), "处理完成", Toast.LENGTH_SHORT).show();
                            getYXtodo();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //获取周检修计划列表
    public void getWeekList() {

        String userId = SPUtil.getString(getContext(), Constant.USER, Constant.USERID, "");

        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {
            userId = null;
        } else if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
            ele_user_id = userId;
            userId = null;
        } else if (jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
            check_user_id = userId;
        } else if (jobType.contains(Constant.SAFETY_SPECIALIZED)) {
            safe_user_id = userId;
        }

        String week = DateUatil.getWeekNum() + "";

        //安全,验收,保电需要传userId
        BaseRequest.getInstance().getService()
                .getOverhaulPlanList(year, month, week, "2", userId, ele_user_id, check_user_id, safe_user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulYearBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulYearBean>> t) throws Exception {
                        List<OverhaulYearBean> overhaulYearBeans = t.getResults();

//                        toDoManageAdapter.setNewData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("fff", e.toString());
                    }
                });
    }

    private void getYXtodo() {
        String userId = SPUtil.getUserId(getContext());
        BaseRequest.getInstance().getService()
                .todoList(userId, "flow_sign,create_time,title")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TodoBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<TodoBean>> t) throws Exception {
                        Log.i("555555555", "refreshTodo");
                        if (t.getCode() == 1) {
                            fragTodoRef.setRefreshing(false);
                            results = t.getResults();
                            if (isTodoPage == IS_TODO_PAGE) {
                                toDoManageAdapter.setNewData(results);
                            }
                            if (results != null) {
                                RxRefreshEvent.publish("todoRefreshNum@" + results.size());
                            }
                            ;
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        fragTodoRef.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    //获取是否是运行班负责人
    public void getGroupName() {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        String month = Integer.parseInt(months[0]) + "";
        String year = years[0];
        String day = Integer.parseInt(days[0]) + "";
        BaseRequest.getInstance().getService()
                .getGroupName(year, month, day, SPUtil.getDepId(getContext()), SPUtil.getUserId(getContext()), "2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GroupBean>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<GroupBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            GroupBean results = t.getResults();
                            if (results != null) {
                                if ("2".contains(results.getSign())) {
                                    SPUtil.putString(getContext(), Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_TEMA_LEADER);
                                }
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    public static class GetTowerId {
        private String tower_id;
        private String id;

        public String getTower_id() {
            return tower_id;
        }

        public void setTower_id(String tower_id) {
            this.tower_id = tower_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
