package ca.brockhome.brockhome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.CookieManager;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class MainActivity extends Activity {

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);

        webView.loadUrl("https://brockhome.ca/forum.php?mod=guide&view=newthread&mobile=2");

        webView.setWebViewClient(new WebViewClient() {
            // 当点击链接时,覆盖窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                view.loadUrl(url);// 加载url
                return super.shouldOverrideUrlLoading(view, url);
                //webView.loadUrl(webView.getUrl());
                //return true;
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// 启用JS脚本
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setBlockNetworkImage(false);
        webSettings.setSupportZoom(true); // 可以缩放
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setHorizontalScrollBarEnabled(false);//水平滑动条不显示
        webSettings.setBuiltInZoomControls(true); // 显示放大缩小
        webSettings.setAllowFileAccess(true);// 设置可以访问缓存文件
        webSettings.setAppCacheEnabled(true);//应用可以有缓存
        webSettings.setJavaScriptEnabled(true); //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        //webSettings.setUserAgentString(MyApplication.getUserAgent());
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true); //开启Application Cache存储机制
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        // 修改ua使得web端正确判断
        String ua = webView.getSettings().getUserAgentString();
        webSettings.setUserAgentString(ua+"; brockhome");
        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        webSettings.setSaveFormData(true);    //设置webview保存表单数据
        webSettings.setSavePassword(true);    //设置webview保存密码
        webSettings.setAllowFileAccess(true);// 设置可以访问文件
        webView.setLongClickable(true);
        webView.setDrawingCacheEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            // 当WebView进度改变时更新窗口进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                // 自己实现
                super.onProgressChanged(view, newProgress);
            }
        });


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && webView.canGoBack()) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    webView.goBack();//返回上个页面
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}




