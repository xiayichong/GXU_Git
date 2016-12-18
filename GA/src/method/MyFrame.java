package method;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import rectangle.bean.GeneBean;
import rectangle.bean.RectangleBean;
import rectangle.bean.SheetBean;

public class MyFrame extends JFrame{
	GeneBean gene;
	SheetBean sheet;
	public MyFrame(GeneBean gene,SheetBean sheet){
		this.gene=gene;
		this.sheet=sheet;
		MyPanel mypanel =new MyPanel(gene,sheet);
		this.add(mypanel);
		this.setVisible(true);
		this.setSize(800, 600);
		this.setTitle("最低水平线算法");
		this.setLocation(200, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
class MyPanel extends JPanel{
	GeneBean gene;
	SheetBean sheet;
	public MyPanel(GeneBean gene,SheetBean sheet){
		this.gene=gene;
		this.sheet=sheet;
		
	}
	 public void paint(Graphics g){
		   super.paint(g);
		   g.setColor(Color.black);
		   g.drawLine(300, 50, sheet.getWidth()+300, 50);
		   g.drawLine(300, 50, 300, 600);
		   g.drawLine(300+sheet.getWidth(), 50, 300+sheet.getWidth(),600);
		   //添充矩形
		   g.setColor(Color.CYAN);
		   List<RectangleBean> rectList= gene.getRectList();
		   for(RectangleBean emp:rectList){
			   g.fill3DRect(300+emp.getX1(),50+emp.getY1(), emp.getWidth(), emp.getLength(),false);
		   }
	 }
}
