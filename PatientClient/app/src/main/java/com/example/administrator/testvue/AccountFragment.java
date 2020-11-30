package com.example.administrator.testvue;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 */
public class AccountFragment extends Fragment {

    private View view;

    private ListView  lv;

    private LinearLayout lin_show;

    private List<Advice> fruitList=new ArrayList<>();

    AdviceAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_account, container, false);
        lv = view.findViewById(R.id.lv);

        /*for(int i=0;i<10;i++){
            Advice  advice = new Advice();
            advice.setContent("我是内容"+i);
            advice.setTime(new Date());
            fruitList.add(advice);
        }*/
        getData();
        adapter=new AdviceAdapter(getActivity(),R.layout.advice_item,fruitList);
        lv.setAdapter(adapter);
        return view;
    }

    public List<Advice>  getData(){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RecoreListDto   recoreListDto = new RecoreListDto();
        recoreListDto.userId = "1";
        String jsonStr = com.alibaba.fastjson.JSON.toJSONString(recoreListDto);
        RequestBody body = RequestBody.create(JSON, jsonStr);
        String address = Data.address+"/user/recordList";
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject jb = JSONObject.parseObject(response.body().string());
                String code = (String) jb.get("code");
                if("0".equals(code)){
                    String data = jb.getString("data");
                    List<Advice> advices = JSONObject.parseArray(data, Advice.class);
                    fruitList.addAll(advices);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                }

            }
        });
        return null;
    }

}
