package player;

public class Chip {
	private int Xcoord;
	private int Ycoord;
	private int color;
	private boolean painted;

	public Chip(int color, int x,int y){
		Xcoord=x;
		Ycoord=y;
		this.color=color;
	}
	
	  void erasePainted(){
	    	this.painted=false;
	    }
	    void setPainted(){
	    	this.painted=true;
	    }
	    boolean getPainted(){
	    	return this.painted;
	    }
    public int getColor(){
    	return color;
    }
    public int getX(){
    	return Xcoord;
    }
    public int getY(){
    	return Ycoord;
    }
  
}
