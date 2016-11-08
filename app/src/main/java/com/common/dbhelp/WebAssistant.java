package com.common.dbhelp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.CookieStore;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.homenet.MainPage;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebAssistant
{
	//���������ʻ��ַ
	public static String BaseWebUrl="http://www.dzhealth.net/";
	
	//ע���ַ
	public static String RegisterWebUrl=BaseWebUrl+"user_addUser.do";
	
	//��¼��ַ
	public static String LoginWebUrl=BaseWebUrl+"user_login.do";
	
	//��֤���ַ
	public static String VerifyWebUrl=BaseWebUrl+"common_getValidateCode.do";
	
	//Ѫѹ�ϱ���ַ
	public static String InputDataWebUrl=BaseWebUrl+"bp_saveBP.do";
	
	//Ѫѹ��ȡ��ַ
	public static String GetDataWebUrl=BaseWebUrl+"bp_loadBPs.do";
	
	
	public static String GetTimeOutUrl=BaseWebUrl+"common_checkLogin.do";
	
	private static CookieStore LoginCookie =null;

	public static MainPage CommonNetTest=null;
	
	
	//�ж��Ƿ���3G����
	public static boolean CheckNetState(Context context)
	{
		boolean Is3G=false;
		
		
		 ConnectivityManager cm = (ConnectivityManager) context   
	                .getSystemService(Context.CONNECTIVITY_SERVICE);   
	        NetworkInfo networkINfo = cm.getActiveNetworkInfo();   
	        if (networkINfo != null  && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) 
	        {   
	        	Is3G= true;   
	        }   
	        
		
		return Is3G;
	}
	
	
	
	//��¼
	public static Boolean UserLogin(String UserName,String UserPass,String[] ErrorInfo)
	{
		Boolean Success=true;
		List <NameValuePair> params =new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username",UserName));		
		params.add(new BasicNameValuePair("password",UserPass));
		
		int Ret=PostData(LoginWebUrl,params,200,true,false, ErrorInfo,false);
		
		if(Ret>0)
			Success=true;
		else
			Success=false;
		return Success;
	}
	//�������
	public static Boolean UploadBloodData(String MaxValue,String MinValue,String AveValue,String Pulse,String GetTime,String[] ErrorInfo)
	{
		Boolean Success=true;
		List <NameValuePair> params =new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("dBP",MinValue));		
		params.add(new BasicNameValuePair("sBP",MaxValue));
		params.add(new BasicNameValuePair("mBP",AveValue));
		params.add(new BasicNameValuePair("pulse",Pulse));		
		params.add(new BasicNameValuePair("createTime",GetTime));
		
		int Ret=PostData(InputDataWebUrl,params,200,true,false, ErrorInfo,false);
		
		if(Ret>0)
			Success=true;
		else
			Success=false;
		return Success;
	}
	
	//�������
	public static Boolean DownloadBloodData(String startTime,String endTime,String[] ErrorInfo)
	{
		Boolean Success=true;
		List <NameValuePair> params =new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("startTime",startTime));		
		params.add(new BasicNameValuePair("endTime",endTime));
		 
		
		
		int Ret=PostData(GetDataWebUrl,params,200,true,false, ErrorInfo,true);
		
		if(Ret>0)
			Success=true;
		else
			Success=false;
		return Success;
	}
	
	
	public static Boolean UserRegister(String UserName,String UserPass,String ChineseName,String IDCard,String VerifyCode,String Sex,String Birthday,String[] ErrorInfo)
	{
		Boolean Success=true;
		
		List <NameValuePair> params =new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username",UserName));		
		params.add(new BasicNameValuePair("password",UserPass));
		params.add(new BasicNameValuePair("chinesename",ChineseName));
		params.add(new BasicNameValuePair("IDcard",IDCard));
		params.add(new BasicNameValuePair("validateCode",VerifyCode));
		params.add(new BasicNameValuePair("sex",Sex));
		params.add(new BasicNameValuePair("birthday",Birthday));
		
		int Ret=PostData(RegisterWebUrl,params,200,true,false, ErrorInfo,false);
		
		if(Ret>0)
			Success=true;
		else
			Success=false;
		
		
		return Success;
	}
	
	
	
	//��ȡ��֤��
	public static Boolean GetVerifyCode(String PhoneNo,String[] ErrorInfo)
	{
		Boolean Success=true;
		
		List <NameValuePair> params =new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("phoneNum",PhoneNo));
		
		int Ret=PostData(VerifyWebUrl,params,200,false,true,ErrorInfo,false);
		
		if(Ret>0)
			Success=true;
		else
			Success=false;
		return Success;
	}
	
	//��ȡ��ʱ״̬�����Ự�Ƿ�ʱ
	public static Boolean GetTimeOutState()
	{
		
		Boolean Success=true;
		String[] ErrorInfo=new String[2];
		List <NameValuePair> params =new ArrayList<NameValuePair>();
		 
		
		int Ret=PostData(GetTimeOutUrl,params,200,true,false, ErrorInfo,false);
		
		if(Ret>0)
			Success=true;
		else
			Success=false;
		
		return Success;
		
		
	}
	
	public static int PostData(String ServiceUrl,List <NameValuePair> params,int SuccessCode,Boolean WithCookie,Boolean SaveCookie,String[] ErrorInfo,Boolean GetSuccResult) 
	{
		
			int  Retult=0;
		    String RetString="";
		 	String uriAPI = ServiceUrl;//"http://172.20.0.206:8082//TestServelt/login.do";
		 	
		 	if(LoginCookie==null)
		 	
		 		LoginCookie =(CookieStore) new BasicCookieStore();
		 	
		 	
		 	if(!isOpenNetwork(CommonNetTest))	//��������ʧ��
		 	{
		 		ErrorInfo[0]="��ǰ�޷���������!";
		 		return 0;
		 	}
		 	
		 	
		    /*����HTTP Post����*/
		    HttpPost httpRequest =new HttpPost(uriAPI);
		    //Post�������ͱ��������NameValuePair[]���д���
		    //������ ����˻�ȡ�ķ���Ϊrequest.getParameter("name")
		     //params=new ArrayList<NameValuePair>();
		    
		    //params.add(new BasicNameValuePair("name","this is post"));
		    
		    
		    HttpContext localContext = new BasicHttpContext();
		    localContext.setAttribute(ClientContext.COOKIE_STORE, LoginCookie);
		    
		    
		    try
		    {
		     
			     //����HTTP request
			     httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			     
			     
			     DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
			      
			     // ����ʱ
			      
			     defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			    
			    // ��ȡ��ʱ
			     
			     defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			     
			     // ���Cookie
			     /*
			     if(WithCookie)
			     {
				     if (LoginCookie != null) 
				     {			     
				            defaultHttpClient.setCookieStore((org.apache.http.client.CookieStore) LoginCookie);			     
				     }
			     }*/
			     
			     
			     //ȡ��HTTP response
			     HttpResponse httpResponse=defaultHttpClient.execute(httpRequest,localContext);
			    
			   
			     
			     //��״̬��Ϊ200 ok 
			     if(httpResponse.getStatusLine().getStatusCode()==SuccessCode)
			     {
				      //ȡ����Ӧ�ִ�
				      String strResult=EntityUtils.toString(httpResponse.getEntity());
				      
				      if(GetSuccResult)				//�Ƿ��ȡ����ֵ
				    	  ErrorInfo[1]=strResult;
				      
				      JSONObject jsonObject = new JSONObject(strResult);
				      
				      if(jsonObject!=null)
				      {
				    	  RetString=jsonObject.getString("success");
				    	  RetString=RetString.trim();
				    	  
				    	  if(RetString.equals("1"))
				    	  {
				    		  Retult=1;
				    		  
				    		  /*
				    		  if(SaveCookie)
						      {
						    	  LoginCookie = (CookieStore) ((AbstractHttpClient) defaultHttpClient).getCookieStore();
						      }*/
				    	  }
				    	  else
				    	  {
				    		  String Error=jsonObject.getString("data");
				    		  if(ErrorInfo!=null)
				    		  {
				    			  ErrorInfo[0]=Error;
				    			  
				    		  }
				    		  Retult=0;
				    	  }
				    	  
				      }
				      
				      
			     }
			     else
			     {
			    	 Retult=0;
			    	 //textView1.setText("Error Response"+httpResponse.getStatusLine().toString());
			     }
		    }
		    catch(ClientProtocolException e)
		    {
			     //textView1.setText(e.getMessage().toString());
		    	Retult=0;
			     e.printStackTrace();
			     if(ErrorInfo!=null)
	    		  {
	    			  ErrorInfo[0]=e.getMessage();
	    			  
	    		  }
		    } 
		    catch (UnsupportedEncodingException e) 
		    {
		    	//textView1.setText(e.getMessage().toString());
		    	if(ErrorInfo!=null)
	    		  {
	    			  ErrorInfo[0]=e.getMessage();
	    			  
	    		  }
		    	e.printStackTrace();
		    	Retult=0;
		    } 
		    catch (IOException e) 
		    {
		    	//textView1.setText(e.getMessage().toString());
		    	e.printStackTrace();
		    	Retult=0;
		    	if(ErrorInfo!=null)
	    		  {
	    			  ErrorInfo[0]=e.getMessage();
	    			  
	    		  }
		    } 
		    catch (JSONException e) 
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Retult=0;
				if(ErrorInfo!=null)
	    		  {
	    			  ErrorInfo[0]=e.getMessage();
	    			  
	    		  }
			}
		return Retult;		
	}
	
	private static boolean isOpenNetwork(Activity ClientAct) 
	{  
	    ConnectivityManager connManager = (ConnectivityManager)ClientAct.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    if(connManager.getActiveNetworkInfo() != null) 
	    {  
	        return connManager.getActiveNetworkInfo().isAvailable();  
	    }  	  
	    return false;  
	}  
	
}
