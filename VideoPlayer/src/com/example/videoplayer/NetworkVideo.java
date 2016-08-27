package com.example.videoplayer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NetworkVideo extends Activity {
	public String url ="http://192.168.253.1/video/getvideo.php";
	public static String BaseURL = "http://192.168.253.1/";
	ListView mVideoListView;  
    NetVideoListAdapter netVideoListAdapter; 
    ArrayList<Netvideo> videolist;
    int videoSize;
    String mPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.networkvideo);
    	videolist = new ArrayList<Netvideo>();
    	
    	getNetVideoList();
    	videoSize = videolist.size(); 
        System.out.println("videoSize="+videoSize);
        netVideoListAdapter = new NetVideoListAdapter(getApplicationContext(), videolist);
        mVideoListView = (ListView)findViewById(R.id.videolistview);  
        mVideoListView.setAdapter(netVideoListAdapter);
        
        mVideoListView.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view, int position,  
                    long id) { 
            	mPath = videolist.get(position).getVideoPath();
                Intent intent = new Intent();  
                intent.setClass(NetworkVideo.this, VideoViewDemo.class);  
                intent.putExtra("video", mPath);
                startActivity(intent);  
            }  
        });
    }
    
    public void getNetVideoList() {
    	
		StringRequest request = new StringRequest(url, 
				new Listener<String>() {

				@Override
				public void onResponse(String arg0) {
					JSONArray jsonArray;					
					//Log.i("info", arg0);
						try {
							jsonArray = new JSONArray(arg0);
							for (int i = 0; i < jsonArray.length(); i++) {
	
								JSONObject object = jsonArray.getJSONObject(i);
								String videoname = object.getString("VideoName");
								String videopath= BaseURL + object.getString("VideoPath");
								String picpath = BaseURL+object.getString("PicPath");
								
								Netvideo netvideo = new Netvideo(videoname, videopath, picpath);
								System.out.println("videopath="+videopath);
								videolist.add(netvideo);
								//String temp[]={VideoName,VideoPath,PicPath};
								//setVideo(temp);
								
							}
							netVideoListAdapter.notifyDataSetChanged();

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				
																		
				}

			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					System.out.println("Response.ErrorListener!!!");

				}
			});
			Volley.newRequestQueue(NetworkVideo.this).add(request);
			System.out.println("creatVideo!!!");
				
	}

	private void setVideo(String[] temp) {
		videolist = new ArrayList<Netvideo>();
		String videoname=temp[0];
		String videopath=temp[1];
		String picpath=temp[2];
		Netvideo netvideo = new Netvideo(videoname, videopath, picpath);
		videolist.add(netvideo);

	}
    
}
