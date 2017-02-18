package com.enatbank.enatmessanger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enatbank.enatmessanger.R;
import com.enatbank.enatmessanger.models.Chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by btinsae on 15/02/2017.
 */

public class SingleChatRecyclerView extends RecyclerView.Adapter<SingleChatRecyclerView.SingleChatViewHolder> {
    private final int SELF = 100;
    List<Chat> chats = new ArrayList<>();
    private static String today;
    private Context mContext;

    public SingleChatRecyclerView(List<Chat> chats, Context mContext) {
        this.chats = chats;
        this.mContext = mContext;
        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public SingleChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == SELF) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_self, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_other, parent, false);
        }
        return new SingleChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleChatViewHolder holder, int position) {
        Chat message = chats.get(position);
        holder.message.setText(message.getChatMessage());

        String timestamp = getTimeStamp(new Date().toString());

        if (message.getUsername() != null)
            timestamp = message.getUsername() + ", " + timestamp;

        holder.timestamp.setText(timestamp);
    }

    @Override
    public int getItemViewType(int position) {
        Chat message = chats.get(position);
        if (message.getUsername().equals("Birhane")) {
            return SELF;
        }

        return position;
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static String getTimeStamp(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = "";

        today = today.length() < 2 ? "0" + today : today;

        try {
            Date date = format.parse(dateStr);
            SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
            String dateToday = todayFormat.format(date);
            format = dateToday.equals(today) ? new SimpleDateFormat("hh:mm a") : new SimpleDateFormat("dd LLL, hh:mm a");
            String date1 = format.format(date);
            timestamp = date1.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public class SingleChatViewHolder extends RecyclerView.ViewHolder {
        TextView message, timestamp;

        public SingleChatViewHolder(View itemView) {
            super(itemView);

            message = (TextView) itemView.findViewById(R.id.message);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
        }


        /*
        @Override
        public void onClick(final View view) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
            String item = mList.get(itemPosition);
            Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();
        }*/
    }
    public void itemInserted(){
        notifyItemInserted(chats.size()-1);
    }
}
