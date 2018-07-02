package com.demo.scenicspot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.demo.demo.myapplication.R;
import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：宋玉磊 on 2016/8/2 0002 17:13
 */
public class WebViewActivity extends Activity {
    @Bind(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //webview.loadUrl(URL.CYCLE_FRIST_WEB + "?id=" + getIntent().getIntExtra("url", 0));
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
}
