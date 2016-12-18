package method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rectangle.bean.GeneBean;
import rectangle.bean.Line;
import rectangle.bean.RectangleBean;
import rectangle.bean.SheetBean;

public class HorizontalLine {
	public static  List<RectangleBean>  getGeneScore(GeneBean gene,SheetBean sheet){
		HorizontalLine h=new HorizontalLine();
		int xn=sheet.getWidth();
		Line line0=new Line();//最初沿着x轴的水平线
		line0.setYl(0);
		line0.setXl1(0);
		line0.setXl2(xn);
		List<Line> lineList=new ArrayList<Line>();
		lineList.add(line0);
		List<RectangleBean> rectList=gene.getRectList();
		lineList=h.sortLineList(lineList);//将水平线集合按yl从小到大排序
		List<RectangleBean> ensureRectList=new ArrayList<RectangleBean>();//已被放入到板中的矩形集合
		for(int j=0;j<rectList.size();j++){//取矩形块的过程
			lineList=h.sortLineList(lineList);//将水平线集合按yl从小到大排序
			int i=0;
			RectangleBean rect=rectList.get(j);
			//将矩形件进行旋转(1表示旋转)
			if(rect.getIsrotate()==1){
				int temp=rect.getWidth();
				rect.setWidth(rect.getLength());
				rect.setLength(temp);
			}
			while(lineList.get(i).getWidel()<rect.getWidth()){//从水平线集合中最低的水平线开始寻找，wid水平线>wid矩形的水平线
			i++;
			}
			Line linec=lineList.get(i);//获得适合的最低水平线
			//放入矩形，并更新水平线集合，包含，选中的水平线，和新增的水平线，更新矩形的坐标
			rect.setX1(linec.getXl1());//设置矩形块的位置x1,x2,y1,y2
			rect.setY1(linec.getYl());
			rect.setX2(rect.getX1()+rect.getWidth());
			rect.setY2(rect.getY1()+rect.getLength());
			ensureRectList.add(rect);//将放入板材的矩形加入该集合
			if(linec.getWidel()==rect.getWidth()){//水平线恰好被占满
				lineList.remove(i);//删除该水平线
			}else{
			linec.setXl1(linec.getXl1()+rect.getWidth());//更新选中的水平线
			}
			int isnew =-1;//判断是需要增加矩形等高的水平线的参数
			for(int m=0;m<lineList.size();m++){//查找水平线集合，判断是否需要增加矩形等高的水平线
					if(lineList.get(m).getYl()==rect.getY2()&&lineList.get(m).getXl1()<=rect.getX1()&&lineList.get(m).getXl2()>=rect.getX2()){//有与该矩形等高的水平线,且该水平线包含矩形的上沿
					isnew=m;//修改isnew 参数
				}
				
			}
			if(isnew==-1){//没有该矩形y轴高度的水平线，或者有等高，但是没有包含该矩形上沿的水平线
				int k=xn;
				int t=0;
				int rectxl2=-1;//限制新增水平线x2的矩形编号
				int rectxl1=-1;//限制新增水平线x1的矩形编号
				for(int e=0;e<ensureRectList.size();e++){//遍历已经放入板中的矩形
					RectangleBean Orect= ensureRectList.get(e);
					if(Orect.getY2()>rect.getY2()&&Orect.getX1()>=rect.getX2()){
						int k1=Orect.getX1();
						if(k1<k){
							k=k1;
							rectxl2=e;
						}
					}
					if(Orect.getY2()>rect.getY2()&&Orect.getX2()<=rect.getX1()){
						int t1=Orect.getX2();
						if(t1>t){
							t=t1;
							rectxl1=e;
						}
					}
					
				}
				if(rectxl2==-1&& rectxl1==-1){//不存在限制该新增水平线xl2和xl1的的矩形
					Line newline1=new Line();
					newline1.setYl(rect.getY2());
					newline1.setXl1(0);
					newline1.setXl2(xn);
					lineList.add(newline1);
				}
				else if(rectxl2!=-1&&rectxl1==-1){//存在限制该新增水平线xl2的的矩形但不存在限制xl1；
					Line newline1=new Line();
					newline1.setYl(rect.getY2());
					newline1.setXl1(0);
					newline1.setXl2(ensureRectList.get(rectxl2).getX1());//将xl2设置成限制该水平线的矩形的x1坐标
					lineList.add(newline1);
				}
				else if(rectxl2==-1&&rectxl1!=-1){//存在限制该新增水平线xl1的的矩形但不存在限制xl2；
					Line newline1=new Line();
					newline1.setYl(rect.getY2());
					newline1.setXl1(ensureRectList.get(rectxl1).getX2());//将xl1设置成限制该水平线的矩形的x2坐标
					newline1.setXl2(xn);
					lineList.add(newline1);
				}
				else if(rectxl2!=-1&&rectxl1!=-1){//存在限制该新增水平线xl1的的矩形同时存在限制xl2的矩形；
					Line newline1=new Line();
					newline1.setYl(rect.getY2());
					newline1.setXl1(ensureRectList.get(rectxl1).getX2());//将xl1设置成限制该水平线的矩形的x2坐标
					newline1.setXl2(ensureRectList.get(rectxl2).getX1());//将xl2设置成限制该水平线的矩形的x1坐标
					lineList.add(newline1);
				}
				
				
			}
			int listnum=lineList.size();
			int k=0;
			while(k<listnum){//新放入的矩形对水平线造成的影响
				boolean flage=false;//方便逻辑判断，两个if都不执行的话，执行k++;
					if(lineList.get(k).getYl()<rect.getY1()){//矩形位于水平线上方
						if(lineList.get(k).getXl1()<rect.getX2()&&lineList.get(k).getXl1()>=rect.getX1()){//矩形影响到了该水平线
							if(lineList.get(k).getXl2()>rect.getX2()){//矩形覆盖了水平线的前半部分
								lineList.get(k).setXl1(rect.getX2());//更新水平线的x1
								k++;
								flage=true;
							}
							else if(lineList.get(k).getXl2()<=rect.getX2()){//上方的矩形完全覆盖了该水平线
								lineList.remove(k);//移除该水平线
								listnum--;
								flage=true;
							}
							
						}
					}
					if(lineList.get(k).getYl()<rect.getY2()&&lineList.get(k).getYl()>=rect.getY1()){//1.水平线位于矩形的中间位置
						if(lineList.get(k).getXl1()<rect.getX1()){//2.水平线发射点位于矩形左侧
							if(lineList.get(k).getXl2()>rect.getX1()){//3.水平线的终点超过了矩形的左测（发射的光线被阻挡了）
								if(lineList.get(k).getXl2()<=rect.getX2()){//4.水平线的终点未超过矩形的右侧
								lineList.get(k).setXl2(rect.getX1());//4.个条件满足水平线被阻挡了，水平线的重点变为阻挡物矩形的最左侧
								k++;
								flage=true;
								}
								else if(lineList.get(k).getXl2()>rect.getX2()){//4.水平线的终点超过了矩形的右侧（中间截断水平线）
									boolean isTwoPart =false;
									for(int n=0;n<ensureRectList.size();n++){//遍历已排入的矩形块，查看分割的水平线的后半段，是否有与水平线等高的矩形，有则后半段，也是一条水平线，没有，则只保留前半段水平线	
										if(ensureRectList.get(n).getY2()==lineList.get(k).getYl()){
											if(ensureRectList.get(n).getX1()<lineList.get(k).getXl2()&&ensureRectList.get(n).getX1()>=rect.getX2()){
												isTwoPart=true;
												break;
											}
										}
									}
									if(isTwoPart){//该矩形将水平线分成了两段，后半段有同高的矩形，故自成一个水平线
										Line newLine=new Line();
										newLine.setXl1(rect.getX2());
										newLine.setXl2(lineList.get(k).getXl2());
										newLine.setYl(lineList.get(k).getYl());
										lineList.add(newLine);//添加后半段新的水平线
										lineList.get(k).setXl2(rect.getX1());//更新前半段水平线的终点
										k++;
										flage=true;
										
									}
									else{//后半段无同高矩形，后半段水平线不存在了
										lineList.get(k).setXl2(rect.getX1());//只更新前半段水平线的终点
										k++;
										flage=true;
									}
								}
							}
							
						}
						if(lineList.get(k).getXl1()>=rect.getX1()&&lineList.get(k).getXl1()<rect.getX2()){//2.2水平线的发射点位于矩形内部
							if(lineList.get(k).getXl2()>rect.getX2()){//3.2水平线的终点超过矩形的右侧
								lineList.get(k).setXl1(rect.getX2());//更新水平线的发射点，为矩形的右侧
								k++;
								flage=true;
							}
							else if(lineList.get(k).getXl2()<=rect.getX2()){//3.3水平线的终点在矩形内部
								lineList.remove(k);//移除水平线
								listnum--;
								flage=true;
							}
							
						}
						
					}
					if(!flage){
						k++;
					}
					
			}
		}
		return ensureRectList;
	}
	public static  double getScore(List<RectangleBean> rectList,SheetBean sheetBean){
		double high=0;
		double  area=0;
		for(RectangleBean emp:rectList){
			if(emp.getY2()>high){
				high=emp.getY2();
			}
			area=area+emp.getLength()*emp.getWidth();
		}
		double score=area/(high*sheetBean.getWidth());
		return score ;
	}
	public  List<Line> sortLineList(List<Line> list){//对水平线从小到大排列
		List<Line> orderlist= new ArrayList<Line>();
		 Object[] beanObjects = list.toArray();  
	     Arrays.sort(beanObjects);  
	     for(int i=0;i<beanObjects.length;i++){
	    	 orderlist.add((Line) beanObjects[i]);
	     }
		return orderlist;
	}
	
}
