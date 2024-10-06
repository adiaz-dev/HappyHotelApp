package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * With arguments captors, you can grab the values that were used to call other services, so you capture the arguments used in the internal dependencies of the methods you are calling.
 * */
class Test10ArgumentCaptors {

  private BookingService bookingService;

  //mock dependencies
  private PaymentService paymentServiceMock;
  private RoomService roomServiceMock;
  private BookingDAO bookingDAOMock;
  private MailSender mailSenderMock;
  private ArgumentCaptor<Double> doubleCaptor;


  @BeforeEach
  void setUp() {
    //first do create the mocks of those services
    this.paymentServiceMock = mock(PaymentService.class);
    this.roomServiceMock = mock(RoomService.class);
    this.bookingDAOMock = mock(BookingDAO.class);
    this.mailSenderMock = mock(MailSender.class);

    //configure the booking service
    this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

    this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
  }



  @Test
  void should_PayCorrectPrice_When_InputOK() {
    //given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),LocalDate.of(2020, 01, 05), 2, true);

    //when
    bookingService.makeBooking(bookingRequest);

    //then
    verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());
    double captured = doubleCaptor.getValue();
    assertEquals(400.0, captured);
  }

  @Test
  void should_PayCorrectPrice_When_MultipleInput() {
    List<Double> expectedValues = Arrays.asList(400.0, 100.0);
    //given
    BookingRequest bookingRequest5Nigth = new BookingRequest("1", LocalDate.of(2020, 01, 01),LocalDate.of(2020, 01, 05), 2, true);
    BookingRequest bookingRequest1Nigth= new BookingRequest("1", LocalDate.of(2020, 01, 01),LocalDate.of(2020, 01, 02), 2, true);

    //when
    bookingService.makeBooking(bookingRequest5Nigth);
    bookingService.makeBooking(bookingRequest1Nigth);

    //then
    verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
    List<Double> capturedValues = doubleCaptor.getAllValues();
    assertEquals(expectedValues, capturedValues);
  }


}
