package com.chh.smartflexboxlayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.chh.flexboxlayoututils.interfaces.setOnItemClickListener;
import com.chh.flexboxlayoututils.widget.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();
        final FlowLayout mSmartFlexboxLayout = findViewById(R.id.mSmartFlexboxLayout);
        mSmartFlexboxLayout.setData(this,dataList);
        mSmartFlexboxLayout.setListener(new setOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isCheck) {
                Log.d("chh","selectedData onItemClick position:"+ position +" isCheck:"+isCheck);
            }
        });

        Button mButtonSelected = findViewById(R.id.mButtonSelected);
        mButtonSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedData = mSmartFlexboxLayout.getSelectedData();
                for (int i = 0; i < selectedData.size(); i++) {
                    Log.d("chh","selectedData:"+ selectedData.get(i));
                }
            }
        });
    }

    private void setData(){
        for (int i = 0; i < 10; i++) {
            if (i == 1||i==4||i==7) {
                dataList.add("长长的标签" + i);
            } else {
                dataList.add("标签" + i);
            }
        }
    }
}
