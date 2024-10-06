package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test03ReturningCustomValues {

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
  void should_CountAvailablePlaces_When_OneRoomAvailable() {
    //given
    int expected = 2;

    //when
    when(this.roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room 1", 2)));
    int actual = bookingService.getAvailablePlaceCount();

    //then
    assertEquals(expected, actual);
  }

  @Test
  void should_CountAvailablePlaces_When_MultipleRoomsAvailable() {
    //given
    int expected = 7;
    List<Room> rooms = Arrays.asList(new Room("Room 1", 2), new Room("Room 2", 5));

    //when
    when(this.roomServiceMock.getAvailableRooms()).thenReturn(rooms);
    int actual = bookingService.getAvailablePlaceCount();

    //then
    assertEquals(expected, actual);
  }

}
