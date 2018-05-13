package me.oogh.similar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.AllWhisper;
import me.oogh.similar.data.entry.Whisper;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-05-13
 * @description 全部心语适配器
 */
public class WhisperAllAdapter extends RecyclerView.Adapter<WhisperAllAdapter.VH> {


    private List<AllWhisper> mDataSet;
    private Unbinder mUnbinder;

    public WhisperAllAdapter(List<AllWhisper> dataSet) {
        mDataSet = dataSet;
    }

    public void updateDataSet(List<AllWhisper> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.whisper_all_recycle_item, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Whisper whisper = mDataSet.get(position).getWhisper();
        holder.title.setText(whisper.getTopic().getTitle());
        holder.content.setText(whisper.getContent());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_all_title)
        Button title;
        @BindView(R.id.tv_all_content)
        TextView content;

        public VH(View itemView) {
            super(itemView);
            mUnbinder = ButterKnife.bind(this, itemView);
        }
    }

}
