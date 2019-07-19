package com.newt.listener;

import android.view.View;

import com.newt.domain.News;

/**
 * Created by Administrator on 2019/4/26.
 *  *item的点击监听器
 */

public interface OnItemClickListener {
    void itemClick(View v, int tag);
}
