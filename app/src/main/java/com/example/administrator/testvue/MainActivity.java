package com.example.administrator.testvue;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private LinearLayout lin_account,lin_report,lin_find,lin_my,lin_top;
    private TextView  tv_top;
    private ImageView     iv_accont,iv_find,iv_my,iv_report;
    private List<Fragment> mList;
    private Button bt_add;
    private MyPagerAdapter  myPagerAdapter;
    private NoSlideViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountFragment accountFragment=new AccountFragment();
        FindFragment  findFragment=new FindFragment();
        MyFragment myFragment=new MyFragment();
        mList=new ArrayList<>();
        mList.add(accountFragment);
        mList.add(findFragment);
        mList.add(myFragment);

        viewPager= (NoSlideViewPager) findViewById(R.id.viewpager);
        lin_account= (LinearLayout) findViewById(R.id.lin_account);
        lin_find= (LinearLayout) findViewById(R.id.lin_find);
        lin_top = findViewById(R.id.lin_top);
        bt_add = findViewById(R.id.bt_add);
        tv_top = findViewById(R.id.tv_top);
        lin_report= (LinearLayout) findViewById(R.id.lin_report);
        iv_accont= (ImageView) findViewById(R.id.image_account);
        iv_find= (ImageView) findViewById(R.id.imageview_find);
        iv_report= (ImageView) findViewById(R.id.imageview_report);

        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager(),mList);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(0);
        tv_top.setText("主页");
        viewPager.setOffscreenPageLimit(3);

        lin_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_top.setText("主页");
                lin_top.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
                iv_accont.setImageResource(R.drawable.ic_fas_fa_envelope_press);
                iv_report.setImageResource(R.drawable.ic_mb_wechat_moment);
                iv_find.setImageResource(R.drawable.ic_fas_fa_user);

            }
        });
        lin_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                tv_top.setText("健康中心");
                lin_top.setVisibility(View.VISIBLE);
                iv_accont.setImageResource(R.drawable.ic_fas_fa_envelope);
                iv_report.setImageResource(R.drawable.ic_mb_wechat_moment_press);
                iv_find.setImageResource(R.drawable.ic_fas_fa_user);

            }
        });
        lin_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
                lin_top.setVisibility(View.GONE);
                iv_accont.setImageResource(R.drawable.ic_fas_fa_envelope);
                iv_report.setImageResource(R.drawable.ic_mb_wechat_moment);
                iv_find.setImageResource(R.drawable.ic_fas_fa_user_press);

            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }



}
