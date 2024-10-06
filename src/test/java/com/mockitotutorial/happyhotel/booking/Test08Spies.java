package com.mockitotutorial.happyhotel.booking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Mock = creates dummy object with no real logic
 * Spy  = real object with real logic that we can modify. we can modify only partial sections of the real object
 * */
class Test08Spies {

  private BookingService bookingService;

  //mock dependencies
  private PaymentService paymentServiceMock;
  private RoomService roomServiceMock;
  private BookingDAO bookingDAOMock;
  private MailSender mailSenderMock;

  @BeforeEach
  void setUp() {
    //first do create the mocks of those services
    this.paymentServiceMock = mock(PaymentService.class);
    this.roomServiceMock = mock(RoomService.class);
    this.bookingDAOMock = spy(BookingDAO.class);
    this.mailSenderMock = mock(MailSender.class);

    //configure the booking service
    this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
  }


  @Test
  void should_MakeBooking_When_InputOK(){
    //given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),LocalDate.of(2020, 01, 05), 2, true);

    //when
    String bookingId = bookingService.makeBooking(bookingRequest);

    //then
    verify(bookingDAOMock, times(1)).save(bookingRequest);
    System.out.println("bookingId: " + bookingId);

  }

  @Test
  void should_CancelBooking_When_InputOK(){
    //given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),LocalDate.of(2020, 01, 05), 2, false);
    bookingRequest.setRoomId("1.3");
    String bookingId = "1";

    //we are partially mocking/altering the get() method of the DAO to return the bookingRequest
    doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);

    //when
    bookingService.cancelBooking(bookingId);
    //then

  }

}
