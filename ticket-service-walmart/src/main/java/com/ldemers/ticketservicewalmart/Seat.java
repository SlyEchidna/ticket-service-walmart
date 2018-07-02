/**
 * @author Lakota Demers
 */
package com.ldemers.ticketservicewalmart;

public class Seat {

	private int row;
    private int column;
	private String state;

	/**
	 * A seat consists of a row number, column number, and is
	 * has an alterable state.
	 * 
	 */
	public Seat(int row, int column, String state) {
        this.row = row;
        this.column = column;
        this.state = state;
    }
	
	public void setState(String state) {
		this.state = state;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getRow() {
		return row;
	}
	
	public String getState() {
		return state;
	}
    
    public boolean isAvail() {
    	if (state == "AVAIL") {
    		return true;
    	}else return false;
    }
}
