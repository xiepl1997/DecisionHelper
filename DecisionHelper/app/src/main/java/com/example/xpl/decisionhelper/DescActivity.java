/*
function:显示转盘的活动，接收来自mainactivity的数据进行转盘选择
tips:
     花了一早上才实现转盘值传给activity的textview这个功能。。。。f**k
     自定义view传值到activity上，这是个坑。。。。。。
     这个值不是view实例化就产生的，是在判断点击后速度减为0再取的,所以不能在oncreat（）中简单地取出来
     所以遇到问题，并且ui还不能在线程中更新，所以想出办法：
     1.在线程中获取转盘结果，发送广播
     2.在主线程中接收广播，更新ui
     3.大吉大利！！！！五一快乐！！！
author：谢沛良
create date：2018.4.25
finished date：2018.4.29
 */

package com.example.xpl.decisionhelper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DescActivity extends AppCompatActivity {

    //用于做决定的数据
    public static String data;
    public String name;
    public static String resule="";
    public TextView title;
    public static TextView textView;
    private DescSufaceView descSufaceView;

    private String str;
    private myThread thread;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    private IntentFilter intentFilter;

    //动画
    private ObjectAnimator title_amim1;
    private ObjectAnimator title_amim2;
    private AnimatorSet animatorSet1;

    private ObjectAnimator myview_amim1;
    private ObjectAnimator myview_amim2;
    private AnimatorSet animatorSet2;

    private ObjectAnimator result_amim1;
    private ObjectAnimator result_amim2;
    private AnimatorSet animatorSet3;

    private HistoryDatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        setContentView(R.layout.activity_desc);
        name = intent.getStringExtra("name");
        textView = (TextView)findViewById(R.id.resule_tv);
        title  = (TextView)findViewById(R.id.title2_tv);
        descSufaceView = (DescSufaceView)findViewById(R.id.myview);
        textView.setText(resule);

        databaseHelper = new HistoryDatabaseHelper(this, "History.db", null, 1);
        db = databaseHelper.getWritableDatabase();

        //动画
        animatorSet1 = new AnimatorSet();
        title_amim1 = ObjectAnimator.ofFloat(title, "scaleX", 0, 1f);
        title_amim2 = ObjectAnimator.ofFloat(title, "scaleY", 0, 1f);
        title_amim1.setDuration(800);
        title_amim2.setDuration(800);
        animatorSet1.play(title_amim1).with(title_amim2);
        animatorSet1.start();

        animatorSet2 = new AnimatorSet();
        result_amim1 = ObjectAnimator.ofFloat(textView, "scaleX", 0, 1f).setDuration(900);
        result_amim2 = ObjectAnimator.ofFloat(textView, "scaleY", 0, 1f).setDuration(900);
        animatorSet2.play(result_amim1).with(result_amim2);
        animatorSet2.start();

        animatorSet3 = new AnimatorSet();
        myview_amim1 = ObjectAnimator.ofFloat(descSufaceView, "scaleX", 0, 1f).setDuration(1200);
        myview_amim2 = ObjectAnimator.ofFloat(descSufaceView, "scaleY", 0, 1f).setDuration(1200);
        animatorSet3.play(myview_amim1).with(myview_amim2);
        animatorSet3.start();

        descSufaceView = (DescSufaceView)findViewById(R.id.myview);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        if(localReceiver == null) {
            intentFilter = new IntentFilter();
            intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
            localReceiver = new LocalReceiver();
            localBroadcastManager.registerReceiver(localReceiver, intentFilter);
        }
        thread = new myThread();
        thread.start();
    }

    //线程加广播实现更新ui
    public class myThread extends Thread{
        private boolean stop = false;
        @Override
        public void run() {
            while(!stop){
                try {
                    sleep(30);
                    Log.i("run", "thread");
                    //            while(descSufaceView.getFlag()!=2);
                    str = descSufaceView.getResule();
                    Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                    localBroadcastManager.sendBroadcast(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public void close(){
            stop = true;
        }
    }
    public class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            textView.setText(str);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(DescActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("data", data);
        intent.putExtras(bundle);
        startActivity(intent);
        thread.close();
        localBroadcastManager.unregisterReceiver(localReceiver);
        this.finish();
    }

    //在页面销毁时将本次选择的数据计入到历史数据库中
    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
        //获取时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年mm月dd日 hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("result", str);
        values.put("time", simpleDateFormat.format(date));
        db.insert("History", null, values);
        values.clear();
        Log.i("DescActivity", name);
    }
}
