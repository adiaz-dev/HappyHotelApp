package com.mockitotutorial.happyhotel.booking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test07VerifyingBehaviour {

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
    this.bookingDAOMock = mock(BookingDAO.class);
    this.mailSenderMock = mock(MailSender.class);

    //configure the booking service
    this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
  }


  @Test
  void should_InvokePayment_When_Prepaid(){
    //given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),LocalDate.of(2020, 01, 05), 2, true);

    //when
    bookingService.makeBooking(bookingRequest);

    //then
    verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0);
    verifyNoMoreInteractions(paymentServiceMock);
  }

  @Test
  void should_NotInvokePayment_When_NoPrepaid(){
    //given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),LocalDate.of(2020, 01, 05), 2, false);

    //when
    bookingService.makeBooking(bookingRequest);

    //then
    //pay() method must be called only if prepaid is True
    verify(paymentServiceMock, never()).pay(any(), anyDouble());
  }

}
