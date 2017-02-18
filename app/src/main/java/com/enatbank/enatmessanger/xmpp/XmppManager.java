package com.enatbank.enatmessanger.xmpp;

import android.util.Log;

import com.enatbank.enatmessanger.utils.Config;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static java.lang.Thread.sleep;

/**
 * Created by btinsae on 14/02/2017.
 */

public class XmppManager {
    private static XMPPTCPConnectionConfiguration mConnection;
    private static AbstractXMPPConnection abstractXMPPConnection;

    public XmppManager() {

    }

    public static boolean login(String username, String password) {

        if (getConnection().isConnected() && getConnection() != null) {
            try {

                if (getConnection().isAuthenticated())
                    return true;
                else {
                    getConnection().login(username, password);
                    return true;
                }

            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static XMPPTCPConnection getConnection() {
        mConnection = XMPPTCPConnectionConfiguration.builder()
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setHost(Config.HOST_NAME)
                .setServiceName(Config.DOMAIN_NAME)
                .setPort(Config.PORT)
                .setDebuggerEnabled(true)
                .setSendPresence(true)
                //.setReconnectionAllowed(true);
                .build();

        try {
            if (abstractXMPPConnection == null) {
                abstractXMPPConnection = new XMPPTCPConnection(mConnection);
                abstractXMPPConnection.connect();
                return (XMPPTCPConnection) abstractXMPPConnection;
            }else
            return (XMPPTCPConnection) abstractXMPPConnection;
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        return (XMPPTCPConnection) abstractXMPPConnection;
    }

    private void setStatus() {
        Presence mPresence = new Presence(Presence.Type.unavailable);
        mPresence.setStatus("Coding");
        try {
            getConnection().sendStanza(mPresence);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        getConnection().disconnect();
    }


    public void chat(String userJabberId,String msg) {
        ChatManager mChatManager = ChatManager.getInstanceFor(getConnection());
        Chat newChat = mChatManager.createChat(userJabberId, new ChatMessageListener() {
            public void processMessage(Chat chat, Message message) {
                Log.d("Received message: ", message.getBody().toString());

            }
        });

        try {
            newChat.sendMessage(msg);
        } catch (SmackException.NotConnectedException e) {
            Log.e("Exception", "Error Delivering block");
        }
    }

    private void chatIncoming() {
        ChatManager chatManager = ChatManager.getInstanceFor(getConnection());
        chatManager.addChatListener(
                new ChatManagerListener() {
                    @Override
                    public void chatCreated(Chat chat, boolean createdLocally) {
                        if (!createdLocally)
                            chat.addMessageListener(new MyNewMessageListener());
                    }
                });
    }

    private void roster() {
        Roster roster = Roster.getInstanceFor(getConnection());
        roster.addRosterListener(new RosterListener() {
            // Ignored events public void entriesAdded(Collection<String> addresses) {}
            public void entriesDeleted(Collection<String> addresses) {
            }

            @Override
            public void entriesAdded(Collection<String> addresses) {

            }

            public void entriesUpdated(Collection<String> addresses) {
            }

            public void presenceChanged(Presence presence) {
                Log.d("Presence changed: ", presence.getFrom() + " " + presence);
            }
        });
    }

    private void sendFile() {
        // Create the file transfer manager
        FileTransferManager manager = FileTransferManager.getInstanceFor(getConnection());
// Create the outgoing file transfer
        OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer("romeo@montague.net");
// Send the file
        try {
            transfer.sendFile(new File("shakespeare_complete_works.txt"), "You won't believe this!");
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }

    private void receiveFile() {
        // Create the file transfer manager
        final FileTransferManager manager = FileTransferManager.getInstanceFor(getConnection());
// Create the listener
        manager.addFileTransferListener(new FileTransferListener() {
            public void fileTransferRequest(FileTransferRequest request) {
                // Check to see if the request should be accepted
                if (shouldAccept(request)) {
                    // Accept it
                    IncomingFileTransfer transfer = request.accept();
                    try {
                        transfer.recieveFile(new File("shakespeare_complete_works.txt"));
                    } catch (SmackException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Reject it
                    try {
                        request.reject();
                    } catch (SmackException.NotConnectedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean shouldAccept(FileTransferRequest request) {
        return true;
    }

    private void fileTransferProgress(IncomingFileTransfer transfer) throws InterruptedException {
        while (!transfer.isDone()) {
            if (transfer.getStatus().equals(FileTransfer.Status.error)) {
                System.out.println("ERROR!!! " + transfer.getError());
            } else {
                System.out.println(transfer.getStatus());
                System.out.println(transfer.getProgress());
            }
            sleep(1000);
        }
    }
}
