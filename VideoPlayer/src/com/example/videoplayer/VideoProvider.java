package com.example.videoplayer;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class VideoProvider implements AbstructProvider{
	private Context context;  
    
    public VideoProvider(Context context) {  
        this.context = context;  
    }  
      
    public ArrayList<Video> getvideoList() {  
    	ArrayList<Video> videolist = null;  
        if (context != null) {  
            Cursor cursor = context.getContentResolver().query(  
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,  
                    null, null);  
            if (cursor != null) {  
                videolist = new ArrayList<Video>();  
                while (cursor.moveToNext()) {  
                    int id = cursor.getInt(cursor  
                            .getColumnIndexOrThrow(MediaStore.Video.Media._ID));  
                    String title = cursor  
                            .getString(cursor  
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));  
                    String album = cursor  
                            .getString(cursor  
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));  
                    String artist = cursor  
                            .getString(cursor  
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));  
                    String displayName = cursor  
                            .getString(cursor  
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));  
                    String mimeType = cursor  
                            .getString(cursor  
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));  
                    String path = cursor  
                            .getString(cursor  
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));  
                    long duration = cursor  
                            .getInt(cursor  
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));  
                    long size = cursor  
                            .getLong(cursor  
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));  
                    String title_key = "A";
                    if (title != null && title.length() > 0) {
                    	/*try {
							title_key = PinyinUtils.chineneToSpell(title.charAt(0) + "");
						} catch (BadHanyuPinyinOutputFormatCombination e) {
							e.printStackTrace();
						}*/
					}
                    AppLog.e("dd",title_key);
                    Video video = new Video(id, title, album, artist, displayName, mimeType, path, size, duration,title_key);  
                    videolist.add(video);  
                }  
                cursor.close();  
            }  
        }  
        return videolist;  
    } 
  
}
