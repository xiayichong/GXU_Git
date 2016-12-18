package rectangle.bean;
/**
 * 最低水平线Bean
 * @author Administrator
 *
 */
public class Line implements Comparable<Line>{
private int yl;
private int xl1;
private int xl2;
private int widel;
public int getYl() {
	return yl;
}
public void setYl(int yl) {
	this.yl = yl;
}
public int getXl1() {
	return xl1;
}
public void setXl1(int xl1) {
	this.xl1 = xl1;
}
public int getXl2() {
	return xl2;
}
public void setXl2(int xl2) {
	this.xl2 = xl2;
}
public int getWidel() {
	return xl2-xl1;
}
public void setWidel(int widel) {
	this.widel = widel;
}
@Override
public int compareTo(Line o) {
	// TODO Auto-generated method stub
	if(this.getYl()>o.getYl()){
	return 1;
	}else{
	return -1;
	}
}
@Override
public String toString() {
	return "Line [yl=" + yl + ", xl1=" + xl1 + ", xl2=" + xl2 + ", widel="
			+ widel + "]";
}

}
