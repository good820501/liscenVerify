package com.homenet.basecompoment;
import com.example.homenet.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class CusAlertDialog 
{
	Context context;
    android.app.AlertDialog ad;
    TextView titleView;
    TextView messageView;
    LinearLayout buttonLayout;


    public CusAlertDialog(Context context) 
    {
        this.context = context;
        ad = new android.app.AlertDialog.Builder(context).create();
        ad.show();
        // Replace the source alert dialog.
        Window window = ad.getWindow();
        window.setContentView(R.layout.custom_dialog_view);
        titleView = (TextView) window.findViewById(R.id.title);
        messageView = (TextView) window.findViewById(R.id.message);
        buttonLayout = (LinearLayout) window.findViewById(R.id.buttonLayout);
        
        
        
    }
    public void setTitle(int resId)
    {
        titleView.setText(resId);
    }


    public void setTitle(String title) {
        titleView.setText(title);
    }


    public void setMessage(int resId) {
        messageView.setText(resId);
    }


    public void setMessage(String message)
    {
        messageView.setText(message);
    }


    /**
     * Button style
     * 
     * @param text
     * @param listener
     */
    public void setPositiveButton(String text, final View.OnClickListener listener)
    {
        Button button = new Button(context);
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        button.setBackgroundResource(R.drawable.order_sliding_down);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setTextSize(20);
        button.setOnClickListener(listener);
        buttonLayout.addView(button);
    }


    /**
     *  Button style
     * 
     * @param text
     * @param listener
     */
    public void setNegativeButton(String text, final View.OnClickListener listener)
    {
        Button button = new Button(context);
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        button.setBackgroundResource(R.drawable.order_sliding_down);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setTextSize(20);
        button.setOnClickListener(listener);
        if (buttonLayout.getChildCount() > 0)
        {
            params.setMargins(20, 0, 0, 0);
            button.setLayoutParams(params);
            buttonLayout.addView(button, 1);
        } else {
            button.setLayoutParams(params);
            buttonLayout.addView(button);
        }


    }


    /**
     * dismiss dialog
     */
    public void dismiss() {
        ad.dismiss();
    }
}
