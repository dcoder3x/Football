package com.app.football.main.whatshot.detail;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * Created by Administrator on 2017/3/17.
 *
 */

class DetailPresenter implements DetailContract.Presenter {
    private static final String TAG = "DetailPresenter";
    DetailContract.View mView;
    DetailPresenter(DetailContract.View view) {
        mView = view;
    }
    @Override
    public void init(String url) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                Document doc;
                String html;
                try {
                    doc = Jsoup.connect(params[0]).get();
                    modifyData(doc);
                    html = doc.html();
                } catch (IOException e) {
                    // TODO
                    html = "<p>出错了</p>";
                    e.printStackTrace();
                }
                return html;
            }

            @Override
            protected void onPostExecute(String s) {
                mView.showWeb(s);
            }
        }.execute(url);
    }

    private void modifyData(Document doc) {
        Elements header = doc.getElementsByTag("header");
        header.remove();

        Elements infos = doc.getElementsByClass("infos");
        infos.remove();

        Elements footer = doc.getElementsByTag("footer");
        footer.remove();

        Element agreement = doc.getElementById("agreement");
        if (agreement != null) {
            agreement.remove();
        }

        // 无法处理pptv或letv的视频请求，所以移除掉
        Elements site = doc.getElementsByAttribute("site");
        site.remove();

        Elements img = doc.getElementsByTag("img");
        if (!img.isEmpty()) {
            img.get(0).remove();
        }


        // 去除js操作，直接显示gif,在gif过多时会卡。为什么通过懂球帝js控制显示gif会卡?
     /*   Elements dataGifSrc = doc.getElementsByClass("data-gif-src");
        for (Element e : dataGifSrc) {
            String str = e.attr("orig-src");
            e.attr("src", str);
            e.removeAttr("orig-src");
            Log.d("orig-src", "removed");
        }
        dataGifSrc.removeAttr("data-src");
        dataGifSrc.removeAttr("data-gif-src");
        dataGifSrc.removeAttr("class");*/
    }
}
