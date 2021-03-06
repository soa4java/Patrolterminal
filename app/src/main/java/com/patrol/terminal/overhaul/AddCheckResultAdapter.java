package com.patrol.terminal.overhaul;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.CheckResultBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.Utils;

import java.io.File;
import java.util.List;

public class AddCheckResultAdapter extends BaseQuickAdapter<CheckResultBean, BaseViewHolder> {
    private boolean isRadioGroupShow = false;
    private List<CheckResultBean> mData;
    private Activity mActivity;
    Uri photoUri;
    String filePath;

    /*  public AddCheckResultAdapter(int layoutResId, @Nullable List<CheckResultBean> data, String year, String month) {
          super(layoutResId, data);
      }
  */
    public AddCheckResultAdapter(Activity activity, int layoutResId, @Nullable List<CheckResultBean> data) {
        super(layoutResId, data);
        //1.需要电脑和路由器(路由器不需要联网)         2.设置路由器账号密码
        this.mData = data;
        this.mActivity = activity;
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
                } else {  //如果未显示
                    checkResultRg.setVisibility(View.VISIBLE);
                    isRadioGroupShow = true;
                }
            }
        });

        viewHolder.setOnClickListener(R.id.delete_result_iv, new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mData.remove(item);
                notifyItemRemoved(viewHolder.getAdapterPosition());
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
                        item.setCheckResult(1);
                        break;
                    case R.id.check_result_verbal_warning_rb:
                        viewHolder.setText(R.id.check_result_tv, "口头警告");
                        item.setCheckResult(2);
                        break;
                    case R.id.check_result_written_corrections_rb:
                        viewHolder.setText(R.id.check_result_tv, "书面整改");
                        item.setCheckResult(3);
                        break;
                }
            }
        });

        EditText checkContentEt = viewHolder.getView(R.id.check_content_et);
        item.setCheckContent(checkContentEt.getText().toString());

        LinearLayout addPicLl = viewHolder.getView(R.id.add_pic_ll);
        ImageView addPicIv = viewHolder.getView(R.id.add_pic_iv);
        viewHolder.setOnClickListener(R.id.add_pic_iv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //photoUri = patrUri(item.getCheckResultId() + "_" + addPicLl.getChildCount());
                startCamera(mActivity, 1003);
                Constant.checkResultId = item.getCheckResultId();
            }
        });

        //设置图片  TODO

        List<CheckResultBean.PictureInfo> bmpList = item.getCheckPics();
        Log.w("linmeng", "bmpList.size():" + bmpList.size());
        if (bmpList.size() > 4) {
            addPicIv.setVisibility(View.GONE);
        } else {
            addPicIv.setVisibility(View.VISIBLE);
        }

        if (bmpList != null && bmpList.size() > 0) {
            addPicLl.removeAllViews();
            for (int i = 0; i < bmpList.size(); i++) {
                Log.w("linmeng", "bmpList.size():" + bmpList.size());
//                if (i > 0) {
//                    addPicLl.removeViewAt(i);
//                }
                ImageView imageView = new ImageView(mActivity);
                imageView.setImageBitmap(bmpList.get(i).getBitmap());
                imageView.setTag(bmpList.get(i).getBitmapId());
                imageView.setPadding(0, 0, 0, 0);
                addPicLl.addView(imageView, new LinearLayout.LayoutParams(180, 180));

            }
        }


    }

    //打开相机
    private void startCamera(Activity activity, int requestCode/*, Uri photoUri*/) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 图片保存路径  这里是在SD卡目录下创建了MyPhoto文件夹
     *
     * @param fileName
     * @return
     */
    private Uri patrUri(String fileName) {  //指定了图片的名字，可以使用时间来命名
        // TODO Auto-generated method stub
        String strPhotoName = fileName + ".jpg";
        String savePath = Environment.getExternalStorageDirectory().getPath()
                + "/MyPhoto/";
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        filePath = savePath + strPhotoName;

        return FileProvider.getUriForFile(mContext.getApplicationContext(),
                "com.patrol.terminal.fileprovider",
                new File(dir, strPhotoName));
    }

}