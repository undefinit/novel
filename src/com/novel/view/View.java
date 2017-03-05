package com.novel.view;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.novel.spider.Craw;
import com.novel.spider.novel;

public class View extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	public JLabel bq1;
	public JLabel bq2;
	public JTextField jTextField;
	public JButton choose;
	public JTextField jTextField2;
	public JButton down;
	public JProgressBar jProgressBar;
	public View(){
		this.view_main();
		this.addAct();
	}
	public void view_main(){
		this.setLayout(null);
		bq1=new JLabel("存储位置");
		bq2=new JLabel("小说URL");

		jTextField=new JTextField("D:\\");
		choose=new JButton("选择");

		jTextField2=new JTextField();
		down=new JButton("下载");

		this.add(bq1);
		bq1.setBounds(10, 7, 60, 30);
		this.add(jTextField);
		jTextField.setBounds(80, 10, 150, 30);
		jTextField.setEditable(false);
		this.add(choose);
		choose.setBounds(235, 10,80, 30);

		this.add(bq2);
		bq2.setBounds(10,44, 60, 30);
		this.add(jTextField2);
		jTextField2.setBounds(80, 47, 150, 30);
		this.add(down);
		down.setBounds(235, 47, 80, 30);
		
		jProgressBar=new JProgressBar();
		jProgressBar.setStringPainted(true);
		jProgressBar.setBackground(Color.green);
		this.add(jProgressBar);
		jProgressBar.setBounds(20, 90, 280, 20);

		this.setVisible(true);
		this.setTitle("小说下载器");
		this.setBounds(450, 380, 330, 150);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==choose) {
			//选择小说下载的位置
			try {
				JFileChooser chooser=new JFileChooser("D:\\");
				chooser.showSaveDialog(this);
				jTextField.setText(chooser.getSelectedFile().getParent());
			} catch (Exception e1) {
			}
		}
		if (e.getSource()==down) {
			//下载小说
			FileWriter fWriter=null;
			List<novel> lists;
			String url=getUrl(jTextField2.getText().trim());
			if (url.equals("")||url==null) {
				JOptionPane.showConfirmDialog(null, "没有找到此小说！");
			}
			else{
				try {
					//抓取小说内容，存到list集合中
					lists=new Craw(url).craw();
					
					//得到小说的名字
					Document document=Jsoup.connect(url).get();
					Element element=document.getElementsByTag("h1").first();
					System.out.println(element.text());
					String text=element.text();

					fWriter=new FileWriter(jTextField.getText().trim()+text+".txt");
					//将小说内容写进文件中
					int i=lists.size();
					System.out.println("------------------------------------"+i);
					jProgressBar.setMaximum(i);
					int j=0;
					for (novel novel : lists) {
						System.out.println(novel.getTitle());
						fWriter.write(novel.getTitle());
						String content=parse(novel.getUrl());
						fWriter.write(content);
						System.out.println(j);
						this.jProgressBar.setValue(j++);
					}
					jProgressBar.setString("下载完成");
					JOptionPane.showMessageDialog(null, "下载完成");
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally {
					try {
						fWriter.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		}

	}
	
	//根据编辑框2里面输入的小说名字，返回小说目录页面的URL
	public String getUrl(String text){
		Document document;
		String url=null;
		if (text.equals("")) {
			JOptionPane.showMessageDialog(null, "请输入你要下载的小说名字");
		}else{
			try {
				document = Jsoup.connect("http://zhannei.baidu.com/cse/search?s=920895234054625192&q="+text.trim()).get();
				Element element=document.getElementsByClass("result-game-item-title-link").first();
				url=element.attr("href");
				System.out.println(url);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return url;
	}
	//抓取每个章节的内容
	public String parse(String url){
		Element element=null;
		try {
			Document document=Jsoup.connect(url).get();
			element=document.getElementById("content");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return element.text();
	}
	//为按钮添加监听
	public void addAct(){
		choose.addActionListener(this);
		down.addActionListener(this);
	}
}
