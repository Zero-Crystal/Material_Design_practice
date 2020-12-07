package com.example.material_design_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton floatActionbtn;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView_food;
    private foodAdapter adapter;
    private List<foodBean> mList = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private List<Integer> highList = new ArrayList<>();
    private int ITEM_SUM = 8;
    private RecyclerView recyclerView_fruits;
    private List<fruitsBean> mFruitList = new ArrayList<>();
    private fruitsAdapter fruitsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.my_page);
        }

        navigationItemSelected();

        refreshLinstener();
    }

    /*
     * 下拉刷新监听方法
     * 一般可以在onRefresh()方法内写更新网络数据的操作；
     * 此处暂时没有网络数据，因此做一些刷新本地记录的操作
     * */
    private void refreshLinstener() {
        refreshLayout.setColorSchemeColors(R.color.design_default_color_on_primary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    /*
     * 使用 匿名类的方式 开启新线程，沉睡2秒，使本地刷新更明显
     * 后续网络数据刷新则不需要此功能
     * runOnUiThread()切换为主线程；
     * */
    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initItemData();
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    /*
     * 左滑弹窗内menu菜单的点击操作
     * */
    private void navigationItemSelected() {
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_log:
                        Toast.makeText(MainActivity.this, "Do you want to check your Log?", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_shopping:
                        Toast.makeText(MainActivity.this, "Do you want to shopping with me?", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this, "I really want to chat with you!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_message:
                        Toast.makeText(MainActivity.this, "Do you want to check your message?", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    /*
     * 初始化数据
     * 包括获得布局实例对象、List数据的加载、和RecyclerView的数据适配
     * */
    private void initData() {
        floatActionbtn = findViewById(R.id.floating);
        floatActionbtn.setOnClickListener(this);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        refreshLayout = findViewById(R.id.refreshlayout);

        initItemData();

        recyclerView_food = findViewById(R.id.recyclerView_vertical);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView_food.setLayoutManager(manager);
        adapter = new foodAdapter(R.layout.item_food_card, mList, highList);
        recyclerView_food.setAdapter(adapter);

        recyclerView_fruits = findViewById(R.id.recyclerView_horizontal);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_fruits.setLayoutManager(layoutManager);
        fruitsAdapter = new fruitsAdapter(R.layout.item_fruits_show, mFruitList);
        //新增一次滑动一个item效果
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        recyclerView_fruits.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recyclerView_fruits);
        recyclerView_fruits.setAdapter(fruitsAdapter);
    }

    /*
     * 添加List数据
     * */
    public void initItemData() {
        //设置第1个item和最后一个item高度为100，中间item的高度为270；
        highList.add(100);
        for (int i = 0; i < ITEM_SUM - 2; i++) {
            highList.add(270);
        }
        highList.add(100);

        //向mList中添加数据；
        mList.clear();
        foodBean food_1 = new foodBean("food_1", R.drawable.food_1);
        mList.add(food_1);
        foodBean food_2 = new foodBean("food_2", R.drawable.food_2);
        mList.add(food_2);
        foodBean food_3 = new foodBean("food_3", R.drawable.food_3);
        mList.add(food_3);
        foodBean food_4 = new foodBean("food_4", R.drawable.food_4);
        mList.add(food_4);
        foodBean food_5 = new foodBean("food_5", R.drawable.food_5);
        mList.add(food_5);
        foodBean food_6 = new foodBean("food_6", R.drawable.food_6);
        mList.add(food_6);
        foodBean food_7 = new foodBean("food_7", R.drawable.food_7);
        mList.add(food_7);
        foodBean food_8 = new foodBean("food_8", R.drawable.food_8);
        mList.add(food_8);

        mFruitList.clear();
        fruitsBean fruit_1 = new fruitsBean("fruit_1", R.drawable.fruit_1);
        mFruitList.add(fruit_1);
        fruitsBean fruit_2 = new fruitsBean("fruit_2", R.drawable.fruit_2);
        mFruitList.add(fruit_2);
        fruitsBean fruit_3 = new fruitsBean("fruit_3", R.drawable.fruit_3);
        mFruitList.add(fruit_3);
        fruitsBean fruit_4 = new fruitsBean("fruit_4", R.drawable.fruit_4);
        mFruitList.add(fruit_4);
    }

    /*
     * 在toolbar标题菜单中添加menu
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /*
     * 标题栏menu的点击事件；
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weather:
                Toast.makeText(this, "you click DOWNLOAD", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "you click DELECT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "you click SETTING", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    /*
     * 地步悬浮按钮的点击事件
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating:
                Snackbar.make(v, "Hey buddy!", Snackbar.LENGTH_SHORT)
                        .setAction("Nice to meet you~", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "What a beautiful day~", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            default:
                break;
        }
    }
}