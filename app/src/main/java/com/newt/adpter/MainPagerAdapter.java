package com.newt.adpter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2019/4/23.
 */

public class MainPagerAdapter extends PagerAdapter {

    private List<View> list;    //list用于存放数据源

    public MainPagerAdapter(List<View> list) {
        this.list = list;
    }

    //返回页卡的数量
    @Override
    public int getCount() {
        return list.size();
    }

    //判断当前view对象是否来自于某对象
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    //实例化一个页卡
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position);
        container.addView(view);
        return view;
    }

    //销毁一个页卡
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
