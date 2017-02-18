package com.enatbank.enatmessanger.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enatbank.enatmessanger.R;
import com.enatbank.enatmessanger.adapters.SingleChatRecyclerView;
import com.enatbank.enatmessanger.models.Chat;
import com.enatbank.enatmessanger.xmpp.XmppManager;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    Button mSend;
    EditText mMessageContent;
    XmppManager xmppManager;
    List<Chat> chats = new ArrayList<>();
    SingleChatRecyclerView mSingleChatRecyclerView;
    RecyclerView mRecyclerView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        xmppManager = new XmppManager();

        final Chat chat = (Chat) getIntent().getSerializableExtra("item");
        if (chat != null) {
            setTitle(chat.getUsername());
        }

        FloatingActionButton mSendChat= (FloatingActionButton) findViewById(R.id.send_chat);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSingleChatRecyclerView = new SingleChatRecyclerView(populate(), this);
        mRecyclerView.setAdapter(mSingleChatRecyclerView);
        mRecyclerView.smoothScrollToPosition(chats.size() - 1);
       /* mSend = (Button) findViewById(R.id.btn_send);*/
        mMessageContent = (EditText) findViewById(R.id.message);
        mSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chats.add(new Chat(R.drawable.avater, chat.getUsername(), mMessageContent.getText().toString(), new Date().toString()));
                mSingleChatRecyclerView.itemInserted();
                mRecyclerView.smoothScrollToPosition(chats.size() - 1);

                xmppManager.chat("btinsae@enatbankxmpp", mMessageContent.getText().toString());

                mMessageContent.setHint("Type message....");
            }
        });

    }

    public List<Chat> populate() {

        int[] avatar = {R.drawable.avater, R.drawable.avater, R.drawable.avater, R.drawable.avater, R.drawable.avater, R.drawable.avater, R.drawable.avater};
        String[] username = {"Birhane", "Abel", "Aman", "Qidus", "Birhane", "Birhane", "Birhane"};
        String[] chatDate = {"2017-02-15", "2017-02-15", "2017-02-15", "2017-02-15", "2017-02-15", "2017-02-15", "2017-02-15"};
        String[] chatMessage = {"Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,"};
        for (int i = 0; i < username.length && i < avatar.length; i++) {
            chats.add(new Chat(avatar[i], username[i], chatMessage[i], chatDate[i]));
        }

        return chats;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MaterialMenuInflater.with(this)
                .setDefaultColor(Color.WHITE)
                .inflate(R.menu.chat_menu,toolbar.getMenu() );
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
