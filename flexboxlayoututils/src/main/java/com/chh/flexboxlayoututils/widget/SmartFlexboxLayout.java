package com.chh.flexboxlayoututils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chh.flexboxlayoututils.R;
import com.chh.flexboxlayoututils.adapter.FlexboxLayoutAdapter;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class SmartFlexboxLayout extends FlexboxLayout {
    private Context context;
    //可选的最大数量
    private int maxSelection ;
    //选择模式
    private int SelectModel;
    //多选模式
    public static final int MulitModel = 0;
    //单选模式
    public static final int SingelModel = 1;
    //默认字体颜色
    private int defaultTextColor;
    //选中时的字体颜色
    private int selectedTextColor;
    //默认时的样式
    private int defauleDrawable;
    //选中时的样式
    private int selectedDrawable;

    public SmartFlexboxLayout(Context context) {
        this(context,null);
        this.context = context;
    }

    public SmartFlexboxLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        this.context = context;
    }

    public SmartFlexboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartFlexboxLayout);
        maxSelection = typedArray.getInt(R.styleable.SmartFlexboxLayout_max_num,0);
        SelectModel = typedArray.getInt(R.styleable.SmartFlexboxLayout_mode,MulitModel);
        defaultTextColor = typedArray.getColor(R.styleable.SmartFlexboxLayout_default_color,0);
        selectedTextColor = typedArray.getColor(R.styleable.SmartFlexboxLayout_selected_textColor,0);
        defauleDrawable = typedArray.getResourceId(R.styleable.SmartFlexboxLayout_default_drawable,0);
        selectedDrawable = typedArray.getResourceId(R.styleable.SmartFlexboxLayout_selected_drawable,0);
        Log.d("chh","maxSelection:"+maxSelection+" SelectModel:"+SelectModel);
        typedArray.recycle();
    }

    private  FlexboxLayoutAdapter flexboxLayoutAdapter;
    public void setResData(int res){
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        flexboxLayoutAdapter = new FlexboxLayoutAdapter(context, res);
//        flexboxLayoutAdapter.setFlexboxLayoutView(this);
        recyclerView.setAdapter(flexboxLayoutAdapter);
    }

    public void setData(List<String> data){
        if (flexboxLayoutAdapter!=null) {
            flexboxLayoutAdapter.setDataList(data);
        }
    }

    public int getMaxSelection() {
        return maxSelection;
    }

    public void setMaxSelection(int maxSelection) {
        this.maxSelection = maxSelection;
    }

    public int getSelectModel() {
        return SelectModel;
    }

    public void setSelectModel(int selectModel) {
        SelectModel = selectModel;
    }

    public int getDefaultTextColor() {
        return defaultTextColor;
    }

    public void setDefaultTextColor(int defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public int getDefauleDrawable() {
        return defauleDrawable;
    }

    public void setDefauleDrawable(int defauleDrawable) {
        this.defauleDrawable = defauleDrawable;
    }

    public int getSelectedDrawable() {
        return selectedDrawable;
    }

    public void setSelectedDrawable(int selectedDrawable) {
        this.selectedDrawable = selectedDrawable;
    }
}
