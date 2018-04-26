/*
function:添加项目文件和选项的目录的页面,弹出alertdialog输入items
author：谢沛良
create date：2018.4.25
 */

package com.example.xpl.decisionhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AdditemsActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText addname_et;
    private Button additems_bt;
    private Button createone_bt;
    private ListView items_lv;
    private ArrayAdapter arrayAdapter;
    private String[] str;

    private int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additems);
        addname_et = (EditText)findViewById(R.id.addname_et);
        additems_bt = (Button)findViewById(R.id.additems_bt);
        items_lv = (ListView)findViewById(R.id.items_lv);
        createone_bt = (Button)findViewById(R.id.createnoe_bt);
        additems_bt.setOnClickListener(this);
        createone_bt.setOnClickListener(this);
        str = new String[1];
    }

    //弹框
    public void getDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //初始化dialog中的控件

        builder.setTitle("输入选项");
        View view = getLayoutInflater().inflate(R.layout.additem_dialog, null);
        final EditText additemsdialog_et = (EditText) view.findViewById(R.id.additemsdialog_et);
        builder.setView(view);
        builder.setCancelable(true);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ss = additemsdialog_et.getText().toString();
                if(ss.length() >= 1) {
                    if (i > 1) {
                        String[] temp = new String[i-1];
                        System.arraycopy(str, 0, temp, 0, i-1);
                        str = new String[i];
                        System.arraycopy(temp, 0, str, 0, i-1);
                        str[i-1] = ss;
                        i++;
                    } else {
                        str = new String[1];
                        i++;
                        str[0] = ss;
                    }
                    arrayAdapter = new ArrayAdapter(AdditemsActivity.this, android.R.layout.simple_list_item_1, str);
                    items_lv.setAdapter(arrayAdapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "检查输入哦",Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
        builder.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.additems_bt:
                getDialog();
                break;
            case R.id.createnoe_bt:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdditemsActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
