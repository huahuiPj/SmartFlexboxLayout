package com.chh.flexboxlayoututils.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chh.flexboxlayoututils.interfaces.setOnItemCheckListener;
import com.chh.flexboxlayoututils.interfaces.setOnItemClickListener;
import com.chh.flexboxlayoututils.widget.SmartFlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlexboxLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int layoutRes;
    private View view ;
    private List<String> data ;
    private Map<Integer,String>  selectedMap = new HashMap<>();
    private Map<Integer,Boolean>  sMap = new HashMap<>();
    private int Oldposition = -1;
    private TextView OldTextView;
    public FlexboxLayoutAdapter(Context context,int layoutRes) {
        this.context = context;
        this.layoutRes = layoutRes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(layoutRes, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if(data!=null&&data.size()>0){
            TextView mTextItems = ((MyViewHolder) holder).mTextItems;
            mTextItems.setText(data.get(position));

            setTextColor(mTextItems,defaultTextColor);
            setBackgroundRes(mTextItems,defauleDrawable);
            setTextSize(mTextItems,textsize);

            sMap.put(position,false);
            if (SelectModel == MulitModel) {
                if (selectedMap.containsKey(position)) {
                    sMap.put(position, true);
                    setTextColor(mTextItems, selectedTextColor);
                    setBackgroundRes(mTextItems, selectedDrawable);
                    //设置选中监听
                    if (checkListener!=null) {
                        checkListener.onChecked(position, mTextItems);
                    }
                } else {
                    //设置取消选中/默认监听
                    if (checkListener!=null) {
                        checkListener.unChecked(position,mTextItems);
                    }
                }
            }else if(SelectModel == SingelModel){
                if (setListData != null && !setListData.isEmpty() && setListData.get(0) == position) {
                    int poi = setListData.get(0);
                    selectedMap.put(poi, data.get(poi));
                    sMap.put(poi, true);
                    setTextColor(mTextItems, selectedTextColor);
                    setBackgroundRes(mTextItems, selectedDrawable);
                    OldTextView = ((MyViewHolder) holder).mTextItems;
                    Oldposition = position;
                    //设置选中监听
                    if (checkListener!=null) {
                        checkListener.onChecked(position, mTextItems);
                    }
                } else {
                    //设置取消选中/默认监听
                    if (checkListener!=null) {
                        checkListener.unChecked(position,mTextItems);
                    }
                }
            }

            ((MyViewHolder) holder).mTextItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) v;
                    String str = data.get(position);
                    if (checkEnable) {
                        boolean isSelected = sMap.get(position);
                        if (SelectModel == MulitModel) { //多选
                            if (isSelected) {
                                setTextColor(textView,defaultTextColor);
                                setBackgroundRes(textView,defauleDrawable);
                                sMap.put(position, false);
                                selectedMap.remove(position);
                            } else {
                                if (selectedMap.size() < maxSelection) {
                                    setTextColor(textView,selectedTextColor);
                                    setBackgroundRes(textView,selectedDrawable);
                                    sMap.put(position, true);
                                    selectedMap.put(position, str);
                                } else {
//                                    Toast.makeText(context,"不能超出最大值"+maxSelection+"个",Toast.LENGTH_LONG).show();
                                }
                            }
                        }else if (SelectModel == SingelModel){ //单选
                            if (!isSelected) {
                                if (selectedMap.size() > 0&&OldTextView!=null&&Oldposition!=-1) {
                                    setTextColor(OldTextView,defaultTextColor);
                                    setBackgroundRes(OldTextView,defauleDrawable);
                                    sMap.put(Oldposition, false);
                                    selectedMap.remove(Oldposition);
                                    //设置取消选中监听
                                    if (checkListener!=null) {
                                        checkListener.unChecked(Oldposition,OldTextView);
                                    }
                                }
                                setTextColor(textView,selectedTextColor);
                                setBackgroundRes(textView,selectedDrawable);
                                //设置选中监听
                                if (checkListener!=null) {
                                    checkListener.onChecked(position,textView);
                                }
                                sMap.put(position, true);
                                selectedMap.put(position, str);

                                OldTextView = textView;
                                Oldposition = position;
                            }
                        }
                    }
                    if (clickListener != null) {
                        clickListener.onItemClick(v, position, sMap.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTextItems;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            int id = itemView.getId();
            View viewById = itemView.findViewById(id);
            if (viewById instanceof TextView) {
                mTextItems = (TextView) viewById;
            } else {
               throw new NullPointerException("xml根布局找不到TextView");
            }
            ViewGroup.LayoutParams lp = viewById.getLayoutParams();
            if (lp instanceof FlexboxLayoutManager.LayoutParams) {
                FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
                if (lineFeed) {

                } else {
                    flexboxLp.setFlexShrink(0f);
                    flexboxLp.setFlexGrow(1);
                }
            }
        }
    }

    //设置数据
    public void setDataList(List<String> dataList){
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.addAll(dataList) ;
        notifyDataSetChanged();
    }

    //设置选中的数据-在最开始加载的时候
    public void setSelectedData(List<Integer> dataList){
        setListData = dataList;
        //设置选择项
        if (SelectModel == MulitModel) {
            if (setListData != null && setListData.size()>0) {
                selectedMap.clear();
                sMap.clear();
                for (int i = 0; i < setListData.size(); i++) {
                    int poi = setListData.get(i);
                    selectedMap.put(poi, data.get(poi));
                }
            }
        }else if(SelectModel == SingelModel){
            if (setListData != null && setListData.size()>0) {
                selectedMap.clear();
                sMap.clear();
                OldTextView = null;
                Oldposition = -1;
            }
        }
        notifyDataSetChanged();
    }

    //获取选中的数据
    public List<String> getSelectedData(){
        Collection<String> values = selectedMap.values();
        List<String> list = new ArrayList<>(values);
        return list;
    }

    //点击事件的监听
    private setOnItemClickListener clickListener;
    public void setListener(setOnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    //选择和取消事件的监听(只用于单选)
    private setOnItemCheckListener checkListener;
    public void setCheckedListener(setOnItemCheckListener checkedListener){
        this.checkListener = checkedListener;
    }

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
    //设置选中的集合数据
    private List<Integer> setListData;
    //设置是否换行
    private boolean lineFeed;
    public void setFlexboxLayoutView(SmartFlexboxLayout flexboxLayout){
        if (flexboxLayout != null) {
            maxSelection = flexboxLayout.getMaxSelection();
            SelectModel = flexboxLayout.getSelectModel();
            defaultTextColor = flexboxLayout.getDefaultTextColor();
            selectedTextColor = flexboxLayout.getSelectedTextColor();
            defauleDrawable = flexboxLayout.getDefauleDrawable();
            selectedDrawable =flexboxLayout.getSelectedDrawable();
            textsize = flexboxLayout.getTextsize();
            checkEnable = flexboxLayout.isCheckEnable();
            lineFeed = flexboxLayout.isLineFeed();
            if (SelectModel==SingelModel) {
                maxSelection = 1;
            } else if (SelectModel==MulitModel&&maxSelection==0) {
                maxSelection=1;
            }
        } else {
            throw new NullPointerException("SmartFlexboxLayout 未初始化");
        }
    }


    //设置字体颜色
    private void setTextColor(TextView textView,int res){
        if (textView!=null&&res!=0) {
            textView.setTextColor(res);
        }
    }

    //设置字体大小
    private void setTextSize(TextView textView,float textsize){
        if (textView!=null&&textsize!=0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textsize);
        }
    }

    //设置背景
    private void setBackgroundRes(TextView textView,int res){
        if (textView!=null&&res!=0) {
            textView.setBackgroundResource(res);
        }
    }
}
