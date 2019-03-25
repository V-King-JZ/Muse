package com.example.muse;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class MainActivity extends AppCompatActivity{
private DrawerLayout mDrawerLayout;
private List<Photo> photoList=new ArrayList<>();
private PhotoAdapter adapter;
private SwipeRefreshLayout swipeRefreshLayout;
private FloatingActionButton fab_random;
private IntentFilter intentFilter;
private MyBroadcast myBroadcast;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(MainActivity.this,"第一次加载图片显示可能较慢，请耐心等待",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);
        initRecylerView();
        initView();
        implement_navigation_transparent();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoAdapter(photoList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPhoto();
            }
        });
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        myBroadcast=new MyBroadcast();
        registerReceiver(myBroadcast,intentFilter);
    }

    private void refreshPhoto() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initRecylerView();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    private void initView(){
        fab_random=findViewById(R.id.fab_random);
        fab_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random=new Random();
                int index=random.nextInt(photos.length);
                Intent in=new Intent(MainActivity.this,PhotoActivity.class);
                String photoId=photos[index].getUri();
                index=random.nextInt(photos.length);
                in.putExtra(PhotoActivity.PHOTO_ID,photoId);
                startActivity(in);
                Toast.makeText(MainActivity.this,"已经随机帮您挑选了一张图片",Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void initRecylerView(){
        photoList.clear();
        Random random=new Random();
        Set<Integer> set=new HashSet<>();
        for(int i=0;i<25;i++) {
            int index=random.nextInt(photos.length);
            set.add(index);
        }
        Iterator<Integer> iterator=set.iterator();
        while (iterator.hasNext()){
            photoList.add(photos[iterator.next()]);
        }

    }
    private void implement_navigation_transparent(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_about_author:
                Intent in=new Intent(MainActivity.this,AboutAuthor.class);
                startActivity(in);
                break;
            case R.id.menu_collection:
                Intent intent=new Intent(MainActivity.this,PhotoCollection.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    class MyBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager=
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable()){

            }
            else {
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("这是广播哦！");
                dialog.setMessage("当前网络不可用啊！");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        }
    }
    Photo[] photos = {new Photo("落叶", "http://pmel2n6c4.bkt.clouddn.com/019f3404-d3e3-45b8-ba0c-d1b70be7a155.JPG"),
            new Photo("水花","http://pmel2n6c4.bkt.clouddn.com/0997e7f2-7a81-4729-b9e3-ac04805e6f47.JPG"),
            new Photo("云雾","http://pmel2n6c4.bkt.clouddn.com/10362840-812f-43ef-a651-916736afb7c9.JPG"),
            new Photo("鹰","http://pmel2n6c4.bkt.clouddn.com/178b5643-7985-476e-891e-6e38212f3c1c.JPG"),
            new Photo("塔尖","http://pmel2n6c4.bkt.clouddn.com/1d195b37-d561-4b59-9d92-98f5f7778419.JPG"),
            new Photo("海面","http://pmel2n6c4.bkt.clouddn.com/240e2af2-d668-4cf6-b9ef-4a789e2bcd37.JPG"),
            new Photo("枫叶","http://pmel2n6c4.bkt.clouddn.com/582f9af4-3c7c-4d2e-b5a4-da9af8278f9a.JPG"),
            new Photo("山峰","http://pmel2n6c4.bkt.clouddn.com/6670e8c9-0d38-4a29-a257-65b500646c35.JPG"),
            new Photo("水母","http://pmel2n6c4.bkt.clouddn.com/8c435d23-d917-4402-bec8-cdfbcf0424ef.JPG"),
            new Photo("热气球","http://pmel2n6c4.bkt.clouddn.com/8e68158d-7291-485c-ac45-9c7a68837491.JPG"),
            new Photo("布加迪威龙","http://pmel2n6c4.bkt.clouddn.com/9ab70d11-4a92-4970-9394-58e0a0d6ff00.JPG"),
            new Photo("冰面","http://pmel2n6c4.bkt.clouddn.com/a8e2fb35-5c6f-43ec-b70d-1cb15adc16eb.JPG"),
            new Photo("浪潮","http://pmel2n6c4.bkt.clouddn.com/b4001ffb-1f22-404f-8ef6-ada05b2540cf.JPG"),
            new Photo("天空","http://pmel2n6c4.bkt.clouddn.com/c0d30498-1b71-482e-ae89-9c6df4db5e44.JPG"),
            new Photo("雪山","http://pmel2n6c4.bkt.clouddn.com/d589efae-6901-4e9f-a428-a1b250aed32e.JPG"),
            new Photo("彩","http://pmel2n6c4.bkt.clouddn.com/dfb2c204-cf00-4e20-a18c-4d99ae7c50b5.JPG"),
            new Photo("暗","http://pmel2n6c4.bkt.clouddn.com/e0c1724f-700c-430c-8bf9-142b51067197.JPG"),
            new Photo("玫瑰","http://pmel2n6c4.bkt.clouddn.com/e3d1fa74-0177-4e2c-a48e-fb458fbe048b.JPG"),
            new Photo("冰痕","http://pmel2n6c4.bkt.clouddn.com/e72a9395-5de3-4574-ae82-54309ccd9723.JPG"),
    };
}
