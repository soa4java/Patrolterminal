package com.patrol.terminal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GradohicProgressAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GraphicProgressBean;
import com.patrol.terminal.bean.InitiateProjectBean2;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//形象进度列表
public class GraphicProgressActivity extends BaseActivity implements TextWatcher {

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
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.graphic_progress)
    SwipeRecyclerView graphicProgress;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.project_search_et)
    EditText projectSearchEt;
    @BindView(R.id.delete_iv)
    ImageView deleteIv;

    private List<GraphicProgressBean> list = new ArrayList<>();
    private List<GraphicProgressBean> mFilterCheckProject = new ArrayList<>();
    private GradohicProgressAdapter adapter;
    private String search="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_progress);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        getGraPro();
        titleName.setText("形象进度");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingIv.setVisibility(View.VISIBLE);
        titleSettingTv.setVisibility(View.GONE);
        titleSettingIv.setImageResource(R.mipmap.add_white);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        graphicProgress.setLayoutManager(manager);
        // 设置监听器。
        graphicProgress.setSwipeMenuCreator(mSwipeMenuCreator);

        // 菜单点击监听。
        graphicProgress.setOnItemMenuClickListener(mItemMenuClickListener);
        adapter = new GradohicProgressAdapter(R.layout.item_gradohic_progress
                , list);
        graphicProgress.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GraphicProgressBean bean = list.get(position);
                Intent intent = new Intent(GraphicProgressActivity.this, GraphicProDetailActivity.class);
                intent.putExtra("bean", bean);
                startActivity(intent);

            }
        });
        projectSearchEt.addTextChangedListener(this);
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem1 = new SwipeMenuItem(GraphicProgressActivity.this);
            deleteItem1.setText("删除");
            deleteItem1.setBackgroundColorResource(R.color.home_red);
            deleteItem1.setTextColor(Color.WHITE);
            deleteItem1.setWidth(width);
            deleteItem1.setHeight(height);
//            deleteItem1.setBackground(R.color.home_red);
//            deleteItem1.setTextSize(15);
//            deleteItem1.setTextColorResource(R.color.white);
//            deleteItem1.setText("删除");
            // 各种文字和图标属性设置。
            rightMenu.addMenuItem(deleteItem1); // 在Item右侧添加一个菜单。
            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };
    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            deleteGraPro(list.get(position).getId(), position);
            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
        }
    };

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Intent intent = new Intent(this, AddGraphicProgressActivity.class);
                startActivityForResult(intent, 245);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 245 && resultCode == RESULT_OK) {
            getGraPro();
        }
    }

    //获取形象进度列表
    public void getGraPro() {
        BaseRequest.getInstance().getService()
                .getGraPro(1, 100, search, "upload_time desc")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GraphicProgressBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<GraphicProgressBean>> t) throws Exception {
                        list = t.getResults();
                        adapter.setNewData(list);

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //删除形象进度
    public void deleteGraPro(String id, int position) {
        BaseRequest.getInstance().getService()
                .deleteGraPro(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        list.remove(position);
                        adapter.notifyItemRemoved(position);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String editStr = projectSearchEt.getText().toString();
        if (TextUtils.isEmpty(editStr)) {
            adapter.setNewData(list);
        }else {
            mFilterCheckProject.clear();
            for (int i = 0; i < list.size();i++) {
                String name = list.get(i).getTemp_project_name();
                if (name.contains(editStr)) {
                    mFilterCheckProject.add(list.get(i));
                }
            }
            adapter.setNewData(mFilterCheckProject);
        }
    }
}
