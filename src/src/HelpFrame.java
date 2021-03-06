package src;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HelpFrame extends BasicFrame implements Runnable{ 
	//帮助界面，主要就是重复了一下主要的界面的线程
	int i=0;
	int back_x=0;
	boolean playing=true;
	boolean paintbird=false;
	//注意左上角为(0,0)坐标点。
	JButton back_but;
	public HelpFrame() {
		// TODO Auto-generated constructor stub
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Flappy Bird Help");
		birds_img=new Image[4];
		pipe_img=new Image[2];
		center_pan=new JPanel(new GridLayout(4,1));
		downtube=new ArrayList<Integer>();//下面管子的y坐标
		uptube=new ArrayList<Integer>();//上面管子的y坐标
		xtube=new ArrayList<>();	
		Bird_x=frame_width/3;
		Bird_y=frame_height/2;
		this.setVisible(true);
		getimages();
		listener();
		new Thread(this).start();//线程
	}
	public void getimages() {
		for (int i = 0; i < 3; i++) {
			birds_img[i]=new ImageIcon("image/"+i+".gif").getImage();
		}
		birds_img[3]=new ImageIcon("image/1.gif").getImage();
		for (int i = 0; i < 2; i++) {
			pipe_img[i]=new ImageIcon("image/pic"+(i+1)+".png").getImage();
		}
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		for (int i = 0; i < frame_width/720+2; i++) {//画背景
			g.drawImage(back_img.getImage(), back_x+720*i, 0, this);
		}
		g.drawImage(birds_img[i], Bird_x, Bird_y, this);//画鸟
		if (!paintbird) {    //降低重复刷新管道图片导致界面闪烁
			for (int i = 0; i < xtube.size(); i++) {
				g.drawImage(pipe_img[0], xtube.get(i), uptube.get(i), this);
				g.drawImage(pipe_img[1], xtube.get(i), downtube.get(i), this);
			}
		}
		g.setFont(new Font("宋体",Font.PLAIN,30));
		g.drawString("历史最高分:"+hisscore, 40, 60);
		g.drawString("当前得分:"+score, 40, 90);
		g.drawString("点击鼠标和空格跳跃，按p暂停", 40, 120);  
		g.drawString("当通过一个一个管道后得分会增加", 40, 150); 
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int wait=0;
		int cl=0;
		score=0;
		while (true) {
			try {
					if (Bird_y<frame_height-54) {
						Bird_y+=4;
					}
					else {
						Bird_y=frame_height-50;
					}
					if (wait%20==0) {
						uptube.add((int)Math.round(frame_height*Math.random()/2)-570);
						downtube.add((int)Math.round(300*Math.random())+frame_height/2+20);
						xtube.add((int)frame_width);
					}
					back_x=(back_x-10)%720;
					cl=(cl+5)%frame_width;
					for (int i = 0; i < xtube.size()-1; i++) {
						if (xtube.get(i)+140>=Bird_x-10 &xtube.get(i)+140<Bird_x) {
							score++;
						}
						if (xtube.get(i)>=Bird_x-140 & xtube.get(i)<=Bird_x+40) {//水平位置判断
							 if (Bird_y<uptube.get(i)+600|Bird_y+30>downtube.get(i)) {//竖直位置判断
								 int m=JOptionPane.showConfirmDialog(this, "返回主界面？" ,"Game Over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
								 if (m==0) {
									 dispose();
								}
								 else {
									score=0;
									uptube.clear();
									downtube.clear();
									xtube.clear();
								}
								 repaint();
								 break;
							}
						}
						xtube.set(i, xtube.get(i)-10);
					}
					if (cl==0) {
						uptube.remove(0);
						downtube.remove(0);
						xtube.remove(0);
					}
					repaint();
					i=(i+1)%4;
					wait++;
					if (exitthread) {
						break;
					}
					
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void openfile() {
		File i=new File("image/0.gif");
		try {
			Desktop.getDesktop().open(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void birdjump() {//鸟的跳跃
		if (Bird_y>65) {
			Bird_y-=35;
		}
		else {
			Bird_y=35;
		}
		paintbird=true;
		repaint();
		paintbird=false;
	}
	@Override
		public void listener() {
			// TODO Auto-generated method stub
			super.listener();
			this.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					super.keyTyped(arg0);
					if (arg0.getKeyChar()=='p') {//暂停按钮
						playing=!playing;
					}
					if (arg0.getKeyChar()==' ') {
						birdjump();
					}
					else {
						
					}
				}
			});
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					super.mouseClicked(arg0);
					birdjump();
				}
			});
		}

}
