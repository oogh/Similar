package me.oogh.similar.adapter;

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
import me.oogh.similar.whisper.WhisperTopicChatActivity;

public class WhisperTopicChatAdapter extends RecyclerView.Adapter<WhisperTopicChatAdapter.VH> {

    private List<Message> mDataSet;
    public WhisperTopicChatAdapter(List<Message> dataSet){
        mDataSet = dataSet;
    }

    public void updateDataSet(List<Message> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.whisper_chat_recycle_item, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Message msg = mDataSet.get(position);
        holder.message.setText(msg.getContent());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_chat_message)
        TextView message;
        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
