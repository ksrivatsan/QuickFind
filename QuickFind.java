import java.util.*;
import java.io.*;

class QuickFind {
	
	public static TreeMap<Integer,Integer> nodeSet = new TreeMap<Integer,Integer>();
	public static HashMap<Integer,Integer> linksCount = new HashMap<Integer,Integer>();
	
	/* Check if a connection between the two nodes exist */
	public static boolean quickFind(Integer a, Integer b){
		// O(1) - best/worst case
		if(!nodeSet.containsKey(a) && !nodeSet.containsKey(b))
			return false;
		else
			return nodeSet.get(a) == nodeSet.get(b);
	}
	
	/* Add two nodes into the graph and update the link counts*/
	public static void union(Integer nodeA, Integer nodeB){
		// O(n) - worst case
		// O(1) - best case
		updateAddLinkCount(nodeA);
		updateAddLinkCount(nodeB);
		int min = (nodeA < nodeB) ? nodeA : nodeB;
		if(!nodeSet.containsKey(nodeA) && !nodeSet.containsKey(nodeB)){
			nodeSet.put(nodeA,min);
			nodeSet.put(nodeB,min);			
		} else if(nodeSet.containsKey(nodeA) && nodeSet.containsKey(nodeB)){
			int value1 = nodeSet.get(nodeA);
			int value2 = nodeSet.get(nodeB);
			int max = value1 > value2 ? value1 : value2;
			min = value1+value2-max;
			replaceLinks(max, min);
		} else if(nodeSet.containsKey(nodeA)){
			nodeSet.put(nodeB, nodeSet.get(nodeA));
		} else{
			nodeSet.put(nodeA, nodeSet.get(nodeB));
		}
	}
	
	/* Delete nodes from Graph */
	public static void delete(Integer nodeA, Integer nodeB){
		// O(n) - worst case
		// O(1) - best case
		if(linksCount.containsKey(nodeA) && linksCount.containsKey(nodeB)){
			updateRemoveLinkCount(nodeA);
			updateRemoveLinkCount(nodeB);
		}
	}
	
	/* helper methods */

	/* Print all nodes & their links of the graph */
	public static void printLinks(){
		System.out.println("NODES");
		for(Map.Entry<Integer, Integer> e : nodeSet.entrySet()){
			System.out.println(e.getKey() + " - " + e.getValue());
		}
		System.out.println("");
		System.out.println("LINKS");
		for(Map.Entry<Integer, Integer> e : linksCount.entrySet()){
			System.out.println(e.getKey() + " - " + e.getValue());
		}
	}
	
	/* Update the link count on addition of Nodes */
	public static void updateAddLinkCount(Integer node){
		// O(1) - best/worst case
		if(linksCount.containsKey(node)){
			linksCount.put(node,linksCount.get(node)+1);
		} else {
			linksCount.put(node,1);
		}
	}
	
	/* Update the link count on deletion of Nodes */
	public static void updateRemoveLinkCount(Integer node){
		if(linksCount.get(node) > 1){
			linksCount.put(node, linksCount.get(node)-1);
		} else {
			linksCount.remove(node);
			removeNodeAssociation(node);
		}
	}
	
	/* Update the nodes to point to the new value on deletion of old value */
	public static void replaceLinks(int oldValue, int newValue){
		// O(n) - average case
		for(Map.Entry<Integer, Integer> e : nodeSet.entrySet()){
			if(e.getValue() == oldValue)
				nodeSet.put(e.getKey(), nodeSet.get(newValue));
		}
	}

	/* Remove the node association from the Graph */
	public static void removeNodeAssociation(Integer node){
		// O(n) - best/worst case
		int value = nodeSet.get(node);
		nodeSet.remove(node);
		int key;
		for(Map.Entry<Integer, Integer> e : nodeSet.entrySet()){
			if(e.getValue() == value){
				key = e.getKey();
				replaceLinks(value, key);
				break;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		FileReader f = new FileReader(".//input.txt");
		BufferedReader br = new BufferedReader(f);
		String line;
		int a,b;
		while((line = br.readLine()) != null){
			String[] line_array = line.split(" ");
			if(line_array.length > 3 && (line_array[0] + line_array[1]).equals("islinked")){
				a = Integer.parseInt(line_array[2]);
				b = Integer.parseInt(line_array[3]);
				System.out.println(quickFind(a, b));
			} else if(line_array.length == 3){
				if(line_array[0].equals("add")){
					a = Integer.parseInt(line_array[1]);
					b = Integer.parseInt(line_array[2]);
					union(a, b);
				} else if(line_array[0].equals("remove")){
					a = Integer.parseInt(line_array[1]);
					b = Integer.parseInt(line_array[2]);
					delete(a, b);
				}
			}
		}
	}
}