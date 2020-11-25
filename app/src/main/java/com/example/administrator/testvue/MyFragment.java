package com.example.administrator.testvue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liys.dialoglib.LDialog;

public class MyFragment extends Fragment {

    private View view;
    private RelativeLayout rea_type;
    private RelativeLayout  lin_login;
    SharedPreferences mContextSp;

    private TextView  tv_login,tv_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my, container, false);
        lin_login = view.findViewById(R.id.lin_login);
        tv_login = view.findViewById(R.id.tv_outlogin);
        tv_name = view.findViewById(R.id.tv_name);
        mContextSp = getActivity().getSharedPreferences( "data", Context.MODE_PRIVATE );
        boolean  isLogin = mContextSp.getBoolean("isLogin",false);
        if(isLogin){
            tv_login.setVisibility(View.VISIBLE);
            String name  = mContextSp.getString("name","");
            tv_name.setText(name);
            tv_name.setEnabled(false);
        }
        lin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                //启动
                startActivity(intent);

            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LDialog dialog = new LDialog(getActivity(), R.layout.dialog_confirm); //设置你的布局
                dialog.with()
                        //设置布局控件的各种属性
                        .setText(R.id.tv_content, "确定要退出登录吗？")
                        .setCancelBtn(R.id.tv_cancel) //点击对应按钮, dialog会消失(可选)
                        .setOnClickListener(new LDialog.DialogOnClickListener() { //设置监听
                            @Override
                            public void onClick(View v, LDialog lDialog) { //可以根据viewId判断
                                if(v.getId()==R.id.tv_confirm){
                                    SharedPreferences.Editor editor = mContextSp.edit();
                                    editor.putString("name", "");
                                    editor.putBoolean("isLogin",false);
                                    editor.putLong("id", 0);
                                    editor.commit();
                                    tv_name.setText("点我登录");
                                    tv_name.setEnabled(true);
                                }
                                lDialog.dismiss();
                            }
                        }, R.id.tv_confirm, R.id.tv_content) //可以设多控件
                        .show();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                tv_login.setVisibility(View.VISIBLE);
                String name  = mContextSp.getString("name","");
                tv_name.setText(name);
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }
}
