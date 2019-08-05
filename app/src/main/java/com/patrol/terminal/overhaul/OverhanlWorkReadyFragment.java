package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.patrol.terminal.R;
import com.patrol.terminal.activity.ControlCardActivity;
import com.patrol.terminal.activity.FirstWTicketActivity;
import com.patrol.terminal.activity.PatrolRecordActivity;
import com.patrol.terminal.activity.SecondWTicketActivity;
import com.patrol.terminal.adapter.GridViewAdapter5;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.bean.MapUserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OverhanlWorkReadyFragment extends BaseFragment {
    @BindView(R.id.gridview)
    GridView gridview;
    private GridViewAdapter5 weekAdapter;
    private List<MapUserInfo> results = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overhaul_other, container, false);
        return view;
    }

    @Override
    protected void initData() {
        results.clear();
        MapUserInfo mapUserInfo1 = new MapUserInfo();
        mapUserInfo1.setUserImgId(R.mipmap.todo6);
        mapUserInfo1.setUserName("保电巡视");
        results.add(mapUserInfo1);

        MapUserInfo mapUserInfo2 = new MapUserInfo();
        mapUserInfo2.setUserImgId(R.mipmap.word);
        mapUserInfo2.setUserName("上传保电方案");
        results.add(mapUserInfo2);

        MapUserInfo mapUserInfo3 = new MapUserInfo();
        mapUserInfo2.setUserImgId(R.mipmap.excel);
        mapUserInfo2.setUserName("上传验收方案");
        results.add(mapUserInfo3);


        weekAdapter = new GridViewAdapter5(getContext(), results);
        gridview.setAdapter(weekAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                switch (i) {
                    case 0:
                        intent.setClass(getContext(), PatrolRecordActivity.class);
                        break;
                    case 1:
                        intent.setClass(getContext(), SecondWTicketActivity.class);
                        break;
                    case 2:
//                        intent.setClass(getContext(), ThirdWTicketActivity.class);
                        break;
////                    case 3:
////                        intent.setClass(getContext(), FourWTicketActivity.class);
////                        break;
//                    case 4:
//                        intent.setClass(getContext(), ControlCardActivity.class);
//                        break;
                }
                startActivity(intent);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}