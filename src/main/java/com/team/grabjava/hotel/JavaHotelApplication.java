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
        SystemLogPresentation systemLogPresentation = new SystemLogPresentation();
        ApplicationLogPresentation applicationLogPresentation = new ApplicationLogPresentation();

        SimpleLogPresentation simpleLogPresentation = new SimpleLogPresentation();

        FailResultLogPresentation failResultLogPresentation = new FailResultLogPresentation();
        SuccessResultLogPresentation successResultLogPresentation = new SuccessResultLogPresentation();

        NotExistLogPresentation notExistLogPresentation = new NotExistLogPresentation();
        NotMatchLogPresentation notMatchLogPresentation = new NotMatchLogPresentation();

        RequestInputLogPresentation requestInputLogPresentation = new RequestInputLogPresentation();
        SelectableMenuLogPresentation selectableMenuLogPresentation = new SelectableMenuLogPresentation();
        GetServiceResponseLogPresentation getServiceResponseLogPresentation = new GetServiceResponseLogPresentation();



        // Service
        HotelService hotelService = new HotelService();

        // Scanner
        Scanner input = new Scanner(System.in);

        // Program
        returnToStartMenu : while(true){
            boolean isAdminMode = false;
            boolean isInStartMenu = true;

            // 시작메뉴 + (관리자메뉴)
            while (isInStartMenu){
                selectableMenuLogPresentation.showSelectableStartMenu();
                if(isAdminMode){
                    selectableMenuLogPresentation.showSelectableHiddenMenu();
                }
                requestInputLogPresentation.showRequestMenuNumberMessage();
                String startMenuSelectCommand = input.nextLine();
                switch (startMenuSelectCommand){
                    case "1":
                        isInStartMenu = false;
                        break;
                    case "2":
                        systemLogPresentation.showSystemExitMessage();
                        System.exit(0);
                        break;
                    case "3":
                    case "4":
                        if(isAdminMode){
                            isAdminMode = false;
                            switch (startMenuSelectCommand){
                                case "3":
                                    List<Reservation> reservationList = hotelService.getHotelReservationList();
                                    if(reservationList.size() == 0){
                                        notExistLogPresentation.showNotExistReservationMessage();
                                    }else{
                                        getServiceResponseLogPresentation.showGetHotelAllReservationList(reservationList);
                                    }
                                    continue;
                                case "4":
                                    int hotelCurrentAsset = hotelService.getHotelAsset();
                                    getServiceResponseLogPresentation.showGetHotelAsset(hotelCurrentAsset);
                                    continue;
                            }
                        }
                    default:
                            isAdminMode = hotelService.validateAdminPassword(startMenuSelectCommand);
                            if(!isAdminMode){
                                notMatchLogPresentation.showNotMatchPasswordMessage();
                            }
                }
            }

            // 호텔 입장
            simpleLogPresentation.showSimpleRequestUserInfoMessage();

            // 1 유저 이름을 입력
            requestInputLogPresentation.showRequestUserNameMessage();
            String userName = input.nextLine();
            if(userName.equals("")){
                notMatchLogPresentation.showNotMatchStringFormatMessage();
                continue;
            }

            // 2. 전화번호를 입력받는다. 만약, 잘못된 전화번호를 입력했다면, 다시 처음으로 돌아간다.
            requestInputLogPresentation.showRequestUserPhoneMessage();
            String userPhone = input.nextLine();
            boolean isValidPhoneNumber = hotelService.validatePhoneNumber(userPhone);
            if(!isValidPhoneNumber) {
                notMatchLogPresentation.showNotMatchPhoneNumberFormatMessage();
                continue;
            }

            // 3. 업데이트할 보유금을 입력받는다. 만약, 잘못된 형식의 숫자를 입력하면, 다시 처음으로 돌아간다.
            int userAsset;
            try {
                requestInputLogPresentation.showRequestUserAssetMessage();
                userAsset = Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
                System.out.println("잘못된 형식의 숫자입니다.");
                continue;
            }

            // 4. 모든 단계를 통과 한다면, 유저정보를 DB에 있는지 확인한다.
            // 만약, 기존 디비에 있다면, 자산만 업데이트해주고,
            // 만약, 기존 디비에 없다면, 새로운 유저를 생성해준다.
            boolean hasUserInDB = hotelService.validateUserDataInDB(userName, userPhone);
            if(hasUserInDB){
                hotelService.putUserAsset(userName, userPhone, userAsset);
            }else{
                hotelService.postNewUser(userName, userPhone, userAsset);
            }

            // 5. 서비스 선택
            while (true){
                selectableMenuLogPresentation.showSelectableMainMenu();
                requestInputLogPresentation.showRequestMenuNumberMessage();
                String selectInput = input.nextLine();
                switch (selectInput){
                    case "1":  // 예약
                        requestInputLogPresentation.showRequestDateTimeMessage();
                        String reservationRequestDate = input.nextLine();
                        boolean isValidDate = hotelService.validateDateFormat(reservationRequestDate);
                        if(!isValidDate){
                            notMatchLogPresentation.showNotMatchDateTimeFormatMessage();
                            continue;
                        }
                        List<Room> roomList = hotelService.getBookableRoomList(reservationRequestDate);
                        if(roomList.size() == 0){
                            notExistLogPresentation.showNotExistEmptyRoomMessage();
                        }else{
                            getServiceResponseLogPresentation.showGetHotelRoomListMessage(roomList);
                            requestInputLogPresentation.showRequestRoomNumberMessage();
                            int selectedRoomNo;
                            try {
                                selectedRoomNo = Integer.parseInt(input.nextLine());
                            }catch(NumberFormatException e){
                                continue;
                            }
                            String reservationResponse = hotelService.requestReservation(selectedRoomNo, userName, userPhone, reservationRequestDate);
                            switch (reservationResponse){
                                case "잔액부족":
                                    notExistLogPresentation.showNotExistRemainMessage();
                                    continue;
                                case "예약실패":
                                    failResultLogPresentation.showFailCancelReservationMessage();
                                    continue;
                                default:
                                    successResultLogPresentation.showSuccessReservationMessage(reservationResponse);
                                    break;
                            }
                            continue;
                        }
                    case "2":  // 예약번호 조회
                        requestInputLogPresentation.showRequestUserNameMessage();
                        String searchUserName = input.nextLine();
                        if(searchUserName.equals("")){
                            notMatchLogPresentation.showNotMatchStringFormatMessage();
                            continue;
                        }

                        requestInputLogPresentation.showRequestUserPhoneMessage();
                        String searchUserPhone = input.nextLine();
                        isValidPhoneNumber = hotelService.validatePhoneNumber(searchUserPhone);
                        if(!isValidPhoneNumber) {
                            notMatchLogPresentation.showNotMatchPhoneNumberFormatMessage();
                            continue;
                        }

                        List<String> reservationIdList = hotelService.getReservationIdList(searchUserName, searchUserPhone);
                        if(reservationIdList.size() == 0){
                            notExistLogPresentation.showNotExistReservationIdMessage();
                        }else {
                            getServiceResponseLogPresentation.showGetUserAllReservationIdList(reservationIdList);
                        }
                        continue;
                    case "3":  // 예약내역 조회
                        requestInputLogPresentation.showRequestReservationIdMessage();
                        String reservationId = input.nextLine();
                        String reservationApiResponse = hotelService.getReservationContent(reservationId);
                        if(reservationApiResponse.equals("")){
                            notExistLogPresentation.showNotExistReservationMessage();
                        }else{
                            getServiceResponseLogPresentation.showGetUserReservationInfoMessage(reservationApiResponse);
                        }
                        continue;
                    case "4":  // 예약 취소
                        requestInputLogPresentation.showRequestReservationIdMessage();
                        reservationId = input.nextLine();
                        reservationApiResponse = hotelService.getReservationContent(reservationId);
                        if(reservationApiResponse.equals("")){
                            notExistLogPresentation.showNotExistReservationMessage();
                            continue;
                        }else{
                            getServiceResponseLogPresentation.showGetUserReservationInfoMessage(reservationApiResponse);
                            requestInputLogPresentation.showRequestCancelCommandMessage();
                        }

                        String cancelConfirmCommand = input.nextLine();
                        switch (cancelConfirmCommand){
                            case "Y":
                                boolean cancelReservationResponse =  hotelService.deleteReservationById(reservationId);
                                if (cancelReservationResponse){
                                    successResultLogPresentation.showSuccessCancelReservationMessage();
                                }else{
                                    failResultLogPresentation.showFailCancelReservationMessage();
                                }
                                break;
                            case "N":
                                failResultLogPresentation.showFailCancelReservationMessage();
                                break;
                            default:
                                notMatchLogPresentation.showNotMatchCommandMessage();
                        }
                        continue;
                    case "5":  // 호텔 나가기
                        applicationLogPresentation.showApplicationExitMainMenuMessage();
                        continue returnToStartMenu;
                    default:
                        notMatchLogPresentation.showNotMatchMenuNumberMessage();
                }
            }
        }
    }
}


