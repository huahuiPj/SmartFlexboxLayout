package com.chh.flexboxlayoututils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chh.flexboxlayoututils.R;
import com.chh.flexboxlayoututils.adapter.FlexboxLayoutAdapter;
import com.chh.flexboxlayoututils.interfaces.setOnItemClickListener;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends FrameLayout {
    //可选的最大数量
    private int maxSelection ;
    //选择模式
    private int SelectModel;
    //多选模式
    public static final int MulitModel = 0;
    //单选模式
    public static final int SingelModel = 1;
    //字体大小
    private float textsize;
    //默认字体颜色
    private int defaultTextColor;
    //选中时的字体颜色
    private int selectedTextColor;
    //默认时的样式
    private int defauleDrawable;
    //选中时的样式
    private int selectedDrawable;
    //设置是否可以选中
    private boolean checkEnable;
    public FlowLayout(@NonNull Context context) {
        this(context,null);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartFlexboxLayout);
        maxSelection = typedArray.getInt(R.styleable.SmartFlexboxLayout_max_num,0);
        SelectModel = typedArray.getInt(R.styleable.SmartFlexboxLayout_mode,maxSelection==MulitModel?-1:(maxSelection==SingelModel?SingelModel:MulitModel));
        defaultTextColor = typedArray.getColor(R.styleable.SmartFlexboxLayout_default_color,0);
        selectedTextColor = typedArray.getColor(R.styleable.SmartFlexboxLayout_selected_textColor,0);
        defauleDrawable = typedArray.getResourceId(R.styleable.SmartFlexboxLayout_default_drawable,0);
        selectedDrawable = typedArray.getResourceId(R.styleable.SmartFlexboxLayout_selected_drawable,0);
        textsize = typedArray.getDimensionPixelSize(R.styleable.SmartFlexboxLayout_text_size,0);
        checkEnable = typedArray.getBoolean(R.styleable.SmartFlexboxLayout_checked_enable,SelectModel==-1?false:true);
        Log.d("chh","maxSelection:"+maxSelection+" SelectModel:"+SelectModel);
        typedArray.recycle();
    }

    private FlexboxLayoutAdapter flexboxLayoutAdapter;
    public void setRes(Context context,int res){
        RecyclerView recyclerView = new RecyclerView(context);
        this.addView(recyclerView);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        recyclerView.setLayoutManager(layoutManager);

        flexboxLayoutAdapter = new FlexboxLayoutAdapter(context, res);
        flexboxLayoutAdapter.setFlexboxLayoutView(this);
        recyclerView.setAdapter(flexboxLayoutAdapter);

    }

    //设置数据
    public void setData(Context context,List<String> data){
        setRes(context,R.layout.item_view);
        if (flexboxLayoutAdapter!=null) {
            flexboxLayoutAdapter.setDataList(data);
        }
    }

    //点击监听
    public void setListener(setOnItemClickListener clickListener){
        flexboxLayoutAdapter.setListener(clickListener);
    }

    //获取选中数据的集合
    public List<String> getSelectedData(){
        return flexboxLayoutAdapter!=null?flexboxLayoutAdapter.getSelectedData():new ArrayList<String>();
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

    public float getTextsize() {
        return textsize;
    }

    public void setTextsize(float textsize) {
        this.textsize = textsize;
    }

    public boolean isCheckEnable() {
        return checkEnable;
    }

    public void setCheckEnable(boolean checkEnable) {
        this.checkEnable = checkEnable;
    }
}
