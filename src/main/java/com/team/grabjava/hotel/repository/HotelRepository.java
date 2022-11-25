package com.team.grabjava.hotel.repository;

import com.team.grabjava.hotel.entity.Hotel;
import com.team.grabjava.hotel.entity.Room;

import java.util.List;

public class HotelRepository {
    private Hotel hotel = new Hotel();

    public int getAsset(){
        return hotel.getHotelAsset();
    }
    public void setAsset(int price){
        hotel.setAsset(price);
    }

    public List<Room> getRoomList(){
        return hotel.getRoomList();
    }
}
