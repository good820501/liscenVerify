package com.common.dbhelp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class dbHelper extends SQLiteOpenHelper 
{

	private final static String DATABASE_NAME="history";
	private final static int DATABASE_VERSION=1;
	private final static String TABLE_NAME="Hos_List";
	public final static String FIELD_ID="ID"; 
	public final static String FIELD_TITLE="sec_Title";
	
	
	
	public dbHelper(Context context)
	{
		super(context, DATABASE_NAME,null, DATABASE_VERSION);
	}
	
	
	 
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		/*
		String sql="Create table "+TABLE_NAME+"("+FIELD_ID+" integer primary key autoincrement,"
		+FIELD_TITLE+" text );";
		db.execSQL(sql);		
		*/ 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql=" DROP TABLE IF EXISTS "+TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public Cursor select()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query(TABLE_NAME, null, null, null, null, null,  " ID asc");
		return cursor;
	}
	public Cursor selectActives()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query("Result_List", null, null, null, null, null,  " ID asc");
		return cursor;
	}
	//��ѯ�����Ϣ
	public Cursor selectExams()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query("phyExamList", null, null, null, null, null,  " ID asc");
		return cursor;
	}
	
	public Cursor selectExamDetail(String Selections)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query("ExamDetail", null, Selections, null, null, null,  " ID asc");
		return cursor;
	}
	
	public Cursor selectSendList(String Selections)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query("SendHis", null, Selections, null, null, null,  " SID asc");
		return cursor;
	}
	//������Դ�ļ�
	public Cursor selectResList(String Selections)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query("FileList", null, Selections, null, null, null,  " SID asc");
		return cursor;
	}
	public Cursor select(String Selections)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query(TABLE_NAME, null, Selections, null, null, null,  " ID asc");
		return cursor;
	}
	
	public Cursor selectActive(String Selections)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query("ActiveList", null, Selections, null, null, null,  " ID asc");
		return cursor;
	}
	public Cursor selectExam(String Selections)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query("phyExamList", null, Selections, null, null, null,  " ID asc");
		
		return cursor;
	}
	
	//�鿴Ѫѹ��¼ֵ
	public Cursor selectBlood(String Selections)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query("BloodHis", null, Selections, null, null, null,  " ID asc");
		return cursor;
	}
	
	public long insert(String Title)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues(); 
		cv.put(FIELD_TITLE, Title);
		long row=db.insert(TABLE_NAME, null, cv);
		return row;
	}
	//���Ѫѹ��¼ֵ����ݿ�
	public long insertBloodData(String MaxValue,String MinValue,String AveValue,String BeatCount,String TimeStr,String UserName)
	{
		long rows=0;
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues(); 
		cv.put("MaxValue", MaxValue);
		cv.put("MinValue", MinValue);
		cv.put("AveValue", AveValue);
		cv.put("HCount", BeatCount);
		cv.put("IsLocal", "1");
		cv.put("HasUpload", "0");
		cv.put("WTime", TimeStr);
		cv.put("UserName", UserName);
		rows=db.insert("BloodHis", null, cv);		
		return rows;
	}

	public long insertSendList(String AffName,String SendDate,String SendTime,String UnifiedNo,String TContent,boolean UpSuccess)
	{
		long rows=0;
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues(); 
		 
		cv.put("AffName", AffName);
		cv.put("SendDate", SendDate);
		cv.put("SendTime", SendTime);
		cv.put("UnifiedNo", UnifiedNo);
		
		if(UpSuccess)
			cv.put("SendState", "1");
		else
			cv.put("SendState", "0");
			
		cv.put("TContent", TContent);
		 
		rows=db.insert("SendHis", null, cv);	
		//db.close();
		return rows;
	}



	public long insertResult(String picName,int success,int uTime,String RDetail)
	{
		long rows=0;
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put("pic_Name", picName);
		cv.put("success", success);
		cv.put("uTime", uTime);
		cv.put("RDetail", RDetail);



		rows=db.insert("Result_List", null, cv);
		return rows;
	}

	public long UpdateSendState(String UnifiedNo)
	{
		long rows=0;
		SQLiteDatabase db=this.getWritableDatabase();
		String where="UnifiedNo='"+UnifiedNo+"'";
		String[] args = {String.valueOf(UnifiedNo)};
		ContentValues cv=new ContentValues(); 
		 
	    cv.put("SendState", "1");
	    rows=db.update("SendHis", cv, "UnifiedNo=?", args);		
		db.close();					   
		return rows;
	}
	
	
	//����ͼƬ
	public long insertSendFiles(String UnifiedNo,String  FileName,String FileType,String FileContent)
	{
		long rows=0;
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues(); 
		cv.put("UnifiedNo", UnifiedNo);
		cv.put("FileName", FileName);
		cv.put("FileType", FileType);
		 
		rows=db.insert("FileList", null, cv);	
		//db.close();
		return rows;
	}
	
	
	public  Boolean GetLoginState(String[] ReturnParams)
	{
		Boolean Logined=false;
		SQLiteDatabase db=this.getReadableDatabase();
		String LoginName="",LoginPass="";
		
		
		Cursor cursor=db.query("SysState", null, null, null, null, null,  " ID asc");
		try
		{
		
		
			if(cursor.getCount()==0)
				Logined=false;
			else
			{
				cursor.moveToFirst();
				
				if(cursor.getString(cursor.getColumnIndex("Login_Name"))!=null)
				{
					LoginName=cursor.getString(cursor.getColumnIndex("Login_Name")).toString().trim();
				}
				if(cursor.getString(cursor.getColumnIndex("Login_Verify"))!=null)
				{
					LoginPass=cursor.getString(cursor.getColumnIndex("Login_Verify")).toString().trim();
				}
				if(ReturnParams!=null)
				{					
					ReturnParams[0]=LoginName;
					ReturnParams[1]=LoginPass;
				}
				
				int State=cursor.getInt(cursor.getColumnIndex("Login_State"));
				if(State==0)
					Logined=false;
				else
					Logined=true;
				
			}
			cursor.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			 if(cursor != null)
			 {  
				 cursor.close(); 
			 }
		}  
			if(db!=null)
				db.close();
		
		 
		return Logined;
	}
	
	public void SetLoginState(Boolean Logined,String LoginName,String LoginPass)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String where="ID=1";
		 
		ContentValues cv=new ContentValues(); 
		if(Logined)
			cv.put("Login_State", "1");
		else
			cv.put("Login_State", "0");
		cv.put("Login_Name", LoginName);	
		cv.put("Login_Verify", LoginPass);
		db.update("SysState", cv, where, null);		
		db.close();
	}
	
	
	public void delete(int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String where=FIELD_ID+"=?";
		String[] whereValue={Integer.toString(id)};
		db.delete(TABLE_NAME, where, whereValue);
	}
	
	public void update(int id,String Title)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String where=FIELD_ID+"=?";
		String[] whereValue={Integer.toString(id)};
		ContentValues cv=new ContentValues(); 
		cv.put(FIELD_TITLE, Title);
		db.update(TABLE_NAME, cv, where, whereValue);
	}
	
	
	
	
}
