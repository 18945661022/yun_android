package com.example.yun;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.util.Log;


/**
 * 
 * 鐢ㄤ簬灏佽&绠�鍖杊ttp閫氫俊
 * 
 */
public class PostRequest implements Runnable {
    
    private static final int NO_SERVER_ERROR=1000;
    //鏈嶅姟鍣ㄥ湴鍧�
    public static final String URL =  "http://android.vividworld.cn/CommController";
    //涓�浜涜姹傜被鍨�
    public final static String ADD = "/add";
    public final static String UPDATE = "/update";
    public final static String PING = "/ping";
    //涓�浜涘弬鏁�
    private static int connectionTimeout = 60000;
    private static int socketTimeout = 60000;
    //绫婚潤鎬佸彉閲�
    private static HttpClient httpClient=new DefaultHttpClient();
    private static ExecutorService executorService=Executors.newCachedThreadPool();
    private static Handler handler = new Handler();
    //鍙橀噺
    private String strResult;
    private HttpPost httpPost;
    private HttpResponse httpResponse;
    private OnReceiveDataListener onReceiveDataListener;
    private int statusCode;
    private String methodName;

    /**
     * 鏋勯�犲嚱鏁帮紝鍒濆鍖栦竴浜涘彲浠ラ噸澶嶄娇鐢ㄧ殑鍙橀噺
     */
    public PostRequest() {
        strResult = null;
        httpResponse = null;
        httpPost = new HttpPost();
//        httpPost = new HttpPost(URL);
    }
    
    /**
     * 娉ㄥ唽鎺ユ敹鏁版嵁鐩戝惉鍣�
     * @param listener
     */
    public void setOnReceiveDataListener(OnReceiveDataListener listener) {
        onReceiveDataListener = listener;
    }

    /**
     * 鏍规嵁涓嶅悓鐨勮姹傜被鍨嬫潵鍒濆鍖杊ttppost
     * 
     * @param requestType
     *            璇锋眰绫诲瀷
     * @param nameValuePairs
     *            闇�瑕佷紶閫掔殑鍙傛暟
     */
    public void iniRequest(String requestType, JSONObject jsonObject) {
//        httpPost.addHeader("Content-Type", "text/json");
        httpPost.addHeader("charset", "UTF-8");

        httpPost.addHeader("Cache-Control", "no-cache");
        HttpParams httpParameters = httpPost.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                connectionTimeout);
        HttpConnectionParams.setSoTimeout(httpParameters, socketTimeout);
        httpPost.setParams(httpParameters);
        try {
            httpPost.setURI(new URI(URL + requestType));
            httpPost.setEntity(new StringEntity(jsonObject.toString(),
                    HTTP.UTF_8));
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 鏍规嵁涓嶅悓鐨勮姹傜被鍨嬫潵鍒濆鍖杊ttppost
     * 
     * @param requestType
     *            璇锋眰绫诲瀷
     * @param nameValuePairs
     *            闇�瑕佷紶閫掔殑鍙傛暟
     */
    public void iniRequest(String requestType, List<NameValuePair> params,String methodName) {
//        httpPost.addHeader("Content-Type", "text/json");
    	this.methodName = methodName;
        httpPost.addHeader("charset", "UTF-8");
        httpPost.addHeader("Cache-Control", "no-cache");
        HttpParams httpParameters = httpPost.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                connectionTimeout);
        HttpConnectionParams.setSoTimeout(httpParameters, socketTimeout);
        httpPost.setParams(httpParameters);
        try {
        	Log.i("Unity", URL);
            httpPost.setURI(new URI(URL + requestType));
            httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
//            httpPost.
        } 
        catch (URISyntaxException e1) {
            e1.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }    
    
    

    /**
     * 鏂板紑涓�涓嚎绋嬪彂閫乭ttp璇锋眰
     */
    public void execute() {
        executorService.execute(this);
        Log.i("Unity", "exeute");
    }

    /**
     * 妫�娴嬬綉缁滅姸鍐�
     * 
     * @return true is available else false
     */
    public static boolean checkNetState(Activity activity) {
        ConnectivityManager connManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 鍙戦�乭ttp璇锋眰鐨勫叿浣撴墽琛屼唬鐮�
     */

	@Override
    public void run() {
        httpResponse = null;
        Log.i("Unity", "run!!!!!!!!!!!!!!!!");
        try {
            httpResponse = httpClient.execute(httpPost);
            strResult = EntityUtils.toString(httpResponse.getEntity());
            Log.i("Unity", "strResult="+strResult);

        } catch (ClientProtocolException e1) {
            strResult = null;
            e1.printStackTrace();
        } catch (IOException e1) {
            strResult = null;
            e1.printStackTrace();
        } finally {
            if (httpResponse != null) {
            	 Log.i("Unity", "get status");
                statusCode = httpResponse.getStatusLine().getStatusCode();
            }
            else
            {
            	 Log.i("Unity", "NO_SERVER_ERROR");
                statusCode=NO_SERVER_ERROR;
            }
            
            if(onReceiveDataListener!=null)
            {
                //灏嗘敞鍐岀殑鐩戝惉鍣ㄧ殑onReceiveData鏂规硶鍔犲叆鍒版秷鎭槦鍒椾腑鍘绘墽琛�
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onReceiveDataListener.onReceiveData(strResult, statusCode,methodName);
                    }
                });
            }
        }
    }

    /**
     * 鐢ㄤ簬鎺ユ敹骞跺鐞唄ttp璇锋眰缁撴灉鐨勭洃鍚櫒
     *
     */
    public interface OnReceiveDataListener {
        /**
         * the callback function for receiving the result data
         * from post request, and further processing will be done here
         * @param strResult the result in string style.
         * @param StatusCode the status of the post
         */
        public abstract void onReceiveData(String strResult,int StatusCode,String methodName);
    }

}