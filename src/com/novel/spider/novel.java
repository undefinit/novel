package com.novel.spider;

public class novel {
	public String title;
	public String url;
	public String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "novel [title=" + title + ", url=" + url + "]";
	}
	

}
