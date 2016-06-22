package com.hss01248.tools.pack.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.widget.RemoteViews;

import com.hss01248.tools.R;
import com.hss01248.tools.base.BaseUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MyNotification {

    public static final int ID_SINGLEINE = 23;
    public static final int ID_MULTILINE = 24;
    public static final int ID_MAILBOX = 25;
    public static final int ID_BIGPIC = 26;
    public static final int ID_CUSTOM_VIEW = 27;
    public static final int ID_BUTTON = 28;
    public static final int ID_PROGRESS = 29;
    public static final int ID_HEADUP = 30;
    public static final int ID_SERVICE = 31;



    /**
     *  //设置想要展示的数据内容
             Intent intent = new Intent(mContext, OtherActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
             PendingIntent pIntent = PendingIntent.getActivity(mContext,
             requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
             int smallIcon = R.drawable.tb_bigicon;
             String ticker = "您有一条新通知";
             String title = "双十一大优惠！！！";
             String content = "仿真皮肤充气娃娃，女朋友带回家！";
     * @return
     */
    public static NotifyUtil showSingline(PendingIntent pIntent,int smallIcon,String ticker,String title,String content){
            //实例化工具类，并且调用接口
            NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_SINGLEINE);
            notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, true);
        return notify1;
    }


    /**
     *
     *
     Intent intent = new Intent(mContext, OtherActivity.class);
     intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent pIntent = PendingIntent.getActivity(mContext,
     requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
     int smallIcon = R.drawable.netease_bigicon;
     String ticker = "您有一条新通知";
     String title = "朱立伦请辞国民党主席 副主席黄敏惠暂代党主席";
     String content = "据台湾“中央社”报道，国民党主席朱立伦今天(18日)向中常会报告，为败选请辞党主席一职，他感谢各位中常委的指教包容，也宣布未来党务工作由副主席黄敏惠暂代，完成未来所有补选工作。";
     //实例化工具类，并且调用接口
     *
     * @param pIntent
     * @param smallIcon
     * @param ticker
     * @param title
     * @param content
     * @return
     */
    public static NotifyUtil showMultiline(PendingIntent pIntent,int smallIcon,String ticker,String title,String content) {

        NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_MULTILINE);
        notify1.notify_normail_moreline(pIntent, smallIcon, ticker, title, content, true, true, false);
        return notify1;
    }


    /**
     *
     Intent intent = new Intent(mContext, OtherActivity.class);
     intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent pIntent = PendingIntent.getActivity(mContext,
     requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
     int largeIcon = R.drawable.fbb_largeicon;
     int smallIcon = R.drawable.wx_smallicon;
     String ticker = "您有一条新通知";
     String title = "冰冰";
     ArrayList<String> messageList = new ArrayList<String>();
     messageList.add("文明,今晚有空吗？");
     messageList.add("晚上跟我一起去玩吧?");
     messageList.add("怎么不回复我？？我生气了！！");
     messageList.add("我真生气了！！！！！你听见了吗!");
     messageList.add("文明，别不理我！！！");
     String content = "[" + messageList.size() + "条]" + title + ": " + messageList.get(0);
     * @param pIntent
     * @param smallIcon
     * @param largeIcon
     * @param messageList
     * @param ticker
     * @param title
     * @param content
     * @return
     */
    public static NotifyUtil showMailBox(PendingIntent pIntent, int smallIcon, int largeIcon,
                                         ArrayList<String> messageList, String ticker,
                                         String title, String content) {

        NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_MAILBOX);
        notify1.notify_mailbox(pIntent, smallIcon, largeIcon, messageList, ticker,
                title, content, true, true, false);
        return notify1;
    }


    /**
     *  //设置想要展示的数据内容
     Intent intent = new Intent(mContext, OtherActivity.class);
     intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent pIntent = PendingIntent.getActivity(mContext,
     requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
     int smallIcon = R.drawable.xc_smaillicon;
     int largePic = R.drawable.screenshot;
     String ticker = "您有一条新通知";
     String title = "已经抓取屏幕截图";
     String content = "触摸可查看您的屏幕截图";
     * @param pIntent
     * @param smallIcon
     * @param ticker
     * @param title
     * @param content
     * @return
     */
    public static NotifyUtil showBigpic(PendingIntent pIntent,int smallIcon,int largePic,String ticker,String title,String content) {

        NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_BIGPIC);
        notify1.notify_bigPic(pIntent, smallIcon, ticker, title, content, largePic, true, true, false);
        return notify1;
    }


    /**
     * //设置想要展示的数据内容
     Intent intent = new Intent(mContext, OtherActivity.class);
     intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent pIntent = PendingIntent.getActivity(mContext,
     requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
     String ticker = "您有一条新通知";

     //设置自定义布局中按钮的跳转界面
     Intent btnIntent = new Intent(mContext, OtherActivity.class);
     btnIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     //如果是启动activity，那么就用PendingIntent.getActivity，如果是启动服务，那么是getService
     PendingIntent Pintent = PendingIntent.getActivity(mContext,
     (int) SystemClock.uptimeMillis(), btnIntent, PendingIntent.FLAG_UPDATE_CURRENT);

     // 自定义布局
     RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
     R.layout.yyb_notification);
     remoteViews.setImageViewResource(R.id.image, R.drawable.yybao_bigicon);
     remoteViews.setTextViewText(R.id.title, "垃圾安装包太多");
     remoteViews.setTextViewText(R.id.text, "3个无用安装包，清理释放的空间");
     remoteViews.setOnClickPendingIntent(R.id.button, Pintent);//定义按钮点击后的动作
     int smallIcon = R.drawable.yybao_smaillicon;
     * @param remoteViews
     * @param pIntent
     * @param smallIcon
     * @param ticker
     * @return
     */
    public static NotifyUtil showCustomView(RemoteViews remoteViews,PendingIntent pIntent, int smallIcon, String ticker) {

        NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_CUSTOM_VIEW);
        notify1.notify_customview(remoteViews, pIntent, smallIcon, ticker, true, true, false);
        return notify1;
    }

    /**
     *  String ticker = "您有一条新通知";
     int smallIcon = R.drawable.android_bigicon;
     int lefticon = R.drawable.android_leftbutton;
     String lefttext = "以后再说";
     Intent leftIntent = new Intent();
     leftIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent leftPendIntent = PendingIntent.getActivity(mContext,
     requestCode, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);

     int righticon = R.drawable.android_rightbutton;
     String righttext = "安装";
     Intent rightIntent = new Intent(mContext, OtherActivity.class);
     rightIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent rightPendIntent = PendingIntent.getActivity(mContext,
     requestCode, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);

     * @param smallIcon
     * @param ticker
     * @return
     */
    public static NotifyUtil showButton(int smallIcon,int lefticon,String lefttext, PendingIntent leftPendIntent,
                                        int righticon,String  righttext, PendingIntent rightPendIntent, String ticker,
                                        String title,String content) {

        NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_BUTTON);
        notify1.notify_button(smallIcon, lefticon, lefttext, leftPendIntent, righticon, righttext, rightPendIntent,
                ticker, title, content, true, true, false);
        return notify1;
    }


    /**
     *  Intent intent = new Intent(mContext, OtherActivity.class);
     intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent rightPendIntent = PendingIntent.getActivity(mContext,
     requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
     int smallIcon = R.drawable.android_bigicon;
     String ticker = "您有一条新通知";
     * @param pendingIntent
     * @param smallIcon

     * @param ticker
     * @param title
     * @param content
     * @return
     */
    public static NotifyUtil getProgressor(PendingIntent pendingIntent,int smallIcon,String ticker,String title,String content) {

        NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_PROGRESS);
        notify1.notify_progress(pendingIntent, smallIcon, ticker, title, content, true, false, false);
        return notify1;
    }

    public static void updateProgress(NotifyUtil notifyUtil,int progress,String finishTxt){
        notifyUtil.update_progress(progress,finishTxt);
    }


    /**
     * int smallIcon = R.drawable.hl_smallicon;
     int largeIcon = R.drawable.fbb_largeicon;
     String ticker = "您有一条新通知";
     String title = "范冰冰";
     String content = "文明，今晚在希尔顿酒店2016号房哈";
     Intent intent = new Intent(mContext, OtherActivity.class);
     intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent pendingIntent = PendingIntent.getActivity(mContext,
     requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);


     int lefticon = R.drawable.hl_message;
     String lefttext = "回复";
     Intent leftIntent = new Intent();
     leftIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent leftPendingIntent = PendingIntent.getActivity(mContext,
     requestCode, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);

     int righticon = R.drawable.hl_call;
     String righttext = "拨打";
     Intent rightIntent = new Intent(mContext, OtherActivity.class);
     rightIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent rightPendingIntent = PendingIntent.getActivity(mContext,
     requestCode, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);
     * @param pendingIntent
     * @param smallIcon
     * @param largeIcon
     * @param ticker
     * @param title
     * @param content
     * @param lefticon
     * @param lefttext
     * @param leftPendingIntent
     * @param righticon
     * @param righttext
     * @param rightPendingIntent
     * @return
     */
    public static NotifyUtil showHeadup(PendingIntent pendingIntent, int smallIcon, int largeIcon, String ticker, String title, String content,
                                       int  lefticon, String lefttext, PendingIntent leftPendingIntent, int righticon, String righttext,
                                        PendingIntent rightPendingIntent) {

        NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_HEADUP);
        notify1.notify_HeadUp(pendingIntent, smallIcon, largeIcon, ticker, title, content,
                lefticon, lefttext, leftPendingIntent, righticon, righttext, rightPendingIntent, true, true, true);
        return notify1;
    }

    public static NotifyUtil showService(){
        NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_SERVICE);
        notify1.showServiceForGround(null, R.mipmap.ic_launcher,"搭搭巴士开始监测","监测嗒嗒巴士中","西乡步行街到香港理工");
        return notify1;
    }


    public static NotifyUtil showSound(PendingIntent pIntent,int smallIcon,String ticker,String title,String content){
        NotifyUtil notify1 = new NotifyUtil(BaseUtils.getContext(), ID_SINGLEINE);
        notify1.showSimpleWithSound(pIntent, smallIcon, ticker, title, content,R.raw.sms);
        return notify1;
    }






    public static void clear(int id){
        NotificationManager nm =   (NotificationManager) BaseUtils.getContext()
                .getSystemService(Activity.NOTIFICATION_SERVICE);
        nm.cancel(id);
    }

    public static void clearAll(){
        NotificationManager nm =   (NotificationManager) BaseUtils.getContext()
                .getSystemService(Activity.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }






}
