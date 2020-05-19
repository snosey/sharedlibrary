package com.dinnova.sharedlibrary.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.dinnova.sharedlibrary.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemImagePagerAdapter extends PagerAdapter {
    public List<ImgModel> imgModels;

    public ItemImagePagerAdapter(List<ImgModel> imgUrl, FragmentActivity fragmentActivity) {
        this.imgModels = imgUrl;
    }

    @Override
    public int getCount() {
        return imgModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

 /*   @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_full_row, container, false);
        final PhotoView imageView = itemView.findViewById(R.id.image);
        final ProgressBar progress = itemView.findViewById(R.id.progress);
        imageView.setOnClickListener(imgModels.get(position).onClickListener);
        Picasso.get().load(imgModels.get(position).ImgUrl).fit().centerCrop().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                progress.setVisibility(View.GONE);
                imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        });

        container.addView(itemView);

        return itemView;
    }*/

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public static class ImgModel {
        public String ImgUrl;
        public View.OnClickListener onClickListener;
    }
}