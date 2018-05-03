/*
function:自定义转盘view，实现绘制转盘，自定义点击监听，点击监听部分后给一个startangle的变化量，
        使转盘“转动”， 将这个变化量递减，呈现转盘速度减小的效果。
author：谢沛良
create date：2018.4.25
 */

package com.example.xpl.decisionhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

public class DescSufaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    private Context mcontext;
    private SurfaceHolder sfh;
    private Canvas canvas;
    private Paint paint0, paint1, paint3, paint4;
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
    //弧形文字范围
    private RectF rectFtext;
    //开始指针图形范围
    private RectF rectFaim;

    //绘制起始角度
    private float startAngle;
    private MyColor myColor;
    private Random random;
    //随机颜色数组
    private int r_color[];
    //要决定的选项
    private String[] data;
    //文字路径
    private Path path,path1;

    //是否旋转标记
    private int flag = 0;
    //start的y坐标
    private float baseLine;

    //结果
    public static String resule = "null";

    public DescSufaceView(Context context) {
        super(context);
    }

    public DescSufaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint0 = new Paint();
        paint1 = new Paint();
        paint3 = new Paint();
        paint4 = new Paint();
        myColor = new MyColor();
        random = new Random();
        path = new Path();
        path1 = new Path();
        //设置扇形边宽色
        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(1);
        paint1.setStyle(Paint.Style.STROKE);
        paint0.setAntiAlias(true);
        paint1.setAntiAlias(true);
        //文本大小，颜色
        paint3.setColor(Color.BLACK);
        paint3.setTextSize(40);
        paint3.setAntiAlias(true);
        paint3.setTextAlign(Paint.Align.CENTER);
        //开始指针颜色
        paint4.setAntiAlias(true);
        paint4.setColor(Color.rgb(130, 207, 235));
        setFocusable(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("s","okok");
        int evevtaction = event.getAction();
        switch (evevtaction){
            case MotionEvent.ACTION_UP:
                int x = (int)event.getX();
                int y = (int )event.getY();
                if(x >= this.getWidth()/2-80 && x <= this.getWidth()/2+80 && y >=this.getHeight()/2-80&&y<=this.getHeight()/2+80){
                    velocity = random.nextInt(5)+4;
                    flag = 1;
                }
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //获取选项，转变成字符串数组
        data = new DescActivity().data.split(",");
        go = true;
        velocity = 0;
        startAngle = 0;
        parts = data.length;
        cX = this.getWidth()/2;
        cY = this.getHeight()/2;
        rectF = new RectF(this.getWidth()/2-cY, 0, this.getWidth()/2+cY, this.getHeight());
        rectFtext = new RectF(this.getWidth()/2-cY+60, 60, this.getWidth()/2+cY-60, this.getHeight()-60);
        rectFaim = new RectF(this.getWidth()/2-80, this.getHeight()/2-80, this.getWidth()/2+80, this.getHeight()/2+80);
        path1.moveTo(this.getWidth()/2, this.getHeight()/2-60);
        path1.lineTo(this.getWidth()/2, this.getHeight()/2+60);
        path1.lineTo(this.getWidth()/2+300, this.getHeight()/2);
        path1.close();
        Paint.FontMetrics fontMetrics = paint3.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        baseLine = (int) (rectFaim.centerY() - top/2 - bottom/2);
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

    //转盘绘制
    public void myDraw() {
        try {
            canvas = sfh.lockCanvas();
            if(canvas!=null){
                canvas.drawColor(Color.rgb(249, 249, 249));
                startAngle = startAngle+velocity;
                //扇形的角度
                float angle = 360/parts;
                //画转盘
                for(int i = 0; i < parts; i++){
                    paint0.setColor(Color.rgb(myColor.getColor(r_color[i],0),myColor.getColor(r_color[i],1),myColor.getColor(r_color[i],2)));
//                    paint0.setColor(Color.RED);
                    canvas.drawArc(rectF, startAngle, angle, true, paint0);
                    canvas.drawArc(rectF, startAngle, angle, true, paint1);
                    path.addArc(rectFtext, startAngle, angle);
                    //写字
                    canvas.drawTextOnPath(data[i], path, 0, 0, paint3);
                    //不去除path之前赋的属性，字就不会转，只会套用第一次add的路径。
                    path.reset();
                    path.addArc(rectFaim, 30, 300);
                    canvas.drawPath(path, paint4);
                    canvas.drawPath(path1, paint4);
                    canvas.drawText("start",cX, baseLine ,paint3);
                    path.reset();
                    //不断刷新result
                    if(360 - (int)startAngle%360 <= (int)angle && 360 - (int)startAngle%360 >= 0){
                        resule = data[i];
                        Log.i("output",resule);
                    }
                    startAngle += angle;
                }
                if(velocity != 0 && velocity != 1){
                    velocity -= 0.01;
                }
                if(velocity < 0){
                    velocity = 0;
                }
                if(velocity == 0 && flag == 1)
                    flag = 2;
            }
        }catch (Exception e){
        }
        finally {
            if(canvas!=null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    public String getResule(){
        return resule;
    }
    public float getVelocity(){
        return velocity;
    }
    public int getFlag(){
        return flag;
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
