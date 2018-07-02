/**
 * @author Lakota Demers
 */
package com.ldemers.ticketservicewalmart.service;

import java.util.*;
import com.ldemers.ticketservicewalmart.Seat;
import com.ldemers.ticketservicewalmart.SeatHold;
import com.ldemers.ticketservicewalmart.Venue;


public class WalmartTicketService implements TicketService {
	
	private Venue venue;

    public Map<Integer, SeatHold> holdMap = new HashMap<Integer, SeatHold>();
    
    
    /**
     * Calls in and creates a Venue Object.
     */
	public WalmartTicketService() {
		venue = new Venue();
	}

	/**
	 * Shows the number of seats marked as AVAIL within the Venue Object.
	 */
	public int numSeatsAvailable() {
		return venue.getAvailSeats();
	}

	/**
	 * Checks to ensure at least one seat is looking to be held and that enough seats are
	 * available within the venue. It then puts a hold on the best seats that it finds. Also 
	 * calls the function to clear expired seat holds to potentially open up more seats for the customer.
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		
		clearExpiredHolds();
		
		if (numSeats<=0)
			throw new IllegalArgumentException("You must hold at least 1 seat.");
		
		if (numSeats>numSeatsAvailable())
			throw new IllegalArgumentException("Not enough seats are left to fulfill your order.");
		
		Set<Seat> seatOrder = findBestSeats(numSeats);
		SeatHold seatHold = new SeatHold(customerEmail, seatOrder);
		holdMap.put(seatHold.getSeatHoldId(), seatHold);
		return seatHold;
	}
	
	/**
	 * This function is use to set the status of a seat to reserved or to available if the
	 * hold time has expired. It provides the user with an Confirmation Code upon successful
	 * reservation or a message if the hold has expired. It also throws an Exception if provided
	 * with an email that does not match the email tied to the seatHoldId.
	 */
	public String reserveSeats(int seatHoldId, String customerEmail) {
		SeatHold seatHold = holdMap.get(seatHoldId);
		
		if(seatHold.getCustomerEmail() != customerEmail)
			throw new IllegalArgumentException("Your email does not match the email on file for this hold.");
		
		if(seatHold.getExpTimer()<System.currentTimeMillis()){
			throw new IllegalArgumentException("Your hold reservation has expired. Please place a new hold.");
		}
		
		Iterator<Seat> iterator = seatHold.getHeldSeats().iterator();
		int count = seatHold.getNumSeats();
		int temp = count;

		while (iterator.hasNext()) {
			Object seatId = iterator.next();
			for(int a = 0; a < venue.getRowSize();a++) {
				
				for(int b = 0; b < venue.getColumnSize();b++) {
					
					if(venue.getSeat(a, b)==seatId) {
						venue.getSeat(a, b).setState("RESRV");
						count--;
					}
					
					if(count<temp) {
						break;
					}
				}
				
				if(count<temp) {
					temp--;
					break;
				}
			}
		}
		
		Random random = new Random();
		int num1 = random.nextInt(9000)+1000;
		int num2 = random.nextInt(90000)+10000;
		String confCode = "WV-"+num1+"-"+num2;
		seatHold.setConfCode(confCode);
		return confCode;
	}
	
	/**
	 * A function used to switch expired held seats back to being available.
	 */
	public void clearExpiredHolds() {
		Iterator<Integer> holdIterator = holdMap.keySet().iterator();
		while(holdIterator.hasNext()) {
			int holdId = holdIterator.next();
			SeatHold seatHold = holdMap.get(holdId);
			Iterator<Seat> seatIterator = seatHold.getHeldSeats().iterator();
			while(seatIterator.hasNext()) {
				
				Object seatId = seatIterator.next();
				for(int a = 0; a < venue.getRowSize();a++) {
					
					for(int b = 0; b < venue.getColumnSize();b++) {
						
						if(venue.getSeat(a, b)==seatId&&venue.getSeat(a, b).getState()=="HELD") {
							
							if(seatHold.getExpTimer()<System.currentTimeMillis()) {
								venue.getSeat(a, b).setState("AVAIL");
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * In this instance the best seats are considered to be closer to the stage and 
	 * the rows alternate the side from which they are filled in order to keep groups
	 * closer together.
	 * 
	 * @param numSeats The number of seats to find.
	 */
	public Set<Seat> findBestSeats(int numSeats){
		Set<Seat> seatFinder = new HashSet<Seat>();
		int count = numSeats;
		for(int a = 0; a < venue.getRowSize();a++) {
			if(a%2==0) {
				for(int b = 0; b < venue.getColumnSize();b++) {
					if(count == 0) {
						break;
					}else if(venue.getSeat(a, b).getState() == "AVAIL") {
						venue.getSeat(a, b).setState("HELD");
						count--;
						seatFinder.add(venue.getSeat(a, b));
					}
				}
			}else {
				for(int c = venue.getColumnSize()-1; c >= 0;c--) {
					if(count == 0) {
						break;
					}else if(venue.getSeat(a, c).getState() == "AVAIL") {
						venue.getSeat(a, c).setState("HELD");
						count--;
						seatFinder.add(venue.getSeat(a, c));
					}
				}
			}
		}
		return seatFinder;
	}
}
