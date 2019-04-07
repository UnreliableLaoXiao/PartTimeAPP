package com.schoolpartime.schoolpartime.event;

import java.util.ArrayList;

public class NumberController {

    ArrayList<NotifyNumber> notifyNumbers = new ArrayList<>();

    private static NumberController numberController = new NumberController();

    private NumberController() {
    }

    public static NumberController getInstance() {
        if(numberController != null){
            return numberController;
        }else {
            return null;
        }
    }

    public interface NotifyNumber{
        void change(int number);
    }

    public void removeNotity(NotifyNumber notifyNumber){
        notifyNumbers.remove(notifyNumber);
    }

    public void addNotity(NotifyNumber notifyNumber){
        notifyNumbers.add(notifyNumber);
    }

    public void NotifyAll(int change){
        for (NotifyNumber notifyNumber:notifyNumbers){
            notifyNumber.change(change);
        }
    }

}
