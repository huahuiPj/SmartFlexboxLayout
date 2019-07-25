package com.chh.flexboxlayoututils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chh.flexboxlayoututils.R;
import com.chh.flexboxlayoututils.adapter.FlexboxLayoutAdapter;
import com.chh.flexboxlayoututils.adapter.MyFragmentViewPagerAdapter;
import com.chh.flexboxlayoututils.interfaces.setOnItemCheckListener;
import com.chh.flexboxlayoututils.interfaces.setOnItemClickListener;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SmartFlexboxLayout extends FrameLayout {
    private Context context;
    //可选的最大数量
    private int maxSelection ;
    //选择模式
    private int SelectModel;
    //多选模式
    private static final int MulitModel = 0;
    //单选模式
    private static final int SingelModel = 1;
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
    //布局方式
    private int orientation;
    //水平布局
    private static final int Horizontal = 0;
    //垂直布局
    private static final int Vertical = 1;
    //设置分割线
    private int divider;
    //设置是否换行
    private boolean lineFeed;
    public SmartFlexboxLayout(@NonNull Context context) {
        this(context,null);
    }

    public SmartFlexboxLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SmartFlexboxLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartFlexboxLayout);
        maxSelection = typedArray.getInt(R.styleable.SmartFlexboxLayout_max_num,0);
        SelectModel = typedArray.getInt(R.styleable.SmartFlexboxLayout_mode,maxSelection==MulitModel?-1:(maxSelection==SingelModel?SingelModel:MulitModel));
        defaultTextColor = typedArray.getColor(R.styleable.SmartFlexboxLayout_default_color,0);
        selectedTextColor = typedArray.getColor(R.styleable.SmartFlexboxLayout_selected_textColor,0);
        defauleDrawable = typedArray.getResourceId(R.styleable.SmartFlexboxLayout_default_drawable,0);
        selectedDrawable = typedArray.getResourceId(R.styleable.SmartFlexboxLayout_selected_drawable,0);
        textsize = typedArray.getDimensionPixelSize(R.styleable.SmartFlexboxLayout_text_size,0);
        checkEnable = typedArray.getBoolean(R.styleable.SmartFlexboxLayout_checked_enable,SelectModel==-1?false:true);
        orientation = typedArray.getInt(R.styleable.SmartFlexboxLayout_orientation,0);
        divider = typedArray.getResourceId(R.styleable.SmartFlexboxLayout_divider,0);
        lineFeed = typedArray.getBoolean(R.styleable.SmartFlexboxLayout_line_feed,true);

        Log.d("chh","maxSelection:"+maxSelection+" SelectModel:"+SelectModel);
        typedArray.recycle();
    }

    //设置控件adapter
    private FlexboxLayoutAdapter flexboxLayoutAdapter;
    public void setRes(Context context,int res){
        RecyclerView recyclerView = new RecyclerView(context);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        if (orientation == Vertical) {
            //垂直布局
            layoutManager.setFlexDirection(FlexDirection.COLUMN);
            if (lineFeed) {
                this.addView(recyclerView);
                //换行
                layoutManager.setFlexWrap(FlexWrap.WRAP);
            } else {
                //不换行
                layoutManager.setFlexWrap(FlexWrap.NOWRAP);
                recyclerView.setNestedScrollingEnabled(false);
                NestedScrollView nestedScrollView = new NestedScrollView(context);
                this.addView(nestedScrollView);
                nestedScrollView.addView(recyclerView);
            }
        } else {
            //水平布局
            layoutManager.setFlexDirection(FlexDirection.ROW);

            if (lineFeed) {
                this.addView(recyclerView);
                //换行
                layoutManager.setFlexWrap(FlexWrap.WRAP);
            } else {
                //不换行
                layoutManager.setFlexWrap(FlexWrap.NOWRAP);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setHorizontalScrollBarEnabled(true);
                NestedScrollView nestedScrollView = new NestedScrollView(context);
                nestedScrollView.setHorizontalScrollBarEnabled(true);
                this.addView(nestedScrollView);
                nestedScrollView.addView(recyclerView);
            }
        }
        if (divider!=0) {
            //设置分割线
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, orientation);
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context,divider));
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        layoutManager.setAlignItems(AlignItems.STRETCH);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.setHasFixedSize(true);

        flexboxLayoutAdapter = new FlexboxLayoutAdapter(context, res);
        flexboxLayoutAdapter.setFlexboxLayoutView(this);
        recyclerView.setAdapter(flexboxLayoutAdapter);
    }

    //设置data
    public void setData(Context context,int res,List<String> data){
        setRes(context,res);
        if (flexboxLayoutAdapter!=null) {
            flexboxLayoutAdapter.setDataList(data);
        }
    }

    //设置data and layout
    public void setData(Context context,List<String> data){
        setRes(context,R.layout.item_view);
        if (flexboxLayoutAdapter!=null) {
            flexboxLayoutAdapter.setDataList(data);
        }
    }

    //点击事件监听
    public void setListener(setOnItemClickListener clickListener){
        flexboxLayoutAdapter.setListener(clickListener);
    }

    //选择和取消事件的监听(只用于单选)
    public void setCheckedListener(setOnItemCheckListener checkedListener){
        flexboxLayoutAdapter.setCheckedListener(checkedListener);
    }

    //设置选中数据
    public void setSelectedData(List<Integer> list){
        if (list!=null&&list.size()>0) {
            flexboxLayoutAdapter.setSelectedData(list);
        }
    }

    //获取选中数据的集合
    public List<String> getSelectedData(){
        return flexboxLayoutAdapter!=null?flexboxLayoutAdapter.getSelectedData():new ArrayList<String>();
    }

    //绑定adapter
    public void bindPagerAdapter(final ViewPager vp, FragmentManager fm, List<Fragment> fragmentList){
        MyFragmentViewPagerAdapter viewPagerAdapter = new MyFragmentViewPagerAdapter(fm,context,fragmentList,this);
        vp.setAdapter(viewPagerAdapter);
        vp.addOnPageChangeListener(viewPagerAdapter);
        this.setCheckedListener(new setOnItemCheckListener() {
            @Override
            public void onChecked(int position, View view) {
                vp.setCurrentItem(position);
            }
            @Override
            public void unChecked(int position, View view) {

            }
        });
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

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean isLineFeed() {
        return lineFeed;
    }

    public void setLineFeed(boolean lineFeed) {
        this.lineFeed = lineFeed;
    }

    public int getDivider() {
        return divider;
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }
}
