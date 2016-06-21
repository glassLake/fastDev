package com.hss01248.mytoast;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by hss01248 on 11/25/2015.
 */
public class ToastUtils {
    private static Toast mToast;
    public static void showToast(final Context context, final String text, final boolean isLong, Handler mainHandler) {

       if (mainHandler != null){
           mainHandler.post(new Runnable() {
               @Override
               public void run() {
                   if (mToast == null) {
                       mToast = Toast.makeText(context, text, isLong ?Toast.LENGTH_LONG :Toast.LENGTH_SHORT);
                   }else {
                       mToast.setText(text);
                       mToast.setDuration(isLong ?Toast.LENGTH_LONG :Toast.LENGTH_SHORT);
                   }
                   mToast.show();
               }
           });
       }



    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public static void showDebugToast(final Context context,final String text,boolean isDebugMode, Handler mainHandler) {
       if (!isDebugMode){
           return;
       }
        showToast(context,text,false,mainHandler);
    }

    public static void showLongToast(final Context context,final String text, Handler mainHandler) {

       showToast(context,text,true,mainHandler);

    }


    private static void showImageCneterToast(final Context context, final String text, final int picId, final boolean isLong, Handler mainHandler){
        if (mainHandler != null) {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View layout = inflater.inflate(R.layout.toast_layout, null);

                    ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
                    image.setImageResource(picId);

                    TextView textV = (TextView) layout.findViewById(R.id.toast_text);
                    textV.setText(text);

                    final Toast toast = new Toast(context);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration((isLong) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            });
        }



    }

    public static void showSuccessToast(Context context,String text, Handler mainHandler){
        showImageCneterToast(context,text, R.mipmap.ic_done_white_36dp,false,mainHandler);
    }

    public static void showFailToast(Context context,String text, Handler mainHandler){
        showImageCneterToast(context,text, R.mipmap.ic_error_outline_white_36dp,false,mainHandler);
    }
}
