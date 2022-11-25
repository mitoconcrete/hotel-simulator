package com.team.grabjava.hotel.repository;

import com.team.grabjava.hotel.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private ArrayList<User> userList = new ArrayList<>();   // index 제한없는 리스트

    public void createUser(String userName, String userPhone, int userAsset){    // 고객의 정보를 받아서 고객 리스트에 추가하는 메소드
        userList.add(new User(userName, userPhone, userAsset));
    }

    public List<User> getUserList() {       // 모든 유저의 리스트를 반환하는 메소드
        return this.userList;               // 이거로 service로 반환하면 거기서 유저 리스트들을 사용할 방법을 정의한다.
    }

    public void setAsset(User user, int userAsset){       // 유저리스트에 있는 해당 유저 정보를 업데이트 하는 메소드
        user.setUserAsset(userAsset);
    }       // parameter가 기본형 변수로 들어오면 기본형 값이 들어오고, 참조형 변수로 들어오면 인스턴스의 주소가 들어온다.
}           // 그래서 for문으로 돌려서 일치하는 값을 찾지 않아도 고유한 인스턴스의 주소값이 set된다.
