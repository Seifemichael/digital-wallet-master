// example of program that detects suspicious transactions
// fraud detection algorithm
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class antifraud {
	public static final boolean isValidTimeStamp(String timeStamp) {		
		//System.out.println(timeStamp.matches("\\d{4}-[01]\\d-[0-3]\\d\\s[0-2][0-3]\\:[0-5]\\d\\:[0-5]\\d"));
		//System.out.println(timeStamp.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}\\:\\d{2}\\:\\d{2}"));
		//System.out.println(timeStamp);
	    if (timeStamp == null || !timeStamp.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}\\:\\d{2}\\:\\d{2}"))
	        return false;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    try{	        
	        dateFormat.parse(timeStamp);
	        return true;
	       
	    } catch (ParseException e) {
	        return false;
	    }
	    
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//./paymo_input/batch_payment.txt ./paymo_input/stream_payment.txt ./paymo_output/output1.txt ./paymo_output/output2.txt ./paymo_output/output3.txt
		
		
		
		Graph myGraph = new Graph();
		try{
			String currentdir = new java.io.File("").getAbsolutePath();
			System.out.println(currentdir);
			System.out.println(currentdir+"\\paymo_input\\batch_payment.txt");
			FileInputStream fstream1= new FileInputStream (currentdir+"\\paymo_input\\batch_payment.txt");
			
			DataInputStream in = new DataInputStream(fstream1);
			BufferedReader br = new BufferedReader (new InputStreamReader(in));
			String Line;
			/*while((Line=br.readLine())!=null){
				System.out.println(Line);
			}*/
/*		 for(int i=0;i<4;i++ ){
				Line=br.readLine();
				if(Line!=null){
				//System.out.println(Line);
				String[] tokens = Line.split(",");	
				System.out.println(tokens[0]+";;;"+tokens[1]+";;;"+tokens[2]);
				if(isValidTimeStamp(tokens[0])){
					if(tokens.length>=3){
						if(i!=0)
							myGraph.addEdge(Integer.valueOf(tokens[1].replaceAll("[^0-9.]", "")),Integer.valueOf(tokens[2].replaceAll("[^0-9.]", "")));
						}
					}
				}
			}	*/
/*			Line=br.readLine();
			while(Line!=null){
				Line=br.readLine();
				if(Line!=null){
				System.out.println(Line);
				String[] tokens = Line.split(",");
				if(tokens.length>=3){	
							System.out.println(tokens[1]+" ;;; "+tokens[2]);
							myGraph.addEdge(Integer.valueOf(tokens[1].replaceAll("[^0-9.]", "")),Integer.valueOf(tokens[2].replaceAll("[^0-9.]", "")));				
				}
				}
				
			}*/
 		 
 		 Line=br.readLine();
 		 while(Line!=null){
				Line=br.readLine();
				if(Line!=null){
				System.out.println(Line);
				String[] tokens = Line.split(",");
				//System.out.println(isValidTimeStamp(tokens[0]));
				if(isValidTimeStamp(tokens[0])){
					if(tokens.length>=3){
						myGraph.addEdge(Integer.valueOf(tokens[1].replaceAll("[^0-9.]", "")),Integer.valueOf(tokens[2].replaceAll("[^0-9.]", "")));
						}
					}
				}
				
			}
			
			br.close();
			in.close();
			fstream1.close();
			
		} catch (IOException e){
			System.err.println("Error: in reading original file" + e.getMessage());
		}
		
/*
		myGraph.addEdge(0, 1);
		myGraph.addEdge(1, 0);
		myGraph.addEdge(0, 3);
		myGraph.addEdge(1, 1);
		myGraph.addEdge(2, 3);
		myGraph.addEdge(3, 1);*/
		//myGraph.printGraph();
		myGraph.writeGraph();
		
		try{
			String currentdir = new java.io.File("").getAbsolutePath();
			System.out.println(currentdir);
			FileInputStream fstream1= new FileInputStream (currentdir+"\\paymo_input\\stream_payment.txt");
			
			DataInputStream in = new DataInputStream(fstream1);
			BufferedReader br = new BufferedReader (new InputStreamReader(in));
			
			// Writers 
			FileOutputStream fostream1 = new FileOutputStream (currentdir+"\\paymo_output\\output1.txt");
			FileOutputStream fostream2= new FileOutputStream (currentdir+"\\paymo_output\\output2.txt");
			FileOutputStream fostream3= new FileOutputStream (currentdir+"\\paymo_output\\output3.txt");
			
			OutputStreamWriter osw1 = new OutputStreamWriter(fostream1); 
			OutputStreamWriter osw2 = new OutputStreamWriter(fostream2);
			OutputStreamWriter osw3 = new OutputStreamWriter(fostream3); 
			BufferedWriter w1 = new BufferedWriter(osw1);
			BufferedWriter w2 = new BufferedWriter(osw2);
			BufferedWriter w3 = new BufferedWriter(osw3);
			
			String Line;			

	       
			
			
 		 Line=br.readLine();
 		 while(Line!=null){
				Line=br.readLine();
				if(Line!=null){
				//System.out.println(Line);
				String[] tokens = Line.split(",");
				//System.out.println(isValidTimeStamp(tokens[0]));
				if(isValidTimeStamp(tokens[0])){
					if(tokens.length>=3){
						
						if(!myGraph.BFSWithHop(Integer.valueOf(tokens[1].replaceAll("[^0-9.]", "")),Integer.valueOf(tokens[2].replaceAll("[^0-9.]", "")),1))
							{
							System.out.println("unverified: You've never had a transaction with this user before. Are you sure you would like to proceed with this payment?");
							w1.write("unverified"+"\n");
							}
						else{
							System.out.println("trusted");
							w1.write("trusted"+"\n");
							}
						
						if(!myGraph.BFSWithHop(Integer.valueOf(tokens[1].replaceAll("[^0-9.]", "")),Integer.valueOf(tokens[2].replaceAll("[^0-9.]", "")),2))
							{
							System.out.println("unverified: This user is not a friend or a \"friend of a friend\". Are you sure you would like to proceed with this payment?");
							w2.write("unverified"+"\n");
							}
						
						else{
							System.out.println("trusted");
							w2.write("trusted"+"\n");
							}
					
						if(!myGraph.BFSWithHop(Integer.valueOf(tokens[1].replaceAll("[^0-9.]", "")),Integer.valueOf(tokens[2].replaceAll("[^0-9.]", "")),4))
							{
							System.out.println("unverified: This user is not a friend or a \"friend of a friend\". Are you sure you would like to proceed with this payment?");
							w3.write("unverified"+"\n");
							}
						
						else{
							System.out.println("trusted");
							w3.write("trusted"+"\n");
							}
						
						}
					}
				}
				
			}
			
			br.close();
			in.close();
			fstream1.close();
			
	        
        w1.close();
        osw1.close();
        fostream1.close();
        
	    w2.close();
	    osw2.close();
	    fostream2.close();
        
	    w3.close();
	    osw3.close();
	    fostream3.close();
			
		} catch (IOException e){
			System.err.println("Error: in reading original file" + e.getMessage());
		}		
		
		
	}

}

class Node{
	
	private int data;
	private boolean visited=false;
	
	public Node(int d, boolean v){
		this.data=d;
		this.visited=v;
	}
	public int get(){
		return this.data;
	}
	public boolean isVisited(){
		return this.visited;
	}
}
class Graph {
	private HashMap<Integer, List<Integer>> adjList;

	public Graph(){
		 adjList = new HashMap<Integer,List<Integer>> ();		
	}	

	public void addEdge(int source, int destination){
		if(source!=destination) {
		if(adjList.containsKey(source)){
			List<Integer> myList=adjList.get(source);	
			if(!myList.contains(destination))
				myList.add(destination);
		//	adjList.put(source, myList);
		}
		else{
			List<Integer> myList= new LinkedList<Integer>();		
			myList.add(destination);
			adjList.put(source, myList);
		}
		
	}
	}
	
	public void printGraph(){
		for(Integer myKey: adjList.keySet()){
			System.out.print(myKey + "->");
			for(Integer value:adjList.get(myKey)){
					System.out.print(value + "-->");
			}
			System.out.println();
		}
	}
	
	public void writeGraph(){
		
		String currentdir = new java.io.File("").getAbsolutePath();
		
		try {			
			System.out.println(currentdir+ "in writing the graph to batch_payment_graph.txt");
			FileOutputStream fostream= new FileOutputStream (currentdir+"\\paymo_input\\batch_payment_graph.txt");
			OutputStreamWriter osw = new OutputStreamWriter(fostream); 
			BufferedWriter w = new BufferedWriter(osw);
			
			String Line=null;
			for(Integer myKey: adjList.keySet()){
				Line=myKey + "->";
				//System.out.print(myKey + "->");
				for(Integer value:adjList.get(myKey)){
					Line=Line + value + "-->";
					//System.out.print(value + "-->");
				}
				Line = Line + "\n";
				//System.out.println();
				 w.write(Line);
			}				        
	        w.close();
	        osw.close();
	        fostream.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file batch_payment_graph.txt");
        }	
		
		System.out.println("Writing the graph to " + currentdir + "\batch_payment_graph.txt is successful");
		
/*		try{
			String currentdir = new java.io.File("").getAbsolutePath();
			System.out.println(currentdir+" in reading the written file batch_payment_graph.txt ");
			FileInputStream fstream1= new FileInputStream (currentdir+"\\paymo_input\\batch_payment_graph.txt");
			
			DataInputStream in = new DataInputStream(fstream1);
			BufferedReader br = new BufferedReader (new InputStreamReader(in));
			String Line;
			while((Line=br.readLine())!=null){
				System.out.println(Line);
			}	
			
			br.close();
			in.close();
			fstream1.close();
			
		} catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}*/
		
	}
	
	public boolean BFSWithHop(int source, int destination, int maxHops){
		if(source==destination) 
			return true;
		else{			
							
			if(adjList.containsKey(source)){
				HashMap<Integer, Integer> hops = new HashMap<>();
				hops.put(source,0);
				LinkedList<Node> myQueue = new LinkedList<Node>();
				Node nn=new Node(source,true);
				myQueue.addLast(nn);
				
				Node parent=null;
				
				while(!myQueue.isEmpty()){			
			
						parent = myQueue.removeFirst();
					    if(hops.containsKey(parent.get()) && hops.get(parent.get()) == maxHops){
					    	return false;					    	
					    }
					    
						for(Integer value:adjList.get(parent.get())){
							if(value==destination)
								return true;
							Node child=new Node(value,true);
							myQueue.addLast(child);	
							hops.put(value, hops.get(parent.get()) + 1);
							}						
						}					
					
				}				
			if (adjList.containsKey(destination)){
				int tmp=source;
				source = destination;
				destination=tmp;
				
				HashMap<Integer, Integer> hops = new HashMap<>();
				hops.put(source,0);
				LinkedList<Node> myQueue = new LinkedList<Node>();
				Node nn=new Node(source,true);
				myQueue.addLast(nn);
				
				Node parent=null;
				
				while(!myQueue.isEmpty()){			
			
						parent = myQueue.removeFirst();
					    if(hops.containsKey(parent.get()) && hops.get(parent.get()) == maxHops){
					    	return false;					    	
					    }
					    
						for(Integer value:adjList.get(parent.get())){
							if(value==destination)
								return true;
							Node child=new Node(value,true);
							myQueue.addLast(child);	
							hops.put(value, hops.get(parent.get()) + 1);
							}
						
						}					
					
				}
			else 
					return false;
				
			}	
		return false;					
	}	

}
