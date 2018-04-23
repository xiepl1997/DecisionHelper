package com.example.xpl.decisionhelper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.xpl.decisionhelper.tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ExplosionField explosionField;
    private Button mainbt1;
    private TextView title_tv;

    private ObjectAnimator mainabt1_anim1;
    private ObjectAnimator mainabt1_anim2;
    private ObjectAnimator mainabt1_anim3;
    private AnimatorSet animatorSet1;

    private ObjectAnimator title_tv_amim1;
    private ObjectAnimator title_tv_amim2;
    private AnimatorSet animatorSet2;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainbt1 = (Button)findViewById(R.id.mainbutton1);
        explosionField = ExplosionField.attach2Window(this);
        title_tv = (TextView)findViewById(R.id.title_tv);

        mainbt1.setVisibility(View.VISIBLE);
        //mainbt1起始跳动动画
        animatorSet1 = new AnimatorSet();
        float buttonX = mainbt1.getTranslationX();
        mainabt1_anim1 = ObjectAnimator.ofFloat(mainbt1, "translationY", buttonX, 60, buttonX);
        mainabt1_anim1.setDuration(1500);
        mainabt1_anim1.setRepeatCount(Animation.INFINITE);
        mainabt1_anim1.setRepeatMode(Animation.REVERSE);

        mainabt1_anim2 = ObjectAnimator.ofFloat(mainbt1, "scaleX", 0, 1f);
        mainabt1_anim3 = ObjectAnimator.ofFloat(mainbt1, "scaleY", 0, 1f);
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

        mainbt1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainbutton1:
                explosionField.explode(mainbt1);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(1000);
                            Intent intent = new Intent(MainActivity.this, DescActivity.class);
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
        }
    }
}
