package com.example.yun;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.yun.PostRequest.OnReceiveDataListener;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;
import com.example.yun.PostRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class VuforiaActivity extends UnityPlayerActivity  {
	Context mContext = null;
	boolean firstStarted;
	private String photoPath;
	private Handler handler; 
	private String modelAssetbundlePath;
	private Gson gson;
	private MyDatabaseHelper dbHelper;
	private SQLiteDatabase db;
	private HashMap<String,Integer> map;
	private int curTaskCount = 0;
	private ProgressDialog pd;
	private String curTargetId;
	private AlertDialog.Builder builder;
	private String alertMessage = "";
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		 
	     
	    super.onCreate(savedInstanceState);
	    
	    //动态布局
	    //this.setContentView(R.layout.activity_main);
	    //LinearLayout unityView = (LinearLayout)this.findViewById(R.id.unityview);
	    //unityView.addView(mUnityPlayer.getView());
	    
	    this.CreateProgressDialog();
	    dbHelper = new MyDatabaseHelper(this,"Record.db",null,1);
	    db = dbHelper.getWritableDatabase();
	    //清理数据库，调试用
	    //this.clearRecord();
	    gson = new Gson();
	    map = new HashMap<String,Integer>();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("错误提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				alertMessage = "";
			}
		});
       
        Log.i("Unity", "oncreate");
        mContext = this;
        firstStarted = true;
        photoPath = getSDPath()+"//DCIM//XIAOBENXIONG";
        //modelAssetbundlePath = photoPath+"//mahayu.mp4";
        File dir = new File(photoPath);                        
        if(!dir.exists()){                                
               dir.mkdir();                    
         }       
        
        handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				super.handleMessage(msg);
				int what = msg.what;
				if(what == -1){
					setBrightness(200);
				}else if(what==0){
					pd.show();
				}else if(what>0&&what<100){
					pd.setProgress(what);
				}else if(what == 100){
					Log.i("tag", "pd.hide");
					pd.hide();
				}else if(what == -2){
					builder.show();
				}
				
			}
        };
	}
	

	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		Log.i("Unity","onConfigurationChanged");
	}
	
	@Override
	public void onStart(){
		super.onStart();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	@Override
	public void onPause(){
		super.onPause();
	}
	
	private void CreateProgressDialog(){
		pd = new ProgressDialog(this);
		/**
		 *水平进度条，不能点击back取消，
		 */
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setCancelable(false);
		pd.setCanceledOnTouchOutside(false);
		pd.setMax(100);
		pd.setMessage("数据下载中，不要扫描其他目标");
		pd.hide();
	}
	
	public void objectTest(final Object obj){
		Log.i("Unity", "objectTestAndroid");
	/*	Camera camera = (Camera)obj;
		Parameters parameters = camera.getParameters();
		int exposureCompensation = parameters.getExposureCompensation();*/
		//Log.i("Unity","turturtut:"+exposureCompensation);
	}
	
	public void unityInitialize(final String str){
		//cameraShow();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					Log.i("tag","new thread");
					Message msg = new Message();
					msg.what = -1;
					handler.sendMessage(msg);
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					
				}
			}
			
		}).start();
	}
	
	public void setBrightness(int brightValue){
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.screenBrightness = (brightValue<=0?-1.0f:(float)brightValue/255);
		window.setAttributes(lp);
		
	}
	
	public void sendMessageToUnity3D(String msg)
    {
		UnityPlayer.UnitySendMessage("ARCamera","getMessageFromAndroid",msg);
    }
	
	public void startCamera(String msg)
	{
		UnityPlayer.UnitySendMessage("ARCamera","OpenCameraDevice",msg);
	}
	
	public void getUnityTargetShare(final String path){
		//TestLog.writeLog("getUnityTargetShare:"+path);
		
	}
	
	public void getMessageFromUnity3d(final int orderId,final String msg){
	
		switch(orderId){
			case 1:
				VuforiaActivity.this.finish();
				break;
			case 2:
				break;
			default:
				break;
		}
	}
	
	public  String getSDPath(){ 
	       File sdDir = null; 
	       boolean sdCardExist = Environment.getExternalStorageState()   
	                           .equals(Environment.MEDIA_MOUNTED);   
	       if   (sdCardExist)   
	       {                               
	         sdDir = Environment.getExternalStorageDirectory();
	       }   
	       return sdDir.toString(); 
	       
	} 	
	
	public String getPath(final String path){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String t = format.format(new Date());
		String returnPath = photoPath+"//"+t+".png";
		Log.i("Unity", returnPath);
		return returnPath;
	}
	
	/*
	public String getVideo(final String path){
		return modelAssetbundlePath;
	}
	
	public String getModel(final String path){
	   String path1 = "file:///"+getSDPath()+"/alamolong.assetbundle"; //"/storage/emulated/0/"
	   return path1;
	}
	*/
	public void initErrorCallback(final String msg){
		builder.setMessage(msg);
		Message msg1 = new Message();
		msg1.what = -2;
		if(!msg.equals(alertMessage)){
			alertMessage = msg;
			handler.sendMessage(msg1);
		}
	}
	
	
	public void updateErrorCallback(final String msg){
		
		builder.setMessage(msg);
		Message msg1 = new Message();
		msg1.what = -2;
		if(!msg.equals(alertMessage)){
			alertMessage = msg;
			handler.sendMessage(msg1);
		}
		
	}
	
	public void targetFoundCallback(final String UniqueTargetId){
		synchronized(this){
			curTargetId = UniqueTargetId;
		}
		String path;
		boolean flag = false;
		//缓存表中找文件
		Log.i("Unity", UniqueTargetId);
		//查找数据库
		Record record = getRecord(UniqueTargetId);
		switch(record.getStatus()){
		case 0:
			synchronized(this){
				int taskCount = this.getTaskCount(UniqueTargetId);
				if(taskCount == -1){
					this.addTaskCount(UniqueTargetId, curTaskCount);
					QueryVuforia(UniqueTargetId,curTaskCount);
					curTaskCount++;
				}else{
					//有任务在下载，重新显示
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);
				}
			}
			break;
		case 1:
			String fullName = photoPath+"//"+ record.getName();
			String method = "videoTexture";
			if(fullName.endsWith("assetbundle")){
				method = "model";
				String head = "file:///";
				fullName = head.concat(fullName) ;
			}
			Log.i("tag", fullName);
			UnityPlayer.UnitySendMessage("ImageTarget",method,fullName);
			break;
		}
		
		/***
		//alamolong
		if(UniqueTargetId.equals("ae2e53a7d57c4eed9be9db29df7a6512")){
			UnityPlayer.UnitySendMessage("ImageTarget","model","file:///"+getSDPath()+"/DCIM/XIAOBENXIONG/alamolong.assetbundle");
			return;
		}else 
		//bawanglong
		
		new Thread(new Runnable(){
			@Override
			public void run() {
			// TODO Auto-generated method stub
			//缓存表中找不到，到数据库下载
			try {
					Thread.sleep(1000);
					if(UniqueTargetId.equals("16d9ff337ede4def9393233a4d2d1c71")){
						UnityPlayer.UnitySendMessage("ImageTarget","videoTextureAuto","mahayu.mp4");//getSDPath()+"/DCIM/XIAOBENXIONG/mahayu.mp4");
					}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}).start();
		/***/
	}

	 private void QueryVuforia(final String ut_id,final int count)
	 {
		 new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				try {
					String formatStr = String.format(getResources( ).getString(R.string.url_str), ut_id,1);
					 Log.i("tag", "链接是："+formatStr);
					URL url = new URL(formatStr);
					try {
						HttpURLConnection connection = (HttpURLConnection)url.openConnection();
						connection.setRequestMethod("GET");
						connection.setUseCaches(false);
						connection.connect();
						InputStream is = connection.getInputStream();
						int code = connection.getResponseCode();
						if ( code == 200) {
			                // 获取返回的数据
			                String result = streamToString(connection.getInputStream());
			                Log.i("tag", "Get方式请求成功，result--->" + result);
			                ResourceData rd = gson.fromJson(result, ResourceData.class);
			    			if (rd != null)
			    			{
			    			    Log.i("tag", rd.getName());
			    				if ((rd.getIsSuccess()).endsWith("1"))
			    				{
			    					UnityTarget uTarget = gson.fromJson(rd.getName().replace("[", "").replace("]", ""), UnityTarget.class);
			    					if (uTarget != null)
			    					{
			    						Record record = new Record();
			    						String UnityTargetName=uTarget.getUnityTargetName();
			    						String downloadUrl = uTarget.getUnityTargetDownloadUrl();
			    						String modifyDate = uTarget.getModifyDate();
			    						int targetSize = uTarget.getUnityTargetSize();
			    						record.setTargetId(ut_id);
			    						record.setName(UnityTargetName);
			    						record.setUrl(downloadUrl);
			    						record.setModifyDate(modifyDate);
			    						record.setTargetSize(targetSize);
			    						record.setStatus(0);
			    						insertRecord(record);
			    						Log.i("tag",downloadUrl );
			    						String fullName = photoPath+"//"+ UnityTargetName;
			    						boolean flag = downloadFile(downloadUrl,fullName,record.getTargetSize(),ut_id);
			    				    	if(flag){
			    				    		Log.i("tag", "进入同步处理");
			    				    		synchronized(VuforiaActivity.this){
			    				    			Log.i("tag", "同步处理开始");
			    				    			//String targetId = VuforiaActivity.this.getId(count);
			    				    			//Log.i("tag", "Target："+targetId);
			    				    			//保存数据库
			    				    			VuforiaActivity.this.updateStatus(ut_id, 1);
			    				    			Log.i("tag", "ut_id:"+ut_id);
			    				    			//和当前id一致就显示
			    				    			if(curTargetId.equals(ut_id)){
			    				    				Log.i("tag", "相等");
			    				    				String method = "videoTexture";
					    							if(fullName.endsWith("assetbundle")){
					    								method = "model";
					    								String head = "file:///";
					    								fullName = head.concat(fullName) ;
					    							}
					    							Log.i("tag", fullName);
					    							UnityPlayer.UnitySendMessage("ImageTarget",method,fullName);
			    				    			}
			    				    			//删除任务
			    				    			
			    				    		}
			    							
			    				    	}else{               //下载失败
			    				    		Log.i("tag", "下载失败");
			    				    		synchronized(VuforiaActivity.this){
			    				    			delTaskCount(ut_id);
			    				    		}
			    				    	    Message msg1 = new Message();
			    							msg1.what = 100;
			    							handler.sendMessage(msg1);
			    							alertMessage = "下载发生错误";
			    							builder.setMessage(alertMessage);
			    							Message msg2 = new Message();
				    						msg2.what = -2;
				    						handler.sendMessage(msg2);
			    				    		
			    				    	}
			    					}
			    				}
			    			}
			                
			            } else {
			                Log.i("Unity", "Get方式请求失败"+code);
			                //清除相关任务
			                synchronized(VuforiaActivity.this){
			                	delTaskCount(ut_id);
			                }
			                alertMessage = "获取信息发生错误";
			                builder.setMessage(alertMessage);
							Message msg3 = new Message();
    						msg3.what = -2;
    						handler.sendMessage(msg3);
			            }
						connection.disconnect();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			 
		 }).start();
		 
	    }
	 
	 public String streamToString(InputStream is) {
	        try {
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            while ((len = is.read(buffer)) != -1) {
	                baos.write(buffer, 0, len);
	            }
	            baos.close();
	            is.close();
	            byte[] byteArray = baos.toByteArray();
	            return new String(byteArray);
	        } catch (Exception e) {
	            Log.i("Unity", e.toString());
	            return null;
	        }
	    }
	 
	 private  boolean downloadFile(String fileUrl, String filePath,int targetSize,String targetId){
		    boolean flag = true;
	        try {
	            // 新建一个URL对象
	            URL url = new URL(fileUrl);
	            // 打开一个HttpURLConnection连接
	            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
	            // 设置连接主机超时时间
	            urlConn.setConnectTimeout(5 * 1000);
	            //设置从主机读取数据超时
	            urlConn.setReadTimeout(5 * 1000);
	            // 设置是否使用缓存  默认是true
	            urlConn.setUseCaches(true);
	            // 设置为Post请求
	            urlConn.setRequestMethod("GET");
	            //urlConn设置请求头信息
	            //设置请求中的媒体类型信息。
	            urlConn.setRequestProperty("Content-Type", "application/json");
	            //设置客户端与服务连接类型
	            urlConn.addRequestProperty("Connection", "Keep-Alive");
	            // 开始连接
	            urlConn.connect();
	            // 判断请求是否成功
	            if (urlConn.getResponseCode() == 200) {
	                File  descFile = new File(filePath);
	                FileOutputStream fos = new FileOutputStream(descFile);;
	                byte[] buffer = new byte[1024];
	                int len;
	                int downloadLen=0;
	                Message msg = new Message();
					msg.what = 0;
					synchronized(VuforiaActivity.this){
					if(curTargetId.equals(targetId))
						handler.sendMessage(msg);
					}
	                InputStream inputStream = urlConn.getInputStream();
	                while ((len = inputStream.read(buffer)) != -1) {
	                    // 写到本地
	                    fos.write(buffer, 0, len);
	                    downloadLen += len;
	                    Message msg1 = new Message();
	                    msg1.what = (int)(downloadLen*100/(float)targetSize);
	                    if(msg.what>100){
	                    	msg.what = 100;
	                    }
	                    if(this.curTargetId.equals(targetId)){
	                    	handler.sendMessage(msg1);
	                    }  
	                }
	                Log.i("tag", "文件下载完毕");
	            } else {
	                Log.i("tag", "文件下载失败");
	                flag = false;
	            }
	            urlConn.disconnect();
	        } catch (Exception e) {
	            Log.i("tag", e.toString());
	            flag = false;
	        }finally{
	        	// 关闭连接
	        }
	        return flag;
	    }
	 
	private void insertRecord(Record record){
		db.execSQL("insert into record(id,name,url,date,size,status) values(?,?,?,?,?,?)", 
				new Object[]{record.getTargetId(),record.getName(),record.getUrl(),record.getModifyDate(),record.getTargetSize(),record.getStatus()});
	}
	
	private void updateRecord(Record record){
		db.execSQL("update record set name = ?,url = ?,date = ?,size = ?,status = ? where id = ?",new Object[]{
				record.getName(),record.getUrl(),record.getModifyDate(),record.getTargetSize(),record.getStatus(),record.getTargetId()
		});
	}
	
	private void updateStatus(String targetId,int status){
		db.execSQL("update record set status = ? where id = ?",new Object[]{Integer.valueOf(status),targetId});
	}
	
	private Record getRecord(String targetId){
		Record record = new Record();
		record.setTargetId(targetId);
		record.setStatus(0);
		Cursor cursor = db.rawQuery("select * from record where id = ?", new String[]{targetId});
		if(cursor.moveToFirst()){
			record.setName(cursor.getString(cursor.getColumnIndex("name")));
			record.setUrl(cursor.getString(cursor.getColumnIndex("url")));
			record.setModifyDate(cursor.getString(cursor.getColumnIndex("date")));
			record.setTargetSize(cursor.getInt(cursor.getColumnIndex("size")));
			record.setStatus(cursor.getInt(cursor.getColumnIndex("status")));		
		}
		return record;
	}
	
	private void clearRecord(){
		db.execSQL("delete from record");
	}
	
	private int getTaskCount(String targetId){
		Integer taskCount = map.get(targetId);
		if(taskCount != null){
			return taskCount;
		}else{
			return -1;
		}
	}
	
	private void addTaskCount(String targetId,int count){
		Integer taskCount = Integer.valueOf(count);
		map.put(targetId, taskCount);
	}
	
	private void delTaskCount(String targetId){
		map.remove(targetId);
	}
	
	private String getId(int count){
		Integer taskCount = Integer.valueOf(count);
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Entry entry = (java.util.Map.Entry<String, Integer>)iter.next();
			Integer temp = (Integer)entry.getValue();
			if(temp.equals(taskCount)){
				return (String)entry.getKey();
			}
		}
		return null;
	}
	
	@Override
	protected void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		setIntent(intent);
		
	}
}
