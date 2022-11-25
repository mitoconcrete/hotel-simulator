package com.team.grabjava.hotel.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class Reservation {
    private String id;
    // 예약 번호
    private Room room;                 // 객실 번호
    private String userName;            // 고객 이름
    private String userPhone;           // 고객 전화번호
    private String reservationDate;       // 예약 날짜

    public Reservation(Room room, String userName, String userPhone, String reservationDate) {
        this.id = UUID.randomUUID().toString();
        this.room = room;
        this.userName = userName;
        this.userPhone = userPhone;
        this.reservationDate = ZonedDateTime.now(ZoneId.of("+9")).format(DateTimeFormatter.ofPattern(reservationDate+"'T'HH:mm:ssz"));
    }

    public String getId() {
        return id;
    }

    public int getRoomNo() {
        return room.getRoomNo();
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getReservationDate() {
        return reservationDate;
    }
}
