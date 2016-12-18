package rectangle.bean;

import java.io.Serializable;
/**
 * 矩形毛坯Bean
 * @author Administrator
 *
 */
public class RectangleBean implements Serializable{
private int id;	//毛坯编号
private int length;//毛坯长度
private int width; //毛坯宽度
private int x1;	
private int y1;
private int x2;
private int y2;
private int isrotate;//是否旋转

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getLength() {
	return length;
}

public void setLength(int length) {
	this.length = length;
}

public int getWidth() {
	return width;
}

public void setWidth(int width) {
	this.width = width;
}

public int getX1() {
	return x1;
}

public void setX1(int x1) {
	this.x1 = x1;
}

public int getY1() {
	return y1;
}

public void setY1(int y1) {
	this.y1 = y1;
}

public int getX2() {
	return x2;
}

public void setX2(int x2) {
	this.x2 = x2;
}

public int getY2() {
	return y2;
}

public void setY2(int y2) {
	this.y2 = y2;
}

public int getIsrotate() {
	return isrotate;
}

public void setIsrotate(int isrotate) {
	this.isrotate = isrotate;
}

@Override
public String toString() {
	return "RectangleBean [id=" + id + ", length=" + length + ", width="
			+ width + ", x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2
			+ ", isrotate=" + isrotate + "]";
}



}
