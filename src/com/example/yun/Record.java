package com.example.yun;

import java.io.Serializable;

public class Record implements Serializable {
	private String targetId;
	private String name;
	private String url;
	private String modifyDate;
	private int targetSize;
	private int status;
	
	public String getTargetId(){
		return targetId;
	}
	
	public void setTargetId(String targetId){
		this.targetId = targetId;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getModifyDate(){
		return modifyDate;
	}
	
	public void setModifyDate(String modifyDate){
		this.modifyDate = modifyDate;
	}
	
	public int getTargetSize(){
		return targetSize;
	}
	
	public void setTargetSize(int targetSize){
		this.targetSize = targetSize;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
}
