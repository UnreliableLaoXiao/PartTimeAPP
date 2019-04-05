package com.schoolpartime.schoolpartime.chat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.entity.Message;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class WebClient extends WebSocketClient{
    public static final String ACTION_RECEIVE_MESSAGE = "com.schoolpartime.chat.BROADCAST";
    public static final String KEY_RECEIVED_DATA = "data";
    private static WebClient mWebClient;
    private Context mContext;
    static boolean isConnected = false;
    /**
     *  路径为ws+服务器地址+服务器端设置的子路径+参数（这里对应服务器端机器编号为参数）
     *  如果服务器端为https的，则前缀的ws则变为wss
     */
    private static final String mAddress = "http://192.168.124.11:8080/websocket/";
    private void showLog(String msg){
        Log.d("WebClient---->", msg);
    }
    private WebClient(URI serverUri, Context context){
        super(serverUri, new Draft_6455());
        mContext = context;
        showLog("WebClient");
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        showLog("open->"+handshakedata.toString());
    }


    @Override
    public void onMessage(String mes) {
        showLog("onMessage->"+mes);
        for (NotifyMessage notifyMessage:messages){
            notifyMessage.notify(mes);
        }
        sendMessageBroadcast(mes);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        isConnected = false;
        showLog("onClose->"+reason);
    }

    @Override
    public void onError(Exception ex) {
        showLog("onError->"+ex.toString());
    }

    /** 初始化
     * @param id  //用户id
     */
    public static void initWebSocket(final Context context, final long id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mWebClient = new WebClient(new URI(mAddress+id), context);
                    try {
                        mWebClient.connectBlocking();
                        isConnected = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static WebClient getInstance(){
        if (mWebClient != null){
            return mWebClient;
        } else {
            Log.d("WebClient---->", "mWebClient init failed");
            return null;
        }
    }

    /** 发送消息广播
     * @param message
     */
    private void sendMessageBroadcast(String message){
        if (!message.isEmpty()){
            Intent intent = new Intent();
            intent.setAction(ACTION_RECEIVE_MESSAGE);
            intent.putExtra(KEY_RECEIVED_DATA,message);
            showLog("发送收到的消息");
            mContext.sendBroadcast(intent);
        }
    }

    ArrayList<NotifyMessage> messages = new ArrayList<>();

    interface NotifyMessage{
        void notify(String mes);
    }

    public void removeNotity(NotifyMessage notifyMessage){
        messages.remove(notifyMessage);
    }

    public void addNotity(NotifyMessage notifyMessage){
        messages.add(notifyMessage);
    }

}
