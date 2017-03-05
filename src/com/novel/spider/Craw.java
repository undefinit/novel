package com.novel.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Craw {
	public String root_url=null;
	List<novel> lists=new ArrayList<novel>(5000);
	public String text;
	public Craw(String url) {
		this.root_url=url;
		this.craw();
	}
	public Craw(){
	}
	public List<novel> craw(){
		try {
			if (root_url==null) {
				JOptionPane.showConfirmDialog(null, "没有找到此小说");
			}
			Document document=Jsoup.connect(root_url).get();
			
			Elements elements=document.getElementsByTag("a");
			
			Element element2=document.getElementsByTag("h1").first();
			text=element2.text();
			int i=0;
			for (Element element : elements) {
				if (i>30) {
					novel _novel=new novel();
					_novel.setTitle(element.text());
					_novel.setUrl(root_url+element.attr("href"));
					lists.add(_novel);
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lists;
	}
}
