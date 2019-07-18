package com.chh.smartflexboxlayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chh.flexboxlayoututils.interfaces.setOnItemClickListener;
import com.chh.flexboxlayoututils.widget.SmartFlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> dataList = new ArrayList<>();
    private List<Integer> selectList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();


        //多选
        final SmartFlexboxLayout mSmartFlexboxLayout_Mulit = findViewById(R.id.mSmartFlexboxLayout_Mulit);
        mSmartFlexboxLayout_Mulit.setData(this,dataList);
//        selectList.add(5);
        mSmartFlexboxLayout_Mulit.setSelectedData(selectList);
        mSmartFlexboxLayout_Mulit.setListener(new setOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isCheck) {
                Log.d("chh","selectedData onItemClick position:"+ position +" isCheck:"+isCheck);
            }
        });

        //单选
        final SmartFlexboxLayout mSmartFlexboxLayout_singer = findViewById(R.id.mSmartFlexboxLayout_singer);
        mSmartFlexboxLayout_singer.setData(this,dataList);
        mSmartFlexboxLayout_singer.setSelectedData(selectList);
        mSmartFlexboxLayout_singer.setListener(new setOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isCheck) {
                Log.d("chh","selectedData onItemClick position:"+ position +" isCheck:"+isCheck);
            }
        });

        //只显示
        final SmartFlexboxLayout mSmartFlexboxLayout_onlyShow = findViewById(R.id.mSmartFlexboxLayout_onlyShow);
        mSmartFlexboxLayout_onlyShow.setData(this,R.layout.item_my_tv,dataList);
        mSmartFlexboxLayout_onlyShow.setListener(new setOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isCheck) {
                Log.d("chh","selectedData onItemClick position:"+ position +" isCheck:"+isCheck);
            }
        });

        Button mButtonSelectedMulit = findViewById(R.id.mButtonSelectedMulit);
        mButtonSelectedMulit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedData = mSmartFlexboxLayout_Mulit.getSelectedData();
//                StringBuffer stringBuffer = new StringBuffer();
                Toast.makeText(MainActivity.this,selectedData.toString(),Toast.LENGTH_LONG).show();
                for (int i = 0; i < selectedData.size(); i++) {
                    Log.d("chh","多选:"+ selectedData.get(i));
                }
            }
        });
        Button mButtonSelectedSingle = findViewById(R.id.mButtonSelectedSingle);
        mButtonSelectedSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedData = mSmartFlexboxLayout_singer.getSelectedData();
                Toast.makeText(MainActivity.this,selectedData.toString(),Toast.LENGTH_LONG).show();
                for (int i = 0; i < selectedData.size(); i++) {
                    Log.d("chh","单选:"+ selectedData.get(i));
                }
            }
        });
    }

    private void setData(){
        for (int i = 0; i < 10; i++) {
            if (i%3==0) {
                dataList.add("大西瓜" + i);
            } else if(i%4==0){
                dataList.add("好吃的香蕉" + i);
            } else if (i % 5 == 0) {
                dataList.add("超级无敌的小芝麻" + i);
            } else {
                dataList.add("我是谁" + i);
            }
        }

        selectList.add(1);
    }
}
