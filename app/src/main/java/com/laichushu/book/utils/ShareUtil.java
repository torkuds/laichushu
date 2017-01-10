package com.laichushu.book.utils;

import android.content.Context;

import com.laichushu.book.R;
import com.laichushu.book.global.ConstantValue;
import com.laichushu.book.ui.share.onekeyshare.OnekeyShare;

/**
 * Created by PCPC on 2017/1/10.
 */

public class ShareUtil {

    /**
     * @param context
     * @param bookDetailUrl
     * @param imgUrl
     * @param qqContent
     * @param rootName
     */
    //share
    public static void showShare(Context context, String linkUrl,
                                  String bookDetailUrl, String imgUrl, String qqContent,
                                  String rootName) {
        OnekeyShare oks = null;
        if (oks == null)
            oks = new OnekeyShare();
        //关闭sso授权
        oks.setSilent(true);
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("来出书");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(linkUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(context.getResources().getString(R.string.app_introduce_details) + bookDetailUrl);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imgUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(linkUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(qqContent);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(rootName);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(ConstantValue.API_LAICHUSHU_URL);
// 启动分享GUI
        oks.show(context);
    }
}
