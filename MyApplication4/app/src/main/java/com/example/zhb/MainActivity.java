package com.example.zhb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.zhb.fragment.BaseActivity;
import com.example.zhb.fragment.BlankFragment;
import com.example.zhb.fragment.BlankFragment1;
import com.example.zhb.fragment.BlankFragment2;
import com.example.zhb.myapplication.R;
import com.hjm.bottomtabbar.BottomTabBar;
import com.luck.picture.lib.config.PictureConfig;

public class MainActivity extends BaseActivity {

//    @BindView(R.id.bottom_tab_bar)
    private BottomTabBar bottomTabBar;
    private BlankFragment1 blankFragment1 = new BlankFragment1();
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        bottomTabBar = (BottomTabBar) findViewById(R.id.bottom_tab_bar);
        setBottomtabBar();

    }

    private void setBottomtabBar() {
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(90,90)
                .setFontSize(12)
                .setTabPadding(10,3,10)
                .setChangeColor(Color.YELLOW,Color.BLACK)
                .addTabItem("模块1",R.drawable.ic_error_black_24dp, BlankFragment.class)
                .addTabItem("模块2",R.drawable.ic_cloud_black_24dp, BlankFragment1.class)
                .addTabItem("模块3",R.drawable.ic_person_black_24dp, BlankFragment2.class)
                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PictureConfig.CHOOSE_REQUEST:
                Message msg = new Message();
                msg.obj =data;
                msg.what = 1;
                mHandler.sendMessage(msg);
                break;
        }

    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }
}
