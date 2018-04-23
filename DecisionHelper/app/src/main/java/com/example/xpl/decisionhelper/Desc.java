/*
function:绘制圆盘，生成随机数
author：xpl
create time:2018.4.21
 */

package com.example.xpl.decisionhelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Desc extends View{
    public Desc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        RectF rectF = new RectF(150, 130, 250,230);
        canvas.drawOval(rectF,paint);
    }
}
