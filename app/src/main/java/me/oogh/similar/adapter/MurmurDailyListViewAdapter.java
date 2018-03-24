package me.oogh.similar.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.Murmur;
import me.oogh.similar.utils.DateUtils;

/**
 * Created by oogh on 18-3-20.
 */

public class MurmurDailyListViewAdapter extends RecyclerView.Adapter<MurmurDailyListViewAdapter.ViewHolder> {


    private List<Murmur> mDataSet;

    public OnRecycleItemClickListener mListener;

    public MurmurDailyListViewAdapter(List<Murmur> dataSet) {
        mDataSet = dataSet;
    }

    public void setOnRecycleItemClickListener(OnRecycleItemClickListener listener) {
        mListener = listener;
    }


    /**
     * 更新数据
     *
     * @param dataSet
     */
    public void updateDataSet(List<Murmur> dataSet) {
        Log.d(TAG, "updateDataSet: 数据更新");
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    private static final String TAG = MurmurDailyListViewAdapter.class.getSimpleName();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.murmur_daily_recycle_item, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Murmur murmur = mDataSet.get(position);

        holder.date.setText(DateUtils.format(murmur.getDate()));
        holder.content.setText(murmur.getContent());
        holder.itemView.setOnClickListener(view -> mListener.onRecycleItemClicked(view, murmur, position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_murmur_daily_date)
        TextView date;
        @BindView(R.id.tv_murmur_daily_content)
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 移除position位置的数据
     *
     * @param position
     */
    public void removeItem(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnRecycleItemClickListener {
        void onRecycleItemClicked(View v, Murmur murmur, int position);
    }
}
