package com.team.grabjava.hotel.entity;



import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Reservation {
    private final String uuid;                    // 예약 번호(UUID)
    private int roomNo;                          // 객실 번호
    private String userName;                    // 고객 이름
    private String userPhone;                   // 고객 전화번호

    private ZonedDateTime reservationDate;      // 예약 날짜




    public Reservation(int roomNo, String userName, String userPhone) {
        this.uuid = UUID.randomUUID().toString();
        this.roomNo = roomNo;
        this.userName = userName;
        this.userPhone = userPhone;
        this.reservationDate = ZonedDateTime.now();
    }

    public String getUuid() {
        return uuid;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public ZonedDateTime getReservationDate() {
        return reservationDate;
    }
}
