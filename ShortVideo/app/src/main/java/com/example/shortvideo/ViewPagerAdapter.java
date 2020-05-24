package com.example.shortvideo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

class VideoInfo {
    public String feedurl;
    public String nickname;
    public String description;
    public String likecount;
    public String avatar;

    public VideoInfo(String feedurl, String nickname, String description, String likecount, String avatar){
        this.feedurl = feedurl;
        this.nickname = nickname;
        this.description = description;
        this.likecount = likecount;
        this.avatar = avatar;
    }

}

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {
    private List<Integer> colors = new ArrayList<>();

    private List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();

    Context context;

    public ViewPagerAdapter(List<VideoInfo> videoInfos, Context context){
        this.videoInfos = videoInfos;
        this.context = context;
    }

    {
        colors.add(android.R.color.black);
        colors.add(android.R.color.holo_purple);
        colors.add(android.R.color.holo_blue_dark);
        colors.add(android.R.color.holo_green_light);
    }
    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPagerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, final int position) {
        String msg = this.videoInfos.get(position).nickname + '\n' + this.videoInfos.get(position).description;
        holder.mTvTitle.setText(msg);
//        holder.mContainer.setBackgroundResource(colors.get(1));
        Glide.with(context).setDefaultRequestOptions(
                new RequestOptions()
                        .frame(0)
                        .centerCrop()
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_foreground)
        ).load(this.videoInfos.get(position).feedurl)
                .into(holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, VideoActivity.class);
                i.putExtra("url", videoInfos.get(position).feedurl);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.videoInfos.size();
    }


    class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        RelativeLayout mContainer;
        ImageView mImageView;
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.container);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mImageView = itemView.findViewById(R.id.imageView);
        }
    }
}

