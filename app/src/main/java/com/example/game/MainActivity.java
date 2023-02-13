package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TintContextWrapper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //属性
    private TextView tvTarget;
    private TextView tvSource;
    private TextView tvIndex;
    private SeekBar sbBulsseys;
    private Button btnOk;
    private Button btnReplay;
    private Button btnHelp;
    private int randomSource;
    private int finalSource = 0;
    private int index = 1;
    private Context mContext;
    private Button query;
    private TextView tv_1;
    SQLiteDatabase database;
    private Button btn_more;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        findView();
        randomOfSource();
        setListener();
        SQLite sqLite = new SQLite(this);
        database = sqLite.getWritableDatabase();
    }

    private void setListener() {
        //为事件源设置监听
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击就会调用这个方法
                //算分数00
                index++;
                int currentSource = sbBulsseys.getProgress();
                int source = 100 - Math.abs(currentSource - randomSource);
                finalSource = source + finalSource;
                setViewText();
                //按钮点击后就会重新出题
                randomOfSource();
            }
        });
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新生成随机数
                randomOfSource();
                finalSource = 0;
                index = 1;
                setViewText();
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Help")
                        .setMessage("这是帮助")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql;
                sql = "insert into xinxi(id,name,class)values('1923059','夏宇星','数媒B19-4')";
                database.execSQL(sql);
                try {
                    tv_1.setText("");
                    sql = "select * from xinxi";
                    Cursor cursor = database.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        String id = cursor.getString(cursor.getColumnIndex("id"));
                        String classes = cursor.getString(cursor.getColumnIndex("class"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        tv_1.setText("学号：" + id + "\n" + "姓名：" + name + "\n" + "班级：" + classes);
                        Toast.makeText(MainActivity.this, "查询成功！", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                startActivity(intent);
            }
        });
    }

    //因为定义为属性了，不需要传参
    private void setViewText() {
        tvSource.setText("分数：" + finalSource);
        tvIndex.setText("局数：" + index);
        sbBulsseys.setProgress(0);
    }

    private void randomOfSource() {
        Random random = new Random();
        randomSource = random.nextInt(99) + 1;
        tvTarget.setText("将进度条拖到：" + randomSource);
    }

    private void findView() {
        tvTarget = (TextView) this.findViewById(R.id.tv_target);
        tvIndex = (TextView) this.findViewById(R.id.tv_index);
        tvSource = (TextView) this.findViewById(R.id.tv_source);
        sbBulsseys = (SeekBar) this.findViewById(R.id.sb_bulsseye);
        btnHelp = (Button) this.findViewById(R.id.btn_help);
        btnOk = (Button) this.findViewById(R.id.btn_ok);
        btnReplay = (Button) this.findViewById(R.id.btn_replay);
        query = findViewById(R.id.query);
        tv_1 = findViewById(R.id.tv_1);
        btn_more = findViewById(R.id.btn_more);
    }
}