import java.util.*;
import java.util.Map.Entry;
import java.io.*;
class SimpleEntry<K,V> implements Map.Entry<K,V>
{
	K key;
	V value;
	public SimpleEntry(K key,V value)
	{
		this.key=key;
		this.value=value;
	}
	
	
	public K getKey() {
		// TODO Auto-generated method stub
		return this.key;
		
	}


	public V getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	
	public Object setValue(Object value) {
		// TODO Auto-generated method stub
		return new SimpleEntry(key,value);
	}
	
}
class Node
{
public String state;
String Depth,Value;
Node parent;
public Node()
{
	state="Node";
	Depth="Depth";
	Value="Value";
	parent=null;
}
public Node(Node n)
{
state=n.state;
Depth=n.Depth;
Value=n.Value;
parent=null;
}
public Node(String state, String Depth, String Value,Node parent)
{
	this.state=state;
	this.Depth=Depth;
	this.Value=Value;
	this.parent=parent;
}
public String toString()
{
	return String.format(state+","+Depth+","+Value);
}

}
class NodeP
{
	public String state;
	public String Depth,Value;
	public String alpha,beta;
	public NodeP parent;
	public NodeP()
	{
		state="Node";
		Depth="Depth";
		Value="Value";
		alpha="Alpha";
		beta="Beta";
		parent=null;
	}
public NodeP(NodeP n)
{
state=n.state;
Depth=n.Depth;
Value=n.Value;
alpha=n.alpha;
beta=n.beta;
parent=null;
}
public NodeP(String state, String Depth, String Value,String alpha,String beta,NodeP parent)
{
	this.state=state;
	this.Depth=Depth;
	this.Value=Value;
	this.alpha=alpha;
	this.beta=beta;
	this.parent=parent;
}

public String toString()
{
	return String.format(state+","+Depth+","+Value+","+alpha+","+beta);
}

}

public class agent 
{
	
public static void main(String args[])
{
new game();	

}
}
class game
{
	int passedTwice=0;
LinkedList<Node> log=new LinkedList<Node>();
LinkedList<NodeP> logP=new LinkedList<NodeP>();
private int mode,cuttingOffDepth;					/*mode in which the game is going to be played*/
private char adj[][]=new char[8][8];/*The matrix which indicates the initial state of the game to be played*/
private char player,opposition;	/*X or O*/
private int evalValues[][]={
		{99,-8,8,6,6,8,-8,99},
		{-8,-24,-4,-3,-3,-4,-24,-8},
		{8,-4,7,4,4,7,-4,8},
		{6,-3,4,0,0,4,-3,6},
		{6,-3,4,0,0,4,-3,6},
		{8,-4,7,4,4,7,-4,8},
		{-8,-24,-4,-3,-3,-4,-24,-8},
		{99,-8,8,6,6,8,-8,99}
				
};
List<Map.Entry<Integer, Integer>> indexListForX=new ArrayList<Map.Entry<Integer,Integer>>();
List<Map.Entry<Integer, Integer>> indexListForO=new ArrayList<Map.Entry<Integer,Integer>>();

public game()
	{
		inputInitialData(adj);
		char [][]permanent = new char[8][8];
		if(player=='X')opposition='O'; else opposition='X';
		//printInfo(); 		/*Checking function*/
		//printPossibleMoves(computePossibleMoves(adj,player));
		//computePossibleMoves(adj,player);
		//adj=makeMove(adj,player,new AbstractMap.SimpleEntry<Integer, Integer>(2,7));
		//player='X';
		//opposition='O';
		//printPossibleMoves(computePossibleMoves(adj,player));
		//computePossibleMoves(adj,player);
		//System.out.println(calculateVal(adj,player));
		//printInfo();
		//adj=makeMove(adj,player,new AbstractMap.SimpleEntry<Integer, Integer>(6,1));
		//printInfo();

	//	greedy();
		copy(adj,permanent);
	//	printInfo();
		if(mode==1)
			{if(1==greedy())solutionG(adj);}
			
		else if(mode==2)
			
			{
			//System.out.println(minimax(adj,cuttingOffDepth));
			Entry<Integer,Integer> e=minimax(adj,0);
			//System.out.println("Values="+e.getKey()+e.getValue());
			if(e==null) {}
			else if(e.getKey()==-1 && e.getValue()==-1){}
			else	copy(makeMove(permanent,player,e),adj);
			
			solutionM(log, adj);
			//printInfo();

			}
		else
			{
	//	System.out.println("ALPHA");
			Entry<Integer,Integer> e=Alpha_Beta_Search(adj,0);
			if(e==null) {}
			else if(e.getKey()==-1 && e.getValue()==-1){}
			else	copy(makeMove(permanent,player,e),adj);
			solutionA(logP, adj);
			}
	
	}

private String[] printPossibleMoves(
		List<Entry<Integer, Integer>> computePossibleMoves) {
	String s[]=new String[computePossibleMoves.size()];
	System.out.println("The possible valid positions for current state are: ");
	for(int i=0;i<computePossibleMoves.size();i++)
	{
		
		s[i]=Character.toString((char)(97+computePossibleMoves.get(i).getValue().intValue()))+(computePossibleMoves.get(i).getKey().intValue()+1);
		//System.out.println(s[i]);
	}
	return s;
	// TODO Auto-generated method stub
	
}
String getState(Map.Entry<Integer, Integer> computePossibleMoves)
{
	return Character.toString((char)(97+computePossibleMoves.getValue().intValue()))+(computePossibleMoves.getKey().intValue()+1);
}

private void copy(char [][]a,char[][]b)
{
for(int i=0;i<8;i++)
	for(int j=0;j<8;j++)
		b[i][j]=a[i][j];
	

}// copy a to b
private char[][] makeMove(char [][]adj,char player,Map.Entry<Integer, Integer> pair)
{
	/* Makes a move according to the player and the indices given. Also, uses
	 * the direction to traverse through which is expected to be evaluated by
	 * another function.
	 * */
	char opposition;
	if(player=='X') opposition='O';
	else opposition='X';
	//System.out.println("From make move with player="+player+"and oppostion="+opposition);
	/*for(int i=0;i<8;i++){
		for(int j=0;j<8;j++)
			System.out.print(adj[i][j]);
		System.out.println();}*/
			
	int i=pair.getKey().intValue(),j=pair.getValue().intValue();
	
	adj[i][j]=player;
	char [][] temp=new char[8][8];
	copy(adj,temp);
	for(int k=1;k<=8;k++)
	{ 
		int x=i,y=j,traced=0;
		switch(k)
		{
		case 1: 
			if(isValid(x,y-1))
			if(temp[x][--y]==opposition)
			{
			temp[x][y--]=player;
			if(!isValid(x,y))
			{
				copy(adj,temp);
				break;
			}
			while(isValid(x,y))
			{
				if(temp[x][y]=='*')
				{
					copy(adj,temp);
					//System.out.println("here");
					break;
				}
				if(temp[x][y]==player)
				{
					/*This means that it is a valid move for position i and j*/
				//	System.out.println("here");
					copy(temp,adj);
					traced=1;
					//possibleMoves.add(new AbstractMap.SimpleEntry<Integer, Integer>(x,y));
					//found=1;
					//System.out.println("Added postion="+possibleMoves.get(possibleMoves.size()-1));
					break;
				}
				
				else if(temp[x][y--]==opposition)
				{
					if(isValid(x,y))
						{
						temp[x][y+1]=player;
						continue;}
					else {
						copy(adj,temp);
						break;} /*because it is not a valid move anymore*/
					
				}
								
				
			}
		}
			if(traced==0)
				copy(adj,temp);    // This means it has found a path and successu
			else traced=0;
				break;
		case 2:
			if(isValid(x-1,y-1))
			if(temp[--x][--y]==opposition)
		{
		temp[x--][y--]=player;
		if(!isValid(x,y))
		{
			copy(adj,temp);
			break;
		}
		while(isValid(x,y))
		{
			if(temp[x][y]=='*')
			{
				copy(adj,temp);
				break;
			}

			if(temp[x][y]==player)
			{
				/*This means that it is a valid move for position i and j*/
				copy(temp,adj);
				traced=1;
				//possibleMoves.add(new AbstractMap.SimpleEntry<Integer, Integer>(x,y));
				//found=1;
				//System.out.println("Added postion="+possibleMoves.get(possibleMoves.size()-1));
				break;
			}
			
			else if(temp[x--][y--]==opposition)
			{
				if(isValid(x,y))
					{
					temp[x+1][y+1]=player;
					continue;}
				else {
					copy(adj,temp);
					break;} /*because it is not a valid move anymore*/
				
			}	
			else {
				copy(adj,temp);
				break;}

			
		}
	}
		if(traced==0)
	copy(adj,temp);    // This means it has found a path and successu
		else traced=0;

			
			break;
			
		case 3:
			if(isValid(x-1,y))
			if(temp[--x][y]==opposition)
		{
		temp[x--][y]=player;
		if(!isValid(x,y))
		{
			copy(adj,temp);
			break;
		}
		while(isValid(x,y))
		{
			if(temp[x][y]=='*')
			{
				copy(adj,temp);
				break;
			}

			if(temp[x][y]==player)
			{
				/*This means that it is a valid move for position i and j*/
				copy(temp,adj);
				traced=1;
				//possibleMoves.add(new AbstractMap.SimpleEntry<Integer, Integer>(x,y));
				//found=1;
				//System.out.println("Added postion="+possibleMoves.get(possibleMoves.size()-1));
				break;
			}
			
			else if(temp[x--][y]==opposition)
			{
				if(isValid(x,y))
					{
					temp[x+1][y]=player;
					continue;}
				else {
					copy(adj,temp);
					break;} /*because it is not a valid move anymore*/
				
			}	
			else {
				copy(adj,temp);
				break;}

			
		}
	}
		if(traced==0)
	copy(adj,temp);    // This means it has found a path and successu
		else traced=0;

			break;
			
		case 4:
			//System.out.println("Entered case 4 with player="+player+"opposiotn="+opposition+"temp[x][y]="+x+y);

			if(isValid(x-1,y+1))
			if(temp[--x][++y]==opposition)
		{
				//System.out.println("Entered case 4 with player="+player+"opposiotn="+opposition+"temp[x-1][y+1]="+temp[x-1][y+1]);
		temp[x--][y++]=player;
		if(!isValid(x,y))
		{
			copy(adj,temp);
			break;
		}
		while(isValid(x,y))
		{
			if(temp[x][y]=='*')
			{
				copy(adj,temp);
				break;
			}

			if(temp[x][y]==player)
			{
				//System.out.println("done");
				/*This means that it is a valid move for position i and j*/
				copy(temp,adj);
				traced=1;
				//possibleMoves.add(new AbstractMap.SimpleEntry<Integer, Integer>(x,y));
				//found=1;
				//System.out.println("Added postion="+possibleMoves.get(possibleMoves.size()-1));
				break;
			}
			
			else if(temp[x--][y++]==opposition)
			{
				if(isValid(x,y))
					{
					temp[x+1][y-1]=player;
					continue;}
				else {
					copy(adj,temp);
					break;} /*because it is not a valid move anymore*/
				
			}	
			else {
				copy(adj,temp);
				break;}

			
		}
	}
		if(traced==0)
	copy(adj,temp);    // This means it has found a path and successu
		else traced=0;

			break;
		case 5:
			
			if(isValid(x,y+1))
			if(temp[x][++y]==opposition)
		{
				
		temp[x][y++]=player;
		if(!isValid(x,y))
		{
			copy(adj,temp);
			break;
		}
		while(isValid(x,y))
		{
			if(temp[x][y]=='*')
			{
				copy(adj,temp);
				break;
			}

			if(temp[x][y]==player)
			{
				/*This means that it is a valid move for position i and j*/
				copy(temp,adj);
				traced=1;
				//possibleMoves.add(new AbstractMap.SimpleEntry<Integer, Integer>(x,y));
				//found=1;
				//System.out.println("Added postion="+possibleMoves.get(possibleMoves.size()-1));
				break;
			}
			
			else if(temp[x][y++]==opposition)
			{
				if(isValid(x,y))
					{
					temp[x][y-1]=player;
					continue;}
				else {
					copy(adj,temp);
					break;} /*because it is not a valid move anymore*/
				
			}	
			else {
				copy(adj,temp);
				break;}

			
		}
	}
		if(traced==0)
	copy(adj,temp);    // This means it has found a path and successu
		else traced=0;

			break;
		case 6:
			if(isValid(x+1,y+1))
			if(temp[++x][++y]==opposition)
		{
			temp[x++][y++]=player;
			if(!isValid(x,y))
			{
				copy(adj,temp);
				break;
			}
					
		while(isValid(x,y))
		{
			if(temp[x][y]=='*')
			{
				copy(adj,temp);
				break;
			}
		//	System.out.println("Now here");

			if(temp[x][y]==player)
			{
				/*This means that it is a valid move for position i and j*/
				copy(temp,adj);
				traced=1;
				//possibleMoves.add(new AbstractMap.SimpleEntry<Integer, Integer>(x,y));
				//found=1;
				//System.out.println("Added postion="+possibleMoves.get(possibleMoves.size()-1));
				break;
			}
			
			else if(temp[x++][y++]==opposition)
			{
				if(isValid(x,y))
					{
					temp[x-1][y-1]=player;
					continue;}
				else {
					copy(adj,temp);
					break;} /*because it is not a valid move anymore*/
				
			}	
			else {
				copy(adj,temp);
				break;}

			
		}
	}
		if(traced==0)
	copy(adj,temp);    // This means it has found a path and successu
		else traced=0;

			break;
		case 7:
			if(isValid(x+1,y))
			if(temp[++x][y]==opposition)
		{
				temp[x++][y]=player;
				if(!isValid(x,y))
				{
					copy(adj,temp);
					break;
				}

		
		while(isValid(x,y))
		{
			if(temp[x][y]=='*')
			{
			//	System.out.println("case 7 if and adj="+adj[x-1][y]);
				copy(adj,temp);
				break;
			}
			if(temp[x][y]==player)
			{
				/*This means that it is a valid move for position i and j*/
				copy(temp,adj);
				traced=1;
				//possibleMoves.add(new AbstractMap.SimpleEntry<Integer, Integer>(x,y));
				//found=1;
				//System.out.println("Added postion="+possibleMoves.get(possibleMoves.size()-1));
				break;
			}
			
			else if(temp[x++][y]==opposition)
			{
				if(isValid(x,y))
					{
					temp[x-1][y]=player;
					continue;}
				else {
					copy(adj,temp);
					break;} /*because it is not a valid move anymore*/
				
			}	
			else {
				copy(adj,temp);
				break;}

			
		}
	}
		if(traced==0)
	copy(adj,temp);    // This means it has found a path and successu
		else traced=0;

			
			break;
		case 8:
			if(isValid(x+1,y-1))
			if(temp[++x][--y]==opposition)
		{
				temp[x++][y--]=player;
				if(!isValid(x,y))
				{
					copy(adj,temp);
					break;
				}

		while(isValid(x,y))
		{
			if(temp[x][y]=='*')
			{
				copy(adj,temp);
				break;
			}
			if(temp[x][y]==player)
			{
				/*This means that it is a valid move for position i and j*/
				copy(temp,adj);
				traced=1;
				//possibleMoves.add(new AbstractMap.SimpleEntry<Integer, Integer>(x,y));
				//found=1;
				//System.out.println("Added postion="+possibleMoves.get(possibleMoves.size()-1));
				break;
			}
			
			else if(temp[x++][y--]==opposition)
			{
				if(isValid(x,y))
					{
					temp[x-1][y+1]=player;
					continue;}
				else {
					copy(adj,temp);
					break;} /*because it is not a valid move anymore*/
				
			}	
			else {
				copy(adj,temp);
				break;}

			
		}
	}
		if(traced==0)
	copy(adj,temp);    // This means it has found a path and successu
		else traced=0;

			
			break;
		
		default: break;
		}
	}
	return adj;
	
}
private void finDIndexes(char[][] adj,char player)
{/*Finds the indexes in which the respective X or Os reside in the corresponding adjacency matrix*/
	for(int i=0;i<8;i++)
		for(int j=0;j<8;j++)
			if(adj[i][j]==player)
			{
				Map.Entry<Integer, Integer> pair=new SimpleEntry<Integer,Integer>(i,j);
				if(player=='X')indexListForX.add(pair);
				else indexListForO.add(pair);
					
			}
	
	}
private int calculateVal(char [][]adj,char player){
	char opposition;
	if(player=='X') opposition='O';
	else opposition='X';
	int playerSum=0,oppositionSum=0;
	for(int i=0;i<8;i++)
		for(int j=0;j<8;j++)
			if(adj[i][j]==player)playerSum+=evalValues[i][j];
			else if(adj[i][j]==opposition)oppositionSum+=evalValues[i][j];
	return (playerSum-oppositionSum);
	
}
private List<Map.Entry<Integer, Integer>> computePossibleMoves(char[][] adj,char player)
{
	char opposition;
	if(player=='X') opposition='O';
	else opposition='X';
	try{
		//System.out.println("From compute possible values");
		//printAdjacency(adj);
	 int reduntant [][]=new int[8][8];
	List<Map.Entry<Integer, Integer>> possibleMoves=new ArrayList<Map.Entry<Integer, Integer>>();
	int found=0;
	/*Stores the list of possible moves from the given adjaency matrix
	 * and for the respective player. Stores that information -- position--in
	 * the list and sends the complete list to the caller, along with the direction to be traversed.*/
	for(int i=0;i<8;i++)
		for(int j=0;j<8;j++)
		{
			if(adj[i][j]==player)
			{
				
				for(int k=1;k<=8;k++)
				{int x=i,y=j;
					switch(k)
					{
					case 1:	/*Left horizontal*/
							//System.out.println("Case 1:"+x+y);
							if(isValid(x,y-1))
							if(adj[x][--y]==opposition)
							while(isValid(x,y))
							{
								if(adj[x][y]==player)break;
								if(adj[x][y]=='*')
								{
									/*This means that it is a valid move for position i and j*/
									if(reduntant[x][y]!=1)	possibleMoves.add(new SimpleEntry<Integer, Integer>(x,y));
									found=1;
									//System.out.println("Added postion in case 1 for i="+i+"j="+j+possibleMoves.get(possibleMoves.size()-1));

									reduntant[x][y]=1;
									break;
								}
								
								else if(adj[x][y--]==opposition)
								{
									if(isValid(x,y))continue;
									else break; /*because it is not a valid move anymore*/
									
								}	
								
								
								
							}
							
						
						break;
					case 2:	 /*Left upper diagonal*/
							//if(found==1)break;
						if(isValid(x-1,y-1))
							if(adj[--x][--y]==opposition)
							while(isValid(x,y))
							{
								if(adj[x][y]==player)break;

								if(adj[x][y]=='*')
								{
									/*This means that it is a valid move for position i and j*/
								if(reduntant[x][y]!=1)possibleMoves.add(new SimpleEntry<Integer, Integer>(x,y));
									//System.out.println("Added postion in case 2 for i="+i+"j="+j+possibleMoves.get(possibleMoves.size()-1));

									found=1;
									reduntant[x][y]=1;
									break;
								}
								
								else if(adj[x--][y--]==opposition)
								{
									if(isValid(x,y))continue;
									else break; /*because it is not a valid move anymore*/
									
								}	
								
								
							}

						
						break;
					case 3: /*Upside Vertical*/
						//if(found==1)break;
						if(isValid(x-1,y))
						if(adj[--x][y]==opposition)
						while(isValid(x,y))
						{
							if(adj[x][y]==player)break;

							if(adj[x][y]=='*')
							{
								/*This means that it is a valid move for position i and j*/
								if(reduntant[x][y]!=1)possibleMoves.add(new SimpleEntry<Integer, Integer>(x,y));
								//System.out.println("Added postion in case 3 for i="+i+"j="+j+possibleMoves.get(possibleMoves.size()-1));

								found=1;
								reduntant[x][y]=1;
								break;
							}
							
							else if(adj[x--][y]==opposition)
							{
								if(isValid(x,y))continue;
								else break; /*because it is not a valid move anymore*/
								
							}	
							
							
						}
 
						break;
					case 4: /*upside right diagonal*/
						//if(found==1)break;
						if(isValid(x-1,y+1))
						if(adj[--x][++y]==opposition)
						while(isValid(x,y))
						{
							if(adj[x][y]==player)break;

							if(adj[x][y]=='*')
							{
								/*This means that it is a valid move for position i and j*/
								if(reduntant[x][y]!=1)	possibleMoves.add(new SimpleEntry<Integer, Integer>(x,y));
							//	System.out.println("Added postion in case 4 for i="+x+"j="+y+possibleMoves.get(possibleMoves.size()-1).getKey()+possibleMoves.get(possibleMoves.size()-1).getValue());

								found=1;
								reduntant[x][y]=1;
								break;
							}
							
							else if(adj[x--][y++]==opposition)
							{
								if(isValid(x,y))continue;
								else break; /*because it is not a valid move anymore*/
								
							}	
							
							
						}

						break;
					case 5: /*Right Horizontal*/ 
						//if(found==1)break;
					//	System.out.println("In case 5:"+x+" "+y);
						if(isValid(x,y+1))
						if(adj[x][++y]==opposition) 
						while(isValid(x,y))
						{
							if(adj[x][y]==player)break;

							//System.out.println("here");
							if(adj[x][y]=='*')
							{
								/*This means that it is a valid move for position i and j*/
								if(reduntant[x][y]!=1)	possibleMoves.add(new SimpleEntry<Integer, Integer>(x,y));
								//System.out.println("Added postion in case 5 for i="+i+"j="+j+possibleMoves.get(possibleMoves.size()-1));

								found=1;
								reduntant[x][y]=1;
								break;
							}
							
							else if(adj[x][y++]==opposition)
							{
								if(isValid(x,y))continue;
								else break; /*because it is not a valid move anymore*/
								
							}	
							
							
						}

						break;
					case 6 : /*Right Lower Diagonal*/ 
						//if(found==1)break;
						if(isValid(x+1,y+1))
						if(adj[++x][++y]==opposition)
						while(isValid(x,y))
						{
							if(adj[x][y]==player)break;

							if(adj[x][y]=='*')
							{
								/*This means that it is a valid move for position i and j*/
								if(reduntant[x][y]!=1)possibleMoves.add(new SimpleEntry<Integer, Integer>(x,y));
								//System.out.println("Added postion in case 6 for i="+i+"j="+j+possibleMoves.get(possibleMoves.size()-1));

								found=1;
								reduntant[x][y]=1;
								break;
							}
							
							else if(adj[x++][y++]==opposition)
							{
								if(isValid(x,y))continue;
								else break; /*because it is not a valid move anymore*/
								
							}	
							
							
						}

						break;
					case 7 : /*Vertical downwards*/ 
						//if(found==1)break;
						if(isValid(x+1,y))
							if(adj[++x][y]==opposition)
						while(isValid(x,y))
						{
							if(adj[x][y]==player)break;

							if(adj[x][y]=='*')
							{
								/*This means that it is a valid move for position i and j*/
								if(reduntant[x][y]!=1)	possibleMoves.add(new SimpleEntry<Integer, Integer>(x,y));
							//	System.out.println("Added postion in case 7 for i="+i+"j="+j+possibleMoves.get(possibleMoves.size()-1));

								found=1;
								reduntant[x][y]=1;
								break;
							}
							
							else if(adj[x++][y]==opposition)
							{
								if(isValid(x,y))continue;
								else break; /*because it is not a valid move anymore*/
								
							}	
							
							
						}

						break;
					case 8 : /*Left vertical downwards*/ 
						//if(found==1)break;
						if(isValid(x+1,y-1))
							if(adj[++x][--y]==opposition)
						while(isValid(x,y))
						{
							if(adj[x][y]==player)break;

							if(adj[x][y]=='*')
							{
								/*This means that it is a valid move for position i and j*/
								if(reduntant[x][y]!=1)	possibleMoves.add(new SimpleEntry<Integer, Integer>(x,y));
							//	System.out.println("Added postion in case 8 for i="+i+"j="+j+possibleMoves.get(possibleMoves.size()-1));

								found=1;
								 reduntant[x][y]=1;
								break;
							}
							
							else if(adj[x++][y--]==opposition)
							{
								if(isValid(x,y))continue;
								else break; /*because it is not a valid move anymore*/
								
							}	
							
							
						}

						break;
					default : break;
					}
				}
			}
			
		}
			   if(found==0){
				//  System.out.println("No valid moves");
				   return null;
				   }
			   //System.out.println(possibleMoves);
			  return sortList(possibleMoves);
//return possibleMoves;
	
	}catch(Exception e){
		//System.out.println("A bug! :"+e);
		return null;
	}
}
private List<Entry<Integer, Integer>> sortList(List<Entry<Integer, Integer>> possibleMoves) {
	
	for(int i=0;i<(possibleMoves.size());i++)
		for(int j=i+1;j<(possibleMoves.size());j++)
		{
			if(compareTo(possibleMoves.get(i),possibleMoves.get(j)))
			{	//System.out.println("swapping"+i+"and"+(j));
				swap(possibleMoves,i,j);
				//System.out.println(possibleMoves);
			}
		}
	// TODO Auto-generated method stub
	return possibleMoves;
}

private void swap(List<Entry<Integer, Integer>> possibleMoves, int i, int j) {
	// TODO Auto-generated method stub
	//System.out.println("Swapping");
	Map.Entry<Integer, Integer> temp=possibleMoves.get(i);
	possibleMoves.remove(i);
	possibleMoves.add(i, possibleMoves.get(j-1));
	possibleMoves.remove(j);
	possibleMoves.add(j,temp);
}

private boolean compareTo(Entry<Integer, Integer> entry,
		Entry<Integer, Integer> entry2) {
	// TODO Auto-generated method stub
	if(entry.getKey()>entry2.getKey())return true;
	if(entry.getKey()<entry2.getKey()) return false;
	if(entry.getValue()>entry2.getValue())return true; 
	/* this means the i value for both are same, and hence j has to compared*/
	return false;
}

private void printAdjacency(char[][] adj) {
	// TODO Auto-generated method stub
	for(int i=0;i<8;i++){
		for(int j=0;j<8;j++)
			System.out.print(adj[i][j]);
		System.out.println();}
	
}

private boolean isValid(int x,int y)
{
if(x<8 && y<8 && x>=0 && y>=0) return true;
else return false;
}

/*ALPHA BETA PRUNING STARTS HERE*/
private Map.Entry<Integer, Integer> Alpha_Beta_Search(char[][] state,int depth) {
	
	// TODO Auto-generated method stub
	
	char [][] localM=new char[8][8];
	copy(state,localM);
	logP.add(new NodeP());// The first value in the list has to be with the heading
	
	
	if(allSame(state))
	{
		//System.out.println("Satisfied");
		logP.add(new NodeP("root","0",String.format("%d",calculateVal(localM, player)),"-Infinity","Infinity",null));
		return null;
		// Every element in the adjaceny matrix is the same, so return immideately.
		//printLogP(logP);
	}
	/*else if(computePossibleMoves(localM,player)==null)
	{
		
		logP.add(new NodeP("root","0","-Infinity","-Infinity","Infinity",null));
		NodeP root=new NodeP(logP.getLast());
		System.out.println("This means that the initial move is a pass");
		logP.add(new NodeP("pass","1","-Infinity","-Infinity","Infinity",root));
		 NodeP localP=new NodeP(logP.getLast());
		List<Map.Entry<Integer, Integer>> list=computePossibleMoves(localM,opposition);
		if(list==null) 
		{
			NodeP n=new NodeP("pass","2","-Infinity","-Infinity","Infinity",root);
			logP.add(n);
			printLogP(logP);
			return null;
		}
		logP.removeLast();
		logP.removeLast();// Now the log will only have the heading and the root
		int v=Max_Value_P(localM,-10000,10000,0,root);
		printLogP(logP);
		return null;
	/*	char[][] localMo=new char[8][8];
		copy(localM,localMo);
		List<Map.Entry<Integer, Integer>> list=computePossibleMoves(localMo,opposition);
		int nodeValues[]=new int[list.size()];
		for(int i=0;i<list.size();i++)
		{
			localMo=new char[8][8];
			copy(localM,localMo);
			copy(makeMove(localMo, opposition, list.get(i)),localMo);
			if(v==calculateVal(localMo, opposition));
			return list.get(i);
		}*/
		
	//}
	else
	{
		logP.add(new NodeP("root","0","-Infinity","-Infinity","Infinity",null));
		NodeP root=new NodeP(logP.getLast());
		
	int v=Max_Value_P(localM,-10000,10000,0,root);
//	printLogP(logP);
	List<Map.Entry<Integer, Integer>> list=computePossibleMoves(localM,player);
	//int nodeValues[]=new int[list.size()];
//	printLogP(logP);
	if(list==null) return null;
	for(int i=0;i<list.size();i++)
	{
		System.out.println("");
		char[][] localMo=new char[8][8];
		copy(localM,localMo);
		copy(makeMove(localMo, player, list.get(i)),localMo);
		if(v==calculateVal(localMo, player))
		return list.get(i);
	}
	
	
	}
return null;	
}
private void printLogP(LinkedList<NodeP> logP2) {
	// TODO Auto-generated method stub
	for(int i=0;i<logP2.size();i++)
		System.out.println(logP2.get(i).toString());
	
	
}

private int Min_Value_P(char[][] state, int alpha, int beta,int depth,NodeP rootP) {
	// TODO Auto-generated method stub
	//System.out.println("alpha="+alpha+"beta="+beta);
//	System.out.println("in depth="+depth);
	NodeP root=new NodeP(rootP);
	char [][] localM=new char[8][8];
	int v=10000;
	int nodeValues[];
	NodeP Lparent;
	copy(state,localM);
	if(allSame(state))
	{
		int rv=	calculateVal(localM,player);
		if(convert(rootP.Value)>rv)
		{
			//System.out.println("Assigning from max terminal because it is same");
			rootP.Value=convert(rv);
		}
		return calculateVal(localM,player);
	}
	
	if(terminalTest(localM,depth,opposition))  
	{
		
int rv=	calculateVal(localM,player);
if(convert(rootP.Value)>rv)
{
	//System.out.println("Assigning from min terminal");
	rootP.Value=convert(rv);
}
//System.out.println("Returning from min="+rv);
return rv;
	}
	else if(computePossibleMoves(localM,opposition)==null)
	{
		Lparent=new NodeP("pass",String.format("%d",depth+1),"-Infinity",convert(alpha),convert(beta),root);
	//	System.out.println("Computing the pass case!");
		char[][] localMo=new char[8][8];
		copy(localM,localMo);
		//if((depth)!=cuttingOffDepth)
		if(logP.getLast().state=="pass")
		{
			
			int rv=	calculateVal(localM,player);
			Lparent.Value=convert(rv);
			logP.add(new NodeP(Lparent));
			v=Min(v,rv);
			if(v<convert(root.Value))
			{
				//System.out.println("Assigning from max terminal because it is same");
				root.Value=convert(rv);
			}
			if(rv<=alpha)
			{
				//System.out.println("Printing here?!");
			logP.add(new NodeP(root));
			return rv;
			}
		beta=Min(beta,v);
		
		root.beta=convert(beta);
		//.out.println("Printing here?! with beta="+root.beta+"and depth="+root.Depth);
		logP.add(new NodeP(root));
			
			return calculateVal(localM,player);

		}
		if((depth+1)==cuttingOffDepth) logP.add(Lparent);
		else
			logP.add(new NodeP(Lparent));
		
		v=Min(v,Max_Value_P(localMo,alpha,beta,depth+1,Lparent)); /*In the case of pass condition*/
		
		NodeP local=new NodeP(root.state,root.Depth,root.Value,root.alpha,root.beta,root.parent);
		if(v<convert(root.Value))
			local.Value=convert(v);
		if(v<=alpha)
			{
			logP.add(new NodeP(local));
			return v;
			}
		beta=Min(beta,v);
		local.beta=convert(beta);
		logP.add(new NodeP(local));
		return v;
	}
	else
	{
		
	//	System.out.println("For minimum value");
	//	System.out.println("alpha="+alpha+"beta="+beta);
		List<Map.Entry<Integer, Integer>> list=computePossibleMoves(localM,opposition);
		//printPossibleMoves(list);
		
		nodeValues=new int[list.size()];
		int lm=10000;
		for(int i=0;i<list.size();i++)
		{	
			char[][] localMo=new char[8][8];
			copy(localM,localMo);
			Lparent=new NodeP(getState(list.get(i)),String.format("%d",depth+1),"-Infinity",convert(alpha),convert(beta),root);
			if((depth+1)==cuttingOffDepth || allSame(makeMove(localMo, player,list.get(i)))) logP.add(Lparent);
			else
				logP.add(new NodeP(Lparent));
			
			int temp;
			v=nodeValues[i]=Min(v,temp=Max_Value_P(makeMove(localMo, opposition,list.get(i)),alpha,beta,depth+1,Lparent));
			if(temp<convert(Lparent.Value))
				Lparent.Value=String.format("%d",temp);
			if(temp<lm)
				lm=temp;
			if(convert(root.Value)>v)
			{
				//System.out.println("Assigning");
				root.Value=convert(v);
			}
		//	NodeP local=new NodeP(root.state,root.Depth,root.Value,root.alpha,root.beta,root.parent);
		
			if(v<=alpha){
		//		System.out.println("Printing here?!");
				logP.add(new NodeP(root));
				return v;
			}
			beta=Min(beta,v);
			root.beta=convert(beta);
			logP.add(new NodeP(root));
		}
		return v;
	}
	
}

private int Max_Value_P(char[][] state, int alpha, int beta,int depth,NodeP rootP) {
	// TODO Auto-generated method stub
	
	//System.out.println("in depth="+depth);
	NodeP root=new NodeP(rootP);
	char [][] localM=new char[8][8];
	int v=-10000;
	int nodeValues[];
	NodeP Lparent;
	copy(state,localM);
	if(allSame(state))
	{
		int rv=	calculateVal(localM,player);
		if(convert(rootP.Value)<rv)
		{
		//	System.out.println("Assigning from max terminal becuase it is same");
			rootP.Value=convert(rv);
		}
		return calculateVal(localM,player);
	}
	if(terminalTest(localM,depth,player))  
	{
		
int rv=	calculateVal(localM,player);
if(convert(rootP.Value)<rv)
{
	//System.out.println("Assigning from max terminal");
	rootP.Value=convert(rv);
	
}
//System.out.println("Returning from max="+rv);
return rv;
	}
	else if(computePossibleMoves(localM,player)==null)
	{
		Lparent=new NodeP("pass",String.format("%d",depth+1),"Infinity",convert(alpha),convert(beta),root);
		char[][] localMo=new char[8][8];
		copy(localM,localMo);
		int temp;
		//if((depth)!=cuttingOffDepth)
		if(logP.getLast().state=="pass")
		{
			logP.add(new NodeP("pass",convert(depth+1),convert(calculateVal(localM,player)),convert(alpha),convert(beta),root));
			int rv=calculateVal(localM,player);
			logP.getLast().Value=convert(rv);
			v=Max(v,rv);
			if(v>convert(root.Value)){
				
			//	System.out.println("ASSIGNING");
					root.Value=convert(v);
				}
				
				if(v>=beta){
					
					logP.add(root);
					return v;

				}
				alpha=Max(alpha,v);
				root.alpha=convert(alpha);
				logP.add(new NodeP(root));
			
			return calculateVal(localM,player);
		}
		if((depth+1)==cuttingOffDepth) logP.add(Lparent);
		else	logP.add(new NodeP(Lparent));
		//System.out.println("Computing the pass case!");
		copy(localM,localMo);
		v=Max(v,Min_Value_P(localMo,alpha,beta,depth+1,Lparent)); /*In the case of pass condition*/
	//	NodeP local=new NodeP(root.state,root.Depth,root.Value,root.alpha,root.beta,root.parent);
		if(v>convert(root.Value))
		{
		
		root.Value=convert(v);
		}
		if(v>=beta)
			{
			logP.add(new NodeP(root));
			return v;
			}
		alpha=Max(alpha,v);
		root.alpha=convert(alpha);
		logP.add(new NodeP(root));
		return v;
	}
	else
	{
		
	//	System.out.println("For maximum value");
		//System.out.println("alpha="+alpha+"beta="+beta);
		List<Map.Entry<Integer, Integer>> list=computePossibleMoves(localM,player);
		//printPossibleMoves(list);
		int lm=-10000;
		nodeValues=new int[list.size()];
		for(int i=0;i<list.size();i++)
		{	
		Lparent=new NodeP(getState(list.get(i)),String.format("%d",depth+1),"Infinity",convert(alpha),convert(beta),root);
		char[][] localMo=new char[8][8];
		copy(localM,localMo);
		if((depth+1)==cuttingOffDepth || allSame(makeMove(localMo, player,list.get(i)))) logP.add(Lparent);
		else	logP.add(new NodeP(Lparent));
			//Lparent=new NodeP(getState(list.get(i)),String.format("%d",depth+1),"-Infinity",convert(alpha),convert(beta),root);
			localMo=new char[8][8];
			copy(localM,localMo);
			int temp;
			v=nodeValues[i]=Max(v,temp=Min_Value_P(makeMove(localMo, player,list.get(i)),alpha,beta,depth+1,Lparent));
			if(temp<convert(Lparent.Value))
				Lparent.Value=String.format("%d",temp);
			if(temp<lm)
				lm=temp;
			if(v>convert(root.Value)){
				
		//	System.out.println("ASSIGNING");
				root.Value=convert(v);
			}
			
			if(v>=beta){
				
				logP.add(new NodeP(root));
				return v;

			}
			alpha=Max(alpha,v);
			root.alpha=convert(alpha);
			logP.add(new NodeP(root));
		}
		return v;
	}

}
private String convert(int n) {
	// TODO Auto-generated method stub
	if(n==10000) return "Infinity";
	if(n==-10000) return "-Infinity";
	
	return String.format("%d",n);
}

/*ALPHA BETA */
/*MINIMAX SPECIFIC METHODS*/
/*Compares the two string variables by converitng them into integers*/
private int intCompare(String a,String b)
{
if(Integer.parseInt(a)>Integer.parseInt(b)) return 1;
else if(Integer.parseInt(a)==Integer.parseInt(b))return 0;
else return -1;

}

private  Map.Entry<Integer,Integer> minimax(char[][] state,int depth) {
	// TODO Auto-generated method stub
	char [][] localM=new char[8][8];
	copy(state,localM);
	
	log.add(new Node());// The first value in the list has to be with the heading
	
	
	if(allSame(state))
	{
		log.add(new Node("root","0",String.format("%d",calculateVal(state, player)),null));
		//printLog(log);
		return null;
	}
		else if(computePossibleMoves(localM,player)==null)
	{
			log.add(new Node("root","0","-Infinity",null));
			Node root=new Node(log.getLast());
			
	//	System.out.println("This means that the initial move is a pass");
		log.add(new Node("pass","1","Infinity",root));
		
		List<Map.Entry<Integer, Integer>> list=computePossibleMoves(localM,opposition);
		
		
		//printLog(log);
		char[][] localMo=new char[8][8];
		
		
		copy(localM,localMo);
		
		Node Lparent=new Node(log.getLast()); /*Local parent for the nodes from here*/
		
		int v=Min_Value(localMo,1,Lparent);
		
		
		if(v<convert(Lparent.Value)) /*This finds the m value*/
			Lparent.Value=String.format("%d",v);
		int min=0;
		if(root.Value=="-Infinity")
		{
			root.Value=String.format("%d",v);		
			log.add(root);
			}
		else{ if(convert(root.Value)<convert(Lparent.Value))
		{
			Node local=new Node(root.state,root.Depth,Lparent.Value,root.parent);
			log.add(local);
			}
		else log.add(root);}
	//	printLog(log);
		return null;
		
	}
		/*If this happens, it means that the initial state is the one that has to be output*/ 
	
else
{
	log.add(new Node("root","0","-Infinity",null));
	Node root=new Node(log.getLast());
//	System.out.println("Computing possible moves from the minimax method");

	List<Map.Entry<Integer, Integer>> list=computePossibleMoves(localM,player);
	int nodeValues[]=new int[list.size()];
	
		
	for(int i=0;i<list.size();i++)
	{	/*Minimize the value*/
	//	System.out.println("For move number="+i);
		char[][] localMo=new char[8][8];
		copy(localM,localMo);
		copy(makeMove(localMo, player, list.get(i)),localMo);
		Node Lparent=new Node(getState(list.get(i)),"1","Infinity",root);
		log.add(new Node(getState(list.get(i)),"1","Infinity",root));
	//	log.add(new Node(getState(list.get(i)),"1","Infinity",Lparent));
		nodeValues[i]=Min_Value(localMo,1,Lparent);
	//	log.add(new Node(Lparent.state,"1",String.format("%d",nodeValues[i]),null));
		if(nodeValues[i]<convert(Lparent.Value)) /*This finds the m value*/
			Lparent.Value=String.format("%d",nodeValues[i]);
		//log.add(Lparent); //At the final iteration, ideally, this should have the correct, maximum value.
		if(root.Value=="-Infinity")
		{
			root.Value=String.format("%d",nodeValues[i]);		
			log.add(root);
			}
		else{ if(convert(root.Value)<convert(Lparent.Value))
		{
			Node local=new Node(root.state,root.Depth,Lparent.Value,root.parent);
			log.add(local);
			}
		else log.add(root);}
	}
	int max=0;
	for(int i=0;i<nodeValues.length;i++)
	{
	//	System.out.println("Value="+nodeValues[i]);
		if(nodeValues[i]>nodeValues[max]) max=i;
	}
		//printLog(log);
	return list.get(max);
	
	
}
}
private void printLog(LinkedList<Node> log2) {
	// TODO Auto-generated method stub
	//System.out.println("THE LOG:");
	for(int i=0;i<log2.size();i++)
	System.out.println(log2.get(i).toString());
}

private int convert(String value) {
	// TODO Auto-generated method stub
	if(value=="Infinity")return 10000;
	else if(value=="-Infinity")return -10000;
	else return Integer.parseInt(value);
 	
}

private int Min_Value(char[][] state,int depth,Node rootP) {
	
	//switchPlayers();
	
//	System.out.println("in depth="+depth);
	char [][] localM=new char[8][8];
	int v=100000;
	int nodeValues[];
	copy(state,localM);
	Node root=new Node(rootP);
	Node Lparent=null;
	if(allSame(state))
	{
		return calculateVal(localM,player);
	}
	if(terminalTest(localM,depth,opposition))  
			{
				
	int rv=	calculateVal(localM,player);
//.out.println("Returning from min="+rv);
	return rv;
			}
	
	else if(computePossibleMoves(localM,opposition)==null)
	{
		/*This means that this is not a terminal state, but at the same time,
		 * the current player does not have a move to play, so he will pass and
		 * ask the opposition(in his perspective) to play by calling max value
		 * on the current state.*/
	//	System.out.println("HERe IN MIN");
		Lparent=new Node("pass",String.format("%d",depth+1),"-Infinity",root);
		if(log.getLast().state=="pass") 
		{
			int rv=calculateVal(localM,player);
			log.add(new Node(Lparent));
			if(convert(root.Value)>rv)
			{	root.Value=convert(rv);
				Node local=new Node(root.state,root.Depth,root.Value,root.parent);
				log.add(local);
				
			}
			else log.add(root);
			return rv;
		}
		if((depth+1)!=cuttingOffDepth)
		log.add(new Node(Lparent));
	//	System.out.println("Computing the pass case!");
		char[][] localMo=new char[8][8];
		copy(localM,localMo);
		v=Min(v,Max_Value(localMo,depth+1,Lparent)); /*In the case of pass condition*/
		//log.add(new Node(Lparent.state,String.format("%d",depth+1),String.format("%d",v),null));
		if(convert(root.Value)>v)
		{
			root.Value=String.format("%d",v);
			Node local=new Node(root.state,root.Depth,root.Value,root.parent);
			
			log.add(local);
			/*Update the value for the next comparision*/
			}
		else log.add(root);	
		return v;
	}
	
	else
		{
	//	System.out.println("For minimum value");
		
	List<Map.Entry<Integer, Integer>> list=computePossibleMoves(localM,opposition);
//	printPossibleMoves(list);
	nodeValues=new int[list.size()];
	int lm=-10000;
	for(int i=0;i<list.size();i++)
	{	
		Lparent=new Node(getState(list.get(i)),String.format("%d",depth+1),"-Infinity",root);
		
		char[][] localMo=new char[8][8];
		int temp;
		copy(localM,localMo);
		if((depth+1)!=cuttingOffDepth)
		log.add(new Node(Lparent));
		v=nodeValues[i]=Min(v,temp=Max_Value(makeMove(localMo, opposition,list.get(i)),depth+1,Lparent));
		if(temp>convert(Lparent.Value))
			Lparent.Value=String.format("%d",temp);
		if(temp>lm)
			lm=temp;
	//	log.add(new Node(Lparent.state,String.format("%d",depth+1),String.format("%d",temp),null));
		//if(nodeValues[i]>convert(Lparent.Value)) /*This finds the maximum value*/
			//Lparent.Value=String.format("%d",nodeValues[i]);
	//	log.add(Lparent); //At the final iteration, ideally, this should have the correct, maximum value.
		if(convert(root.Value)>temp)
		{
			root.Value=String.format("%d",temp);
			Node local=new Node(root.state,root.Depth,root.Value,root.parent);
			
			log.add(local);
			/*Update the value for the next comparision*/
			}
		else log.add(root);	
	}
	// TODO Auto-generated method stub
	
	return v;
	}
}

private void switchPlayers() {
	// TODO Auto-generated method stub
	char temp=player;
	player=opposition;
	opposition=temp;
}

private int Min(int a,int b)
{
	//System.out.println("Values got to Min functions are="+a+"and:"+b);
return (a>b)?b:a;	
}
private int Max_Value(char[][] state,int depth,Node rootP) {
	// TODO Auto-generated method stub
	//switchPlayers();
	int nodeValues[];
	Node Lparent=null;
	char [][] localM=new char[8][8];
	int v=-100000;
	copy(state,localM);
	Node root=new Node(rootP);
	if(terminalTest(localM,depth,player))  
	{
		int rv=	calculateVal(localM,player);
	//	System.out.println("Returning from max="+rv);
		return rv;
	}	
	else if(computePossibleMoves(localM,player)==null){
		/*This means that this is not a terminal state, but at the same time,
		 * the current player does not have a move to play, so he will pass and
		 * ask the opposition(in his perspective) to play by calling max value
		 * on the current state.*/
		
		Lparent=new Node("pass",String.format("%d",depth+1),"Infinity",root);
		char[][] localMo=new char[8][8];
		if(log.getLast().state=="pass") 
		{
			int rv=calculateVal(localM,player);
			log.add(new Node(Lparent));
			if(convert(root.Value)<rv)
			{	root.Value=convert(rv);
				Node local=new Node(root.state,root.Depth,root.Value,root.parent);
				log.add(local);
				
			}
			else log.add(root);
			return rv;
			
		}
		copy(localM,localMo);
		int temp;
		if((depth+1)!=cuttingOffDepth)
		log.add(Lparent);
	//.out.println("Computing the pass case!");
		copy(localM,localMo);
		v=Max(v,Min_Value(localMo,depth+1,Lparent));
		//log.add(new Node(Lparent.state,String.format("%d",depth+1),String.format("%d",v),null));
		//if(nodeValues[i]>convert(Lparent.Value)) /*This finds the maximum value*/
			//Lparent.Value=String.format("%d",nodeValues[i]);
//		log.add(Lparent); //At the final iteration, ideally, this should have the correct, maximum value.
		if(convert(root.Value)<v)
		{
			root.Value=String.format("%d",v);
			Node local=new Node(root.state,root.Depth,root.Value,root.parent);
			log.add(local);
			}
		else log.add(root);

		return v;
			
		}
		else{
	//System.out.println("For maximum value");
	
	List<Map.Entry<Integer, Integer>> list=computePossibleMoves(localM,player);
	//(list);
	nodeValues=new int[list.size()];
	int lm=10000;
	for(int i=0;i<list.size();i++)
	{
		Lparent=new Node(getState(list.get(i)),String.format("%d",depth+1),"Infinity",root);
		char[][] localMo=new char[8][8];
		copy(localM,localMo);
		int temp;
		if((depth+1)!=cuttingOffDepth)
		log.add(Lparent);
		v=nodeValues[i]=Max(v,temp=Min_Value(makeMove(localMo, player,list.get(i)),depth+1,Lparent));
		if(temp<convert(Lparent.Value))
			Lparent.Value=String.format("%d",temp);
		if(temp<lm)
			lm=temp;
	//	log.add(new Node(Lparent.state,String.format("%d",depth+1),String.format("%d",temp),null));
		//if(nodeValues[i]>convert(Lparent.Value)) /*This finds the maximum value*/
			//Lparent.Value=String.format("%d",nodeValues[i]);
//		log.add(Lparent); //At the final iteration, ideally, this should have the correct, maximum value.
		if(convert(root.Value)<temp)
		{
			root.Value=String.format("%d",temp);
			Node local=new Node(root.state,root.Depth,root.Value,root.parent);
			log.add(local);
			}
		else log.add(root);
		}
	return v;
	}
}

private int Max(int a, int b) {
	// TODO Auto-generated method stub
	//System.out.println("Values got to Max functions are="+a+"and:"+b);
	return a>b?a:b;
}
/*MINIMAX SPECIFIC METHODS*/
private boolean allSame(char [][]adj)
{
	int Xocc=0,Oocc=0;
	for(int i=0;i<8;i++)
		for(int j=0;j<8;j++)
			if(adj[i][j]=='X')Xocc++;
			else if(adj[i][j]=='O')Oocc++;
	if(Xocc==0 || Oocc==0) return true;
	return false;
	
}
private boolean terminalTest(char[][] state,int depth,char player)
{
	
	char opposition;
	if(player=='X')opposition='O';
	else opposition='X';
if(depth==cuttingOffDepth)return true;
return false;
}
private boolean terminalState(char[][] state,char player) {
	// TODO Auto-generated method stub
	List<Entry<Integer,Integer>> rv=computePossibleMoves(state, player);
	//System.out.println("rv="+rv);
	if(rv==null) return true;
	return false;
}

private int greedy() {
	// TODO Auto-generated method stub
	System.out.println("OK");
	int posInList=0,max=0;
	 char temp[][]=new char[8][8];
	copy(adj,temp);
	List<Map.Entry<Integer, Integer>> list=computePossibleMoves(adj, player);
	if(list==null){outputToFile(adj);return 1;}
	for(int k=0;k<list.size();k++)
	{
		copy(adj,temp);
		//System.out.println("here");
		int tempVal=calculateVal((makeMove(temp,player,list.get(k))),player);
	//	System.out.println("Value calculated="+tempVal);
		if(k==0){max=tempVal; posInList=0;}
		else if(max<tempVal){max=tempVal;posInList=k;}
	}
	//System.out.println("Most optimal move in the list is found to have an value="+max+"And has a position in the list:"+posInList);
	for(int k=0;k<list.size();k++)
	{
		copy(adj,temp);
		int tempVal=calculateVal((makeMove(temp,player,list.get(k))),player);
		//System.out.println(tempVal);
		if(max==tempVal && k!=posInList){
			//System.out.println("Comparing...");
			posInList=compPos(list,k,posInList);
		}
	}
	copy(makeMove(adj,player,list.get(posInList)),adj);
//	System.out.println("Next move:");
	solutionG(adj);
	return 2;
	//System.out.println("Best Move="+list.get(posInList).getKey()+" "+list.get(posInList).getValue()+" with weight ="+max);
}
/*This method calculates the first occurance of the given indices.
 * K is the first position of the list and posInLIst is the second one. 
 * Both of which are respective positions of the elements in the list
 *	which we are trying to compare. <In the case of equal evaluation funtion value>*/
private int compPos(List<Entry<Integer, Integer>> list, int k,
		int posInList) {
	// TODO Auto-generated method stub
	if(list.get(posInList).getKey()<list.get(k).getKey())return posInList;
	else if(list.get(posInList).getKey()>list.get(k).getKey()) return k;
	else if(list.get(posInList).getKey()==list.get(k).getKey())
	{
		if(list.get(posInList).getValue()<list.get(k).getValue())return posInList;
		else return k;
	}
	return 0;
}

private void outputToFile(char[][] adj2) {
	// TODO Auto-generated method stub
	
}

private void printInfo() {
	// TODO Auto-generated method stub
	try{
	System.out.println("The input data recieved from the file are as follows:");
	System.out.println(String.format("Mode=%d\nplayer=%c\nopposition=%c\nadj is as follows:",mode,player,opposition));
	printAdjacency(adj);
	}catch(Exception e){
		System.out.println("An Exception! Details:"+e);
	}
	
}
public void inputInitialData(char adj[][]){
	String line;
	int lineCount=0;
	BufferedReader bf;
	int rowNo=0;
	try{
		 bf=new BufferedReader(new FileReader("input.txt"));    /*REMEMBER TO CHANGE IT BEFORE SUBMISSION*/
			while((line=bf.readLine())!=null)
			{
				//System.out.println(line);
				if(lineCount==0)mode=Integer.parseInt(line);
				else if(lineCount==1)player=line.charAt(0);
				else if(lineCount==2)cuttingOffDepth=Integer.parseInt(line);
				else{
					//char temp[]=new char[8];
					for(int i=0;i<8;i++)
						adj[rowNo][i]=line.charAt(i);						
					rowNo++;
				}
				lineCount++;

				
			}
		}catch(IOException e)
		{
			System.out.println("\n Exception in reading file :"+e);
		}
		catch(Exception e)
		{
			System.out.println("And excepption with details :"+e);
			
		}

}

private void solutionM(LinkedList<Node> log,char[][] adj) {
	try{
		
	String fileName="output.txt";
	
	PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(fileName,false)));
	for(int i=0;i<8;i++){
		for(int j=0;j<8;j++)
			pw.print(adj[i][j]);
		pw.println();}	
	for(int i=0;i<log.size();i++)
	{
		pw.println(log.get(i).toString());
	}
	pw.close();
	}catch(FileNotFoundException e){
		System.out.println("File not found:"+e);
	}
	catch(IOException e){
		System.out.println("IO error"+e);
		
	}
}
private void solutionA(LinkedList<NodeP> log,char[][] adj) {
	try{
		
	String fileName="output.txt";
//	System.out.println("Here");
	PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(fileName,false)));
	for(int i=0;i<8;i++){
		for(int j=0;j<8;j++)
			pw.print(adj[i][j]);
		pw.println();}
	for(int i=0;i<log.size();i++)
	{
		pw.println(log.get(i).toString());
	}
	pw.close();
	}catch(FileNotFoundException e){
		System.out.println("File not found:"+e);
	}
	catch(IOException e){
		System.out.println("IO error"+e);
		
	}
}


private void solutionG(char[][] adj) {
	try{
		
	String fileName="output.txt";
	PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(fileName,false)));

	for(int i=0;i<8;i++)
	{
		for(int j=0;j<8;j++)
			pw.print(adj[i][j]);
		
		pw.println();
		}
	pw.close();
	}catch(FileNotFoundException e){
		System.out.println("File not found:"+e);
	}
	catch(IOException e){
		System.out.println("IO error"+e);
		
	}
}
}
