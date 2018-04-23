package me.oogh.similar.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.Message;

/**
 * Created by oogh on 18-3-2.
 */

public class MessageRecycleAdapter extends RecyclerView.Adapter<MessageRecycleAdapter.ViewHolder> {


    private List<Message> mMessages;

    public MessageRecycleAdapter(List<Message> messages) {
        mMessages = messages;
    }

    public void updateMessages(List<Message> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_recycle_item, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.avatar.setText(message.getAvatar());
        holder.nickname.setText(message.getNickname());
        holder.content.setText(message.getContent());
        holder.datetime.setText(message.getDatetime());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_message_item_avatar)
        TextView avatar;
        @BindView(R.id.tv_message_item_nickname)
        TextView nickname;
        @BindView(R.id.tv_message_item_content)
        TextView content;
        @BindView(R.id.tv_message_item_datetime)
        TextView datetime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
