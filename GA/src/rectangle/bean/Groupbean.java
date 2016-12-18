package rectangle.bean;

import java.util.List;
/**
 * 种群Bean,包含染色体集合以及进化的代数
 * @author Administrator
 *
 */
public class Groupbean {
private List<GeneBean> geneList;
private int generation;
public List<GeneBean> getGeneList() {
	return geneList;
}
public void setGeneList(List<GeneBean> geneList) {
	this.geneList = geneList;
}
public int getGeneration() {
	return generation;
}
public void setGeneration(int generation) {
	this.generation = generation;
}
@Override
public String toString() {
	return "Groupbean [geneList=" + geneList + ", generation=" + generation
			+ "]";
}




}
