package com.sofacity.laichushu.ui.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sofacity.laichushu.R;
import com.sofacity.laichushu.mvp.home.HomeHotModel;
import com.sofacity.laichushu.ui.activity.BookDetailActivity;
import com.sofacity.laichushu.ui.activity.CategoryListActivity;
import com.sofacity.laichushu.utils.GlideUitl;
import com.sofacity.laichushu.utils.UIUtil;

import java.util.ArrayList;

/**
 * 分类详情列表
 * Created by wt on 2016/11/10.
 */

public class CaregoryListAdapter extends RecyclerView.Adapter<CaregoryListAdapter.ViewHolder> {

    private CategoryListActivity mActivity;
    private ArrayList<HomeHotModel.DataBean> mAllData;

    public CaregoryListAdapter(CategoryListActivity mActivity, ArrayList<HomeHotModel.DataBean> mAllData) {
        this.mActivity = mActivity;
        this.mAllData = mAllData;
    }

    @Override
    public CaregoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = UIUtil.inflate(R.layout.item_home_book);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CaregoryListAdapter.ViewHolder holder, int position) {
        final HomeHotModel.DataBean dataBean = mAllData.get(position);
        GlideUitl.loadImg(mActivity, dataBean.getCoverUrl(), holder.bookIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转图书详情页
                Bundle bundle = new Bundle();
                bundle.putParcelable("bean", dataBean);
                UIUtil.openActivity(mActivity, BookDetailActivity.class, bundle);
            }
        });
        holder.titleTv.setText(dataBean.getArticleName());
        holder.typeTv.setText(dataBean.getTopCategoryName());
        holder.authorTv.setText(dataBean.getAuthorName());
        holder.numRb.setRating(dataBean.getLevel());
        holder.commentTv.setText("(" + dataBean.getCommentNum() + "评论)");
        holder.wordTv.setText("约" + dataBean.getWordNum());
        holder.moneyTv.setText(dataBean.getAwardMoney() + "元");
        holder.rewardTv.setText("(" + dataBean.getAwardNum() + "人打赏)");
        holder.markTv.setText(dataBean.getScore() + "分");
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mAllData == null ? 0 : mAllData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView bookIv;
        private TextView titleTv;
        private TextView typeTv;
        private RatingBar numRb;
        private TextView markTv;
        private TextView commentTv;
        private TextView authorTv;
        private TextView wordTv;
        private TextView moneyTv;
        private TextView rewardTv;
        private View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            bookIv = (ImageView) itemView.findViewById(R.id.iv_book);
            titleTv = (TextView) itemView.findViewById(R.id.tv_title);
            typeTv = (TextView) itemView.findViewById(R.id.tv_type);
            numRb = (RatingBar) itemView.findViewById(R.id.ratbar_num);
            markTv = (TextView) itemView.findViewById(R.id.tv_mark);
            commentTv = (TextView) itemView.findViewById(R.id.tv_comment);
            authorTv = (TextView) itemView.findViewById(R.id.tv_author);
            wordTv = (TextView) itemView.findViewById(R.id.tv_word);
            moneyTv = (TextView) itemView.findViewById(R.id.tv_money);
            rewardTv = (TextView) itemView.findViewById(R.id.tv_reward);
        }
        
    }

    public ArrayList<HomeHotModel.DataBean> getmAllData() {
        return mAllData;
    }

    public void setmAllData(ArrayList<HomeHotModel.DataBean> mAllData) {
        this.mAllData = mAllData;
    }
}
