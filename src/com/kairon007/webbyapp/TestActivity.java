package com.kairon007.webbyapp;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;



public class TestActivity extends FragmentActivity {
	// Create an instance of ActionBar
	ActionBar actionbar;
	Menu customMenu;
	WebView web;
	ProgressBar prgPageLoading;
	String mGetNewsId, mGetNewsTitle;
	String url;
	WebView webby;
	String regId, response;
	Context context;
	public int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static String homepageurl = "http://www.mundoimagenes.me";
	

	String randomToken;
	ProgressDialog pbarDialog;
	
	Button proceedButton;
	

	String defHtml = "file:///android_asset/no_connection_pacuni.html";
	MenuItem miPrev, miNext, miRefresh, miStop;
	private String mActivity;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.actionbar_browser, menu);
		customMenu = menu;
		miPrev = (MenuItem) menu.findItem(R.id.abPrevious);
		miNext = (MenuItem) menu.findItem(R.id.abNext);
		miRefresh = (MenuItem) menu.findItem(R.id.abRefresh);
		miStop = (MenuItem) menu.findItem(R.id.abStop);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onBackPressed() {
		/*
		 * if (webby.copyBackForwardList().getCurrentIndex() > 0) {
		 * webby.goBack(); }
		 */if (web.canGoBack()) {
			web.goBack();
		} else {
			// Your exit alert code, or alternatively line below to finish
			super.onBackPressed(); // finishes activity
		}
	}

	// Listener when item selected Menu in action bar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:
			// Previous page or exit
			/*
			 * Intent i = new Intent(TestActivity.this, TestActivity.class);
			 * startActivity(i); finish();
			 */
			/*
			 * if (web.copyBackForwardList().getCurrentIndex() > 0) {
			 * web.goBack(); } else { // Your exit alert code, or alternatively
			 * line below to finish super.onBackPressed(); // finishes activity
			 * }
			 */
			if (web.canGoBack()) {
				startBrowsinggggg(homepageurl);
			}

			return true;

		case R.id.abPrevious:
			if (web.canGoBack()) {
				web.goBack();
			}
			break;
		case R.id.abNext:
			if (web.canGoForward()) {
				web.goForward();
			}
			break;
		case R.id.abRefresh:
			web.reload();
			break;
		case R.id.abStop:
			web.stopLoading();
			break;
		case R.id.abBrowser:
			Intent iBrowser = new Intent(Intent.ACTION_VIEW);
			iBrowser.setData(Uri.parse(homepageurl));
			startActivity(iBrowser);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_browser);
		// Get ActionBar and set back button on actionbar
		web = (WebView) findViewById(R.id.webView);
		prgPageLoading = (ProgressBar) findViewById(R.id.prgPageLoading);
		actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("Pacuni");
		startBrowsinggggg(homepageurl);

	}

	public class MyWebb extends WebViewClient {
		public Context ctxt;

		@Override
		public void onLoadResource(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onLoadResource(view, url);
			Log.d("ONLOADRESOURCE", "RESOURCE" + url);

		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			Log.d("ONPAGEFINISHED", "FINISH" + url);
			

		}
	}

	public void startBrowsinggggg(final String URLLL) {
		// Connecct view object and xml ids
		
			CookieSyncManager cookieSyncManager = CookieSyncManager
					.createInstance(web.getContext());
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);

			web.setHorizontalScrollBarEnabled(true);
			web.getSettings().setAllowFileAccess(true);
			web.getSettings().setJavaScriptEnabled(true);
			WebSettings mS = web.getSettings();
			mS.setSaveFormData(true);
			mS.setSavePassword(true);
			Log.d("S", web.getSettings().getUserAgentString());
			web.getSettings().setUserAgentString(
					web.getSettings().getUserAgentString() + "android-pacuni");
			web.getSettings().setBuiltInZoomControls(true);
			web.getSettings().setDisplayZoomControls(false);
			setProgressBarVisibility(true);

			// web.getSettings().setBuiltInZoomControls(true);
			web.getSettings().setUseWideViewPort(true);
			web.setInitialScale(1);

			web.loadUrl(URLLL);

			final Activity act = this;
			web.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView webview, int progress) {

					act.setProgress(progress * 100);
					prgPageLoading.setProgress(progress);

				}

			});

			web.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageStarted(WebView view, String url,
						Bitmap favicon) {

					super.onPageStarted(web, url, favicon);
					homepageurl = url;
					prgPageLoading.setVisibility(View.VISIBLE);
					Log.d("HELLO:url:URLLL", url + ":" + URLLL);
					String str = url;
					
					if (url.contains("pacuni.com")) {

						MenuItem item1 = customMenu.findItem(R.id.abPrevious);
						item1.setVisible(false);
						MenuItem item2 = customMenu.findItem(R.id.abNext);
						item2.setVisible(false);
						MenuItem item3 = customMenu.findItem(R.id.abRefresh);
						item3.setVisible(false);
						MenuItem item4 = customMenu.findItem(R.id.abStop);
						item4.setVisible(false);
						MenuItem item5 = customMenu.findItem(R.id.abBrowser);
						item5.setVisible(false);
						actionbar.hide();
					} else {
						MenuItem item1 = customMenu.findItem(R.id.abPrevious);
						item1.setVisible(true);
						MenuItem item2 = customMenu.findItem(R.id.abNext);
						item2.setVisible(true);
						MenuItem item3 = customMenu.findItem(R.id.abRefresh);
						item3.setVisible(true);
						MenuItem item4 = customMenu.findItem(R.id.abStop);
						item4.setVisible(true);
						MenuItem item5 = customMenu.findItem(R.id.abBrowser);
						item5.setVisible(true);
						actionbar.show();
					}

				}

				@Override
				public void onPageFinished(WebView view, String url) {

					super.onPageFinished(web, url);
					String str = url;
					
					prgPageLoading.setProgress(0);
					prgPageLoading.setVisibility(View.GONE);

					if (web.canGoBack()) {
						miPrev.setEnabled(true);
						miPrev.setIcon(R.drawable.ic_action_previous_item);
					} else {
						miPrev.setEnabled(false);
						miPrev.setIcon(R.drawable.ic_action_previous_item_disabled);
					}

					if (web.canGoForward()) {
						miNext.setEnabled(true);
						miNext.setIcon(R.drawable.ic_action_next_item);
					} else {
						miNext.setEnabled(false);
						miNext.setIcon(R.drawable.ic_action_next_item_disabled);
					}
				}

				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) {
					Toast.makeText(act, description, Toast.LENGTH_SHORT).show();
				}

			});
		}

	

	/*
	 * public void startBrowsingggg(String URLL) { Log.d("HELLO", URLL); webby =
	 * (WebView) findViewById(R.id.webView); prgPageLoading = (ProgressBar)
	 * findViewById(R.id.prgPageLoading); // webby.setWebViewClient(new
	 * WebViewClient()); webby.setWebViewClient(new MyWebb());
	 * webby.setWebChromeClient(new WebChromeClient() { public void
	 * onProgressChanged(WebView view, int progress) { setProgress(progress *
	 * 100); } }); setProgressBarVisibility(true); webby.setWebChromeClient(new
	 * WebChromeClient() { public void onProgressChanged(WebView view, int
	 * progress) { //Make the bar disappear after URL is loaded, and changes
	 * string to Loading... setTitle("Loading..."); setProgress(progress * 100);
	 * //Make the bar disappear after URL is loaded
	 * 
	 * // Return the app name after finish loading if(progress == 100)
	 * setTitle(R.string.app_name); setProgress(progress*100);
	 * prgPageLoading.setProgress(progress); } });
	 * webby.getSettings().setSavePassword(true);
	 * webby.getSettings().setUseWideViewPort(true);
	 * webby.getSettings().setLoadWithOverviewMode(true);
	 * webby.getSettings().setJavaScriptEnabled(true);
	 * webby.setDownloadListener(new DownloadListener() { public void
	 * onDownloadStart(String url, String userAgent, String contentDisposition,
	 * String mimetype, long contentLength) { Intent i = new
	 * Intent(Intent.ACTION_VIEW); i.setData(Uri.parse(url)); startActivity(i);
	 * } }); // webby.loadUrl("file:///android_asset/contact_us.html");
	 * webby.loadUrl(URLL); }
	 */



	}

	/**
	 * Stores the registration id, app versionCode, and expiration time in the
	 * application's {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration id
	 */
	

	/**
	 * Gets the current registration id for application on GCM service.
	 * <p>
	 * If result is empty, the registration has failed.
	 * 
	 * @return registration id, or empty string if the registration is not
	 *         complete.
	 */
	

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration id, app versionCode, and expiration time in the
	 * application's shared preferences.
	 */


