package com.novel.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.novel.spider.Craw;
import com.novel.view.View;

public class SpiderManager{
	public View view;
	public SpiderManager() {
		view=new View();
	}
	public static void main(String[] args) {
		SpiderManager manager=new SpiderManager();
	}
}
