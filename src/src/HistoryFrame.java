package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class HistoryFrame extends BasicFrame{//得分纪录界面，这个界面中显示出所有历史记录

	private ArrayList<String> his_score = new ArrayList<String>();
	private Date date=new Date();
	JButton back_but=new JButton("返回");

	JList<Object> jList=new JList<Object>();

	ImageIcon icon=new ImageIcon("image/bg.jpg");
	JLabel labpic=new JLabel(icon);
	public HistoryFrame() {
		// TODO Auto-generated constructor stub
		File hisfile = new File("flappybird hisscore.txt");
		if (!hisfile.exists()) {
			try {
				PrintWriter output = new PrintWriter(hisfile);
				this.hisscore = 0;
				output.println(this.date.toString() + " 0");
				output.close();
			} catch (FileNotFoundException var10) {
				var10.printStackTrace();
			}
		} else {
			try {
				Scanner input = new Scanner(hisfile);
				while(input.hasNext()) {
					this.his_score.add(input.nextLine());
				}
				input.close();
			} catch (FileNotFoundException var11) {
				var11.printStackTrace();
			}
		}

		String[] listDate=new String[his_score.size()+1];
		for(int i=0;i<his_score.size();i++){
			listDate[i]="第"+(i+1)+": "+ his_score.get(i);
		}
		//创建列表
		jList.setListData(listDate);
		jList.setFont(new Font("宋体",Font.PLAIN,30));

		JScrollPane scrollPane=new JScrollPane();//将列表添加到滚动框

		scrollPane.setViewportView(jList);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);//设置需要时显示竖向滚动条
		scrollPane.setWheelScrollingEnabled(true);//响应鼠标滚动
		//设置透明不知道为什么也不行
		scrollPane.setBorder(new EmptyBorder(5,5,5,5));//设置边框
		
		this.setTitle("FlappyBird历史排行");//设置标题

		this.setSize(500,1000);//设置窗口大小
		this.setLocationRelativeTo(null);//居中
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());//设置顶层为Border布局
		this.add(scrollPane,BorderLayout.CENTER);//滚动界面位置

		JPanel bottom_pan=new JPanel(new FlowLayout());
		bottom_pan.setOpaque(false);
		bottom_pan.add(back_but);
		this.add(bottom_pan,BorderLayout.SOUTH);//设置返回按钮在上中
		this.validate();
		this.setVisible(true);
		listener();

	}
	@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			super.paint(g);
			g.drawImage(back_img.getImage(), 0, 0, this);
			if (frame_width>back_img.getIconWidth()) {      
				g.drawImage(back_img.getImage(), back_img.getIconWidth(), 0, this);//加了如果界面放大之后右边补
			}
		}


	@Override
		public void listener() {
			// TODO Auto-generated method stub
			super.listener();
			back_but.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					dispose();
				}
			});
		}
}
