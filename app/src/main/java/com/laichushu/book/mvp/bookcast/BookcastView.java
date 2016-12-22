package com.laichushu.book.mvp.bookcast;

import com.laichushu.book.bean.netbean.BookDetailsModle;
import com.laichushu.book.mvp.home.HomeHotModel;

/**
 * Created by PCPC on 2016/11/21.
 */

public interface BookcastView {
    void getDataSuccess(HomeHotModel model);

    void getCollectionDataSuccess(HomeHotModel model);

    void getBookDetailsByIdDataSuccess(BookDetailsModle modle);

    void getDataFail(String msg);

    void showDialog();

    void dismissDialog();
}
