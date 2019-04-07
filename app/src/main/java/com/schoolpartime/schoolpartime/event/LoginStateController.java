package com.schoolpartime.schoolpartime.event;

import java.util.ArrayList;

public class LoginStateController {

    ArrayList<NotifyLoginState> notifyLoginStates = new ArrayList<>();

    private static LoginStateController loginStateController = new LoginStateController();

    private LoginStateController() {
    }

    public static LoginStateController getInstance() {
        if(loginStateController != null){
            return loginStateController;
        }else {
            return null;
        }
    }

    public interface NotifyLoginState{
        void loginStateChange(boolean state);
    }

    public void removeNotity(NotifyLoginState notifyNumber){
        notifyLoginStates.remove(notifyNumber);
    }

    public void addNotity(NotifyLoginState notifyNumber){
        notifyLoginStates.add(notifyNumber);
    }

    public void NotifyAll(boolean state){
        for (NotifyLoginState notifyNumber:notifyLoginStates){
            notifyNumber.loginStateChange(state);
        }
    }

}
