package com.example.videoplayer;

public class Netvideo {
	private String VideoName;
	private String VideoPath;
	private String PicPath;
	
	public Netvideo(String VideoName, String VideoPath, String PicPath){
		setVideoName(VideoName);
		setVideoPath(VideoPath);
		setPicPath(PicPath);
	}
	
	public String getVideoName() {
		return VideoName;
	}
	public void setVideoName(String videoName) {
		VideoName = videoName;
	}
	public String getVideoPath() {
		return VideoPath;
	}
	public void setVideoPath(String videoPath) {
		VideoPath = videoPath;
	}
	public String getPicPath() {
		return PicPath;
	}
	public void setPicPath(String picPath) {
		PicPath = picPath;
	}
	
	
	
}
