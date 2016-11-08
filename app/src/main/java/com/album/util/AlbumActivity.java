package com.album.util;//com.album.util.AlbumActivity

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.common.dbhelp.WndAssistant;
import com.example.homenet.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

 
/**
 * 调用相册界面
 * @author Alan
 * 2013-11-15
 */
public class AlbumActivity extends Activity implements android.view.View.OnClickListener{
	
	private GridView gridView;              //控件
	private ArrayList<String> dataList = new ArrayList<String>();                 //所有数据
	private HashMap<String,ImageView> hashMap = new HashMap<String,ImageView>();  //图片键值
	private ArrayList<String> selectedDataList = new ArrayList<String>();         //被选中的数据组
	private String cameraDir = "/DCIM/";   //指定文件路径
//	private String cameraDir = "/";   //指定文件路径
	private ProgressBar progressBar;       //进度条
	private AlbumGridViewAdapter gridImageAdapter;  //适配器
	private LinearLayout selectedImageLayout;       //底部布局
	private Button okButton;
	private HorizontalScrollView scrollview;        //滚动组件
	TextView 		DetailCaption;
	ImageButton 	Return;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		WndAssistant.SetWindowNoTitle(this);
		setContentView(R.layout.activity_album);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		selectedDataList = (ArrayList<String>)bundle.getSerializable("dataList");  //获取上个窗体转过来的数据组
		InitControls();
		init();  //实例化控件
		initListener();  //点击事件
		
	}
	private void InitControls()
	{
		 
	    	Return=(ImageButton)findViewById(R.id.imgBtnSel1);
	    	if(Return!=null)
	    	{   		
	    		Return.setOnClickListener(this);
	    	}
	    	 
	    	//HosDescripe=(TextView)findViewById(R.id.textView2);
	    	
	    	DetailCaption=(TextView)findViewById(R.id.textView1);
	    	
	    	if(DetailCaption!=null)
	    	{
	    		DetailCaption.setTextColor(WndAssistant.COLOR2);
	    		DetailCaption.setTextSize(20); 
	    		WndAssistant.LayoutCaptionText(DetailCaption,"选择图片", this, 100);
	    	}
		
		
	}
	public void onClick(View v) 
	{
		int BtnID = v.getId();
		int BtnCt = 0;
		String StrTemp;
		
		switch(BtnID)
		{
			
			case R.id.imgBtnSel1:
				finish();
				break;
			case R.id.imageView3:
				 
				break;
		}
	}
	private void init() {
		
		progressBar = (ProgressBar)findViewById(R.id.progressbar);
		progressBar.setVisibility(View.GONE);
		gridView = (GridView)findViewById(R.id.myGrid);
		gridImageAdapter = new AlbumGridViewAdapter(AlbumActivity.this, dataList,selectedDataList);
		gridView.setAdapter(gridImageAdapter);
		refreshData(); //执行异步后台操作
		selectedImageLayout = (LinearLayout)findViewById(R.id.selected_image_layout);
		okButton = (Button)findViewById(R.id.ok_button);
		scrollview = (HorizontalScrollView)findViewById(R.id.scrollview);
		
		initSelectImage();
		
	}

	private void initSelectImage() {
		if(selectedDataList==null)
			return;
		for(final String path:selectedDataList){
			ImageView imageView = (ImageView) LayoutInflater.from(AlbumActivity.this).inflate(R.layout.choose_imageview, selectedImageLayout,false);
			selectedImageLayout.addView(imageView);			
			hashMap.put(path, imageView);
			ImageManager2.from(AlbumActivity.this).displayImage(imageView, path,R.drawable.camera_default,100,100);  //被选中的状态
			imageView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					removePath(path);
					gridImageAdapter.notifyDataSetChanged();
				}
			});
		}
		okButton.setText("完成("+selectedDataList.size()+"/8)");
	}

	private void initListener() {
		
		gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
			
			@Override
			public void onItemClick(final ToggleButton toggleButton, int position, final String path,boolean isChecked) {
				
				if(selectedDataList.size()>=8){
					toggleButton.setChecked(false);
					if(!removePath(path)){
						Toast.makeText(AlbumActivity.this, "只能选择8张图片", 200).show();
					}
					return;
				}
					
				if(isChecked){
					if(!hashMap.containsKey(path)){
						ImageView imageView = (ImageView) LayoutInflater.from(AlbumActivity.this).inflate(R.layout.choose_imageview, selectedImageLayout,false);
						selectedImageLayout.addView(imageView);
						imageView.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								
								int off = selectedImageLayout.getMeasuredWidth() - scrollview.getWidth();  
							    if (off > 0) {  
							    	  scrollview.smoothScrollTo(off, 0); 
							    } 
								
							}
						}, 100);
						
						hashMap.put(path, imageView);
						selectedDataList.add(path);
						ImageManager2.from(AlbumActivity.this).displayImage(imageView, path,R.drawable.camera_default,100,100);
						imageView.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								toggleButton.setChecked(false);
								removePath(path);
								
							}
						});
						okButton.setText("完成("+selectedDataList.size()+"/8)");
					}
				}else{
					removePath(path);
				}
				
				
				
			}
		});
		
		okButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				// intent.putArrayListExtra("dataList", dataList);
				bundle.putStringArrayList("dataList",selectedDataList);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
				
			}
		});
		
	}
	
	private boolean removePath(String path){
		if(hashMap.containsKey(path)){
			selectedImageLayout.removeView(hashMap.get(path));
			hashMap.remove(path);
			removeOneData(selectedDataList,path);
			okButton.setText("完成("+selectedDataList.size()+"/8)");
			return true;
		}else{
			return false;
		}
	}
	
	
	private void removeOneData(ArrayList<String> arrayList,String s){
		for(int i =0;i<arrayList.size();i++){
			if(arrayList.get(i).equals(s)){
				arrayList.remove(i);
				return;
			}
		}
	}
	
	/**
	 * 执行异步后台操作
	 */
    private void refreshData(){
    	
    	new AsyncTask<Void, Void, ArrayList<String>>(){
    		
    		@Override
    		protected void onPreExecute() {
    			progressBar.setVisibility(View.VISIBLE);
    			super.onPreExecute();
    		}

			@Override
			protected ArrayList<String> doInBackground(Void... params) {
				ArrayList<String> tmpList = new ArrayList<String>();
				
				ArrayList<String> listDirlocal =  listAlldir(new File(cameraDir));  //指定读取文件夹
				
                ArrayList<String> listDiranjuke = new ArrayList<String>();
                listDiranjuke.addAll(listDirlocal);
                
                for (int i = 0; i < listDiranjuke.size(); i++){
                    listAllfile(new File( listDiranjuke.get(i) ),tmpList);
                }
				return tmpList;
				
			}
			
			protected void onPostExecute(ArrayList<String> tmpList) {
				
				if(AlbumActivity.this==null||AlbumActivity.this.isFinishing()){
					return;
				}
				progressBar.setVisibility(View.GONE);
				dataList.clear();
				dataList.addAll(tmpList);
				gridImageAdapter.notifyDataSetChanged();
				return;
				
			};
    		
    	}.execute();
    	
    }
    /**
     * 指定读取文件夹下的所有文件夹
     * @param nowDir
     * @return
     */
    private ArrayList<String>  listAlldir(File nowDir){
        ArrayList<String> listDir = new ArrayList<String>();
        nowDir = new File(Environment.getExternalStorageDirectory() + nowDir.getPath());
        if(!nowDir.isDirectory()){  //判断是不是文件夹类型
            return listDir;
        }
                
        File[] files = nowDir.listFiles();

        for (int i = 0; i < files.length; i++){
            if(files[i].getName().substring(0,1).equals(".")){
               continue; 
            }
            File file = new File(files[i].getPath()); 
            if(file.isDirectory()){
                listDir.add(files[i].getPath());
            }
        }              
        return listDir;
    }
    
    
    static class CompratorByLastModified implements Comparator<File>  
    {  
	     public int compare(File f1, File f2) {  
	      long diff = f1.lastModified()-f2.lastModified();  
	          if(diff<0)  
	            return 1;  
	          else if(diff==0)  
	            return 0;  
	          else  
	            return -1;  
	          }  
	    public boolean equals(Object obj){  
	      return true;  
	    }  
    }  
    /**
     * 获取全部的符合规定的文件格式的文件路径
     * @param baseFile 根文件夹
     * @param tmpList 保存文件
     * @return
     */
    private ArrayList<String>  listAllfile(File baseFile,ArrayList<String> tmpList){
        if(baseFile != null && baseFile.exists()){
            File[] file = baseFile.listFiles();
            Arrays.sort(file, new AlbumActivity.CompratorByLastModified());  
            for(File f : file){
            	//图片类型
            	if (f.getPath().endsWith(".jpg") || f.getPath().endsWith(".gif") ||f.getPath().endsWith(".png")
                        || f.getPath().endsWith(".jpeg") ||f.getPath().endsWith(".bmp")) {
            		tmpList.add(f.getPath());
				}
            }
        }         
        return tmpList;
    }
    
    @Override
    public void onBackPressed() {
//    	finish();
    	super.onBackPressed();
    }
    
    @Override
    public void finish() {
    	super.finish();
//    	ImageManager2.from(AlbumActivity.this).recycle(dataList);
    }
    
    @Override
    protected void onDestroy() {
    	
    	super.onDestroy();
    }
    

}
