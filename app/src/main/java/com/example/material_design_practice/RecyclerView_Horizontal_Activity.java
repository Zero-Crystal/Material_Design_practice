package com.example.material_design_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerView_Horizontal_Activity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;//侧滑菜单控件

    private RecyclerView recyclerView_fruits;
    private List<pictureResponse.ResBean.VerticalBean> mFruitList = new ArrayList<>();
    private fruitsAdapter fruitsAdapter;
    private int ITEM_SUM = 8;

    private static final String TAG = "Zero";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview__horizontal);

        initData();

        setSupportActionBar(toolbar);//传入ToolBar实例
        ActionBar actionBar = getSupportActionBar();//使用getSupportActionBar()得到ActionBar实例
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//让导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.mipmap.my_page);//设置导航按钮图标，即最左侧的图标，这个图标就叫"HomeAsUp"
        }

        navigationItemSelected();

    }

    private void initData() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation);

        initItemData();

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
        createRetrofit();

        /*mFruitList.clear();
        fruitsBean fruit_1 = new fruitsBean("fruit_1", R.drawable.fruit_1);
        mFruitList.add(fruit_1);
        fruitsBean fruit_2 = new fruitsBean("fruit_2", R.drawable.fruit_2);
        mFruitList.add(fruit_2);
        fruitsBean fruit_3 = new fruitsBean("fruit_3", R.drawable.fruit_3);
        mFruitList.add(fruit_3);
        fruitsBean fruit_4 = new fruitsBean("fruit_4", R.drawable.fruit_4);
        mFruitList.add(fruit_4);*/
    }

    /*
     * 标题栏menu的点击事件；
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weather:
                Toast.makeText(this, "you click weather", Toast.LENGTH_SHORT).show();
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
     * 左滑弹窗内menu菜单的点击操作
     * */
    private void navigationItemSelected() {
        navigationView.setCheckedItem(R.id.nav_call);//将"nav_call"默认选中
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_log:
                        Toast.makeText(RecyclerView_Horizontal_Activity.this, "Do you want to check your Log?", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_shopping:
                        Toast.makeText(RecyclerView_Horizontal_Activity.this, "Do you want to shopping with me?", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_call:
                        Toast.makeText(RecyclerView_Horizontal_Activity.this, "I really want to chat with you!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_message:
                        Toast.makeText(RecyclerView_Horizontal_Activity.this, "Do you want to check your message?", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://service.picasso.adesk.com")
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        api.getPicture().enqueue(new Callback<pictureResponse>() {
            @Override
            public void onResponse(Call<pictureResponse> call, Response<pictureResponse> response) {
                List<pictureResponse.ResBean.VerticalBean> data = response.body().getRes().getVertical();
                if (data != null && data.size() > 0) {
                    mFruitList.clear();
                    for (int i = 0; i < ITEM_SUM; i++) {
                        mFruitList.add(data.get(i));
                    }
                    fruitsAdapter.notifyItemInserted(mFruitList.size());
                } else {
                    Log.d(TAG, "onResponse: 壁纸数据为空！");
                }
                Log.d(TAG, "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<pictureResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }
}