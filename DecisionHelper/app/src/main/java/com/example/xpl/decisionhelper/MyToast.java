/*
function:优化Toast
author: 谢沛良
create date:2018.5.1
 */

package com.example.xpl.decisionhelper;

import android.content.Context;
import android.widget.Toast;

public class MyToast {
    private static Toast toast;
    public static void shows(Context context, String msg){
        if(toast == null){
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        }
        else{
            toast.setText(msg);
        }
        toast.show();
    }
}
