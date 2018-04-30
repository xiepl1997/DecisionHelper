/*
function:决策历史页面
author：xpl
create date：2018.4.30
 */

package com.example.xpl.decisionhelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    public LinkedList<Map<String, String>> data;
    private HistoryDatabaseHelper databaseHelper;

    //History数据库中的行数
    private int size;
    private String[] name;
    private String[] result;
    private String[] time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView) findViewById(R.id.history_lv);
        data = getData();
        HistoryAdapter ah = new HistoryAdapter(this);
        listView.setAdapter(ah);

    }

    public LinkedList<Map<String, String>> getData() {
        LinkedList<Map<String, String>> list = new LinkedList<Map<String, String>>();
        Map<String, String> map;

        //将数据库中的数据取出
        databaseHelper = new HistoryDatabaseHelper(this, "History.db", null, 1);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from History", null);
        //获取行数
        size = cursor.getCount();
        name = new String[size];
        result = new String[size];
        time = new String[size];
        int i = 0;
        //数据库遍历
        while(cursor.moveToNext()){
            name[i] = "事件："+cursor.getString(cursor.getColumnIndex("name"));
            result[i] = "选择："+cursor.getString(cursor.getColumnIndex("result"));
            time[i] = cursor.getString(cursor.getColumnIndex("time"));
            i++;
        }
        db.close();
        //将数据装入
        for(int j = 0; j < size; j++) {
            map = new HashMap<String, String>();
            map.put("name", name[j]);
            map.put("result", result[j]);
            map.put("time", time[j]);
            list.add(map);
        }
        return list;
    }

    static class ViewHolder{
        TextView name;
        TextView result;
        TextView time;
    }

    public class HistoryAdapter extends BaseAdapter{
        private Context mcontext;
        public HistoryAdapter(Context context) {
            this.mcontext = context;
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
                convertView = LayoutInflater.from(mcontext).inflate(R.layout.history_list, parent, false);
                viewHolder.name = (TextView)convertView.findViewById(R.id.history_name_tv);
                viewHolder.result = (TextView)convertView.findViewById(R.id.history_result_tv);
                viewHolder.time = (TextView)convertView.findViewById(R.id.history_time_tv);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(data.get(position).get("name").toString());
            viewHolder.result.setText(data.get(position).get("result").toString());
            viewHolder.time.setText(data.get(position).get("time").toString());

            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
