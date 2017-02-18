package com.enatbank.enatmessanger.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enatbank.enatmessanger.R;
import com.enatbank.enatmessanger.activities.ChatActivity;
import com.enatbank.enatmessanger.models.Chat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by btinsae on 15/02/2017.
 */

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder> {
    private List<Chat> chats = new ArrayList<>();
    private Context mContext;
    int position;

    public ChatRecyclerViewAdapter(List<Chat> chats, Context mContext) {
        this.chats = chats;
        this.mContext = mContext;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        Chat chat = chats.get(position);
        this.position = holder.getAdapterPosition();
        holder.chatMessage.setText(chat.getChatMessage());
        holder.username.setText(chat.getUsername());
        holder.chatDate.setText(chat.getChatDate());
        Picasso.with(mContext)
                .load(chat.getUserAvatar())
                .placeholder(R.drawable.avater)
                .error(R.drawable.avater)
                .into(holder.userAvater);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatTextView username, chatMessage, chatDate;
        private AppCompatImageView userAvater;

        public ChatViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Typeface customFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
            username = (AppCompatTextView) itemView.findViewById(R.id.textview_username);
            username.setTypeface(customFont);
            chatMessage = (AppCompatTextView) itemView.findViewById(R.id.chat_content);
            chatMessage.setTypeface(customFont);
            chatDate = (AppCompatTextView) itemView.findViewById(R.id.chat_date);
            chatDate.setTypeface(customFont);
            userAvater = (AppCompatImageView) itemView.findViewById(R.id.avatar);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            intent.putExtra("item", chats.get(getAdapterPosition()));
            v.getContext().startActivity(intent);
        }
    }
}
