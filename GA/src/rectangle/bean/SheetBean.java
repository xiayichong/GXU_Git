package rectangle.bean;

/**
 * 排样图边界Bean
 * @author Administrator
 *
 */
public class SheetBean {
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
private int length;
private int width;
public SheetBean(int width){
	this.width=width;
}
}
