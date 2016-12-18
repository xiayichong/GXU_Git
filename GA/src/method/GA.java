package method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import rectangle.bean.GeneBean;
import rectangle.bean.Groupbean;
import rectangle.bean.RectangleBean;
import rectangle.bean.SheetBean;

public class GA {
	//生成宽度为400的排样图边框
	static SheetBean sheetBean=new SheetBean(400);
	static GeneBean goodone=new GeneBean();
	
	public static void GaAction() throws Exception{
	//1.生成初始种群
		int groupSize=118;//种群大小
		int generation=500;//种群最大代数
	Groupbean intiGroupbean =Method.initGroup(groupSize);
	//2.获得初始种群基因
	List<GeneBean> intiGeneList=intiGroupbean.getGeneList();
	//3.对初始种群里个个体通过最低水平线算法，评估适应度
	for(GeneBean emp:intiGeneList){//遍历每个个体
		List<RectangleBean> rectList=HorizontalLine.getGeneScore(emp, sheetBean);
		emp.setScore(HorizontalLine.getScore(rectList, sheetBean));//获得最低水平线之后，当前个体的适应值
	}
	//4.对当前种群，按适应度，从大到小排序
	 intiGeneList=Method.sortGeneBeanList(intiGeneList);
	 intiGroupbean.setGeneList(intiGeneList);//初始种群的基因组，也换成
	 List<RectangleBean> rectList11 =Method.deepCopy(intiGeneList.get(0).getRectList());//则群体的第一个就是适应度最好的
	 goodone.setScore(intiGeneList.get(0).getScore());
	 goodone.setRectList(rectList11);
	 double bestScore =goodone.getScore();
	 System.out.println("(1)"+goodone.getScore());
	 int nowgeneration=1;//目前的代数
	 Groupbean oldGroup=intiGroupbean;//老一代的群体
	//5.设置选择因子，交叉因子，变异因子
		double ps=0.8;//选择因子
		double pc=0.1;//交叉因子
		double pm=0.1;//变异因子
		int noChangeGeneration=0;//最优未未变化代数
	 while(nowgeneration<generation){//循环多少代
	//6.根据各种因子，创建下一代的群体
	Groupbean newGroup=new Groupbean();//新一代的群体
	newGroup.setGeneration(nowgeneration);//设置当前群体代
	List<GeneBean> newGenebeanList =new ArrayList<GeneBean>();//新的群体的所有基因组
	newGroup.setGeneList(newGenebeanList);//将基因组加入新群体组
	int selectedGene =(int) (groupSize*ps);//被选择的基因数量
	int jiaochaGene=(int)(groupSize*pc);//需要交叉的数量
	int bianyiGene=groupSize-selectedGene-jiaochaGene;//需要变异的数量
	//7.选择过程
	for(int i=0;i<selectedGene;i++){//将被选择的基因放入新的群体，基因组是从大到小排序的，故前selectedGene个基因加入新群体即可
		newGenebeanList.add(oldGroup.getGeneList().get(i));
	}
	//8.交叉过程
	List<GeneBean> linshiGenelist=new ArrayList<GeneBean>();//临时存放需要进行交叉的基因组
	for(int i=0;i<jiaochaGene;i++){
		linshiGenelist.add(oldGroup.getGeneList().get(i+selectedGene));
	}
	while(!linshiGenelist.isEmpty()){//遍历linshiGenelist
		int length= linshiGenelist.size();
		if(length==1){
			newGenebeanList.add(linshiGenelist.get(0));
			linshiGenelist.remove(0);
		}else{
		GeneBean firstGene=linshiGenelist.get(0);//取第一个基因
		int randomNum= (int)(Math.random()*(length-1)+1);//随机选择第二个基因的位置
		GeneBean secondGene=linshiGenelist.get(randomNum);//取得第二个基因
		linshiGenelist.remove(randomNum);//从临时队列里移除两个选中的基因,先移除后面的，再移除前面的（原因自己想。。）
		linshiGenelist.remove(0);
		//将两个基因进行环形交叉
		List<RectangleBean> firstList=firstGene.getRectList();
		List<RectangleBean> secondList=secondGene.getRectList();//获得两个基因的基因序列
		int geneLength=firstList.size();//基因的长度
		int s1=(int)(Math.random()*geneLength);//第一个基因位
		int s2=(int)(Math.random()*geneLength);//第二个基因位
		if(s1<s2){//s1在s2的前面，则选取s1-s2的基因进行交叉
			HashSet<Integer> s1Set=new HashSet<Integer>();//用于去重复阶段
			HashSet<Integer> s2Set=new HashSet<Integer>();
			for(int i=s1;i<=s2;i++){//交换 s1和s2之间的
				
				RectangleBean temp=firstList.get(i);
				s2Set.add(temp.getId());
				s1Set.add(secondList.get(i).getId());
				firstList.set(i, secondList.get(i));
				secondList.set(i, temp);
			}
			int k=0;
			for(int i=0;i<geneLength;i++){
				if(i>s2||i<s1){
					if(s1Set.contains(firstList.get(i).getId())){
						for(int j=k;j<geneLength;j++){
							if(j>s2||j<s1){
								if(s2Set.contains(secondList.get(j).getId())){
									RectangleBean temp=firstList.get(i);
									firstList.set(i, secondList.get(j));
									secondList.set(j, temp);
									k=j;
									break;
								}
							}
						}
					}
				}
			}
			
			
			
		}
		else if(s1>s2){//s2在s1前面，这时选取的基因为s1-最后，0-s2
			HashSet<Integer> s1Set=new HashSet<Integer>();//用于去重复阶段
			HashSet<Integer> s2Set=new HashSet<Integer>();
			for(int i=0;i<=s2;i++){
				RectangleBean temp=firstList.get(i);
				s2Set.add(temp.getId());
				s1Set.add(secondList.get(i).getId());
				firstList.set(i, secondList.get(i));
				secondList.set(i, temp);
			}
			for(int i=s1;i<geneLength;i++){
				RectangleBean temp=firstList.get(i);
				s2Set.add(temp.getId());
				s1Set.add(secondList.get(i).getId());
				firstList.set(i, secondList.get(i));
				secondList.set(i, temp);
			}
			int k=0;
			for(int i=0;i<geneLength;i++){
				if(i>s2&&i<s1){
					if(s1Set.contains(firstList.get(i).getId())){
						for(int j=k;j<geneLength;j++){
							if(j>s2&&j<s1){
								if(s2Set.contains(secondList.get(j).getId())){
									RectangleBean temp=firstList.get(i);
									firstList.set(i, secondList.get(j));
									secondList.set(j, temp);
									k=j;
									break;
								}
							}
						}
					}
				}
			}
			
			
		}
		else if(s1==s2){
			RectangleBean temp=firstList.get(s1);
			firstList.set(s1, secondList.get(s1));
			secondList.set(s1, temp);
			if(firstList.get(s1).getId()!=secondList.get(s1).getId()){
				for(int i=0;i<geneLength;i++){
					if(i!=s1&&firstList.get(i).getId()==firstList.get(s1).getId()){
						for(int j=0;j<geneLength;j++){
							if(j!=s1&&secondList.get(j).getId()==secondList.get(s1).getId()){
								RectangleBean temp1=firstList.get(i);
								firstList.set(i,secondList.get(j));
								secondList.set(j, temp1);
							}
						}
					}
				}
			}
		}
		//交叉完毕将基因序列放回基因
		firstGene.setRectList(firstList);
		secondGene.setRectList(secondList);
		//将两个基因放入newGenebeanList
		newGenebeanList.add(firstGene);
		newGenebeanList.add(secondGene);
		}
	}
	//9.变异过程
		for(int i=(groupSize-bianyiGene);i<groupSize;i++){
			GeneBean bean= oldGroup.getGeneList().get(i);
			double random=Math.random();
			if(random<0.5){//进行，交换变异
				int randomset1=(int)(Math.random()*bean.getRectList().size());
				int randomset2=(int)(Math.random()*bean.getRectList().size());
				 RectangleBean temp=bean.getRectList().get(randomset1);
				 bean.getRectList().set(randomset1, bean.getRectList().get(randomset2));
				 bean.getRectList().set(randomset2, temp);
			}else{//进行旋转变异
				int randomset=(int)(Math.random()*bean.getRectList().size());
				int isrotate=bean.getRectList().get(randomset).getIsrotate();
				if(isrotate==0){
					bean.getRectList().get(randomset).setIsrotate(1);
				}else{
					bean.getRectList().get(randomset).setIsrotate(0);
				}
			
				
			}
			newGenebeanList.add(bean);
		}
	//10.对新群体每个个体，测试适应度
		for(GeneBean emp:newGenebeanList){
			List<RectangleBean> rectList=HorizontalLine.getGeneScore(emp, sheetBean);
			emp.setScore(HorizontalLine.getScore(rectList, sheetBean));//获得最低水平线之后，当前个体的适应值
		}
	//11.将群体的个体按适应度从大到小排列
		newGenebeanList=Method.sortGeneBeanList(newGenebeanList);
		newGroup.setGeneList(newGenebeanList);
		
		if(newGroup.getGeneList().get(0).getScore()>bestScore){//新群体有更优的个体了
			//这是在这里对各种因子进行变化
//			double ps=0.8;//选择因子
//			double pc=0.1;//交叉因子
//			double pm=0.1;//变异因子
			if(ps<0.8){
				ps=ps+0.1;
				pc=pc-0.1;
			}
			noChangeGeneration=0;
			 List<RectangleBean> rectList12 =Method.deepCopy(newGenebeanList.get(0).getRectList());//则群体的第一个就是适应度最好的
			 goodone.setScore(newGenebeanList.get(0).getScore());
			 goodone.setRectList(rectList12);
			bestScore=goodone.getScore();
			System.out.println(goodone.getScore());
			
		}
		
		else if(newGenebeanList.get(0).getScore()==goodone.getScore()){//没有更好的个体产生
			//
			if(noChangeGeneration==3){
				ps=0.2;
				pc=0.7;
				pm=0.1;
				noChangeGeneration=0;
			}
			else if(ps>0.2){
				ps=ps-0.1;
				pc=pc+0.1;
				noChangeGeneration++;
				}
			
		}
		
		oldGroup =newGroup;//将新的群体变成老一代。继续下一代循环
		nowgeneration++;
	 }
	
		}
	
	public static void main(String[] args) throws Exception {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");//归并排序解决排序bug
		GaAction() ;
		 System.out.println("最好的是"+goodone.getScore());
		 new MyFrame(goodone,sheetBean);
//		 HorizontalLine.getGeneScore(goodone, sheetBean);
			
	}
}
