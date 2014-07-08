/*
 * Definition of a Project to be used with the ProjSelect class.
 * 
 * The profit field can be negative, which means the project is  
 * a loss
 * 
 * The predecessor field is a list of upto three projects the 
 * current project depends on.
 *
 */
import java.util.*;

public class Project implements Comparable<Project> {
	public int label;  
	public int profit;
	public List<Project> predecessor; 

	public Project(int v){
		label = v;
		predecessor = new ArrayList<Project>(3);
		profit = 0;
	}

	public Project(int v, int p){
		label = v;
		predecessor = new ArrayList<Project>(3);
		profit = p;
	}
	
	public String toString(){ 
		return "  P"+label; 
	}
	
	public void display(){
		System.out.print("  P"+label+" profit = "+ profit +" predecessors:"); 
		for (int i = 0; i < predecessor.size(); i++) System.out.print(predecessor.get(i));
		System.out.println();
	}
	
	public int compareTo(Project other){
		if (label > other.label) return 1;
		else if (label == other.label) return 0;
		else return -1;
	}
}
