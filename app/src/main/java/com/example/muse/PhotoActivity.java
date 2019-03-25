package com.example.muse;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoActivity extends AppCompatActivity {
public  static final String PHOTO_ID ="photo_id";
public  static final String PHOTO_Name ="photo_name";
    ImageView imageView;
FloatingActionButton download_image,love_image,use_image_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        init();
        Toast.makeText(PhotoActivity.this,"图片正在加载请耐心等待",Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  //全屏的意思，也就是会将状态栏隐藏.
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE         //表示会让应用的主体内容占用系统状态栏的空间
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;      //表示会让应用的主体内容占用系统导航栏的空间
            decorView.setSystemUiVisibility(option);                //设置系统UI的可不可见
            getWindow().setStatusBarColor(Color.TRANSPARENT);       //设置状态栏为透明
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent=getIntent();
        String photoId=intent.getStringExtra(PHOTO_ID);
        imageView=findViewById(R.id.entire_image);
        Glide.with(PhotoActivity.this).load(photoId).centerCrop().into(imageView);
        FloatingActionMenu fab =(FloatingActionMenu) findViewById(R.id.floating_action_menu);
        fab.setClosedOnTouchOutside(true);
    }


    public void init(){
        Intent intent=getIntent();
        final String photoId=intent.getStringExtra(PHOTO_ID);
        final String photoName=intent.getStringExtra(PHOTO_Name);
        download_image=findViewById(R.id.download_image);
        love_image=findViewById(R.id.love_image);
        use_image_background=findViewById(R.id.use_image_background);
        download_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(PhotoActivity.this).load(photoId).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                        try {
                            savaBitmap(photoName, bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                Snackbar.make(v,"已下载",Snackbar.LENGTH_SHORT).show();
            }
        });
        use_image_background.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                WallpaperManager wallpaperManager=WallpaperManager.getInstance(PhotoActivity.this);
                try {
                    imageView.setDrawingCacheEnabled(true);
                    wallpaperManager.setBitmap(imageView.getDrawingCache());
                    Snackbar.make(v,"壁纸设置成功",Snackbar.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        love_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                Intent intent=getIntent();
                String photoId=intent.getStringExtra(PHOTO_ID);
                String photoName=intent.getStringExtra(PHOTO_Name);
                Photo photo=new Photo(photoName,photoId);
                photo.save();
                Snackbar.make(v,"已收藏，请进入收藏页面查看",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    public void savaBitmap(String imgName, byte[] bytes) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = null;
            FileOutputStream fos = null;
            try {
                filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/Muse";
                File imgDir = new File(filePath);
                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                }
                imgName = filePath + "/" + imgName;
                fos = new FileOutputStream(imgName);
                fos.write(bytes);
                Toast.makeText(PhotoActivity.this, "图片已保存到" + filePath, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(PhotoActivity.this, "请检查SD卡是否可用", Toast.LENGTH_SHORT).show();
        }
    }

}
