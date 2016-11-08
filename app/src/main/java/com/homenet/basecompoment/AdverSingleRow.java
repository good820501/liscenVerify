package com.homenet.basecompoment;

import java.util.ArrayList;

import com.example.homenet.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

public class AdverSingleRow extends ScrollView 
{
	View convertView ;
	//���캯��
		public AdverSingleRow(Context context)
		{ 
			this(context, null);
			
			LayoutInflater listContainer;   //��ͼ��������

	        listContainer = LayoutInflater.from(context); //������ͼ��������������������

	        convertView = listContainer.inflate(R.layout.singlenews, null);
	        this.addView(convertView);
	        
		}
		
		public AdverSingleRow(Context context, AttributeSet attrs) 
		{
	        this(context, attrs, 0);
	    }
		public AdverSingleRow(Context context, AttributeSet attrs, int defStyle) 
		{
	        super(context, attrs, defStyle);	        
		}
		 
		 
		
		
		
}
