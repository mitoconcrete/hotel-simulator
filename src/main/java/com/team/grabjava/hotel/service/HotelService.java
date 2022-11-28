package com.team.grabjava.hotel.service;

import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.entity.Room;
import com.team.grabjava.hotel.entity.User;
import com.team.grabjava.hotel.repository.HotelRepository;
import com.team.grabjava.hotel.repository.ReservationRepository;
import com.team.grabjava.hotel.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HotelService {
    HotelRepository hotelRepository = new HotelRepository();
    ReservationRepository reservationRepository = new ReservationRepository();
    UserRepository userRepository = new UserRepository();

    // 1. Get Service
    public List<Reservation> getHotelReservationList(){
        return reservationRepository.getReservationList();
    }

    public int getHotelAsset(){
        return hotelRepository.getAsset();
    }

    public String requestReservation(int roomNo, String userName, String userPhone, String date) {
        for (Room room : getBookableRoomList(date)) {         // ReservationList - list에서 해당 date에 예약 가능한 방정보를 모두 불러온다.       사용방법 잘못됨 -> 이유알면 date쓰는이유도 알게됨
            if (room.getRoomNo() == roomNo) {                              // 그 중에 roomNo와 일치하는 room list가 있다면
                for (User user : userRepository.getUserList()) {
                    if (user.getUserName().equals(userName) && user.getUserPhone().equals(userPhone)) {
                        if (user.getUserAsset() >= room.getPrice()) {
                            // ReservationRepository에서 id를 새로 생성해주고 Db에 저장해준다.
                            // id를 받아와야됨 -> reservationRapository에서 reservation list를 for문으로 돌리고, if문으로 이름번호 같은애(다른거도 비교해야하나 생각해보기) 뽑아서 같은거의 id값을 찾는다.
                            int updateUserAsset = user.getUserAsset() - room.getPrice();
                            user.setUserAsset(updateUserAsset);
                            Reservation reservation = reservationRepository.createReservation(room, userName, userPhone, date);
                            putHotelAsset(reservation.getRoom().getPrice());
                            return reservation.getReservationId();  // 찾은거를 (reservation.getId)리턴한다.
                        } else {
                            return "잔액부족";
                        }
                    }
                }
            }
        }
        return "예약실패";
    }

    public List<Room> getBookableRoomList(String date){

        List<Room> list = new ArrayList<>(hotelRepository.getRoomList());

        for(Room room : hotelRepository.getRoomList()) {
            for (Reservation reservation : reservationRepository.getReservationList()) {
                String reservationDate = reservation.getReservationDate().substring(0, 10);
                if (reservationDate.equals(date) && reservation.getRoom().getRoomNo()==room.getRoomNo()) {
                    list.remove(room);
                    break;
                }
            }
        }
        return list;
    }

    public String getReservationContent(String reservationId) {
        StringBuilder reservationContent = new StringBuilder();

        for (Reservation r : reservationRepository.getReservationList()) {
            if (r.getReservationId().equals(reservationId)) {

                reservationContent.append(r.getReservationId()).append(" / ").append(r.getReservationDate()).append(" / ").append(r.getRoom().getSize()).append("평");
            }
        }
        return reservationContent.toString();
    }

    public List<String> getReservationIdList(String userName, String userPhone){
        List<Reservation> reservations = reservationRepository.getReservationList();
        return reservations.stream().filter(reservation -> reservation.getUserName().equals(userName)
                        && reservation.getUserPhone().equals(userPhone))
                .map(Reservation::getReservationId)
                .collect(Collectors.toList());
    }

    public void postNewUser(String userName, String userPhone, int userAsset) {
        userRepository.createUser(userName, userPhone, userAsset);
    }

    public void putUserAsset(String userName, String userPhone, int userAsset) {
        for (User user : userRepository.getUserList()) {
            // 이름, 전화번호가 모두 일치하는 유저리스트가 있다면
            if (userName.equals(user.getUserName()) && userPhone.equals(user.getUserPhone())) {
                // 유저리스트 의 소지금을 변경하는 매서드를 실행해라.
                userRepository.setAsset(user, userAsset);
                return;
            }
        }
    }

    public void putHotelAsset(int userAsset){
        hotelRepository.setAsset(userAsset);
        hotelRepository.getAsset();
    }

    public boolean deleteReservationById(String reservationId){
        for (Reservation r : reservationRepository.getReservationList()) {
            if(r.getReservationId().equals(reservationId)){
                reservationRepository.deleteReservation(r);
                return true;
            }
        }
        return false;
    }
    public boolean validateUserDataInDB(String userName, String userPhone) {
        for (User user : userRepository.getUserList()) {
            if (userName.equals(user.getUserName()) && userPhone.equals(user.getUserPhone())) {
                return true;
            }
        }

        return false;
    }
    public boolean validateDateFormat(String date) {
        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatParser.setLenient(false);
            dateFormatParser.parse(date);
            // 날짜가 같거나, 7일내에 있거나
            return LocalDate.now().compareTo(LocalDate.parse(date)) <= 0 &&  LocalDate.now().plusDays(7).isAfter(LocalDate.parse(date));
        } catch (Exception e) {
            return false;
        }
    }
    public boolean validatePhoneNumber(String phoneNumber){
        return Pattern.matches("^01(?:0|1|[6-9])-\\d{4}-\\d{4}$", phoneNumber);
    }

    public boolean validateAdminPassword(String password){
        return hotelRepository.getAdminPassword().equals(password);
    }
}
