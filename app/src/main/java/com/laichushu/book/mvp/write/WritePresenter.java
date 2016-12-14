package com.laichushu.book.mvp.write;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.laichushu.book.R;
import com.laichushu.book.bean.JsonBean.RewardResult;
import com.laichushu.book.bean.netbean.ArticleBookList_Paramet;
import com.laichushu.book.bean.netbean.ArticleVote_Paramet;
import com.laichushu.book.bean.netbean.DeleteNewBook_Paramet;
import com.laichushu.book.bean.netbean.MyArticBooklist_paramet;
import com.laichushu.book.bean.netbean.MySignEditor_paramet;
import com.laichushu.book.bean.netbean.PublishNewBook_Paramet;
import com.laichushu.book.bean.netbean.SignStateResult;
import com.laichushu.book.bean.netbean.UpdateBookPermission_Paramet;
import com.laichushu.book.global.ConstantValue;
import com.laichushu.book.mvp.home.HomeHotModel;
import com.laichushu.book.retrofit.ApiCallback;
import com.laichushu.book.ui.activity.CreateNewBookActivity;
import com.laichushu.book.ui.activity.MainActivity;
import com.laichushu.book.ui.activity.ManageWorksActivity;
import com.laichushu.book.ui.base.BasePresenter;
import com.laichushu.book.ui.widget.SpinerPopWindow;
import com.laichushu.book.utils.LoggerUtil;
import com.laichushu.book.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * 写书 presenter
 * Created by wangtong on 2016/11/16.
 */
public class WritePresenter extends BasePresenter<WriteView> {
    private String pageNo = "1";
    private String userId = ConstantValue.USERID;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    private String permission = "1";

    //初始化构造
    public WritePresenter(WriteView view) {
        attachView(view);
    }

    public void getArticleBookList() {
        LoggerUtil.e("获取创作列表");
        ArticleBookList_Paramet paramet = new ArticleBookList_Paramet(ConstantValue.USERID, pageNo, ConstantValue.PAGESIZE1, "");
        LoggerUtil.toJson(paramet);
        addSubscription(apiStores.getArticleBookList(paramet), new ApiCallback<HomeHotModel>() {
            @Override
            public void onSuccess(HomeHotModel model) {
                mvpView.getDataSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail("code:" + code + "\nmsg:" + msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 删除
     *
     * @param articleId
     */
    public void deleteBook(String articleId, final int position) {
        mvpView.showLoading();
        LoggerUtil.e("删除新书");
        DeleteNewBook_Paramet paramet = new DeleteNewBook_Paramet(articleId, ConstantValue.USERID);
        addSubscription(apiStores.deleteNewBook(paramet), new ApiCallback<RewardResult>() {
            @Override
            public void onSuccess(RewardResult model) {
                mvpView.deleteNewBook(model, position);
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail2("code:" + code + "\nmsg:" + msg);
            }

            @Override
            public void onFinish() {
                mvpView.hideLoading();
            }
        });
    }

    /**
     * 发表
     *
     * @param articleId
     * @param type
     * @param index
     */
    public void publishNewBook(String articleId, final String type, final int index) {
        mvpView.showLoading();
        LoggerUtil.e("发表");
        PublishNewBook_Paramet paramet = new PublishNewBook_Paramet(articleId, userId, type);
        addSubscription(apiStores.publishNewBook(paramet), new ApiCallback<RewardResult>() {
            @Override
            public void onSuccess(RewardResult model) {
                mvpView.publishNewBook(model, index, type);
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail4("code:" + code + "\nmsg:" + msg);
            }

            @Override
            public void onFinish() {
                mvpView.hideLoading();
            }
        });
    }

    /**
     * 选择权限 对话框
     *
     * @param mActivity
     * @param dataBean
     */
    public void openPermissionAlertDialog(Activity mActivity, final HomeHotModel.DataBean dataBean) {

        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(mActivity);
        final View customerView = UIUtil.inflate(R.layout.dialog_permission);
        RadioButton publicRbn = (RadioButton) customerView.findViewById(R.id.rbn_public);
        RadioButton privateRbn = (RadioButton) customerView.findViewById(R.id.rbn_private);
        RadioButton masterRbn = (RadioButton) customerView.findViewById(R.id.rbn_master);
        RadioButton pressRbn = (RadioButton) customerView.findViewById(R.id.rbn_press);
        RadioButton fansRbn = (RadioButton) customerView.findViewById(R.id.rbn_fans);
        ImageView cancelBtn = (ImageView) customerView.findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        publicRbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPermission("1");
//                permissionTv.setText("默认公开");
                loadUpdateBookPermission(dataBean.getActivityId(), getPermission());
                dialogBuilder.dismiss();

            }
        });
        privateRbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPermission("2");
//                permissionTv.setText("仅个人");
                loadUpdateBookPermission(dataBean.getArticleId(), getPermission());
                dialogBuilder.dismiss();
            }
        });
        masterRbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPermission("3");
//                permissionTv.setText("编辑");
                loadUpdateBookPermission(dataBean.getArticleId(), getPermission());
                dialogBuilder.dismiss();
            }
        });
        pressRbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPermission("4");
//                permissionTv.setText("出版社");
                loadUpdateBookPermission(dataBean.getArticleId(), getPermission());
                dialogBuilder.dismiss();
            }
        });
        fansRbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPermission("5");
//                permissionTv.setText("粉丝");
                loadUpdateBookPermission(dataBean.getArticleId(), getPermission());
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder
                .withTitle(null)                                  // 为null时不显示title
                .withDialogColor("#FFFFFF")                       // 设置对话框背景色                               //def
                .isCancelableOnTouchOutside(true)                 // 点击其他地方或按返回键是否可以关闭对话框
                .withDuration(500)                                // 对话框动画时间
                .withEffect(Effectstype.Slidetop)                 // 动画形式
                .setCustomView(customerView, mActivity)                // 添加自定义View
                .show();
    }

    public void loadUpdateBookPermission(String id, String permission) {
        mvpView.showLoading();
        LoggerUtil.e("发表");
        UpdateBookPermission_Paramet bookParamet = new UpdateBookPermission_Paramet(userId, id, permission);
        addSubscription(apiStores.getUpdateBookPermissionDetails(bookParamet), new ApiCallback<RewardResult>() {
            @Override
            public void onSuccess(RewardResult model) {
                mvpView.updateBookPermission(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail4("code:" + code + "\nmsg:" + msg);
            }

            @Override
            public void onFinish() {
                mvpView.hideLoading();
            }
        });
    }
//

    /**
     * 修改签约状态
     */
    public void getSignStateDeta(final String articleId) {
        mvpView.showLoading();
        LoggerUtil.e("修改签约状态");
        addSubscription(apiStores.getSignStateDetails(new MyArticBooklist_paramet("", "", "", "")), new ApiCallback<SignStateResult>() {
            @Override
            public void onSuccess(SignStateResult model) {
                mvpView.getSignStateDeteSuccess(model, articleId);
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail4("code:" + code + "\nmsg:" + msg);
            }

            @Override
            public void onFinish() {
                mvpView.hideLoading();
            }
        });
    }

    /**
     * 修改签约状态
     */
    public void getSignEditorDeta(String pressId, String articleId, String editorId) {
        mvpView.showLoading();
        LoggerUtil.e("修改签约状态");
        MySignEditor_paramet editParamet = new MySignEditor_paramet(pressId, articleId, editorId, userId);
        addSubscription(apiStores.getSignEditorDetails(editParamet), new ApiCallback<RewardResult>() {
            @Override
            public void onSuccess(RewardResult model) {
                mvpView.getSignEditorDataSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.getDataFail4("code:" + code + "\nmsg:" + msg);
            }

            @Override
            public void onFinish() {
                mvpView.hideLoading();
            }
        });
    }

    //删除确认
    public void loadDelete(Activity mActivity, final String articleId, final int position) {
        final String[] sex = new String[1];
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mActivity);
        final int[] posion = new int[1];
        builder.setTitle("")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBook(articleId, position);
                    }
                })
                .show();
    }
}
