package player;

public class Gameboard {
	private Chip[][] board=new Chip[8][8];
	private MachinePlayer player;
	private int chipNumber;
	public static final int EMPTY = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 2;
	public static final int WHITEWIN = 100;
	public static final int BLACKWIN = -100;
	private List potentialNetwork;
	private int potentialNetworkCount;
	private Chip[] neighbor;
	private int connectionNumber;
	

	public Chip cellContent(int x, int y) {
		return board[x][y];
	}

	
	
	public Chip[] neighbors(int x, int y) {
	   if (x == 0) {
			neighbor = new Chip[5];
			neighbor[0] = cellContent(0, y + 1);
			neighbor[1] = cellContent(1, y + 1);
			neighbor[2] = cellContent(1, y);
			neighbor[3] = cellContent(1, y - 1);
			neighbor[4] = cellContent(0, y - 1);
		} else if (x == 7) {
			neighbor = new Chip[5];
			neighbor[0] = cellContent(7, y + 1);
			neighbor[1] = cellContent(6, y + 1);
			neighbor[2] = cellContent(6, y);
			neighbor[3] = cellContent(6, y - 1);
			neighbor[4] = cellContent(7, y - 1);
		} else if (y == 0) {
			neighbor = new Chip[5];
			neighbor[0] = cellContent(x - 1, 0);
			neighbor[1] = cellContent(x - 1, 1);
			neighbor[2] = cellContent(x, 1);
			neighbor[3] = cellContent(x + 1, 1);
			neighbor[4] = cellContent(x + 1, 0);
		} else if (y == 7) {
			neighbor = new Chip[5];
			neighbor[0] = cellContent(x - 1, 7);
			neighbor[1] = cellContent(x - 1, 6);
			neighbor[2] = cellContent(x, 6);
			neighbor[3] = cellContent(x + 1, 6);
			neighbor[4] = cellContent(x + 1, 7);
		} else {
			neighbor = new Chip[8];
			neighbor[0] = cellContent(x - 1, y - 1);
			neighbor[1] = cellContent(x - 1, y);
			neighbor[2] = cellContent(x - 1, y + 1);
			neighbor[3] = cellContent(x, y + 1);
			neighbor[4] = cellContent(x + 1, y + 1);
			neighbor[5] = cellContent(x + 1, y);
			neighbor[6] = cellContent(x + 1, y - 1);
			neighbor[7] = cellContent(x, y - 1);
		}
		return neighbor;

	}
    public List search(Chip p,Connection.Direction d){
    	List l=new DList();
    	if (p.getX()==0){
    		Connection.Direction[] directions={Connection.Direction.RIGHT,Connection.Direction.UPPERRIGHT,Connection.Direction.LOWERRIGHT};
    		for (Connection.Direction h :directions){
    			Connection c= explore(p,h);
    			if (c != null && c.getConnectXcoord() !=7){
    				l.insertBack(c);
    				connectionNumber++;
    			}
    		}
    	}else if (p.getX()==7){
    		Connection.Direction[] directions={Connection.Direction.LEFT,Connection.Direction.UPPERLEFT,Connection.Direction.LOWERLEFT};
    		for (Connection.Direction h :directions){
    			Connection c= explore(p,h);
    			if (c != null && c.getConnectXcoord() !=0){
    				l.insertBack(c);
    				connectionNumber++;
    			}
    		}
    	}else if (p.getY()==0){
    		Connection.Direction[] directions={Connection.Direction.DOWN,Connection.Direction.LOWERLEFT,Connection.Direction.LOWERRIGHT};
    		for (Connection.Direction h :directions){
    			Connection c= explore(p,h);
    			if (c != null && c.getConnectYcoord() !=7){
    				l.insertBack(c);
    				connectionNumber++;
    			}
    		}
    	}else if (p.getY()==7){
    		Connection.Direction[] directions={Connection.Direction.UP,Connection.Direction.UPPERLEFT,Connection.Direction.UPPERRIGHT};
    		for (Connection.Direction h :directions){
    			Connection c= explore(p,h);
    			if (c != null && c.getConnectYcoord() !=0){
    				l.insertBack(c);
    				connectionNumber++;
    			}
    		}
    	}
    	else{
    		for (Connection.Direction h :Connection.Direction.values()){
    	      if(h !=d && h !=Connection.getOppositeDirection(d)){
            	Connection c= explore(p,h);
            	if (c != null){
            	l.insertBack(c);
            	connectionNumber++;
            	}
            }
    	}
    	}
    	return l;
    }
            	
    public Connection explore(Chip p, Connection.Direction d){
    	Connection c =null;
    	if(d==Connection.Direction.UPPERRIGHT){
    		for (int i = p.getX() + 1, j = p.getY() - 1; i <= 7 && j >= 0; i++, j--) {
    			if (cellContent(i, j) == null) {
    			} else if (p.getColor()== cellContent(i, j).getColor()&& cellContent(i,j).getPainted()==false) {
    				c = new Connection(p.getX(), p.getY(), i, j);
    				c.setDirection(Connection.Direction.UPPERRIGHT);
    				break;
    			}else {
    				break;
    			}
    		}
    	}else if (d==Connection.Direction.RIGHT){
            for (int i = p.getX() + 1; i <= 7; i++) {
			if (cellContent(i, p.getY()) == null) {
			} else if (p.getColor() == cellContent(i, p.getY()).getColor()&& cellContent(i,p.getY()).getPainted()==false) {
			    c = new Connection(p.getX(), p.getY(), i, p.getY());
				c.setDirection(Connection.Direction.RIGHT);
			    break;
			}else {
				break;
			}
		}
    	}else if (d==Connection.Direction.LEFT){
     
    	for (int i = p.getX() - 1; i >= 0; i--) {
		    if (cellContent(i, p.getY()) == null) {
			} else if (p.getColor() == cellContent(i, p.getY()).getColor() && cellContent(i,p.getY()).getPainted()==false) {
				c = new Connection(p.getX(), p.getY(), i, p.getY());
				c.setDirection(Connection.Direction.LEFT);
			    break;
			}else {
				break;
			}
		}
		}else if (d==Connection.Direction.LOWERLEFT){
          for (int i = p.getX()- 1, j = p.getY() + 1; i >= 0 && j <= 7; i--, j++) {
        	  if (cellContent(i,j)==null) {
  			}else if (p.getColor() == cellContent(i, j).getColor() && cellContent(i,j).getPainted()==false) {
			    c = new Connection(p.getX(), p.getY(), i, j);
				c.setDirection(Connection.Direction.LOWERLEFT);
				break;
			} else {
				break;
			}
		}
    }else if (d==Connection.Direction.DOWN){
    	for (int i = p.getY() + 1; i <= 7; i++) {
			if (cellContent(p.getX(), i) == null) {
			}else if (p.getColor() == cellContent(p.getX(), i).getColor() && cellContent(p.getX(),i).getPainted()==false) {
				c = new Connection(p.getX(), p.getY(), p.getX(), i);
				c.setDirection(Connection.Direction.DOWN);
			    break;
			} else {
				break;
			}
		}
    }else if (d==Connection.Direction.UP){
    	for (int i =p.getY() -1;i>=0;i--){
    		if (cellContent(p.getX(), i) == null) {
			}else if (p.getColor() == cellContent(p.getX(), i).getColor() && cellContent(p.getX(),i).getPainted()==false) {
				c = new Connection(p.getX(), p.getY(), p.getX(), i);
				c.setDirection(Connection.Direction.UP);
			    break;
			} else {
				break;
			}
		}
    	}else if (d==Connection.Direction.UPPERLEFT){
    		for (int i = p.getX() - 1, j = p.getY() - 1; i >= 0 && j >= 0; i--, j--) {
    			if (cellContent(i, j) == null) {
				} else if (p.getColor() == cellContent(i, j).getColor() &&cellContent(i,j).getPainted()==false) {
				    c = new Connection(p.getX(), p.getY(), i, j);
					c.setDirection(Connection.Direction.UPPERLEFT);
			        break;
				} else {
					break;
				}
			}
    	}else if(d==Connection.Direction.LOWERRIGHT){
			for (int i = p.getX() + 1, j = p.getY() + 1; i <= 7 && j <= 7; i++, j++) {
				if (cellContent(i, j) == null) {
				}else if (p.getColor() == cellContent(i, j).getColor() && cellContent(i,j).getPainted()==false) {
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
								}else {
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
}
