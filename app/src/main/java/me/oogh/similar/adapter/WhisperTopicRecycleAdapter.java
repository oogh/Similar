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
import me.oogh.similar.data.entry.Topic;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-11
 * @description
 */
public class WhisperTopicRecycleAdapter extends RecyclerView.Adapter<WhisperTopicRecycleAdapter.ViewHolder> {

    private List<Topic> mDataSet;

    public WhisperTopicRecycleAdapter(List<Topic> dataSet) {
        mDataSet = dataSet;
    }

    public void updateTopics(List<Topic> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.whisper_topic_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Topic topic = mDataSet.get(position);
        holder.title.setText(topic.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_whisper_topic_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
