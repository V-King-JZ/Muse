package com.example.muse;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class PhotoCollection extends AppCompatActivity {
    RecyclerView collectionRecyclerView;
    PhotoAdapter collectionAdapter;
    List<Photo> collectionList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photo_collection);
        collectionList=new ArrayList<>();
        addPhoto();
        collectionRecyclerView=findViewById(R.id.collection_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        collectionRecyclerView.setLayoutManager(layoutManager);
        collectionAdapter=new PhotoAdapter(collectionList);
        collectionRecyclerView.setAdapter(collectionAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back_button:
                Intent in=new Intent(PhotoCollection.this,MainActivity.class);
                startActivity(in);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void addPhoto(){
        List<Photo> photos= DataSupport.findAll(Photo.class);
        if(photos.isEmpty()){
            AlertDialog.Builder dialog=new AlertDialog.Builder(PhotoCollection.this);
            dialog.setMessage("你还没有收藏图片！快去收藏自己喜欢的图片吧！");
            dialog.setPositiveButton("去收藏", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(PhotoCollection.this,MainActivity.class);
                    startActivity(intent);
                }
            });
            dialog.show();
        }
        else {
            for (Photo photo : photos) {
                collectionList.add(new Photo(photo.getName(), photo.getUri()));
            }
        }
    }
}
