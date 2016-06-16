package com.hss01248.mytoast;

import android.content.Context;
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
    public static void showToast(final Context context, String text,boolean isLong) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, isLong ?Toast.LENGTH_LONG :Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
            mToast.setDuration(isLong ?Toast.LENGTH_LONG :Toast.LENGTH_SHORT);
        }

        mToast.getView().post(new Runnable() {
            @Override
            public void run() {
                mToast.show();
            }
        });

    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public static void showDebugToast(final Context context,final String text,boolean isDebugMode) {
       if (!isDebugMode){
           return;
       }
        showToast(context,text,false);
    }

    public static void showLongToast(final Context context,final String text) {

       showToast(context,text,true);

    }


    private static void showImageCneterToast(Context context, final String text, int picId, boolean isLong){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_layout, null);

        ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
        image.setImageResource(picId);

        TextView textV = (TextView) layout.findViewById(R.id.toast_text);
        textV.setText(text);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration((isLong) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void showSuccessToast(Context context,String text){
        showImageCneterToast(context,text,R.mipmap.ic_done_white_36dp,false);
    }

    public static void showFailToast(Context context,String text){
        showImageCneterToast(context,text,R.mipmap.ic_error_outline_white_36dp,false);
    }
}
