package com.example.videoplayer;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NetVideoListAdapter extends BaseAdapter{

	private LayoutInflater mLayoutInflater;
	ArrayList<Netvideo> videolist;
	public NetVideoListAdapter(Context context, ArrayList<Netvideo> videolists){  
        mLayoutInflater = LayoutInflater.from(context);  
        this.videolist = videolists;  
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videolist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;  
        if (convertView == null) {  
            holder = new ViewHolder();  
            convertView = mLayoutInflater.inflate(R.layout.videoitem, null);  
            holder.img = (ImageView)convertView.findViewById(R.id.video_img);  
            holder.title = (TextView)convertView.findViewById(R.id.video_title);  
            holder.time = (TextView)convertView.findViewById(R.id.video_time);  
            convertView.setTag(holder);  
        }else {  
            holder = (ViewHolder)convertView.getTag();  
        }  
            holder.title.setText(videolist.get(position).getVideoName());    
            holder.time.setText(videolist.get(position).getVideoPath()); 
            HttpUtils.setPicBitmap(holder.img, videolist.get(position).getPicPath()); 
           // holder.img.setImageBitmap(videolist.get(position).getPicPath());  
          
        return convertView;  
	}
	
	public final class ViewHolder{  
        public ImageView img;  
        public TextView title;  
        public TextView time;  
    }  

}
