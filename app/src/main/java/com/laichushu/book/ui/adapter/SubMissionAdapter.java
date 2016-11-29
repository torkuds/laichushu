package com.laichushu.book.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laichushu.book.R;
import com.laichushu.book.bean.netbean.MessageCommentResult;
import com.laichushu.book.utils.GlideUitl;
import com.laichushu.book.utils.ToastUtil;
import com.laichushu.book.utils.UIUtil;

import java.util.List;

/**
 * Created by PCPC on 2016/11/29.
 */

public class SubMissionAdapter extends RecyclerView.Adapter<SubMissionAdapter.ViewHolder> {
    private Context context;
    private List<MessageCommentResult.DataBean> dataBeen;

    public SubMissionAdapter(Context context, List<MessageCommentResult.DataBean> dataBean) {
        this.context = context;
        this.dataBeen = dataBean;
    }

    @Override
    public SubMissionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = UIUtil.inflate(R.layout.item_msg_submission);
        return new SubMissionAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubMissionAdapter.ViewHolder holder, final int position) {
        GlideUitl.loadImg(context, "", holder.ivHead);
        holder.tvBookName.setText(dataBeen.get(position).getSenderName());
         holder.tvPubName.setText(dataBeen.get(position).getContent());
//        if(dataBeen.get(position).getStatus().equals("true")){
//            holder.status.setText("通过审核");
//            holder.status.setTextColor(context.getResources().getColor(R.color.red2));
//        }else{
//            holder.status.setText("审核中");
//            holder.status.setTextColor(context.getResources().getColor(R.color.Grey));
//        }

        holder.ivReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("回复消息！");
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataBeen == null ? 0 : dataBeen.size();
    }

    public void refreshAdapter(List<MessageCommentResult.DataBean> listData) {
        dataBeen.clear();
        if (listData.size() > 0) {
            dataBeen.addAll(listData);
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout llItem;
        public final TextView tvPubName, tvBookName;
        public final ImageView ivHead, ivReplay;
        public Button status;
        public final View root;

        public ViewHolder(View root) {
            super(root);
            llItem = (LinearLayout) root.findViewById(R.id.ll_item);
            tvPubName = (TextView) root.findViewById(R.id.tv_publishName);
            tvBookName = (TextView) root.findViewById(R.id.tv_bookName);
            ivHead=(ImageView)root.findViewById(R.id.iv_subHead);
            ivReplay=(ImageView)root.findViewById(R.id.iv_replayMsg);
            status = (Button) root.findViewById(R.id.btn_status);
            this.root = root;
        }
    }
}
