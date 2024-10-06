package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.lenient;
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
 * When using MockitoExtension, automatically strict stubbing is required by Mockito, so you must
 * only mock the classes/methods you actually use in your tests, because if you mock objects which
 * are not actually called, you will get a Mockito exception:
 * org.mockito.exceptions.misusing.UnnecessaryStubbingException: Unnecessary stubbings detected.
 * Clean & maintainable test code requires zero unnecessary code
 * <p>
 * You can use lenient, but it is not recommended to have mock objects which are actually not used
 */
@ExtendWith(MockitoExtension.class)
class Test14StaticMethods {

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

      double expected = 400.0;
      mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);

      double actual = bookingService.calculatePriceEuro(bookingRequest);

      //then
      assertEquals(expected, actual);

    }



  }

}
