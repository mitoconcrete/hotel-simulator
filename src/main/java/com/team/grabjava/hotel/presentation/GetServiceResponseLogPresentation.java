package com.team.grabjava.hotel.presentation;

import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.entity.Room;

import java.util.List;

public class GetServiceResponseLogPresentation {
    public void showGetHotelAllReservationList(List<Reservation> reservations){
        for(Reservation reservation: reservations){
            System.out.println(reservation.getReservationId() + " / " + reservation.getRoom().getRoomNo() + " / " + reservation.getUserName() + " / " + reservation.getUserPhone() + " / " + reservation.getReservationDate());
        }
    }

    public void showGetHotelAsset(int hotelAsset){
        System.out.println("호텔의 현재 보유금 : " + hotelAsset + " 원");
    }

    public void showGetUserAllReservationIdList(List<String> reservationIdList){
        for(String reservationId : reservationIdList){
            System.out.println(reservationId);
        }
    }

    public void showGetUserReservationInfoMessage(String reservationInfo){
        System.out.println(reservationInfo);
    }

    public void showGetHotelRoomListMessage(List<Room> roomList){
        for(Room room : roomList){
            System.out.println(room.getRoomNo() + " / " + room.getSize()+"평" + " / " + room.getPrice() + "원");
        }
    }



}
