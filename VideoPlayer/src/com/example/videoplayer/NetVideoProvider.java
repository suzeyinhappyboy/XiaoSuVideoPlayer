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

import android.content.Context;

public class NetVideoProvider {
	private Context context;
	public String url ="http://192.168.253.1/video/getvideo.php";
	public static String BaseURL = "http://192.168.253.1/";
	private ArrayList<Netvideo> videolist;
	Boolean flag = false;
	public NetVideoProvider(Context context) {  
        this.context = context;  
    }  
	
	public ArrayList<Netvideo> getNetVideoList() {
    	videolist = new ArrayList<Netvideo>();
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
								String videopath= object.getString("VideoPath");
								String picpath = object.getString("PicPath");
								Netvideo netvideo = new Netvideo(videoname, videopath, picpath);
								System.out.println("videopath="+videopath);
								videolist.add(netvideo);
								//String temp[]={VideoName,VideoPath,PicPath};
								//setVideo(temp);
								
							}
							//mAdapter.notifyDataSetChanged();

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
			Volley.newRequestQueue(context).add(request);
			System.out.println("creatVideo!!!");
			return videolist;
				
	}
}
