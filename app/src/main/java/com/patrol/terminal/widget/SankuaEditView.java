package com.patrol.terminal.widget;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GridViewAdapter2;
import com.patrol.terminal.sqlite.DefactContentDBHelper;
import com.patrol.terminal.sqlite.MyOpenhelper;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.PictureSelectorConfig;

import java.util.ArrayList;

/**
 * 三跨六防通用编辑框
 */
public class SankuaEditView extends LinearLayout {

    private View view;
    private Context context;
    private AutoCompleteTextView sankua_yhnr;
    private RadioGroup sankua_rad;
    private GridView sk_gridview;
    private TextView item_title;
    private LinearLayout item_context;
    private RelativeLayout item_title_rl;

    private ImageView item_tssx_del;

    private GridViewAdapter2 mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源

    private String YHDJStr;
    public static int YB = 0;
    public static int ZD = 1;
    public static int JJ = 2;

    private CursorAdapter cursorAdapter;
    private Cursor cursor;
    private onTssxClick mOnTssxClick;


    public SankuaEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = (View) LayoutInflater.from(context).inflate(R.layout.cusview_sankua, this, true);
        sankua_yhnr = (AutoCompleteTextView) view.findViewById(R.id.sankua_yhnr);
        sankua_rad = (RadioGroup) view.findViewById(R.id.sankua_rad);
        sk_gridview = view.findViewById(R.id.sk_gridview);
        item_title = view.findViewById(R.id.item_tssx_title);
        item_context = view.findViewById(R.id.ll_content);
        item_title_rl = view.findViewById(R.id.item_title_rl);
        item_tssx_del = view.findViewById(R.id.item_tssx_del);

        initClick();
//        initData();
    }

    /**
     * 设置item title
     *
     * @param title
     */
    public void setItemTilte(String title) {
        item_title.setText(title);
    }

    /**
     * 是否显示
     *
     * @param isShow
     */
    public void setViewVisibility(boolean isShow) {
        if (isShow) {
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(GONE);
        }
    }

    /**
     * 当前控件显示状态
     *
     * @return
     */
    public boolean getShowState() {
        if (view.getVisibility() == VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回隐患等级文字
     *
     * @return
     */
    public String getRadioDJ() {
        if (TextUtils.isEmpty(YHDJStr))
            return "一般";
        else
            return YHDJStr;
    }

    /**
     * 返回隐患内容
     *
     * @return
     */
    public String getYHStr() {
        return sankua_yhnr.getText().toString().trim();
    }

    /**
     * 设置隐患等级
     */
    public void setDjStatus(int dj) {

        RadioButton rb;
        int count = sankua_rad.getChildCount();
        for (int i = 0; i < count; i++) {
            rb = (RadioButton) sankua_rad.getChildAt(i);
            if (dj == i) {
                rb.setChecked(true);
            } else {
                rb.setChecked(false);
            }
        }
    }

    public void setDjStatus(String dj) {
        dj = dj.trim();
        RadioButton rb;
        if(TextUtils.isEmpty(dj)) dj = "一般";

        int count = sankua_rad.getChildCount();
        for (int i = 0; i < count; i++) {
            rb = (RadioButton) sankua_rad.getChildAt(i);
            if (dj.equals(rb.getText().toString().trim())) {
                rb.setChecked(true);
            } else {
                rb.setChecked(false);
            }
        }
    }


    public void initClick() {
        sankua_rad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                YHDJStr = rb.getText().toString().trim();
            }
        });

        item_title_rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item_context.isShown()) {
                    item_context.setVisibility(GONE);
                } else {
                    item_context.setVisibility(VISIBLE);
                }
            }
        });


        sankua_yhnr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor1 = cursorAdapter.getCursor();
                if (cursor1 != null && cursor1.getCount() > 0) {
                    boolean isExist = cursor1.moveToPosition(position);
                    if (isExist) {
                        String levelStr = cursor1.getString(cursor.getColumnIndex(MyOpenhelper.DefactTvColumns.LEVEL));
                        Log.w("linmeng", "levelStr:" + levelStr);   //这里获取的是缺陷等级，给陈飞用！  TODO
                        setDjStatus(levelStr);
                    }
                }
            }
        });

        item_tssx_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnTssxClick.clickDel();

            }
        });

    }

    public void setOnItemClick(onTssxClick click){
        this.mOnTssxClick = click;
    }

    public interface onTssxClick{
        void clickDel();

    }

    public void setAutoAdapter(CursorAdapter cursorAdapter,Cursor cursor)
    {
        this.cursorAdapter = cursorAdapter;
        this.cursor = cursor;
        sankua_yhnr.setAdapter(cursorAdapter);
    }





}
