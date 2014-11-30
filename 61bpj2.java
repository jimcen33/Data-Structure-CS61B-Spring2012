public class Gameboard{


	//
	private Chip[][] board;
	private Player player;
	private int chipNumber;
	
	public static final int BLACK=0; 
	public static final int WHITE=1;
	public static final int WHITEWIN=100;
	public static final int BLACKWIN=-100;
	private List potentialNetwork;
	private int potentialNetworkCount;
	private Chip[] neighbor;
	private int whiteConnectionNumber;
	private int blackConnectionNumber;
	
	


	//Creates a Gameboard with the given player
	//
	public Gameboard(Player p){
		player=p;
		board = new Chip[8][8];	
		chipNumber=0;
	}
	
	/**
	//Return the color(chip) of the cell.
	//x,y are the coordinates of a cell.
	public int cellColor(int x, int y){
		if (cellContent(x,y)==null){
		   return EMPTY;
		}
		else{
		   return cellContent(x,y).getColor();
		}  
	}*/
	
	public Chip cellContent(int x, int y){
		if(checkInBounds(x, y)){
			return board[x][y];
		}
		else{
			return null;
		}
	}  
	/**
	public int[] neighbors(int x, int y){
		int w = cellContent(x-1, y);
		int nw = cellContent(x-1, y-1);
		int n = cellContent(x, y-1);
		int ne = cellContent(x+1, y-1);
		int e = cellContent(x+1, y);
		int se = cellContent(x+1, y+1);
		int s = cellContent(x, y+1);
		int sw = cellContent(x-1, y+1);
		int[] around = {w, nw, n, ne, e, se, s, sw};
		return around;
	}*/
	//Return an array of its neighbors around a given position.
	public Chip[] neighbors(int x, int y){
		/**if (x==0&&y==0){
			neighbor =new int[3];
			neighbor[0]=cellContent(0,1);
			neighbor[1]=cellContent(1,1);
			neighbor[2]=cellContent(1,0);
		}else if (x==0&&y==7){
			neighbor=new int[3];
			neighbor[0]=cellContent(0,6);
			neighbor[1]=cellContent(1,6);
			neighbor[2]=cellContent(1,7);
		}else if (x==7 && y==0){
			neighbor=new int[3];
			neighbor[0]=cellContent(7,1);
			neighbor[1]=cellContent(6,1);
			neighbor[2]=cellContent(6,0);
		}else if (x==7 && y==7){
			neighbor=new int[3];
			neighbor[0]=cellContent(7,6);
			neighbor[1]=cellContent(6,6);
			neighbor[2]=cellContent(6,7);*/
		Chip[] neighbor=null;
		if (x==0){
			neighbor=new Chip[5];
			neighbor[0]=cellContent(0,y+1);
			neighbor[1]=cellContent(1,y+1);
			neighbor[2]=cellContent(1,y);
			neighbor[3]=cellContent(1,y-1);
			neighbor[4]=cellContent(0,y-1);
		}else if(x==7){
			neighbor=new Chip[5];
			neighbor[0]=cellContent(7,y+1);
			neighbor[1]=cellContent(6,y+1);
			neighbor[2]=cellContent(6,y);
			neighbor[3]=cellContent(6,y-1);
			neighbor[4]=cellContent(7,y-1);
		}else if(y==0){
			neighbor=new Chip[5];
			neighbor[0]=cellContent(x-1,0);
			neighbor[1]=cellContent(x-1,1);
			neighbor[2]=cellContent(x,1);
			neighbor[3]=cellContent(x+1,1);
			neighbor[4]=cellContent(x+1,0);
		}else if(y==7){
			neighbor=new Chip[5];
			neighbor[0]=cellContent(x-1,7);
			neighbor[1]=cellContent(x-1,6);
			neighbor[2]=cellContent(x,6);
			neighbor[3]=cellContent(x+1,6);
			neighbor[4]=cellContent(x+1,7);
		}else{
			neighbor=new Chip[8];
			neighbor[0]=cellContent(x-1,y-1);
			neighbor[1]=cellContent(x-1,y);
			neighbor[2]=cellContent(x-1,y+1);
			neighbor[3]=cellContent(x,y+1);
			neighbor[4]=cellContent(x+1,y+1);
			neighbor[5]=cellContent(x+1,y);
			neighbor[6]=cellContent(x+1,y-1);
			neighbor[7]=cellContent(x,y-1);
		}
		return neighbor;
	}
		
	//Return the number of a certain color of neighbors around a given position
	public int neighborNumber(int color, int x, int y){
		int number =0;
		Chip[] around = neighbors(int x, int y);
		for(int i=0; i<around.length; i++){
			if((around[i] !=null) && around[i].getColor() == color){
				number++;
			}
		}
		return number;
	}

	//add a chip in the given position.
	public void addChip(int color, int x, int y){
		Chip chip = new Chip(color, x, y);
		board[x][y] = chip;
		chipNumber++;
	}
	
	//remove a chip in the given position.
	public void removeChip(int x, int y){
		board[x][y] = null;
		chipNumber--;
	}

	//Move a chip from the original position to the new position. 
	public void moveChip(int srcX, int srcY, int desX, int desY){
		int color = board[srcX][srcY].getColor();
		Chip chip = new Chip(color, desX, desY);
		board[desX][desY] = chip;
		board[srcX][srcY] = null;
	}

	//check its moveKind and then perform the move and return true. 
	public boolean performMove(int color, Move m){
		if(isValidMove(m.x1, m.y1,m.x2,m.y2,color)){
			if(m.moveKind == Move.ADD){
				addChip(color, m.x1, m.y1);
				return true;
			}
			else if(m.moveKind == Move.STEP){
				moveChip(m.x2, m.y2, m.x1, m.y1);
				return true;
			}
			else{
				return true;
			}
		}
		else{
			return false;
		}
	}

	//Undo the performed move.Use in chooseMove().
	public void undoMove(int color, Move m){
		
			if(m.moveKind == Move.ADD){
				removeChip(m.x1, m.y1);
			}else{
			
			if (isValidMove(m.x2,m.y2,m.x1,m.y1,color){
			if(m.moveKind == Move.STEP){
				moveChip(m.x1, m.y1, m.x2, m.y2);
			}
			}
			}
		
	}
	
	//Test whether the potential move is in the bounds of the gameboard.
	private boolean checkInBounds(int x, int y){
		if(x>-1 && x<8 && y>-1 && y<8){
			return true;
		}
		return false;
	}
	
	//Test whether the potential move is to the corner.
	private boolean checkCorner(int x, int y){
		if((x==0 && y==0) || (x==0 && y==7) || (x==7 && y==0) || (x==7 && y==7)){
			return false;
		}
		return true;
	}

	//Test whether the potential move is to the goal of the opposite color.
	private boolean checkOppositeGoal(int x, int y, int color){
		if((color == BLACK) && (x==0 || x==7)){
			return false;
		}
		if((color == WHITE) && (y==0 || y==7)){
			return false;
		}
		return true;
	}

	//Test whether the potential move is to a square that is already occupied.
	private boolean checkOccupied (int x, int y){
		if(cellContent(x, y) != null){
			return false;
		}
		return true;
	}

	//Test whether the potential move will form cluster with other two existed chips.
	private boolean checkCluster(int x, int y,int color){
		int number = neighborNumber(color, x, y);
		if(number==0){
			return true;
		}
		else if(number ==1){
			Chip[] around = neighbors(x, y);
			for(int i=0; i<around.length; i++){
				if((around[i] !=null) && around[i].getColor() == color){
					if(neighborNumber(color, around[i].getX(), around[i].getY())==0){
						return true;
					}
					else{
						return false;
					}
				}
			}	
		}
		else{
			return false;
		}
	}
	 
	//Test whether the potential move is valid or not.
	//x,y are coordinate of the potential move. 
	public boolean isValidMove(int x, int y, int v, int w,int color){
	    Chip p=cellContent(v,w);
	    if (cellContent(v,w) != null){
		    removeChip(v,w);
			}
		if(checkCorner(x, y) && checkOppositeGoal(x, y, color) && checkOccupied(x, y) && checkCluster(x, y, color) && checkInBounds(x, y)){
			if ( p !=null){
			addChip(p.getColor(),p.getX(),p.getY());
			}
			return true;
		}
		else{
		    if (p !=null){
			addChip(p.getColor(),p.getX(),p.getY());
			}
			return false;
			
		}
	}

	//Generate a DList of valid Moves.
	public List generateMoves(int color){
	//first use moveKind to test the next move should be which kind of move
	//then walk through every cell of the gameboard and test whether it's a valid move, 
	//if a potential move is a valid move, insert it into the DList. 
		List mylist = new DList();
		if(moveKind()==Move.ADD){
			for(int i=0; i<8; i++){
				for(int j=0; j<8; j++){
					if(isValidMove(i, j,0,0, color)){
						Move m = new Move(i, j);
						mylist.insertBack(m);
					}
				}
			}
		}
		if(moveKind() == Move.STEP){
			for(int i=0; i<8; i++){
				for(int j=0; j<8; j++){
					if(cellContent(i, j) !=null && cellContent(i, j).getColor()==color){
						for(int n=0; n<8; n++){
							for(int m=0; m<8; m++){
								if(isValidMove(n,m,i,j,color) && (n!= i || m!= j)){
									Move m = new Move(n, m, i, j);
									mylist.insertBack(m);
								}
							}
						}
					}
			    }
			}	
		}
		return mylist;
	}
	
	
	//return the current move kind for this gameboard.
	public int moveKind(){
		if(chipNumber < 20){
			return Move.ADD;
		}
		else{
			return Move.STEP;
		}
	}


	public int evaluate() {
		if (findNetwork(WHITE) == WHITEWIN) {
			return WHITEWIN;
		} else if (findNetwork(BLACK) == BLACKWIN) {
			return BLACKWIN;
		} else {
			whiteConnectionNumber = 0;
			blackConnectionNumber = 0;
			for (int i = 0; i <= 7; i++) {
				for (int j = 0; j <= 7; j++) {
					if (cellContent(i, j) != null) {
						search(cellContent(i, j), null);
					}
				}
			}
			return (whiteConnectionNumber - blackConnectionNumber) / 2;
		}
	}

	public List search(Chip p, Connection.Direction d) {
		List l = new DList();
		if (p.getX() == 0) {
			Connection.Direction[] directions = { Connection.Direction.RIGHT,
					Connection.Direction.UPPERRIGHT,
					Connection.Direction.LOWERRIGHT };
			for (Connection.Direction h : directions) {
				Connection c = explore(p, h);
				if (c != null && c.getConnectXcoord() != 7) {
					l.insertBack(c);
					whiteConnectionNumber++;
				}
			}
		} else if (p.getX() == 7) {
			Connection.Direction[] directions = { Connection.Direction.LEFT,
					Connection.Direction.UPPERLEFT,
					Connection.Direction.LOWERLEFT };
			for (Connection.Direction h : directions) {
				Connection c = explore(p, h);
				if (c != null && c.getConnectXcoord() != 0) {
					l.insertBack(c);
					whiteConnectionNumber++;
				}
			}
		} else if (p.getY() == 0) {
			Connection.Direction[] directions = { Connection.Direction.DOWN,
					Connection.Direction.LOWERLEFT,
					Connection.Direction.LOWERRIGHT };
			for (Connection.Direction h : directions) {
				Connection c = explore(p, h);
				if (c != null && c.getConnectYcoord() != 7) {
					l.insertBack(c);
					blackConnectionNumber++;
				}
			}
		} else if (p.getY() == 7) {
			Connection.Direction[] directions = { Connection.Direction.UP,
					Connection.Direction.UPPERLEFT,
					Connection.Direction.UPPERRIGHT };
			for (Connection.Direction h : directions) {
				Connection c = explore(p, h);
				if (c != null && c.getConnectYcoord() != 0) {
					l.insertBack(c);
					blackConnectionNumber++;
				}
			}
		} else {
			for (Connection.Direction h : Connection.Direction.values()) {
				if (h != d && h != Connection.getOppositeDirection(d)) {
					Connection c = explore(p, h);
					if (c != null) {
						l.insertBack(c);
						if (p.getColor() == BLACK) {
							blackConnectionNumber++;
						} else if (p.getColor() == WHITE) {
							whiteConnectionNumber++;
						}
					}
				}
			}
		}
		return l;
	}

	public Connection explore(Chip p, Connection.Direction d) {
		Connection c = null;
		if (d == Connection.Direction.UPPERRIGHT) {
			for (int i = p.getX() + 1, j = p.getY() - 1; i <= 7 && j >= 0; i++, j--) {
				if (cellContent(i, j) == null) {
				} else if (p.getColor() == cellContent(i, j).getColor()
						&& cellContent(i, j).getPainted() == false) {
					c = new Connection(p.getX(), p.getY(), i, j);
					c.setDirection(Connection.Direction.UPPERRIGHT);
					break;
				} else {
					break;
				}
			}
		} else if (d == Connection.Direction.RIGHT) {
			for (int i = p.getX() + 1; i <= 7; i++) {
				if (cellContent(i, p.getY()) == null) {
				} else if (p.getColor() == cellContent(i, p.getY()).getColor()
						&& cellContent(i, p.getY()).getPainted() == false) {
					c = new Connection(p.getX(), p.getY(), i, p.getY());
					c.setDirection(Connection.Direction.RIGHT);
					break;
				} else {
					break;
				}
			}
		} else if (d == Connection.Direction.LEFT) {

			for (int i = p.getX() - 1; i >= 0; i--) {
				if (cellContent(i, p.getY()) == null) {
				} else if (p.getColor() == cellContent(i, p.getY()).getColor()
						&& cellContent(i, p.getY()).getPainted() == false) {
					c = new Connection(p.getX(), p.getY(), i, p.getY());
					c.setDirection(Connection.Direction.LEFT);
					break;
				} else {
					break;
				}
			}
		} else if (d == Connection.Direction.LOWERLEFT) {
			for (int i = p.getX() - 1, j = p.getY() + 1; i >= 0 && j <= 7; i--, j++) {
				if (cellContent(i, j) == null) {
				} else if (p.getColor() == cellContent(i, j).getColor()
						&& cellContent(i, j).getPainted() == false) {
					c = new Connection(p.getX(), p.getY(), i, j);
					c.setDirection(Connection.Direction.LOWERLEFT);
					break;
				} else {
					break;
				}
			}
		} else if (d == Connection.Direction.DOWN) {
			for (int i = p.getY() + 1; i <= 7; i++) {
				if (cellContent(p.getX(), i) == null) {
				} else if (p.getColor() == cellContent(p.getX(), i).getColor()
						&& cellContent(p.getX(), i).getPainted() == false) {
					c = new Connection(p.getX(), p.getY(), p.getX(), i);
					c.setDirection(Connection.Direction.DOWN);
					break;
				} else {
					break;
				}
			}
		} else if (d == Connection.Direction.UP) {
			for (int i = p.getY() - 1; i >= 0; i--) {
				if (cellContent(p.getX(), i) == null) {
				} else if (p.getColor() == cellContent(p.getX(), i).getColor()
						&& cellContent(p.getX(), i).getPainted() == false) {
					c = new Connection(p.getX(), p.getY(), p.getX(), i);
					c.setDirection(Connection.Direction.UP);
					break;
				} else {
					break;
				}
			}
		} else if (d == Connection.Direction.UPPERLEFT) {
			for (int i = p.getX() - 1, j = p.getY() - 1; i >= 0 && j >= 0; i--, j--) {
				if (cellContent(i, j) == null) {
				} else if (p.getColor() == cellContent(i, j).getColor()
						&& cellContent(i, j).getPainted() == false) {
					c = new Connection(p.getX(), p.getY(), i, j);
					c.setDirection(Connection.Direction.UPPERLEFT);
					break;
				} else {
					break;
				}
			}
		} else if (d == Connection.Direction.LOWERRIGHT) {
			for (int i = p.getX() + 1, j = p.getY() + 1; i <= 7 && j <= 7; i++, j++) {
				if (cellContent(i, j) == null) {
				} else if (p.getColor() == cellContent(i, j).getColor()
						&& cellContent(i, j).getPainted() == false) {
					c = new Connection(p.getX(), p.getY(), i, j);
					c.setDirection(Connection.Direction.LOWERRIGHT);
					break;
				} else {
					break;
				}
			}
		}
		return c;

	}

	public void NetworkHelper(List l, Chip p) {
		try {
			loop: if (potentialNetwork.length() >= 5) {
				if (p.getColor() == BLACK && p.getY() == 7) {
					potentialNetworkCount = 1;
				} else if (p.getColor() == WHITE && p.getX() == 7) {
					potentialNetworkCount = 1;
				}
			} else if (p.getColor() == BLACK && p.getY() == 7) {
			} else if (p.getColor() == WHITE && p.getX() == 7) {
			} else if (l.length() == 0) {
			} else if (l.length() != 0) {
				p.setPainted();
				for (ListNode g = l.front(); g.isValidNode(); g = g.next()) {
					Connection h = (Connection) g.item();
					if (p.getColor() == BLACK && h.getConnectYcoord() == 0) {
					} else if (p.getColor() == WHITE
							&& h.getConnectXcoord() == 0) {
					} else {
						potentialNetwork.insertBack(g);
						List f = search(
								cellContent(h.getConnectXcoord(),
										h.getConnectYcoord()), h.getDirection());
						NetworkHelper(
								f,
								cellContent(h.getConnectXcoord(),
										h.getConnectYcoord()));
						if (potentialNetworkCount != 1) {
							potentialNetwork.back().remove();
						} else {
							p.erasePainted();
							break loop;
						}
					}
				}
				p.erasePainted();
			}
		} catch (InvalidNodeException ex) {
			System.err.println(ex);
		}
	}

	public int findNetwork(int color) {
		potentialNetworkCount = 1;
		potentialNetwork = null;
		List l = new DList();
		loop: if (color == BLACK) {
			for (int i = 1; i < 7; i++) {
				if (cellContent(i, 0) != null
						&& cellContent(i, 0).getColor() == BLACK) {
					l = search(cellContent(i, 0), null);
					if (l.length() != 0) {
						cellContent(i, 0).setPainted();
						try {
							for (ListNode d = l.front(); d.isValidNode(); d = d
									.next()) {
								potentialNetwork.insertBack(d);
								Connection c = (Connection) d.item();
								List f = search(
										cellContent(c.getConnectXcoord(),
												c.getConnectYcoord()),
										c.getDirection());
								NetworkHelper(
										f,
										cellContent(c.getConnectXcoord(),
												c.getConnectYcoord()));
								if (potentialNetworkCount == 1) {
									cellContent(i, 0).erasePainted();
									break loop;
								} else {
									potentialNetwork.back().remove();
								}
							}

						} catch (InvalidNodeException ex) {
							System.err.println(ex);
						}
						cellContent(i, 0).erasePainted();
					}
				}
			}
		}

		else if (color == WHITE) {
			for (int i = 1; i < 7; i++) {
				if (cellContent(0, i) != null
						&& cellContent(0, i).getColor() == WHITE) {
					l = search(cellContent(0, i), null);
					if (l.length() != 0) {
						cellContent(0, i).setPainted();
						try {
							for (ListNode d = l.front(); d.isValidNode(); d = d
									.next()) {
								potentialNetwork.insertBack(l.front());
								Connection c = (Connection) d.item();
								List f = search(
										cellContent(c.getConnectXcoord(),
												c.getConnectYcoord()),
										c.getDirection());
								NetworkHelper(
										f,
										cellContent(c.getConnectXcoord(),
												c.getConnectYcoord()));
								if (potentialNetworkCount == 1) {
									cellContent(0, i).erasePainted();
									break loop;
								} else {
									potentialNetwork.back().remove();
								}
							}
						} catch (InvalidNodeException ex) {
							System.err.println(ex);
						}
						cellContent(i, 0).erasePainted();
					}
				}

			}

		}
		if (potentialNetworkCount == 1) {
			if (color == WHITE) {
				return WHITEWIN;
			} else {
				return BLACKWIN;
			}
		} else {
			return 0;
		}
	}
	
	//the main method of the program. The chooseMove method in the MachinePlayer class will call this method to get the best move.	
public BestMove chooseMove(int alpha, int beta,int depth,int color){
  BestMove mybest= new BestMove();
  BestMove bestReply=null;
  List listMove=generateMoves(color);
  ListNode node=null;
  
  
  if((depth==0)&&(color==WHITE)){
    mybest.score=evaluate();
    return mybest;
    }
  else if((depth==0)&&(color==BLACK)) { 
  			mybest.score=evaluate();
    	    return mybest;
        }
    
  if (color==WHITE) {
  	mybest.score= alpha;
  	}
  	else { mybest.score = beta;
  	}
  	
   if (chipNumber<2){
   	   int chip = Math.abs(((int) Math.random())*10)-3);
   	   if(color==BLACK){
   	   myBest.move =new Move(chip,0);	
  	   }
  	   else { myBest.move= new Move(0,chip);
  	   }
  	   return myBest;
  	 }
   if(chipNumber<4){
		int chip = Math.abs(((int) Math.random())*10)-3);
   		if(color==BLACK){
   			myBest.move=new Move(chip, LENGTH+1);
   		else { myBest.move=new Move(LENGTH+1,chip);
   		}
   	  return mybest;
   	}
   	else { 
   	 node = listMove.front();
   	 Move m=node.item;
   	 for(int i=0; i<listMove.lenghth(); i++){
   	 	performMove(color, m);
   	 	if(color==WHITE){
   	 	bestReply = chooseMove(alpha, beta, depth--, BLACK);
   	 	}
   		else { 
   		bestReply = chooseMove(alpha, beta, depth--,WHITE);
   		}
   		undoMove(color, m);
   		if((color==WHITE)&&(bestReply.score>myBest.score)) {
   			myBest.move=m;
   			myBest.score=bestReply.score;
   		    alpha=bestReply.score;
   		}
   		else { if ((color==BLACK)&&(bestReply.score < myBest.score)){
   					myBest.move = m;
   					myBest.score = bestReply.score;
   					beta = bestReply.score;
   					}
   			  }
   		if (alpha >= beta) {
   		 return myBest;
   		 }
   		node= listMove.next(node);
   	     }
   	 }
   	   return myBest;
}

}
}