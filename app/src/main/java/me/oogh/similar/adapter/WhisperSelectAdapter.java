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
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.Whisper;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-23
 * @description
 */
public class WhisperSelectAdapter extends RecyclerView.Adapter<WhisperSelectAdapter.VH> {
    private List<Whisper> mDataSet;

    private SparseBooleanArray mSelectedItems;
    private Context mContext;

    public WhisperSelectAdapter(List<Whisper> dataSet) {
        mDataSet = dataSet;
        mSelectedItems = new SparseBooleanArray();
    }

    public void updateDataSet(List<Whisper> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.whisper_select_recycle_item, parent, false);
        return new VH(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(VH holder, int position) {
        Whisper whisper = mDataSet.get(position);
        holder.title.setText(whisper.getTopic().getTitle());
        holder.content.setText(whisper.getContent());
        Drawable ripple = mContext.getResources().getDrawable(R.drawable.ic_ripple_recycle_item, null);
        Drawable color = mContext.getResources().getDrawable(R.drawable.ic_color_recycle_item, null);
        Drawable drawable = mSelectedItems.get(position) ? color : ripple;
        holder.itemView.setBackground(drawable);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_title)
        Button title;
        @BindView(R.id.tv_content)
        TextView content;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 返回被选中Item的数量
     *
     * @return
     */
    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    /**
     * 获取选中项的集合
     *
     * @return
     */
    public SparseBooleanArray getSelectedItems() {
        return mSelectedItems;
    }


    /**
     * 切换 position 位置 itemView 的选中状态
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
     * 清空存放被选中项的集合
     */
    public void clearSelectedItems() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }
}
