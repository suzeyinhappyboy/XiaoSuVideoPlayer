package com.example.videoplayer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webviewtest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webvideo);
		MyWebView = (WebView)findViewById(R.id.webvideo_webview);
		MyWebView.getSettings().setJavaScriptEnabled(true);
		MyWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
			
		});
		MyWebView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}
			
		});
		MyWebView.setDownloadListener(new DownloadListener() {
			
			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype, long contentLength) {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		MyWebView.loadUrl("http://192.168.253.1/video/");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(MyWebView.canGoBack()){
				MyWebView.goBack();
				return true;
			}
		}else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			if (MyWebView.canGoForward()) {
				MyWebView.goForward();
			}
			return true;
		}else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			if (MyWebView.canGoBack()) {
				MyWebView.goBack();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	private WebView MyWebView;
}
