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
import me.oogh.similar.data.entry.Whisper;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-16
 * @description
 */
public class WhisperAdapter extends RecyclerView.Adapter<WhisperAdapter.ViewHolder> {

    private List<Whisper> mDataSet;

    public WhisperAdapter(List<Whisper> dataSet) {
        mDataSet = dataSet;
    }

    public void updateDataSet(List<Whisper> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.whisper_recycle_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Whisper whisper = mDataSet.get(position);
        holder.content.setText(whisper.getContent());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_whisper_content)
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 移除某索引下的数据
     *
     * @param position
     */
    public void removeAt(int position) {
        mDataSet.remove(position);
        notifyDataSetChanged();
    }


}
