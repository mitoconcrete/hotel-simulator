package com.team.grabjava.hotel.entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Room> roomList = new ArrayList<>();
    private int hotelAsset;

    public Hotel(){
        for(int i=1;i<16;i++){
            if(i<6) this.roomList.add(new Room(i,10,80000));
            else if(i<11) this.roomList.add(new Room(i,15,120000));
            else this.roomList.add(new Room(i,18,150000));
        }

        this.hotelAsset = 1000000;
    }

    public void setAsset(int price){
        this.hotelAsset += price;
    }


    public List<Room> getRoomList() {
        return roomList;
    }

    public int getHotelAsset() {
        return hotelAsset;
    }
}
