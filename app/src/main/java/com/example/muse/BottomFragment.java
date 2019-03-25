package com.example.muse;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BottomFragment extends Fragment {
    ImageView WechatQRcode,ALipayQRcode;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bottom,container,false);
        TextView textView=view.findViewById(R.id.brief_introduction);
        Typeface typeface=getResources().getFont(R.font.jian_ti);
        textView.setTypeface(typeface);
        textView.setText("做这款作品的初衷是给大家推送好看的壁纸。\n好看的壁纸有很多，" +
                "但是好看而且适合作为手机壁纸的图片少之又少。\n名字的意思是灵感，它将为您推送" +
                "经过作者亲自筛选的适合作为手机壁纸的精妙绝伦的图片");
//        WechatQRcode=view.findViewById(R.id.Wechat_QRcode);
//        ALipayQRcode=view.findViewById(R.id.AliPay_QRcode);
//        Glide.with(this).load(R.drawable.qr_wechet).centerCrop().into(WechatQRcode);
//        Glide.with(this).load(R.drawable.qr_alipay).centerCrop().into(ALipayQRcode);
        return view;
    }
}
