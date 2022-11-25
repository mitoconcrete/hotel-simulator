package com.team.grabjava.hotel.service.personal_service;

import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.entity.Room;
import com.team.grabjava.hotel.entity.User;
import com.team.grabjava.hotel.presentation.ReservationCancelInterface;
import com.team.grabjava.hotel.presentation.ReservationInterface;
import com.team.grabjava.hotel.repository.ReservationRepository;
import com.team.grabjava.hotel.repository.UserRepository;
import com.team.grabjava.hotel.service.HotelService;

import java.util.ArrayList;
import java.util.Date;

public class SangwooService {       // getUserList에서 이름, 번호 같은 사람을 꺼내서

    UserRepository userRepository = new UserRepository();

    public String requestReservation(int roomNo, String userName, String userPhone, String date) {
        for (Room room : getReservationableRoomList(date)) {         // ReservationList - list에서 해당 date에 예약 가능한 방정보를 모두 불러온다.       사용방법 잘못됨 -> 이유알면 date쓰는이유도 알게됨
            if (room.equals(roomNo)) {                              // 그 중에 roomNo와 일치하는 room list가 있다면
                for (User user : userRepository.getUserList()) {
                    if (user.getUserName().equals(userName) && user.getUserPhone().equals(userPhone)) {
                        if (user.getUserAsset() >= reservationRepository.getprice()) {
                             // ReservationRepository에서 id를 새로 생성해주고 Db에 저장해준다.
                            // id를 받아와야됨 -> reservationRapository에서 reservation list를 for문으로 돌리고, if문으로 이름번호 같은애(다른거도 비교해야하나 생각해보기) 뽑아서 같은거의 id값을 찾는다.
                            return reservationRepository.createReservation(room, userName, userPhone, date);  // 찾은거를 (reservation.getId)리턴한다.
                        } else {
                            return "잔액부족";
                        }
                    }
                }
            } else {
                return "예약실패";
            }
        }
        return "까꿍";
    }
}



// 다른 클래스에서 가져오는거는 객체에서 접근해줘야한다.
// 이름, 번호의 유저 -> 자산과 방 가격을 비교       방을 어디서 가져오나?
// 내가 하고있는거 -> getReservationableRoomList에서 유저가 선택한 방 번호가 예약 가능한지 확인해보고 후처리 진행
//      방은 어디서 가져오지?  ->  getRoomList에서 for문으로 돌린다음....아무튼 getReservationableRoomList에서 가져오지않나?
//  Date reserbationDate가 필요한 이유???????????????????????????????????????
// 그럼 먼저 이름,번호의 유저의 자산을 가져오고
//      일치하는 유저는 어디서가져오지?
// 예약가능한 방 리스트의 방 번호와 유저가 선택한 방 번호가 일치하는지 먼저 체크
// 일치했다면 후처리
//  자산과 방 가격으로 if문 돌리기
// 일치하지 않다면 예약오류 리턴

// 참조객체 복사 -> 주소때문에 막하면안됨