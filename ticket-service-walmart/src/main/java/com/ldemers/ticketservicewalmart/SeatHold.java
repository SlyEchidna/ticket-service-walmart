/**
 * @author Lakota Demers
 */
package com.ldemers.ticketservicewalmart;

import java.util.Random;
import java.util.Set;

public class SeatHold {
	
	private int seatHoldId;
	
	private int numSeats;
	
	private String customerEmail;
	
	private long expTimer;
	
	private Set<Seat> heldSeats;
	
	private String confCode;
	
	/**
	 * Creates an Object that is used to store and handle customer seat orders.
	 * 
	 * @param customerEmail A variable provided by the customer to identify them.
	 * @param heldSeats A HashSet used to store the seats held for the SeatHold object.
	 */
	public SeatHold(String customerEmail, Set<Seat> heldSeats) {
		Random random = new Random();
		this.seatHoldId = random.nextInt(900000000)+100000000;
		this.numSeats = heldSeats.size();
		this.customerEmail = customerEmail;
		this.expTimer = System.currentTimeMillis() + 10000;
		this.heldSeats = heldSeats;
	}
	
	public int getSeatHoldId() {
		return seatHoldId;
	}
	
	public int getNumSeats() {
		return numSeats;
	}
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	
	public long getExpTimer() {
		return expTimer;
	}
	
	public Set<Seat> getHeldSeats() {
		return heldSeats;
	}
	
	public String getConfCode() {
		return confCode;
	}
	
	public void setConfCode(String confCode) {
		this.confCode = confCode;
	}
}
