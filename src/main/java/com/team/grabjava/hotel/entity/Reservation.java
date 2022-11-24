package com.team.grabjava.hotel.entity;

import java.util.Date;

public class Reservation {
    private String id;                  // 예약 번호
    private int roomNo;                 // 객실 번호
    private String userName;            // 고객 이름
    private String userPhone;           // 고객 전화번호
    private Date reservationDate;       // 예약 날짜

    public Reservation(String id, int roomNo, String userName, String userPhone) {
        this.id = id;
        this.roomNo = roomNo;
        this.userName = userName;
        this.userPhone = userPhone;
        this.reservationDate = new Date();
    }

    public String getId() {
        return id;
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

    public Date getReservationDate() {
        return reservationDate;
    }
}
