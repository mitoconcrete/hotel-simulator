package com.team.grabjava.hotel;

import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.entity.Room;
import com.team.grabjava.hotel.presentation.*;
import com.team.grabjava.hotel.repository.ReservationRepository;
import com.team.grabjava.hotel.service.HotelService;

import java.util.List;
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
        ReservationSearchInterface reservationSearchInterface = new ReservationSearchInterface();
        ReservationCancelInterface reservationCancelInterface = new ReservationCancelInterface();
        ReservationNumberSearchInterface reservationNumberSearchInterface = new ReservationNumberSearchInterface();
        PhoneNumberValidationErrorInterface phoneNumberValidationErrorInterface = new PhoneNumberValidationErrorInterface();

        // Service
        HotelService hotelService = new HotelService();

        // Scanner
        Scanner input = new Scanner(System.in);

        // Programs
        exit : while(true){
            boolean isAdminMode = false;
            boolean isInStartMenu = true;

            while (isInStartMenu){
                selectServiceInterface.showSelectableStartMenu();
                if(isAdminMode){
                    selectServiceInterface.showSelectableHiddenMenu();
                }
                selectServiceInterface.showSelectNumberInputMessage();
                String startMenuSelectCommand = input.nextLine();
                switch (startMenuSelectCommand){
                    case "1":
                        isInStartMenu = false;
                        break;
                    case "2":
                        System.out.println("시스템을 종료합니다.");
                        System.exit(0);
                        break;
                    default:
                        if(isAdminMode && startMenuSelectCommand.equals("3")){
                            List<Reservation> reservationList = hotelService.getHotelReservationList();
                            if(reservationList.size() == 0){
                                getAllRervationsInterface.showNotExistReservationsMessage();
                            }else{
                                getAllRervationsInterface.showAllReservationsMessage(reservationList);
                            }
                            isAdminMode = false;
                            continue;
                        }else{
                            isAdminMode = hotelService.checkAdminPassword(startMenuSelectCommand);
                            if(!isAdminMode){
                                System.out.println("잘못된 커멘드입니다.");
                            }
                        }
                    break;
                }
            }

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
            boolean isValidPhoneNumber = hotelService.phoneNumberValidation(userPhone);
            if(!isValidPhoneNumber) {
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

            // 5. 서비스 선택
            while (true){
                selectServiceInterface.showSelectableMainMenu();
                selectServiceInterface.showSelectNumberInputMessage();
                String selectInput = input.nextLine();
                switch (selectInput){
                    case "1":  // 예약
                        reservationInterface.startScanner();
                        String reservationRequestDate = input.nextLine();
                        boolean isValidDate = hotelService.checkDateFormat(reservationRequestDate);
                        if(!isValidDate){
                            reservationInterface.showOutOfDateRangeMessage();
                            continue;
                        }
                        List<Room> roomList = hotelService.getReservationableRoomList(reservationRequestDate);
                        if(roomList.size() == 0){
                            reservationInterface.showNoEmptyRoomMessage();
                        }else{
                            reservationInterface.showHasEmptyRoomMessage(roomList);
                            int selectedRoomNo;
                            try {
                                selectedRoomNo = Integer.parseInt(input.nextLine());
                            }catch(NumberFormatException e){
                                continue;
                            }
                            String reservationResponse = hotelService.requestReservation(selectedRoomNo, userName, userPhone, reservationRequestDate);
                            switch (reservationResponse){
                                case "잔액부족":
                                    reservationInterface.showNoMoneyReservationMessage();
                                    continue;
                                case "예약실패":
                                    reservationInterface.showAlreadyReservationMessage();
                                    continue;
                                default:
                                    reservationInterface.showSuccessReservationMessage(reservationResponse);
                                    break;
                            }
                            continue;
                        }
                    case "2":  // 예약번호 조회
                        reservationNumberSearchInterface.startScanner();
                        userInfoInterface.showInputUserNameMessage();
                        String searchUserName = input.nextLine();
                        if(searchUserName.equals("")){
                            System.out.println("이름은 공백이 될 수 없습니다.");
                            continue;
                        }

                        userInfoInterface.showInputUserPhoneMessage();
                        String searchUserPhone = input.nextLine();
                        isValidPhoneNumber = hotelService.phoneNumberValidation(searchUserPhone);
                        if(!isValidPhoneNumber) {
                            phoneNumberValidationErrorInterface.showPhoneNumberValidationError();
                            continue;
                        }

                        List<String> reservationIdList = hotelService.getReservationIdList(searchUserName, searchUserPhone);
                        if(reservationIdList.size() == 0){
                            reservationNumberSearchInterface.showNotExistReservationIdMessage();
                        }else {
                            reservationNumberSearchInterface.showExistReservationIdListMessage(reservationIdList);
                        }
                        continue;
                    case "3":  // 예약내역 조회
                        reservationSearchInterface.startScanner();
                        String searchReservationId = input.nextLine();
                        String reservationSearchResponse = hotelService.getReservationContent(searchReservationId);
                        if(reservationSearchResponse.equals("")){
                            reservationSearchInterface.showNotExistReservationMessage();
                        }else{
                            reservationSearchInterface.showReservationInfoMessage(reservationSearchResponse);
                        }
                        continue;
                    case "4":  // 예약 취소
                        reservationCancelInterface.startScanner();
                        searchReservationId = input.nextLine();
                        reservationSearchResponse = hotelService.getReservationContent(searchReservationId);
                        if(reservationSearchResponse.equals("")){
                            reservationCancelInterface.showNotExistReservationMessage();
                            continue;
                        }else{
                            reservationCancelInterface.showReservationInfoMessage(reservationSearchResponse);
                        }

                        String cancelConfirmCommand = input.nextLine();
                        switch (cancelConfirmCommand){
                            case "Y":
                                boolean cancelReservationResponse =  hotelService.requestReservationCancel(searchReservationId);
                                if (cancelReservationResponse){
                                    reservationCancelInterface.showSuccessCancelReservationMessage();
                                }else{
                                    reservationCancelInterface.showFailCancelReservationMessage();
                                }
                                break;
                            case "N":
                                reservationCancelInterface.showFailCancelReservationMessage();
                                break;
                            default:
                                reservationCancelInterface.showCommandErrorMessage();
                        }
                        continue;
                    case "5":  // 호텔 나가기
                        System.out.println("호텔에서 퇴장합니다. 안녕히가세요:)");
                        continue exit;
                    default:
                        System.out.println("1-5 내의 숫자를 입력해주세요.");
                }
            }
        }
    }
}


