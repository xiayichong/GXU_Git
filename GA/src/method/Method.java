package method;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import rectangle.bean.*;
public class Method {
public static Groupbean initGroup(int groupsize) throws Exception{//随机生成初始群体
	Groupbean groupbean=new Groupbean();
	Method method =new Method();
	//从TXT文件中读取一组板材数据，存到list集合中
	List<RectangleBean> rectList=method.getRectangleListByreadTxtFile("E:\\src\\rectangle.txt");
	List<GeneBean> geneList=new ArrayList<GeneBean>();
	for(int i=1;i<=groupsize;i++){
		List<RectangleBean> rectListcopy=Method.deepCopy(rectList);
		
		Collections.shuffle(rectListcopy);//随机打乱基因序列生成新的基因序列
		for(int j=0;j<rectListcopy.size();j++){//50%概率90度旋转
		double k=Math.random();
		if(k<=0.5){
			rectListcopy.get(j).setIsrotate(1);//90度旋转属性
		}else{
			rectListcopy.get(j).setIsrotate(0);
		}
		}
		GeneBean geneBean=new GeneBean();
		geneBean.setRectList(rectListcopy);//生成基因
		geneList.add(geneBean);
	}
groupbean.setGeneList(geneList);
groupbean.setGeneration(1);//设置为初代
	return groupbean;
}


public static  List<RectangleBean> getRectangleListByreadTxtFile(String filePath){//读取毛坯文件
	 List<RectangleBean> beans=new ArrayList<RectangleBean>();
    try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int id=1;
                lineTxt = bufferedReader.readLine();
                while(lineTxt != null&&!lineTxt.equals("")){
                String attribute[] = lineTxt.split(",");
                int t=Integer.parseInt(attribute[2]);
                for(int i=0;i<t;i++){
                	RectangleBean rec=new RectangleBean();
                	rec.setId(id++);
                	rec.setLength(Integer.parseInt(attribute[0]));
                	rec.setWidth(Integer.parseInt(attribute[1]));
                	beans.add(rec);
                }
                lineTxt = bufferedReader.readLine();
                }
                read.close();
    }else{
        System.out.println("找不到指定的文件");
    }
    } catch (Exception e) {
        System.out.println("读取文件内容出错");
        e.printStackTrace();
    }
    return beans;
 
}
public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  //深克隆
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
    out.writeObject(src);  
                              
    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
    ObjectInputStream in = new ObjectInputStream(byteIn);  
    @SuppressWarnings("unchecked")  
    List<T> dest = (List<T>) in.readObject();  
    return dest;  
}  

public static List<GeneBean> sortGeneBeanList(List<GeneBean> list){//对基因list按从大到小排列
	List<GeneBean> orderlist= new ArrayList<GeneBean>();
	Object[] beanObjects=list.toArray();
	Arrays.sort(beanObjects);
	for(int i=0;i<beanObjects.length;i++){
		orderlist.add((GeneBean) beanObjects[i]);
	}
	return orderlist;
}
//public  static void main( String arg[]){
//			
//			GeneBean Gbean=new GeneBean();
//			SheetBean sheetBean=new SheetBean(10);
//			sheetBean.setWidth(10);
//			List<RectangleBean> rectList=getRectangleListByreadTxtFile("E:\\src\\rectangle.txt");
//			Gbean.setRectList(rectList);
//			 List<RectangleBean> ensureReList=HorizontalLine.getGeneScore(Gbean, sheetBean);
//			 new MyFrame(Gbean,sheetBean);
//			 int high=0;
//			 for(int i=0;i<ensureReList.size();i++){
//				 	if(ensureReList.get(i).getY2()>high){
//				 		high=ensureReList.get(i).getY2();
//				 	}
//				 	System.out.println("ID:"+ensureReList.get(i).getId()+",x1:"+ensureReList.get(i).getX1()+",x2:"+ensureReList.get(i).getX2()+",y1:"+ensureReList.get(i).getY1()+",y2:"+ensureReList.get(i).getY2());
//			 }
//			 System.out.println("所用的高度为："+high);
//			
//}
}
