/**
 * @author Lakota Demers
 */
package com.ldemers.ticketservice.test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static java.lang.Thread.sleep;

import com.ldemers.ticketservicewalmart.SeatHold;
import com.ldemers.ticketservicewalmart.service.WalmartTicketService;

public class TicketServiceTest {

	String testEmail = "ldemers@walmart.com";

    @Rule
    public ExpectedException expEx = ExpectedException.none();
	
	@Test
	public void testInitialVenueSize(){
		WalmartTicketService wts = new WalmartTicketService();
		assertEquals(200, wts.numSeatsAvailable());
	}
	
	@Test
	public void testHoldSeats(){
		WalmartTicketService wts = new WalmartTicketService();
		@SuppressWarnings("unused")
		SeatHold seatHold = wts.findAndHoldSeats(5, testEmail);
		assertEquals(195, wts.numSeatsAvailable());
	}

	@Test
	public void testHoldMoreThanAvailable() {
		WalmartTicketService wts = new WalmartTicketService();
        expEx.expect(IllegalArgumentException.class);
        expEx.expectMessage("Not enough seats are left to fulfill your order.");
		@SuppressWarnings("unused")
		SeatHold seatHold = wts.findAndHoldSeats(201, testEmail);
		assertEquals(200, wts.numSeatsAvailable());
	}

	@Test
	public void testHoldNothing() {
		WalmartTicketService wts = new WalmartTicketService();
        expEx.expect(IllegalArgumentException.class);
        expEx.expectMessage("You must hold at least 1 seat.");
		@SuppressWarnings("unused")
		SeatHold seatHold = wts.findAndHoldSeats(0, testEmail);
		assertEquals(200, wts.numSeatsAvailable());
	}
	
	@Test
	public void testExpiredHoldsReturned() throws InterruptedException{
		WalmartTicketService wts = new WalmartTicketService();
		@SuppressWarnings("unused")
		SeatHold seatHold = wts.findAndHoldSeats(5, testEmail);
		sleep(10001);
		wts.clearExpiredHolds();
		assertEquals(200, wts.numSeatsAvailable());
	}
	
	@Test
	public void testConfCodeLength(){
		WalmartTicketService wts = new WalmartTicketService();
		SeatHold seatHold = wts.findAndHoldSeats(5, testEmail);
		String confCode = wts.reserveSeats(seatHold.getSeatHoldId(), seatHold.getCustomerEmail());
		assertEquals(13, confCode.length());
	}
	
	@Test
	public void testReserveWithIncorrectEmail(){
		WalmartTicketService wts = new WalmartTicketService();
		SeatHold seatHold = wts.findAndHoldSeats(5, testEmail);
        expEx.expect(IllegalArgumentException.class);
        expEx.expectMessage("Your email does not match the email on file for this hold.");
        wts.reserveSeats(seatHold.getSeatHoldId(), "imposter@walmart.com");
	}
	
	@Test
	public void testReserveExpiredHold() throws InterruptedException{
		WalmartTicketService wts = new WalmartTicketService();
		SeatHold seatHold = wts.findAndHoldSeats(5, testEmail);
        sleep(10001);
        expEx.expect(IllegalArgumentException.class);
        expEx.expectMessage("Your hold reservation has expired. Please place a new hold.");
        wts.reserveSeats(seatHold.getSeatHoldId(), testEmail);
	}
}
