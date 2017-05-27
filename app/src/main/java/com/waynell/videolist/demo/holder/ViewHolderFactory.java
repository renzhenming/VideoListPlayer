package com.waynell.videolist.demo.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waynell.videolist.demo.R;
import com.waynell.videolist.demo.model.BaseItem;

/**
 * @author Wayne
 */
public class ViewHolderFactory {

    public static BaseViewHolder<? extends BaseItem> buildViewHolder(ViewGroup parent, int viewType,int height) {
        switch (viewType) {
            case BaseItem.VIEW_TYPE_VIDEO:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item ,parent, false);
//                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                layoutParams.height = height;
                return new VideoViewHolder(view);

            case BaseItem.VIEW_TYPE_PICTURE:
                return new PicViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pic_list_item, parent, false));

            default:
            case BaseItem.VIEW_TYPE_TEXT:
                return new TextViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.text_list_item, parent, false));

        }
    }

}
