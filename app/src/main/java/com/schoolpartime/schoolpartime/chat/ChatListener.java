package com.schoolpartime.schoolpartime.chat;

import com.schoolpartime.schoolpartime.entity.Message;

public interface ChatListener {

    void sendMessage(Message message) throws InterruptedException;

}
