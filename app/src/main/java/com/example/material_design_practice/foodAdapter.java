package com.example.material_design_practice;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class foodAdapter extends BaseQuickAdapter<foodBean, BaseViewHolder> {
    private List<Integer> mhighList = new ArrayList<>();
    private CardView cardView;

    public foodAdapter(int layoutResId, @Nullable List<foodBean> data, List<Integer> highList) {
        super(layoutResId, data);
        this.mhighList = highList;
    }

    @Override
    protected void convert(BaseViewHolder helper, foodBean item) {
        cardView = helper.getView(R.id.item_cardView);
        ImageView imageView = helper.getView(R.id.iv_item_image);

        Glide.with(mContext).load(item.getFoodId()).into(imageView);

        ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
        layoutParams.height = dip2px(mhighList.get(helper.getAdapterPosition()));

        helper.addOnClickListener(R.id.item_cardView);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    private int dip2px(float dpVale) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

}
