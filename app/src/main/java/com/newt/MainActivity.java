package com.newt;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.newt.adpter.MainPagerAdapter;
import com.newt.ui.pager.PageHappy;
import com.newt.ui.pager.PageIndex;
import com.newt.ui.pager.PagePerson;
import com.newt.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpMain;
    private RadioGroup rgBottom;    //RadioButton(单选框)的容器
    private List<View> list = new ArrayList<>();
    private PageIndex pageIndex;    //新闻标题栏
    private PagePerson pagePerson;  //个人
    private PageHappy pageHappy;    //推荐
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.immerse(this);
        setContentView(R.layout.activity_main);

        //初始化三个基本视图
        initView();

        //初始化最外层的ViewPager（新闻、推荐、个人）
        initPagers();
        vpMain.setAdapter(new MainPagerAdapter(list));  //new MainPagerAdapter(list):为viewPager设置数据源
        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {  //单选框监听器
                switch (checkedId) {
                    case R.id.rb_news:  //页面跳转到新闻页面
                        vpMain.setCurrentItem(0, false);
                        break;
                    case R.id.rb_happy: //页面跳转到视频页面
                        vpMain.setCurrentItem(1, false);
                        break;
                    case R.id.rb_person:    //页面跳转到个人页面
                        vpMain.setCurrentItem(2, false);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void initPagers() {

        pageIndex = new PageIndex(this);     //新闻
        View viewPageIndex = pageIndex.getRootView();
        list.add(viewPageIndex);

        pageHappy = new PageHappy(this);    //推荐
        View viewPageHappy = pageHappy.getRootView();
        list.add(viewPageHappy);

        pagePerson = new PagePerson(this);  //个人
        View viewPagePerson = pagePerson.getRootView();
        list.add(viewPagePerson);
    }

    private void initView() {
        vpMain = (ViewPager) findViewById(R.id.vp_main);
        rgBottom = (RadioGroup) findViewById(R.id.rg_bottom);
    }
}
