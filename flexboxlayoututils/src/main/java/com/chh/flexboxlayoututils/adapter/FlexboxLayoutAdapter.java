package com.chh.flexboxlayoututils.adapter;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chh.flexboxlayoututils.R;
import com.chh.flexboxlayoututils.interfaces.setOnItemClickListener;
import com.chh.flexboxlayoututils.widget.FlowLayout;

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
            ((MyViewHolder) holder).mTextItems.setText(data.get(position));
            ((MyViewHolder) holder).mTextItems.setTextColor(defaultTextColor);
            ((MyViewHolder) holder).mTextItems.setBackgroundResource(defauleDrawable);
            if(textsize!=0){
                ((MyViewHolder) holder).mTextItems.setTextSize(TypedValue.COMPLEX_UNIT_PX,textsize);
            }
            sMap.put(position,false);
            ((MyViewHolder) holder).mTextItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) v;
                    String str = data.get(position);
                    if (checkEnable) {
                        boolean isSelected = sMap.get(position);
                        if (SelectModel == MulitModel) { //多选
                            if (isSelected) {
                                textView.setTextColor(defaultTextColor);
                                textView.setBackgroundResource(defauleDrawable);
                                sMap.put(position, false);
                                selectedMap.remove(position);
                            } else {
                                if (selectedMap.size() < maxSelection) {
                                    textView.setTextColor(selectedTextColor);
                                    textView.setBackgroundResource(selectedDrawable);
                                    sMap.put(position, true);
                                    selectedMap.put(position, str);
                                } else {
//                                    Toast.makeText(context,"不能超出最大值"+maxSelection+"个",Toast.LENGTH_LONG).show();
                                }
                            }
                        }else if (SelectModel == SingelModel){ //单选
                            if (!isSelected) {
                                if (selectedMap.size() > 0) {
                                    OldTextView.setTextColor(defaultTextColor);
                                    OldTextView.setBackgroundResource(defauleDrawable);
                                    sMap.put(Oldposition, false);
                                    selectedMap.remove(Oldposition);
                                }
                                textView.setTextColor(selectedTextColor);
                                textView.setBackgroundResource(selectedDrawable);
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
            mTextItems = itemView.findViewById(R.id.mTextItems);
        }
    }

    public void setDataList(List<String> dataList){
        //设置数据
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.addAll(dataList) ;
        notifyDataSetChanged();
    }

    public List<String> getSelectedData(){
        Collection<String> values = selectedMap.values();
        List<String> list = new ArrayList<>(values);
        return list;
    }

    private setOnItemClickListener clickListener;
    public void setListener(setOnItemClickListener clickListener){
        this.clickListener = clickListener;
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
    public void setFlexboxLayoutView(FlowLayout flexboxLayout){
        if (flexboxLayout != null) {
            maxSelection = flexboxLayout.getMaxSelection();
            SelectModel = flexboxLayout.getSelectModel();
            defaultTextColor = flexboxLayout.getDefaultTextColor();
            selectedTextColor = flexboxLayout.getSelectedTextColor();
            defauleDrawable = flexboxLayout.getDefauleDrawable();
            selectedDrawable =flexboxLayout.getSelectedDrawable();
            textsize = flexboxLayout.getTextsize();
            checkEnable = flexboxLayout.isCheckEnable();
            if (SelectModel==SingelModel) {
                maxSelection = 1;
            } else if (SelectModel==MulitModel&&maxSelection==0) {
                maxSelection=1;
            }
        } else {
            throw new NullPointerException("SmartFlexboxLayout 未初始化");
        }
    }
}
