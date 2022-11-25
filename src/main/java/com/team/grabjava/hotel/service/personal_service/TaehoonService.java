package com.team.grabjava.hotel.service.personal_service;

import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.repository.HotelRepository;
import com.team.grabjava.hotel.repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TaehoonService {
    ReservationRepository reservationRepository = new ReservationRepository();
    public List<Reservation> getReservationNumberList(String userName, String userPhone){
        List<Reservation> reservations = reservationRepository.getReservationList();
        return reservations.stream().filter(reservation -> reservation.getUserName().equals(userName)
                                            && reservation.getUserPhone().equals(userPhone))
                                    .map(reservation -> reservation.getId())
                                    .collect(Collectors.toList());
    }

    public boolean checkAdminPassword(String password){
        final String adminPassword = "password";
        return adminPassword.equals(password);
    }
}
