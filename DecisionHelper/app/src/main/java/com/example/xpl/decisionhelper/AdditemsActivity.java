/*
function:添加项目文件和选项的目录的页面,弹出alertdialog输入items
         数据库的建立，数据的将输入添加到数据库中
author：谢沛良
create date：2018.4.25
 */

package com.example.xpl.decisionhelper;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

    private MyDatabaseHelper myDatabaseHelper;
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
        myDatabaseHelper = new MyDatabaseHelper(this, "Data.db", null, 1);
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

        //实现动态增添字符串数组str，将str显示到listview上
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
                    MyToast.shows(getApplicationContext(), "检查输入哦");
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
                //将字符串数组中的数据存入一个字符串中，项与项之间用空格隔开
                String data = "";
                for (int i = 0; i < str.length; i++) {
                    data += str[i];
                    if (i != str.length - 1) {
                        data += "*";
                    }
                }
                if(addname_et.getText().toString().length()>=1 && str.length>=2) {
                    //创建or打开数据库，导入数据
                    SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name", addname_et.getText().toString());
                    values.put("data", data);
                    db.insert("Data", null, values);
                    values.clear();
                    Intent intent = new Intent(AdditemsActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", addname_et.getText().toString());
                    bundle.putString("data", data);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if(addname_et.getText().toString().length() < 1) {
                    MyToast.shows(getApplicationContext(), "项目名字还没写呢^_^");
                }
                else if(str.length<2) {
                    MyToast.shows(getApplicationContext(), "选项少于两个的呢^_^");
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdditemsActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
        super.onBackPressed();
    }
}
