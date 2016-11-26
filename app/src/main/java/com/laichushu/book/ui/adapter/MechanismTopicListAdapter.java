package com.laichushu.book.ui.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laichushu.book.R;
import com.laichushu.book.mvp.mechanismtopiclist.MechanismTopicListModel;
import com.laichushu.book.ui.activity.TopicDetilActivity;
import com.laichushu.book.utils.GlideUitl;
import com.laichushu.book.utils.UIUtil;

import java.util.ArrayList;

/**
 * 机构话题列表
 * Created by wangtong on 2016/11/26.
 */

public class MechanismTopicListAdapter extends RecyclerView.Adapter<MechanismTopicListAdapter.ViewHolder> {
    private ArrayList<MechanismTopicListModel.DataBean> mData;
    private Activity mActivity;
    public MechanismTopicListAdapter(ArrayList<MechanismTopicListModel.DataBean> mData, Activity mActivity) {
        this.mData = mData;
        this.mActivity = mActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = UIUtil.inflate(R.layout.item_mechanismtopiclist);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
        final MechanismTopicListModel.DataBean dataBean = mData.get(position);
        holder.topicNameTv.setText(dataBean.getTitle());
        holder.topicAuthorTv.setText(dataBean.getCreatUserName());
        holder.topicContentTv.setText(dataBean.getContent());
        holder.topicTiemTv.setText(dataBean.getCreateDate());
        GlideUitl.loadRandImg(mActivity,dataBean.getCreaterPhoto(),holder.topicUserheadIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("bean",dataBean);
                UIUtil.openActivity(mActivity,TopicDetilActivity.class,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView topicUserheadIv;
        private TextView topicAuthorTv;
        private TextView topicNameTv;
        private TextView topicContentTv;
        private TextView topicTiemTv;

        public ViewHolder(View itemView) {
            super(itemView);
            topicUserheadIv = (ImageView) itemView.findViewById(R.id.iv_topic_userhead);
            topicAuthorTv = (TextView) itemView.findViewById(R.id.tv_topic_author);
            topicContentTv = (TextView) itemView.findViewById(R.id.tv_topic_content);
            topicTiemTv = (TextView) itemView.findViewById(R.id.tv_topic_time);
            topicNameTv = (TextView) itemView.findViewById(R.id.tv_topic_name);
        }
    }

    public ArrayList<MechanismTopicListModel.DataBean> getmData() {
        return mData;
    }

    public void setmData(ArrayList<MechanismTopicListModel.DataBean> mData) {
        this.mData = mData;
    }
}
