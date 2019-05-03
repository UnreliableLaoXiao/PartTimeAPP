package com.schoolpartime.schoolpartime.chat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.entity.Message;
import com.schoolpartime.schoolpartime.event.NumberController;
import com.schoolpartime.schoolpartime.util.LogUtil;

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
    private Gson gson = new Gson();
    public static volatile boolean isConnected = false;
    /**
     *  路径为ws+服务器地址+服务器端设置的子路径+参数（这里对应服务器端机器编号为参数）
     *  如果服务器端为https的，则前缀的ws则变为wss
     */
    private static final String mAddress = "http://172.28.131.4:8080/websocket/";
    private void showLog(String msg){
        LogUtil.d("WebClient---->"+msg);
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
        Message message = gson.fromJson(mes, Message.class);
        switch (message.getMsg_type()){
            case 1:
            {
                /**
                 * 此处为聊天message
                 */
                NumberController.getInstance().NotifyAll(1);
                for (NotifyMessage notifyMessage:messages){
                    notifyMessage.notify(mes);
                }
                sendMessageBroadcast(mes);

            }
            break;
            case 2:
            {

            }
            break;
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        isConnected = false;
        showLog("onClose->"+reason);
        if (code == 888){
            LogUtil.d("关闭WebClient成功");
            return;
        }
        LogUtil.d("重新连接服务器");
        new Thread(){
            @Override
            public void run() {
                int index = 0 ;
                while (!isConnected && index++ <3){
                    try {
                        Thread.sleep(2000);
                        showLog("重新连接->");
                        isConnected = mWebClient.reconnectBlocking();
                        showLog("重新连接->----------------" + isConnected);
                    } catch (InterruptedException e) {
                        showLog("连接异常-------------->");
                    }
                }
            }
        }.start();

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
            intent.setComponent(new ComponentName("com.schoolpartime.schoolpartime",
                    "com.schoolpartime.schoolpartime.broadcast_receiver.PushBroadCastReceiver"));
            intent.setAction(ACTION_RECEIVE_MESSAGE);
            intent.putExtra(KEY_RECEIVED_DATA,message);
            showLog("发送收到的消息");
            mContext.sendBroadcast(intent);
        }
    }

    ArrayList<NotifyMessage> messages = new ArrayList<>();

    public interface NotifyMessage{
        void notify(String mes);
    }

    public void removeNotity(NotifyMessage notifyMessage){
        messages.remove(notifyMessage);
    }

    public void addNotity(NotifyMessage notifyMessage){
        messages.add(notifyMessage);
    }

}
