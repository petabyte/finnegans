package org.finnegans;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
/**
 * 
 * @author georgesanchez
 *
 */
public class WebViewActivity extends Activity {
	WebView myWebView;
	private static final String finnegans_url="http://finnegans.org/beer-locator-mobile/";
	private static final String progressMessage="Getting beer locations...";
	ProgressDialog progressDialog;
	WebViewClient webViewClient;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //Web View
        myWebView = (WebView) findViewById(R.id.finnegansWebView);
        myWebView.setVerticalScrollBarEnabled(false);
        myWebView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewClient = new WebViewClient(){
        	 public boolean shouldOverrideUrlLoading(WebView view, String url) {              
                 view.loadUrl(url);
                 return true;
             }
             public void onLoadResource (WebView view, String url) {
            	 if(progressDialog == null){
            	       progressDialog = new ProgressDialog(WebViewActivity.this);
            	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            	        progressDialog.setMessage(progressMessage);
            	        progressDialog.setIndeterminate(true);
            	        progressDialog.show();
            	 }
                	 
             }
             public void onPageFinished(WebView view, String url) {
                 if (progressDialog.isShowing()) {
                     progressDialog.dismiss();
                 }
             } 
        };
        myWebView.setWebViewClient(webViewClient);         
        myWebView.loadUrl(finnegans_url);
        //Refresh Button
        ImageButton refreshButton = (ImageButton) findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				refresh();
			}
		});
        //Back Button
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				back();
				
			}
		});
       
    }
    /**
     * 
     */
    void back(){
    	WebViewActivity.this.myWebView.loadUrl(finnegans_url);
    }
    
    /**
     * 
     */
    void refresh(){
    	WebViewActivity.this.myWebView.loadUrl("javascript:window.location.reload( true )");
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	 getMenuInflater().inflate(R.menu.activity_web_view, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.refresh_item:
            refresh();
            return true;
        case R.id.back_item:
            back();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
}
