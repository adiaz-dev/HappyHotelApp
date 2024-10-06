package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Mockito tries to match the BDD style (Behavior Driven Development) by using some aliases
 * */
@ExtendWith(MockitoExtension.class)
class Test12Bdd {

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
  void should_CountAvailablePlaces_When_OneRoomAvailable() {
    //given
    given(this.roomServiceMock.getAvailableRooms()).willReturn(
        Collections.singletonList(new Room("Room 1", 2)));
    int expected = 2;

    //when
    int actual = bookingService.getAvailablePlaceCount();

    //then
    assertEquals(expected, actual);
  }

  @Test
  void should_InvokePayment_When_Prepaid(){
    //given
    BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),LocalDate.of(2020, 01, 05), 2, true);

    //when
    bookingService.makeBooking(bookingRequest);

    //then
    then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0);
    verifyNoMoreInteractions(paymentServiceMock);
  }

}
