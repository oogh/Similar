package me.oogh.similar.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.MyTopic;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-11
 * @description
 */
public class WhisperMyTopicRecycleAdapter extends RecyclerView.Adapter<WhisperMyTopicRecycleAdapter.ViewHolder> {

    private List<MyTopic> mDataSet;
    private SparseBooleanArray mSelectedItems;
    private Context mContext;

    public void setClearedListener(OnDataClearedListener clearedListener) {
        mClearedListener = clearedListener;
    }

    private OnDataClearedListener mClearedListener;


    public SparseBooleanArray getSelectedItems() {
        return mSelectedItems;
    }

    public WhisperMyTopicRecycleAdapter(Context context, List<MyTopic> dataSet) {
        mContext = context;
        mDataSet = dataSet;
        mSelectedItems = new SparseBooleanArray();
    }

    public void updateTopics(List<MyTopic> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
        if (dataSet.size() == 0) {
            mClearedListener.onDataCleared();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.whisper_topic_recycle_item, parent, false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyTopic topic = mDataSet.get(position);
        holder.title.setText(topic.getTitle());
        // TODO: 心愿数量还没有被统计
        Drawable ripple = mContext.getResources().getDrawable(R.drawable.ic_ripple_recycle_item, null);
        Drawable color = mContext.getResources().getDrawable(R.drawable.ic_color_recycle_item, null);
        Drawable drawable = mSelectedItems.get(position) ? color : ripple;
        holder.itemView.setBackground(drawable);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_whisper_topic_item_title)
        TextView title;
        @BindView(R.id.tv_whisper_topic_item_count)
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 切换 itemView的选中状态
     *
     * @param position
     */
    public void toggleSelectStatus(int position) {
        if (mSelectedItems.get(position)) {
            mSelectedItems.delete(position);
        } else {
            mSelectedItems.put(position, true);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取被选中Item的数量
     *
     * @return
     */
    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    /**
     * 清空存放被选中项的缓存集合
     */
    public void clearSelectedItems() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public interface OnDataClearedListener {
        void onDataCleared();
    }
}
