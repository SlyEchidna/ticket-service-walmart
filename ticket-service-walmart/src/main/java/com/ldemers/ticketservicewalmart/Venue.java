/**
 * @author Lakota Demers
 */
package com.ldemers.ticketservicewalmart;

public class Venue {
	
	private final static int numRows = 20;
	private final static int numColumns = 10;
	private final static int venueSize = numColumns * numRows;
	private final static Seat[][] seat = new Seat[numRows][numColumns];
	
	/**
	 * Initializes with a fixed number of rows and columns.
	 */
	public Venue() {
		buildVenue();
	}
	
	public void buildVenue() {
		String state = "AVAIL";
		for(int a=0;a<getRowSize();a++) {
			for(int b=0;b<getColumnSize();b++) {
				seat[a][b] = new Seat(a,b,state);
			}
		}
	}
	
	public int getAvailSeats() {
		int availSeats = 0;
		for(int a=0;a<getRowSize();a++) {
			for(int b=0;b<getColumnSize();b++) {
				if(seat[a][b].isAvail()==true) {
					availSeats++;
				}
			}
		}
		return availSeats;
	}
	
	public Seat getSeat(int a, int b) {
		return seat[a][b];
	}
	
	public int getRowSize() {
		return seat.length;
	}
	
	public int getColumnSize() {
		return seat[0].length;
	}
	
	public int getVenueSize() {
		return venueSize;
	}
}
