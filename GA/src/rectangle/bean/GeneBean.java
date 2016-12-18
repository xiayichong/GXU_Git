package rectangle.bean;

import java.util.List;
/**
 * 染色体Bean
 * @author Administrator
 *
 */
public class GeneBean implements Comparable<GeneBean>{
private List<RectangleBean> rectList;	//基因
private double score;//适应度函数值
public List<RectangleBean> getRectList() {
	return rectList;
}
public void setRectList(List<RectangleBean> rectList) {
	this.rectList = rectList;
}
public double getScore() {
	return score;
}
public void setScore(double score) {
	this.score = score;
}
@Override
public String toString() {
	return "GeneBean [rectList=" + rectList + ", score=" + score + "]";
}
//适应度函数值越大，就越排在前面
@Override
public int compareTo(GeneBean o) {
	if(this.score>o.score){
		return -1;
	}else{
		return 1;
	}
}


}
