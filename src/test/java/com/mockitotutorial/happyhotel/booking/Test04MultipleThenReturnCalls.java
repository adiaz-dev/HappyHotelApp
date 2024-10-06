package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test04MultipleThenReturnCalls {

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
    this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock,
        mailSenderMock);
  }


  @Test
  void should_CountAvailablePlaces_When_CalledMultipleTimes() {
    //given
    int expectedFirstCall = 5;
    int expectedSecondCall = 0;

    //when
    when(this.roomServiceMock.getAvailableRooms()).thenReturn(
        Collections.singletonList(new Room("Room 1", 5))).thenReturn(Collections.emptyList());

    //call the method under testing
    int actualFirstCall = bookingService.getAvailablePlaceCount();
    int actualSecondCall = bookingService.getAvailablePlaceCount();

    //then
    assertAll(
        () -> assertEquals(expectedFirstCall, actualFirstCall),
        () -> assertEquals(expectedSecondCall, actualSecondCall)
    );

  }

}
