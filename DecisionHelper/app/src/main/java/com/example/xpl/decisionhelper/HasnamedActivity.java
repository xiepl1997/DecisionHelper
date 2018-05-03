/*
function:用于保存用户输入的记录以供于再次选择而不用重新编辑事件名和选项
author:谢沛良
create date:2018.5.2
 */

package com.example.xpl.decisionhelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HasnamedActivity extends AppCompatActivity{

    private LinkedList<Map<String, String>> data;

    private ListView listView;
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase db;
    private String[] names;
    private String[] datas;
    private int size;
    private HasnamedAdapter hasnamedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasnamed);
        listView = findViewById(R.id.hasnamed_lv);
        data = getData();
        hasnamedAdapter = new HasnamedAdapter(this);
        listView.setAdapter(hasnamedAdapter);

    }

    //取数据库中的数
    public LinkedList<Map<String, String>> getData() {

        LinkedList<Map<String, String>> list = new LinkedList<>();
        Map<String, String> map;

        //打开数据库获取数据
        myDatabaseHelper = new MyDatabaseHelper(this, "Data.db", null, 1);
        db = myDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Data", null);
        size = cursor.getCount();
        datas = new String[size];
        names = new String[size];
        int i = 0;
        //取数
        while(cursor.moveToNext()){
            names[i] = "事件："+cursor.getString(cursor.getColumnIndex("name"));
            datas[i] = "选项："+cursor.getString(cursor.getColumnIndex("data"));
            i++;
        }
        db.close();
        for(int j = 0; j < size; j++){
            map = new HashMap<String, String>();
            map.put("name", names[j]);
            map.put("data", datas[j]);
            list.add(map);
        }
        return list;
    }

    static class ViewHolder{
        private TextView name;
        private TextView data;
        private ImageButton go;
    }

    public class HasnamedAdapter extends BaseAdapter{

        private Context mcontext;

        public HasnamedAdapter(Context context){
            mcontext = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mcontext).inflate(R.layout.hasnamed_list, parent, false);
                viewHolder.name = convertView.findViewById(R.id.hasnamed_name_tv);
                viewHolder.data = convertView.findViewById(R.id.hasnamed_data_tv);
                viewHolder.go = convertView.findViewById(R.id.hasnamed_ib);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final String n = data.get(position).get("name").toString();
            final String d = data.get(position).get("data").toString();
            viewHolder.name.setText(n);
            viewHolder.data.setText(d);
            viewHolder.go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HasnamedActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", n.substring(3, n.length()));
                    bundle.putString("data", d.substring(3, d.length()));
                    intent.putExtras(bundle);
                    HasnamedActivity.this.startActivity(intent);
                    HasnamedActivity.this.finish();
                }
            });
            return convertView;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HasnamedActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
