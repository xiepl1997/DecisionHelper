/*
function:绘制转盘
author：谢沛良
create date：2018.4.25
 */

package com.example.xpl.decisionhelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

public class DescSufaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    private SurfaceHolder sfh;
    private Canvas canvas;
    private Paint paint0, paint1;
    //线程开断标记
    private boolean go;
    //绘制线程
    private Thread thread;
    //中心点
    private float cX;
    private float cY;
    //扇形块数
    private int parts;
    //转速
    private float velocity;
    //转盘范围
    private RectF rectF;
    //绘制起始角度
    private float startAngle;
    private MyColor myColor;
    private Random random;
    //随机颜色数组
    private int r_color[];

    public DescSufaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint0 = new Paint();
        paint1 = new Paint();
        myColor = new MyColor();
        random = new Random();
        //设置扇形边宽色
        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(1);
        paint1.setStyle(Paint.Style.STROKE);
        paint0.setAntiAlias(true);
        paint1.setAntiAlias(true);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        go = true;
        velocity = 4;
        startAngle = 0;
        parts = 15;
        cX = this.getWidth()/2;
        cY = this.getHeight()/2;
        rectF = new RectF(this.getWidth()/2-cY, 0, this.getWidth()/2+cY, this.getHeight());
        thread = new Thread(this);
        thread.start();
        //获取扇形随机颜色数组
        r_color = new int[parts];
        for(int i = 0; i < parts; i++){
            int r = random.nextInt(22);
            r_color[i] = r;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        go = false;
        holder.removeCallback(this);
        Log.i("ss", "bybe");
    }

    public void myDraw() {
        try {
            canvas = sfh.lockCanvas();
            if(canvas!=null){
                canvas.drawColor(Color.WHITE);
                startAngle = startAngle+velocity;
                //扇形的角度
                float angle = 360/parts;
                //画转盘
                for(int i = 0; i < parts; i++){
                    paint0.setColor(Color.rgb(myColor.getColor(r_color[i],0),myColor.getColor(r_color[i],1),myColor.getColor(r_color[i],2)));
//                    paint0.setColor(Color.RED);
                    canvas.drawArc(rectF, startAngle, angle, true, paint0);
                    canvas.drawArc(rectF, startAngle, angle, true, paint1);

                    startAngle += angle;
                }
                if(velocity != 1){
                    velocity -= 0.01;
                }
                if(velocity < 0){
                    velocity = 0;
                }

            }
        }catch (Exception e){
        }
        finally {
            if(canvas!=null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    //线程里绘制转盘
    @Override
    public void run() {
        while(go){
            long start = System.currentTimeMillis();
            myDraw();
            long end = System.currentTimeMillis();
            try {
                if(end-start<10){
                    Thread.sleep(10- (end-start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
