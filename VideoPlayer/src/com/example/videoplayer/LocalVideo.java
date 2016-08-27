package com.example.videoplayer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class LocalVideo extends Activity {
	public LocalVideo instance = null; 
	ListView mVideoListView;  
    VideoListViewAdapter mVideoListViewAdapter;  
    ArrayList<Video> listVideos;
    private TextView first_letter_overlay;
	private ImageView alphabet_scroller; //字母滚动查询表
    int videoSize;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localvideo);
		
		instance = this;  
		VideoProvider provider = new VideoProvider(LocalVideo.this);  
        listVideos = provider.getvideoList();  
        
        System.out.println("videoSize="+videoSize);
        mVideoListViewAdapter = new VideoListViewAdapter(this, listVideos);
        videoSize = listVideos.size();
        mVideoListView = (ListView)findViewById(R.id.videolistview);  
        mVideoListView.setAdapter(mVideoListViewAdapter);  
        mVideoListView.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view, int position,  
                    long id) {  
                Intent intent = new Intent();  
                intent.setClass(LocalVideo.this, VideoViewDemo.class);  
                //Bundle bundle = new Bundle();  
                //bundle.putSerializable("video", listVideos.get(position));  
                intent.putExtra("video", listVideos.get(position).getPath());
                System.out.println("listVideos.get(position)="+listVideos.get(position).getPath());
                startActivity(intent);  
            }  
        });  
        loadImages();
        first_letter_overlay = (TextView)findViewById(R.id.first_letter_overlay);
        alphabet_scroller = (ImageView)findViewById(R.id.alphabet_scroller);
        alphabet_scroller.setClickable(true);
        alphabet_scroller.setOnTouchListener(asOnTouch);
	}

	 private void loadImages() {  
	        final Object data = getLastNonConfigurationInstance();  
	        if (data == null) {  
	            new LoadImagesFromSDCard().execute();  
	        } else {  
	            final LoadedImage[] photos = (LoadedImage[]) data;  
	            if (photos.length == 0) {  
	                new LoadImagesFromSDCard().execute();  
	            }  
	            for (LoadedImage photo : photos) {  
	                addImage(photo);  
	            }  
	        }  
	    }  
	    private void addImage(LoadedImage... value) {  
	        for (LoadedImage image : value) {  
	            mVideoListViewAdapter.addPhoto(image);  
	            mVideoListViewAdapter.notifyDataSetChanged();  
	        }  
	    }  
	    @Override  
	    public Object onRetainNonConfigurationInstance() {  
	        final ListView grid = mVideoListView;  
	        final int count = grid.getChildCount();  
	        final LoadedImage[] list = new LoadedImage[count];  
	  
	        for (int i = 0; i < count; i++) {  
	            final ImageView v = (ImageView) grid.getChildAt(i);  
	            list[i] = new LoadedImage(  
	                    ((BitmapDrawable) v.getDrawable()).getBitmap());  
	        }  
	  
	        return list;  
	    }  
	    /**  
	        * 获取视频缩略图  
	        * @param videoPath  
	        * @param width  
	        * @param height  
	        * @param kind  
	        * @return  
	        */    
	       private Bitmap getVideoThumbnail(String videoPath, int width , int height, int kind){    
	        Bitmap bitmap = null;    
	        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);    
	        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);    
	        return bitmap;    
	       }    
	  
	    class LoadImagesFromSDCard extends AsyncTask<Object, LoadedImage, Object> {  
	        @Override  
	        protected Object doInBackground(Object... params) {  
	            Bitmap bitmap = null;  
	            for (int i = 0; i < videoSize; i++) {  
	                bitmap = getVideoThumbnail(listVideos.get(i).getPath(), 120, 120, Thumbnails.MINI_KIND);  
	                if (bitmap != null) {  
	                    publishProgress(new LoadedImage(bitmap));  
	                }  
	            }  
	            return null;  
	        }  
	          
	        @Override  
	        public void onProgressUpdate(LoadedImage... value) {  
	            addImage(value);  
	        }  
	    } 
	    
	    /**
		 * A-Z
		 */
		private OnTouchListener asOnTouch = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 0
					alphabet_scroller.setPressed(true);
					first_letter_overlay.setVisibility(View.VISIBLE);
					mathScrollerPosition(event.getY());
					break;
				case MotionEvent.ACTION_UP:// 1
					alphabet_scroller.setPressed(false);
					first_letter_overlay.setVisibility(View.GONE);
					break;
				case MotionEvent.ACTION_MOVE:
					mathScrollerPosition(event.getY());
					break;
				}
				return false;
			}
		};

		/**
		 * 显示字符
		 * 
		 * @param y
		 */
		private void mathScrollerPosition(float y) {
			int height = alphabet_scroller.getHeight();
			float charHeight = height / 28.0f;
			char c = 'A';
			if (y < 0)
				y = 0;
			else if (y > height)
				y = height;

			int index = (int) (y / charHeight) - 1;
			if (index < 0)
				index = 0;
			else if (index > 25)
				index = 25;

			String key = String.valueOf((char) (c + index));
			first_letter_overlay.setText(key);

			int position = 0;
			if (index == 0)
				mVideoListView.setSelection(0);
			else if (index == 25)
				mVideoListView.setSelection(mVideoListViewAdapter.getCount() - 1);
			else {
				int size = listVideos.size();
				for (int i = 0; i < size; i++) {
					if (listVideos.get(i).getTitle_key().startsWith(key)) {
						mVideoListView.setSelection(position);
						break;
					}
					position++;
				}
			}
		}
		@Override
		protected void onDestroy() {
			super.onDestroy();
		}
}
