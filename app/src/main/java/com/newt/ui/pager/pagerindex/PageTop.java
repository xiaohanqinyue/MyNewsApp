package com.newt.ui.pager.pagerindex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.newt.NewsDetailActivity;
import com.newt.R;
import com.newt.adpter.TopRecyclerViewAdapter;
import com.newt.domain.News;
import com.newt.listener.OnItemClickListener;
import com.newt.listener.OnLoadMoreListener;
import com.newt.utils.Constant;
import com.newt.utils.HttpUtil;
import com.newt.utils.ToastUtil;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/4/24.
 */

public class PageTop extends BaseIndexPage {


    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TopRecyclerViewAdapter topRecyclerViewAdapter;
    private static final String TAG = "PageTop";


    public PageTop(Context context) {
        this.context = context;
        initView();
    }


    public void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.page_index_top, null);

        /**
         * 下拉刷新控件
         * */
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.pi_top_srl);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        /*
        * 首次自动刷新
        * */
        swipeRefreshLayout.setRefreshing(true);
        initData();
        //设置是否进行刷新，刷新完毕后设置为false，则刷新控件消失
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.pi_top_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        topRecyclerViewAdapter = new TopRecyclerViewAdapter(context);
        recyclerView.setAdapter(topRecyclerViewAdapter);

        //下拉刷新加载头条数据
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                //设置是否进行刷新，刷新完毕后设置为false，则刷新控件消失
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        //上拉加载
        topRecyclerViewAdapter.setOnLoadMoreListenerMore(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                dataFromNet();
            }
        });


        //新闻项被点击
        topRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(View v, int tag) {
                View view = v.findViewById(R.id.url);
                String url = (String) view.getTag();
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("url", url);
                TextView tvNewTitle = (TextView) v.findViewById(R.id.tv_new_title);
                TextView ivImageUrl = (TextView) v.findViewById(R.id.tv_image_url);
                intent.putExtra("title", tvNewTitle.getText());
                intent.putExtra("image", ivImageUrl.getText());
                context.startActivity(intent);
            }
        });
    }

    public void initData() {
        //网络请求数据
        dataFromNet();
    }

    private void dataFromNet() {

        HttpUtil.sendHttpRequest(Constant.urlTop, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final Activity activity = (Activity) PageTop.this.context;
                //安卓不允许在子线程中进行UI操作，需要通过runOnUiThread（）将线程切换到主线程，然后再更新UI元素
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.show(context, "可能断网了");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Activity activity = (Activity) PageTop.this.context;
                final String responseText = response.body().string(); //得到返回的具体信息
                //使用GSON方式解析JSON格式的数据
                final News news = new Gson().fromJson(responseText, News.class);
                //Gson to obj
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //使用SharedPreferences存储新闻数据，SharedPreferences是使用键值对来存储数据的
                        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
                        edit.putString("news", responseText);
                        edit.apply();   //将添加的数据提交
                        showNews(news);
                    }
                });
            }
        });
    }

    private void showNews(News news) {
        News newsF = topRecyclerViewAdapter.getNews();
        if (newsF != null) {
            newsF.getResult().getData().addAll(news.getResult().getData());
        } else {
            topRecyclerViewAdapter.setNews(news);
        }
        topRecyclerViewAdapter.notifyDataSetChanged();
    }

    public View getRootView() {
        return rootView;
    }


}
