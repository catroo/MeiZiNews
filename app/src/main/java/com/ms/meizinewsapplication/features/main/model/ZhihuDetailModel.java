package com.ms.meizinewsapplication.features.main.model;

import android.content.Context;

import com.ms.meizinewsapplication.features.main.json.ZhihuDetail;
import com.ms.meizinewsapplication.features.main.main_web.MainApi;
import com.ms.meizinewsapplication.features.main.main_web.ZhiHuNews;
import com.ms.meizinewsapplication.features.base.utils.tool.DebugUtil;
import com.ms.retrofitlibrary.util.RxJavaUtil;
import com.ms.retrofitlibrary.web.MyGsonRetrofit;
import com.ms.retrofitlibrary.web.MyOkHttpClient;

import org.loader.model.CommonModel;
import org.loader.model.OnModelListener;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 啟成 on 2016/3/11.
 */
public class ZhihuDetailModel implements CommonModel<ZhihuDetail> {

    private String detailID;

    public void loadWeb(Context context, final OnModelListener<ZhihuDetail> listener, String detailID) {

        MyGsonRetrofit.getMyGsonRetrofit().init(context, MainApi.ZHI_HU_NEWS);
        this.detailID = detailID;

        loadWeb(context, listener);
    }


    @Override
    public void loadWeb(Context context, final OnModelListener<ZhihuDetail> listener) {
        ZhiHuNews zhiHuNews = MyGsonRetrofit.getMyGsonRetrofit().getCreate(ZhiHuNews.class);

        Observable<ZhihuDetail> mZhihuDetail = zhiHuNews.RxZhihuDetail(MyOkHttpClient.getCacheControl(context), detailID);
        RxJavaUtil.rxIoAndMain(mZhihuDetail, new Subscriber<ZhihuDetail>() {
                    @Override
                    public void onCompleted() {
                        listener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.debugLogErr(e, "ZhihuDetailModel+++++\n" + e.toString());
                        listener.onError(e.toString());
                    }

                    @Override
                    public void onNext(ZhihuDetail zhihuDetail) {

                        DebugUtil.debugLogD(zhihuDetail.toString());
                        listener.onSuccess(zhihuDetail);
                    }

                }
        );
    }

}
