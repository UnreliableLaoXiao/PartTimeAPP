package com.schoolpartime.schoolpartime.weight.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.schoolpartime.schoolpartime.R;

import java.util.ArrayList;
import java.util.List;

public class Views {

    public static List<View> GetWelcomeViews(Activity activity, View.OnClickListener listener){
        List<View> views = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater. from(activity);
        int[] layoutIds = new int[]{R.layout.welcome_screen_1, R.layout.welcome_screen_2, R.layout.welcome_screen_3};
        for (int layoutId : layoutIds) {
            views.add(inflater.inflate(layoutId, null));
        }
        Button enterButton = (views.get(views.size() - 1)).findViewById(R.id.enter_button);
        enterButton.setOnClickListener(listener);
        return views;
    }

    private static ImageView[] GetWelcomeDots(Activity Activity){
        int[] dotsIds = new int[]{R.id.dot_01, R.id. dot_02, R.id.dot_03,};
        ImageView[] dots = new ImageView[ dotsIds. length];
        for( int i=0; i< dotsIds. length; i++){
            dots[i] = Activity.findViewById(dotsIds[i]);
        }
        return dots;
    }

    public static void SetWelcomeDots(Activity activity,int pos) {
        ImageView[] dots = GetWelcomeDots(activity);
        //选定arg0位置的页面
        for (int i = 0; i < dots.length; i++) {
            if (i == pos) {
                dots[i].setImageResource(R.drawable.wihte_circle);
            } else {
                dots[i].setImageResource(R.drawable.gray_circle);
            }
        }
    }

}
