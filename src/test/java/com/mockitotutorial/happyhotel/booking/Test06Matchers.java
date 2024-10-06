package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class Test06Matchers {

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
  void should_NotCompleBooking_When_PriceTooHigh() {
    //given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),LocalDate.of(2020, 01, 05), 2, true);

    //thrown a RuntimeException on any object pass as argument to the mock object
    when(this.paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class);

    //when
    Executable executable = () -> bookingService.makeBooking(bookingRequest);

    //then
    assertThrows(BusinessException.class, executable);
  }

}
