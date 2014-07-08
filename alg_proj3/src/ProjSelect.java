import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class ProjSelect {   
	private static Project[] projs;
	private static BufferedReader read;
	private static Random randomGenerator;
	private static int size;
	private static int posProfits; // total positive profits

	public static void main(String[] args) {

		read = new BufferedReader(new InputStreamReader(System.in));
		randomGenerator = new Random();

		try
		{
			System.out.print("Please enter number of projects : ");
			size = Integer.parseInt(read.readLine());

			// create array
			projs = new Project[size];
			posProfits = 0;

			// Creating projects 
			for(int i=0; i<size; i++) {
				int x = randomGenerator.nextInt(50*size);
				x -= 50*(size-i-1);
				if (x > 0) posProfits += x;
				Project p = new Project(i+1, x);
				if (i > 0) {
					int n = randomGenerator.nextInt(Math.min(i, 3))+1;
					// System.out.println("n = "+n);
					for (int j = 0; j < n; j++) {
						x = randomGenerator.nextInt(i);
						if (p.predecessor.contains(projs[x]) == false) p.predecessor.add(projs[x]);
					}
				}
				projs[i] = p;
				if (size <= 30) p.display();
			}
			System.out.println("The sum of all positive profits = " + posProfits);
		} catch(Exception ex){
			ex.printStackTrace();
		}

		// How to select a subset of projects such that the total profits of all selected
		// projects is maximal ? 
		// Note that if a project is selected, then all its predecessors will be selected.

		int source = 0; 
		int sink = size+1;

		//create flow network with space for all projects, source, and sink
		FlowNetwork G = new FlowNetwork(size+2);

		for(int i=0; i < size; i++)
		{
			//iterate through predecessors for each project
			List<Project> preList = projs[i].predecessor;

			//set the predecessor edge weight as a very high value
			for(int j =0; j< preList.size(); j++) G.addEdge(new FlowEdge(projs[i].label, preList.get(j).label, posProfits+1));

			//connect the project to the source if cost is positive
			if(projs[i].profit > 0)
				G.addEdge(new FlowEdge(source, projs[i].label, projs[i].profit));
			//connect the sink to the project if the cost is negative
			else
				G.addEdge(new FlowEdge(projs[i].label, sink, -projs[i].profit));
		}

		//the Ford-Fulkerson algorithm runs and finds the maximum flow
		FordFulkerson ford = new FordFulkerson(G, source, sink);
		int optProfit = 0;

		//simply check the algorithm for the projects in the max cut
		for(int i = 0; i < size; i++)
		{
			if(ford.inCut(projs[i].label))
			{
				System.out.println(projs[i].toString() + " is a good choice from the list");
				optProfit += projs[i].profit;
			}
		}
		//show the solution
		System.out.println("The highest possible profit is "+ optProfit);
	}
}
