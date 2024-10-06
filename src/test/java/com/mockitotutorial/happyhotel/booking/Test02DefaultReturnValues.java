package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test02DefaultReturnValues {

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

    //nice mock default values, for example
    //empty list
    System.out.println("List returned " + roomServiceMock.getAvailableRooms());
    //null objects
    System.out.println("Object returned " + roomServiceMock.findAvailableRoomId(null));
    //0 or false for primitives
    System.out.println("Primitive returned " + roomServiceMock.getRoomCount());
  }



  @Test
  void should_CountAvailablePlaces() {
    //given
    int expected = 0;

    //when
    int actual = bookingService.getAvailablePlaceCount();

    //then
    assertEquals(expected, actual);
  }

}
