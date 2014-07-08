public class Pair {
    private Integer row;
    private Integer column;

    public Pair(int row, int col) {
    	super();
    	this.row = row;
    	this.column = column;
    }

    public boolean equals(Object other) {
    	if (other instanceof Pair) {
    		Pair otherPair = (Pair) other;
    		return 
    		((  this.row == otherPair.row ||
    			( this.row != null && otherPair.row != null &&
    			  this.row.equals(otherPair.row))) &&
    		 (	this.column == otherPair.column ||
    			( this.column != null && otherPair.column != null &&
    			  this.column.equals(otherPair.column))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + row + ", " + column + ")"; 
    }

    public int getRow() {
    	return row;
    }

    public void setRow(int row) {
    	this.row = row;
    }

    public int getColumn() {
    	return column;
    }

    public void setColumn(int column) {
    	this.column = column;
    }
}