package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.fragment.ControlDepFrgment;
import com.patrol.terminal.fragment.ControlToolFragment;
import com.patrol.terminal.fragment.NewControlQualityFragment;
import com.patrol.terminal.fragment.YXControlDepFrgment;
import com.patrol.terminal.fragment.YXControlToolFragment;
import com.patrol.terminal.fragment.YXNewControlQualityFragment;
import com.patrol.terminal.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*填写控制卡*/
public class ControlCardActivity extends BaseActivity {
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
    @BindView(R.id.control_rb1)
    RadioButton controlRb1;
    @BindView(R.id.control_rb2)
    RadioButton controlRb2;
    @BindView(R.id.control_rb3)
    RadioButton controlRb3;
    @BindView(R.id.control_card_vg)
    NoScrollViewPager controlCardVg;
    @BindView(R.id.control_card_rg)
    RadioGroup controlCardRg;
    //private boolean isLook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_card);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        //isLook = getIntent().getBooleanExtra("is_look", false);  //是否为查看模式
        titleName.setText("控制卡");
        String from = getIntent().getStringExtra("from");

        List<Fragment> fragmentList = new ArrayList<>();

        if ("yx".equals(from)) {
            fragmentList.add(new YXControlDepFrgment());
            fragmentList.add(new YXNewControlQualityFragment());
            fragmentList.add(new YXControlToolFragment());//工器具配置表
        } else {
            fragmentList.add(new ControlDepFrgment());
            fragmentList.add(new NewControlQualityFragment());
            fragmentList.add(new ControlToolFragment());
        }
        MyFragmentPagerAdapter CardPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        controlCardVg.setAdapter(CardPagerAdapter);
        controlCardVg.setNoScroll(true);
        controlCardVg.setOffscreenPageLimit(3);
        controlCardRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.control_rb1:
                        controlCardVg.setCurrentItem(0);
                        break;
                    case R.id.control_rb2:
                        controlCardVg.setCurrentItem(1);
                        break;
                    case R.id.control_rb3:
                        controlCardVg.setCurrentItem(2);
                        break;
                }
            }
        });
    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }


}
