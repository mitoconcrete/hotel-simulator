package com.team.grabjava.hotel.entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private final int INITIAL_ROOM_COUNT = 15;
    private final int INIITAL_HOTEL_ASSSET = 100_000;

    private final String ADMIN_PASSWORD = "password";
    private List<Room> roomList = new ArrayList<>();
    private int hotelAsset;

    public Hotel(){
        for (int i = 1; i <= INITIAL_ROOM_COUNT; i++) {
            if (i < 6) this.roomList.add(new Room(i, 10, 80000));
            else if (i < 11) this.roomList.add(new Room(i, 15, 120000));
            else this.roomList.add(new Room(i, 18, 150000));
        }

        this.hotelAsset = INIITAL_HOTEL_ASSSET;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public int getHotelAsset() {
        return hotelAsset;
    }

    public String adminPassword() {
        return ADMIN_PASSWORD;
    }

    public void setAsset(int price){
        this.hotelAsset += price;
    }
}
