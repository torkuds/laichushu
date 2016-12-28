package com.laichushu.book.mvp.findeditmypage;

import com.laichushu.book.bean.JsonBean.RewardResult;
import com.laichushu.book.bean.netbean.FindEditorInfoModel;
import com.laichushu.book.mvp.allcomment.SendCommentMoudle;
import com.laichushu.book.mvp.campaign.AuthorWorksModle;
import com.laichushu.book.mvp.home.HomeHotModel;
import com.laichushu.book.mvp.topicdetail.TopicdetailModel;

/**
 * Created by PCPC on 2016/12/24.
 */

public interface FindEditMyPageView {

    void getEditorInfoDataSuccess(FindEditorInfoModel model);

    void getSendDataSuccess(RewardResult model);
    void getEditorFindArticleDataSuccess(HomeHotModel model);

    void SaveScoreLikeData(RewardResult model, String type);

    void getEditorCommentListDataSuccess(TopicdetailModel model);

    void getAuthorWorksDataSuccess(AuthorWorksModle model);
    void getArticleVoteDataSuccess(RewardResult model);
    void getSendMsgToPartyDataSuccess(RewardResult model);

    void getDataFail(String msg);

    void getDataFail5(String msg);

    void showDialog();

    void dismissDialog();
}
