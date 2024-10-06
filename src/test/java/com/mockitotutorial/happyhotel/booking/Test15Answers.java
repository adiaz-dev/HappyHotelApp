package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * With Answers you can have a more complex logic to return from the static methods
 */
@ExtendWith(MockitoExtension.class)
class Test15Answers {

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
  void should_InvokePayment_When_Prepaid() {

    try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {
      //given
      BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
          LocalDate.of(2020, 01, 05), 2, false);

      double expected = 400.0 * 0.8;
      mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenAnswer(inv -> (double) inv.getArgument(0)*0.8);

      double actual = bookingService.calculatePriceEuro(bookingRequest);

      //then
      assertEquals(expected, actual);

    }



  }

}
