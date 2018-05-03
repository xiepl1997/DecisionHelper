/*
function:主页面，主要实现了控件的展示，控件的动画，主页面的显示和按键的监听
author：谢沛良
create date：2018.4.21
 */

package com.example.xpl.decisionhelper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xpl.decisionhelper.tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //控件
    private ExplosionField explosionField;
    private Button main_bt1;
    private TextView title_tv;
    private Button addname_bt;
    private ImageButton history_bt;
    private ImageButton hasnamed_bt;
    private ImageButton setting_bt;

    //主按键1动画
    private ObjectAnimator mainabt1_anim1;
    private ObjectAnimator mainabt1_anim2;
    private ObjectAnimator mainabt1_anim3;
    private AnimatorSet animatorSet1;
    //title动画
    private ObjectAnimator title_tv_amim1;
    private ObjectAnimator title_tv_amim2;
    private AnimatorSet animatorSet2;
    //addname_bt动画
    private ObjectAnimator addname_bt_amim1;
    private ObjectAnimator addname_bt_amim2;
    private AnimatorSet animatorSet3;
    //history_bt动画
    private ObjectAnimator history_bt_amim1;
    private ObjectAnimator history_bt_amim2;
    private AnimatorSet animatorSet4;
    //hasnamed_bt动画
    private ObjectAnimator hasnamed_bt_amim1;
    private ObjectAnimator hasnamed_bt_amim2;
    private AnimatorSet animatorSet5;
    //setting_bt动画
    private ObjectAnimator setting_bt_amim1;
    private ObjectAnimator setting_bt_amim2;
    private AnimatorSet animatorSet6;

    //选择要决定的事件的事件名，事件包含的选项
    private String name;
    private String data;

    //是否选择好事件
    boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化，开始动画
        ViewInit();
        ViewAnimotarStart();

        main_bt1.setOnClickListener(this);
        title_tv.setOnClickListener(this);
        addname_bt.setOnClickListener(this);
        history_bt.setOnClickListener(this);
        hasnamed_bt.setOnClickListener(this);
        setting_bt.setOnClickListener(this);
        //接收其他activity的数据
        Intent intent = getIntent();
        name = intent.getStringExtra("name"); // 事件名
        data = intent.getStringExtra("data"); // 事件选项
        main_bt1.setText(main_bt1.getText().toString()+":\n"+name);
    }

    //view初始化
    public void ViewInit(){
        main_bt1 = (Button)findViewById(R.id.main_bt1);
        explosionField = ExplosionField.attach2Window(this);
        title_tv = (TextView)findViewById(R.id.title_tv);
        addname_bt = (Button)findViewById(R.id.addname_bt);
        history_bt = (ImageButton)findViewById(R.id.history_bt);
        hasnamed_bt = (ImageButton)findViewById(R.id.hasnamed_bt);
        setting_bt = (ImageButton)findViewById(R.id.setting_bt);
    }

    //控件动画显示
    @SuppressLint("WrongConstant")
    public void ViewAnimotarStart(){
        //main_bt1起始跳动动画
        animatorSet1 = new AnimatorSet();
        float buttonX = main_bt1.getTranslationX();
        mainabt1_anim1 = ObjectAnimator.ofFloat(main_bt1, "translationY", buttonX, 60, buttonX);
        mainabt1_anim1.setDuration(1500);
        mainabt1_anim1.setRepeatCount(Animation.INFINITE);
        mainabt1_anim1.setRepeatMode(Animation.REVERSE);
        //main_bt1放大动画
        mainabt1_anim2 = ObjectAnimator.ofFloat(main_bt1, "scaleX", 0, 1f);
        mainabt1_anim3 = ObjectAnimator.ofFloat(main_bt1, "scaleY", 0, 1f);
        mainabt1_anim2.setDuration(1500);
        mainabt1_anim3.setDuration(1500);
        animatorSet1.play(mainabt1_anim2).with(mainabt1_anim3).with(mainabt1_anim1);
        animatorSet1.start();

        //title_tv动画
        animatorSet2 = new AnimatorSet();
        title_tv_amim1 = ObjectAnimator.ofFloat(title_tv, "scaleX", 0, 1f);
        title_tv_amim2 = ObjectAnimator.ofFloat(title_tv, "scaleY", 0, 1f);
        title_tv_amim1.setDuration(800);
        title_tv_amim2.setDuration(800);
        animatorSet2.play(title_tv_amim1).with(title_tv_amim2);
        animatorSet2.start();

        //addname_bt动画
        animatorSet3 = new AnimatorSet();
        addname_bt_amim1 = ObjectAnimator.ofFloat(addname_bt, "scaleX", 0, 1f).setDuration(900);
        addname_bt_amim2 = ObjectAnimator.ofFloat(addname_bt, "scaleY", 0, 1f).setDuration(900);
        animatorSet3.play(addname_bt_amim1).with(addname_bt_amim2);
        animatorSet3.start();
        //history_bt
        animatorSet4 = new AnimatorSet();
        history_bt_amim1 = ObjectAnimator.ofFloat(history_bt, "scaleX", 0, 1f).setDuration(1200);
        history_bt_amim2 = ObjectAnimator.ofFloat(history_bt, "scaleY", 0, 1f).setDuration(1200);
        animatorSet4.play(history_bt_amim1).with(history_bt_amim2);
        animatorSet4.start();
        //hasnamed_bt
        animatorSet5 = new AnimatorSet();
        hasnamed_bt_amim1 = ObjectAnimator.ofFloat(hasnamed_bt, "scaleX", 0, 1f).setDuration(800);
        hasnamed_bt_amim2 = ObjectAnimator.ofFloat(hasnamed_bt, "scaleY", 0, 1f).setDuration(800);
        animatorSet5.play(hasnamed_bt_amim1).with(hasnamed_bt_amim2);
        animatorSet5.start();
        //setting_bt
        animatorSet6 = new AnimatorSet();
        setting_bt_amim1 = ObjectAnimator.ofFloat(setting_bt, "scaleX", 0, 1f).setDuration(1000);
        setting_bt_amim2 = ObjectAnimator.ofFloat(setting_bt, "scaleY", 0, 1f).setDuration(1000);
        animatorSet6.play(setting_bt_amim1).with(setting_bt_amim2);
        animatorSet6.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_bt1:
                if(name != null) {
                    explosionField.explode(main_bt1);
                        new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Thread.sleep(1000);
                                Intent intent = new Intent(MainActivity.this, DescActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("name", name);
                                bundle.putString("data", data);
                                intent.putExtras(bundle);
                                MainActivity.this.startActivity(intent);
                                /*
                                结束掉主页面，在下一个页面按下返回键重启主页面，解决使用爆炸效果后
                                返回主页面按键不显示的问题
                                */
                                MainActivity.this.finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
                else{
                    MyToast.shows(this, "您还没选择事件的呢^_^");
                }
                break;
            case R.id.addname_bt:
                explosionField.explode(addname_bt);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(1000);
                            Intent intent = new Intent(MainActivity.this, AdditemsActivity.class);
                            MainActivity.this.startActivity(intent);
                            /*
                            结束掉主页面，在下一个页面按下返回键重启主页面，解决使用爆炸效果后
                            返回主页面按键不显示的问题
                            */
                            MainActivity.this.finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.history_bt:
                explosionField.explode(history_bt);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(1000);
                            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                            MainActivity.this.startActivity(intent);
                            /*
                            结束掉主页面，在下一个页面按下返回键重启主页面，解决使用爆炸效果后
                            返回主页面按键不显示的问题
                            */
                            MainActivity.this.finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.hasnamed_bt:
                explosionField.explode(hasnamed_bt);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try{
                            Thread.sleep(1000);
                            Intent intent = new Intent(MainActivity.this, HasnamedActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.setting_bt:
                explosionField.explode(setting_bt);
                break;
        }
    }

    private long times = 0;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - times > 2000){
            MyToast.shows(this, "再按下返回键退出应用");
            times = System.currentTimeMillis();
        }
        else{
            super.onBackPressed();
            System.exit(0);
//            finish();
        }
    }
}
