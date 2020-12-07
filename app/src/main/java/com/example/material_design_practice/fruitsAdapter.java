package com.example.material_design_practice;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class fruitsAdapter extends BaseQuickAdapter<fruitsBean, BaseViewHolder> {
    public fruitsAdapter(int layoutResId, @Nullable List<fruitsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, fruitsBean item) {
        ImageView imageView = helper.getView(R.id.image_fruit);
        Glide.with(mContext).load(item.getFruitsId()).into(imageView);
    }
}
