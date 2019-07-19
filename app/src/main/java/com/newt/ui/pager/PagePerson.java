package com.newt.ui.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.newt.R;

/**
 * Created by Administrator on 2019/3/23.
 *   个人
 */

public class PagePerson {


    private View rootView;
    private Context context;

    public PagePerson(Context context) {
        this.context = context;
        rootView = initView();

    }

    private View initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.page_person, null);
        return rootView;
    }

    private void initData() {

    }

    public View getRootView() {
        return rootView;
    }
}
