package com.patrol.terminal.overhaul;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.CheckResultBean;
import com.patrol.terminal.bean.OverhaulYearBean;

import java.util.List;

import androidx.annotation.Nullable;

public class AddCheckResultAdapter extends BaseQuickAdapter<CheckResultBean, BaseViewHolder> {
    private boolean isRadioGroupShow = false;
    private List<CheckResultBean> mData;

  /*  public AddCheckResultAdapter(int layoutResId, @Nullable List<CheckResultBean> data, String year, String month) {
        super(layoutResId, data);
    }
*/
    public AddCheckResultAdapter(int layoutResId, @Nullable List<CheckResultBean> data) {
        super(layoutResId, data);
        //1.需要电脑和路由器(路由器不需要联网)         2.设置路由器账号密码
        this.mData = data;

    }


    @Override
    protected void convert(BaseViewHolder viewHolder, CheckResultBean item) {
        RadioGroup checkResultRg = viewHolder.getView(R.id.check_result_rg);

        viewHolder.setOnClickListener(R.id.check_result_rl, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRadioGroupShow) {   //如果显示
                    checkResultRg.setVisibility(View.GONE);
                    isRadioGroupShow = false;
                }else {  //如果未显示
                    checkResultRg.setVisibility(View.VISIBLE);
                    isRadioGroupShow = true;
                }
            }
        });

        viewHolder.setOnClickListener(R.id.delete_result_iv, new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mData.remove(item);
                notifyDataSetChanged();
            }
        });


        checkResultRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                checkResultRg.setVisibility(View.GONE);
                isRadioGroupShow = false;

                switch (checkedId) {
                    case R.id.check_result_ok_rb:
                        viewHolder.setText(R.id.check_result_tv, "通过");
                        item.setCheckResult(0);
                        break;
                    case R.id.check_result_verbal_warning_rb:
                        viewHolder.setText(R.id.check_result_tv, "口头警告");
                        item.setCheckResult(1);
                        break;
                    case R.id.check_result_written_corrections_rb:
                        viewHolder.setText(R.id.check_result_tv, "书面整改");
                        item.setCheckResult(2);
                        break;
                }
            }
        });

        EditText checkContentEt = viewHolder.getView(R.id.check_content_et);
        item.setCheckContent(checkContentEt.getText().toString());
        //设置图片  TODO




    }

}