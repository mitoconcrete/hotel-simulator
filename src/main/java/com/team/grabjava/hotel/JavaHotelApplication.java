package com.team.grabjava.hotel;

import com.team.grabjava.hotel.presentation.*;
import com.team.grabjava.hotel.repository.ReservationRepository;
import com.team.grabjava.hotel.service.HotelService;

import java.util.Objects;
import java.util.Scanner;

public class JavaHotelApplication {
    public static void main(String[] args) {
        // Interface
        HotelInterface hotelInterface = new HotelInterface();
        UserInfoInterface userInfoInterface = new UserInfoInterface();
        ReservationInterface reservationInterface = new ReservationInterface();
        SelectServiceInterface selectServiceInterface = new SelectServiceInterface();
        GetAllRervationsInterface getAllRervationsInterface = new GetAllRervationsInterface();
        ReservationCancelInterface reservationCancelInterface = new ReservationCancelInterface();
        ReservationNumberSearchInterface reservationNumberSearchInterface = new ReservationNumberSearchInterface();
        PhoneNumberValidationErrorInterface phoneNumberValidationErrorInterface = new PhoneNumberValidationErrorInterface();

        // Service
        HotelService hotelService = new HotelService();

        // Scanner
        Scanner input = new Scanner(System.in);

        // Programs
        while(true){
            // 1. 유저 정보를 입력받는다.
            userInfoInterface.startScanner();

            // 1-1. 유저 이름을 입력받는다. 만약 빈 문자열이 넘어오면 처음으로 되돌아간다.
            userInfoInterface.showInputUserNameMessage();
            String userName = input.nextLine();
            if(userName.equals("")){
                System.out.println("이름은 공백이 될 수 없습니다.");
                continue;
            }

            // 2. 전화번호를 입력받는다. 만약, 잘못된 전화번호를 입력했다면, 다시 처음으로 돌아간다.
            userInfoInterface.showInputUserPhoneMessage();
            String userPhone = input.nextLine();
            boolean isValidatePhoneNumber = hotelService.phoneNumberValidation(userPhone);
            if(!isValidatePhoneNumber) {
                phoneNumberValidationErrorInterface.showPhoneNumberValidationError();
                continue;
            }

            // 3. 업데이트할 보유금을 입력받는다. 만약, 잘못된 형식의 숫자를 입력하면, 다시 처음으로 돌아간다.
            int userAsset;
            try {
                userInfoInterface.showInputUserAssetMessage();
                userAsset = Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
                System.out.println("잘못된 형식의 숫자입니다.");
                continue;
            }

            // 4. 모든 단계를 통과 한다면, 유저정보를 DB에 있는지 확인한다.
            // 만약, 기존 디비에 있다면, 자산만 업데이트해주고,
            // 만약, 기존 디비에 없다면, 새로운 유저를 생성해준다.
            boolean hasUserInDB = hotelService.isFindUserService(userName, userPhone);
            if(hasUserInDB){
                hotelService.updateUserAssetService(userName, userPhone, userAsset);
            }else{
                hotelService.createUserService(userName, userPhone, userAsset);
            }
        }
    }
}


