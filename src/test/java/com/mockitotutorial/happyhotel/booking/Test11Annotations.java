package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * You can use Mockito annotations instead of mock() methods, just remember to use the ExtendWith
 * */
@ExtendWith(MockitoExtension.class)
class Test11Annotations {

  @InjectMocks
  private BookingService bookingService;

  //mock dependencies
  @Mock
  private PaymentService paymentServiceMock;
  @Mock
  private RoomService roomServiceMock;
  @Mock
  private BookingDAO bookingDAOMock;
  @Mock
  private MailSender mailSenderMock;
  @Captor
  private ArgumentCaptor<Double> doubleCaptor;


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
