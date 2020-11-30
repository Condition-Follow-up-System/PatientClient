package com.example.administrator.testvue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button bt;

    private EditText  et_card,et_password;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bt = findViewById(R.id.bt_register);
        et_card = findViewById(R.id.et_card_register);
        et_password = findViewById(R.id.et_password_register);
        context=getApplicationContext();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString().trim();
                String idNo = et_card.getText().toString().trim();
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                LoginDto   loginDto = new LoginDto();
                loginDto.idNo = idNo;
                loginDto.password = password;
                String jsonStr = com.alibaba.fastjson.JSON.toJSONString(loginDto);
                RequestBody body = RequestBody.create(JSON, jsonStr);
                String address = Data.address+"/user/register";
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
                            Bundle bundle = new Bundle();
                            bundle.putString("idNo", idNo);
                            bundle.putString("password", password);
                            Intent sendIntent = new Intent();
                            sendIntent.putExtras(bundle);
                            RegisterActivity.this.setResult(RESULT_OK, sendIntent);
                            RegisterActivity.this.finish();
                        }else{
                            String data = (String) jb.get("data");
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });
            }
        });
    }








}
