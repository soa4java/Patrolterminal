package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.CommitDefectActivity;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.bean.PatrolLevel3;
import com.patrol.terminal.bean.PatrolLevel4;

import java.util.List;

public class SpecialContentAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
    public static final int TYPE_4 = 4;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    public SpecialContentAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_1, R.layout.item_patrol_content_1);
        addItemType(TYPE_2, R.layout.item_patrol_content_1_2);
        addItemType(TYPE_3, R.layout.item_patrol_content_1_3);
        addItemType(TYPE_4, R.layout.item_patrol_content_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_1:
                PatrolLevel1 item1 = (PatrolLevel1) item;
                helper.setText(R.id.tv_title, item1.getTitle());
                if (item1.getSubItems() == null || item1.getSubItems().size() == 0) {
                    helper.getView(R.id.iv_expand).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.iv_expand).setVisibility(View.VISIBLE);
                    helper.setImageResource(R.id.iv_expand, item1.isExpanded() ? R.mipmap.next : R.mipmap.btn_down);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = helper.getAdapterPosition();
                            if (item1.isExpanded()) {
                                collapse(pos);
                            } else {
                                expand(pos);
//      }
                            }
                        }
                    });
                }
                break;
            case TYPE_2:
                PatrolLevel2 item2 = (PatrolLevel2) item;
                helper.setText(R.id.tv_title, item2.getName());
                if (item2.getSubItems() == null || item2.getSubItems().size() == 0) {
                    helper.getView(R.id.iv_expand).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.iv_expand).setVisibility(View.VISIBLE);
                    helper.setImageResource(R.id.iv_expand, item2.isExpanded() ? R.mipmap.next : R.mipmap.btn_down);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = helper.getAdapterPosition();
                            if (item2.isExpanded()) {
                                collapse(pos);
                            } else {
                                expand(pos);
//      }
                            }
                        }
                    });
                }
                break;
            case TYPE_3:
                PatrolLevel3 item3 = (PatrolLevel3) item;
                helper.setText(R.id.tv_title, item3.getName());
                if (item3.getSubItems() == null || item3.getSubItems().size() == 0) {
                    helper.getView(R.id.iv_expand).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.iv_expand).setVisibility(View.VISIBLE);
                    helper.setImageResource(R.id.iv_expand, item3.isExpanded() ? R.mipmap.next : R.mipmap.btn_down);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = helper.getAdapterPosition();
                            if (item3.isExpanded()) {
                                collapse(pos);
                            } else {
                                expand(pos);
//      }
                            }
                        }
                    });
                }
                break;
            case TYPE_4:
                PatrolLevel4 item4 = (PatrolLevel4) item;
                helper.setText(R.id.tv_content, item4.getName());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, CommitDefectActivity.class);
                        intent.putExtra("isPatrol", false);
                        intent.putExtra("name", item4.getName());
                        intent.putExtra("category", item4.getCategory());
//                        intent.putExtra("listLevel3", (Serializable) listLevel3);
//                        intent.putExtra("listLevel4", (Serializable) listLevel4);
                        intent.putExtra("id", item4.getId());
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }
}
