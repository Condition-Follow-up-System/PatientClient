package com.example.administrator.testvue;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdviceAdapter extends ArrayAdapter<Advice> {
    public AdviceAdapter(@NonNull Context context, int resource, List<Advice> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Advice fruit=getItem(position); //获取当前项的Fruit实例

        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        ViewHolder viewHolder;
        if (convertView==null){

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(R.layout.advice_item,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.content=view.findViewById(R.id.content);
            viewHolder.time=view.findViewById(R.id.time);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        viewHolder.content.setText(fruit.getContent());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(fruit.getDate());
        viewHolder.time.setText(dateString);
        return view;
    }

    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder{
        TextView content;
        TextView time;
    }
}
