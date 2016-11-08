
package com.example.homenet;

import java.io.File;
import java.io.IOException;
import java.util.List;


import com.wl.util.LogsUtil;
import com.wl.util.SupportedSizesReflect;



import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.dbhelp.WebAssistant;
import com.common.dbhelp.WndAssistant;
import com.common.dbhelp.dbHelper;


/**
 * TODO
 * @author  
 * @version 1.0.0
 */

public class RecordActivity extends Activity implements Callback,MediaPlayer.OnPreparedListener, OnBufferingUpdateListener, 
OnCompletionListener
 {
	private static final String TAG="RecordActivity";
	
	public String videoPath="/sdcard/love.3gp";
	
	private MediaRecorder mediarecorder;// 褰曞埗瑙嗛鐨勭被
	private MediaPlayer mediaPlayer;//鎾斁瑙嗛鐨勭被
	private SurfaceView surfaceview;// 鏄剧ず瑙嗛鐨勬帶浠�
	private  Camera camera;
	//瀹炵幇杩欎釜鎺ュ彛鐨凜allback鎺ュ彛
	private SurfaceHolder surfaceHolder;
	/**
	 * 鏄惁姝ｅ湪褰曞埗true褰曞埗涓�false鏈綍鍒�
	 */
	private boolean isRecord=false;
	
	public boolean isCameraBack=true;
	Button right_button;
	private ImageView recordIv;
	private ImageView recordPlayIv;
	private ImageView OkBtn;
	
	private int mVideoWidth;
	private int mVideoHeight;
	int cameraCount = 0;
	
	private int cameraPosition = 1;//0浠ｈ〃鍓嶇疆鎽勫儚澶达紝1浠ｈ〃鍚庣疆鎽勫儚澶�

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WndAssistant.SetWindowNoTitle(this);
		setContentView(R.layout.layout_video_record1);
		
		 Intent intent =getIntent();
		
		 videoPath=intent.getStringExtra("FiiePath");
		 
		 
		setTitleStr("");

				
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
			
		initView();

		
	}
	

	/**
	 * 
	 */
	private void initView() {
		
		surfaceview = (SurfaceView) this.findViewById(R.id.surfaceview);
		recordIv=(ImageView)findViewById(R.id.recordIv);
		
		OkBtn=(ImageView)findViewById(R.id.okbtn);
		
		recordPlayIv=(ImageView)findViewById(R.id.recordPlayIv);
		
		SurfaceHolder holder = surfaceview.getHolder();// 鍙栧緱holder
		holder.addCallback(this); // holder鍔犲叆鍥炶皟鎺ュ彛
		// setType蹇呴』璁剧疆锛岃涓嶅嚭閿�
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		recordIv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				recordVideo(v);
			}
			
		});
		
		OkBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				CloseAndReturn();
			}
			
		});
		
		
	}
	
	private void showDialog(String mess)
    {
    	try
    	{
    		/*AlertDialog   ShowInfo;
    		
    		 ShowInfo= new AlertDialog.Builder(SalesActivity.this).create();
    		 
    		 if(ShowInfo!=null)
    		 {
    			 
    			 
    			 
    		 }*/
	      new AlertDialog.Builder(RecordActivity.this).setTitle("灾情速报")
	       .setMessage(mess)
	       .setNegativeButton("确定",new DialogInterface.OnClickListener()
	       {
	         public void onClick(DialogInterface dialog, int which)
	         {          
	         }
	       })
	       .show();
    	}
    	catch(Exception Exp)
    	{
    		
    		
    		String ErrorInfo=Exp.getMessage();
    	}
    }
	
	
	//关闭录像窗口并返回
	public void CloseAndReturn()
	{
		Intent aintent = new Intent(RecordActivity.this, SalesActivity.class);
		/* 将数据打包到aintent Bundle 的过程略 */
		
		
		
		if(isRecord)
		{
			
			showDialog("请点击停止录制键，再点击确定键！");
			return;
		}
		
		
		aintent.putExtra("VideoFileName", this.videoPath);
		setResult(RESULT_OK,aintent); //这理有2个参数(int resultCode, Intent intent)
		 
		
		if(this.surfaceview!=null)
		{
			surfaceview.getHolder().getSurface().release();
			
			
		}
		
		if(camera!=null)
			camera.release();
		if(mediaPlayer!=null)
		{
			mediaPlayer.reset();
			mediaPlayer.release();
			
			
		}
		if(mediarecorder!=null)
		{
			mediarecorder.reset();
			mediarecorder.release();
			
			
		}
		
		finish();
		
		
		
	}
	
	
	/**
	 * 鎾斁瑙嗛
	 * TODO
	 * @param v
	 */
	public void playVideo(View v){
		recordPlayIv.setVisibility(View.GONE);
		try {
			mediaPlayer=new MediaPlayer();
			mediaPlayer.setDataSource(videoPath);
			mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.prepareAsync();
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block       
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

	}
	
	/**
	 * 寮�褰曞埗/鍋滄褰曞埗
	 * TODO
	 * @param v
	 */
	public void recordVideo(View v){
		if(isRecord)
		{
			isRecord=false;
			if(right_button!=null)
				right_button.setEnabled(true);
			recordIv.setImageResource(R.drawable.video_recorder_start_btn_nor);
			recordPlayIv.setVisibility(View.VISIBLE);
			if (mediarecorder != null) {
				// 鍋滄褰曞埗
				mediarecorder.stop();
				// 閲婃斁璧勬簮
				mediarecorder.reset();
				mediarecorder.release();
				
				mediarecorder = null;
			}
			if(camera!=null){
				camera.release();
			}
			
			
			
			
		}else{
			
			
			//File file = new File("/sdcard/video.mp4");
			
			File file = new File(videoPath);
			if (file.exists()) {
				// 如果文件存在，删除它，演示代码保证设备上只有一个录音文件
				file.delete();
			}
			;
			if(right_button!=null)
				right_button.setEnabled(false);
			
			isRecord=true;
			recordIv.setImageResource(R.drawable.video_recorder_recording_btn);
			recordPlayIv.setVisibility(View.GONE);
			mediarecorder = new MediaRecorder();// 鍒涘缓mediarecorder瀵硅薄
//			// 浠庨害鍏嬮婧愯繘琛屽綍闊�
//			mediarecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT); 
//			// 璁剧疆杈撳嚭鏍煎紡 
//			mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT); 
//			// 璁剧疆缂栫爜鏍煎紡 
//			mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			/**
			 * 璁剧疆绔栫潃褰曞埗
			 */
			if(camera!=null){
				camera.release();
			}
			
			if(cameraPosition==1){
				
				//camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);//鎵撳紑鎽勫儚澶�
				camera = Camera.open();
//				 camera = Camera.open(cameraPosition);//鎵撳紑鎽勫儚澶�
//		         Camera.Parameters parameters = camera.getParameters();
//		         camera.setDisplayOrientation(90);
				   
		       
//		         camera.setParameters(parameters); 
				
		         camera=deal(camera);
		         mediarecorder.setOrientationHint(90);//瑙嗛鏃嬭浆90搴�
		     
		      
			}else{
				camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);//鎵撳紑鎽勫儚澶�
		         Camera.Parameters parameters = camera.getParameters();
		         camera.setDisplayOrientation(90);
		         camera.setParameters(parameters); 
				mediarecorder.setOrientationHint(270);//瑙嗛鏃嬭浆90搴�
			}
			
		    camera.unlock();
		  
		    mediarecorder.setCamera(camera);
			
			// 璁剧疆褰曞埗瑙嗛婧愪负Camera(鐩告満)
			mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			// 璁剧疆褰曞埗瀹屾垚鍚庤棰戠殑灏佽鏍煎紡THREE_GPP涓�gp.MPEG_4涓簃p4
			mediarecorder
					.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			// 璁剧疆褰曞埗鐨勮棰戠紪鐮乭263 h264
			mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			// 璁剧疆瑙嗛褰曞埗鐨勫垎杈ㄧ巼銆傚繀椤绘斁鍦ㄨ缃紪鐮佸拰鏍煎紡鐨勫悗闈紝鍚﹀垯鎶ラ敊
			mediarecorder.setVideoSize(176, 144);
			// 璁剧疆褰曞埗鐨勮棰戝抚鐜囥�蹇呴』鏀惧湪璁剧疆缂栫爜鍜屾牸寮忕殑鍚庨潰锛屽惁鍒欐姤閿�
			mediarecorder.setVideoFrameRate(20);
			
			
			mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
			// 璁剧疆瑙嗛鏂囦欢杈撳嚭鐨勮矾寰�
			//mediarecorder.setOutputFile(videoPath);
			mediarecorder.setOutputFile(file.getAbsolutePath());
			try {
				// 鍑嗗褰曞埗
				mediarecorder.prepare();
				mediarecorder.start();
				 
					
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
			
		}
	}

	protected void setTitleStr(String str) {
			TextView titleText = (TextView) findViewById(R.id.common_title_text);
			titleText.setText(str.trim());
			
			Button left_button=(Button)findViewById(R.id.left_button);
			left_button.setVisibility(View.GONE);
		
			right_button=(Button)findViewById(R.id.right_button);
			right_button.setVisibility(View.VISIBLE);
			right_button.setText("");
			right_button.setBackgroundResource(R.drawable.btn_video_switch_bg);
			right_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					cameraCount=Camera.getNumberOfCameras();
					if(isCameraBack){
						isCameraBack=false;
					}else{
						isCameraBack=true;
					}
//					SurfaceHolder holder = surfaceview.getHolder();// 鍙栧緱holder
//					holder.addCallback(RecordActivity.this); // holder鍔犲叆鍥炶皟鎺ュ彛
//					LogsUtil.i(TAG, "cameraCount="+cameraCount);
					
					int cameraCount = 0;
	                CameraInfo cameraInfo = new CameraInfo();
	                cameraCount = Camera.getNumberOfCameras();//寰楀埌鎽勫儚澶寸殑涓暟

	                for(int i = 0; i < cameraCount; i++) {
	                	
	                    Camera.getCameraInfo(i, cameraInfo);//寰楀埌姣忎竴涓憚鍍忓ご鐨勪俊鎭�
	                    if(cameraPosition == 1) {
	                        //鐜板湪鏄悗缃紝鍙樻洿涓哄墠缃�
	                        if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {//浠ｈ〃鎽勫儚澶寸殑鏂逛綅锛孋AMERA_FACING_FRONT鍓嶇疆      CAMERA_FACING_BACK鍚庣疆  
	                        	
	                        	camera.stopPreview();//鍋滄帀鍘熸潵鎽勫儚澶寸殑棰勮
	                            camera.release();//閲婃斁璧勬簮
	                            camera = null;//鍙栨秷鍘熸潵鎽勫儚澶�
	                            camera = Camera.open(i);//鎵撳紑褰撳墠閫変腑鐨勬憚鍍忓ご
	                            try {
	                            	deal(camera);
	                                camera.setPreviewDisplay(surfaceHolder);//閫氳繃surfaceview鏄剧ず鍙栨櫙鐢婚潰
	                            } catch (IOException e) {
	                                // TODO Auto-generated catch block
	                                e.printStackTrace();
	                            }
	                            camera.startPreview();//寮�棰勮
	                            cameraPosition = 0;
	                            break;
	                        }
	                    } else {
	                        //鐜板湪鏄墠缃紝 鍙樻洿涓哄悗缃�
	                        if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//浠ｈ〃鎽勫儚澶寸殑鏂逛綅锛孋AMERA_FACING_FRONT鍓嶇疆      CAMERA_FACING_BACK鍚庣疆  
	                            camera.stopPreview();//鍋滄帀鍘熸潵鎽勫儚澶寸殑棰勮
	                            camera.release();//閲婃斁璧勬簮
	                            camera = null;//鍙栨秷鍘熸潵鎽勫儚澶�
	                            camera = Camera.open(i);//鎵撳紑褰撳墠閫変腑鐨勬憚鍍忓ご
	                            try {
	                            	deal(camera);
	                                camera.setPreviewDisplay(surfaceHolder);//閫氳繃surfaceview鏄剧ず鍙栨櫙鐢婚潰
	                            } catch (IOException e) {
	                                // TODO Auto-generated catch block
	                                e.printStackTrace();
	                            }
	                            camera.startPreview();//寮�棰勮
	                            cameraPosition = 1;
	                            break;
	                        }
	                    }

	                }
					
				}
			});
			
	 }
	
	 /**
	  * 杩斿洖
	  *<b>function:</b>
	  *@author cuiran
	  *@createDate 2013-8-20 涓嬪崍2:22:48
	  */
	 public void back(){
			
		finish();
		
		
	 }

	 public Camera deal(Camera camera){
			//璁剧疆camera棰勮鐨勮搴︼紝鍥犱负榛樿鍥剧墖鏄�鏂�0搴︾殑   
			camera.setDisplayOrientation(90); 
		
			Size pictureSize=null;
			Size previewSize=null;
			Camera.Parameters parameters = camera.getParameters();
			parameters.setPreviewFrameRate(5);
			//璁剧疆鏃嬭浆浠ｇ爜
			parameters.setRotation(90);  
//			parameters.setPictureFormat(PixelFormat.JPEG);
			
			List<Size> supportedPictureSizes
			= SupportedSizesReflect.getSupportedPictureSizes(parameters);
			List<Size> supportedPreviewSizes
			= SupportedSizesReflect.getSupportedPreviewSizes(parameters);

			if ( supportedPictureSizes != null &&
					supportedPreviewSizes != null &&
					supportedPictureSizes.size() > 0 &&
					supportedPreviewSizes.size() > 0) {

					//2.x
					pictureSize = supportedPictureSizes.get(0);

					int maxSize = 1280;
					if(maxSize > 0){
						for(Size size : supportedPictureSizes){
							if(maxSize >= Math.max(size.width,size.height)){
								pictureSize = size;
								break;
							}
						}
					}

					WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
					Display display = windowManager.getDefaultDisplay();
					DisplayMetrics displayMetrics = new DisplayMetrics();
					display.getMetrics(displayMetrics);

					previewSize = getOptimalPreviewSize(
										supportedPreviewSizes,
										display.getWidth(),
										display.getHeight()); 

					parameters.setPictureSize(pictureSize.width, pictureSize.height);
					parameters.setPreviewSize(previewSize.width, previewSize.height);								

				}
			camera.setParameters(parameters);
			return camera;
	 }
	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		// 灏唄older锛岃繖涓猦older涓哄紑濮嬪湪oncreat閲岄潰鍙栧緱鐨刪older锛屽皢瀹冭祴缁檚urfaceHolder
		surfaceHolder = holder;
	}
	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		surfaceHolder = holder;
		
		try {
			if(isCameraBack){
				camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);//鎵撳紑鎽勫儚澶�
				
			}else{
				camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);//鎵撳紑鎽勫儚澶�
				
			}
			 
			//璁剧疆camera棰勮鐨勮搴︼紝鍥犱负榛樿鍥剧墖鏄�鏂�0搴︾殑   
			camera.setDisplayOrientation(90); 
		
			Size pictureSize=null;
			Size previewSize=null;
			Camera.Parameters parameters = camera.getParameters();
			parameters.setPreviewFrameRate(5);
			//璁剧疆鏃嬭浆浠ｇ爜
			parameters.setRotation(90);  
//			parameters.setPictureFormat(PixelFormat.JPEG);
			
			List<Size> supportedPictureSizes
			= SupportedSizesReflect.getSupportedPictureSizes(parameters);
			List<Size> supportedPreviewSizes
			= SupportedSizesReflect.getSupportedPreviewSizes(parameters);

			if ( supportedPictureSizes != null &&
					supportedPreviewSizes != null &&
					supportedPictureSizes.size() > 0 &&
					supportedPreviewSizes.size() > 0) {

					//2.x
					pictureSize = supportedPictureSizes.get(0);

					int maxSize = 1280;
					if(maxSize > 0){
						for(Size size : supportedPictureSizes){
							if(maxSize >= Math.max(size.width,size.height)){
								pictureSize = size;
								break;
							}
						}
					}

					WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
					Display display = windowManager.getDefaultDisplay();
					DisplayMetrics displayMetrics = new DisplayMetrics();
					display.getMetrics(displayMetrics);

					previewSize = getOptimalPreviewSize(
										supportedPreviewSizes,
										display.getWidth(),
										display.getHeight()); 

					parameters.setPictureSize(pictureSize.width, pictureSize.height);
					parameters.setPreviewSize(previewSize.width, previewSize.height);								

				}
			camera.setParameters(parameters);
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		if(camera!=null){
			camera.release();
		}
		if(surfaceview!=null)
		{
			surfaceview.getHolder().removeCallback(this);
			surfaceview.getHolder().getSurface().release();
			
		
		}
		surfaceview = null;
		surfaceHolder = null;
		if (surfaceHolder != null) {
			surfaceHolder=null;
		}
		if (mediarecorder != null) {
			mediarecorder=null;
		}
		 if (mediaPlayer != null) {
			 mediaPlayer.release();
			 mediaPlayer = null;
		 }

	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer.OnCompletionListener#onCompletion(android.media.MediaPlayer)
	 */
	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		 LogsUtil.i(TAG, "onCompletion");
		 if (mediaPlayer != null) {
			 mediaPlayer.release();
			 mediaPlayer = null;
		 }
		 recordPlayIv.setVisibility(View.VISIBLE);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer.OnPreparedListener#onPrepared(android.media.MediaPlayer)
	 */
	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		mVideoWidth = mediaPlayer.getVideoWidth();
		mVideoHeight = mediaPlayer.getVideoHeight();
		  if (mVideoWidth != 0 && mVideoHeight != 0)
		  {
			 
		   /* 璁剧疆瑙嗛鐨勫搴﹀拰楂樺害 */
		   surfaceHolder.setFixedSize(mVideoWidth,mVideoHeight);
		  
		   /* 寮�鎾斁 */
		   mediaPlayer.start();
		  }
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
    public void onDestroy() 
	{
         super.onDestroy();
         
         if(surfaceview!=null)
        	 surfaceview.getHolder().getSurface().release();

    }
	
	
	/* (non-Javadoc)
	 * @see android.media.MediaPlayer.OnBufferingUpdateListener#onBufferingUpdate(android.media.MediaPlayer, int)
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer player, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
