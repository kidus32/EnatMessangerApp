package com.enatbank.enatmessanger.xmpp;

import android.util.Log;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by btinsae on 16/02/2017.
 */
public class MyNewMessageListener implements ChatMessageListener {
    @Override
    public void processMessage(Chat chat, Message message) {
        Log.d("MSG",message.getBody().toString());
    }
}
