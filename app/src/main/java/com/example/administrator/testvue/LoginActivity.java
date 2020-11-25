package com.example.administrator.testvue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Button bt;

    private TextView  tv_register;

    private EditText  et_card,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bt = findViewById(R.id.bt);
        et_card = findViewById(R.id.et_card);
        et_password = findViewById(R.id.et_password);
        tv_register = findViewById(R.id.tv_register);
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
                String address = Data.address+"/user/login";
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
                            JSONObject jb1 = JSONObject.parseObject(data);
                            long id = jb1.getLong("id");
                            String name = jb1.getString("name");
                            SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", name);
                            editor.putBoolean("isLogin",true);
                            editor.putLong("id", id);
                            editor.commit();

                            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                            LocalBroadcastManager.getInstance(LoginActivity.this).sendBroadcast(intent);
                            finish();
                        }

                    }
                });

            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,0);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            String idNo = bundle.getString("idNo");
            String password = bundle.getString("password");
            et_card.setText(idNo);
            et_password.setText(password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
