package com.example.yun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	
	public static final String CREATE_RECORD = "create table IF NOT EXISTS record(pk integer primary key autoincrement,id text not NULL,name text,url  text,date text,size integer,status integer not NULL)";
	private Context mContext;
	
	public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mContext = context;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i("tag", "�������ݿ�biao ");
		db.execSQL(CREATE_RECORD);
		
	}

	//����汾�Ż�ִ��
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//��������
		db.execSQL("DROP TABLE IF EXISTS " +"record");
		onCreate(db);
		//��������
	}
	

}
