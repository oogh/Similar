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
import me.oogh.similar.data.entry.Comment;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-05-17
 * @description
 */
public class WhisperCommentAdapter extends RecyclerView.Adapter<WhisperCommentAdapter.VH> {


    private List<Comment> mDataSet;

    public WhisperCommentAdapter(List<Comment> dataSet) {
        mDataSet = dataSet;
    }

    public void updateDataSet(List<Comment> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.whisper_comment_recycle_item, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Comment comment = mDataSet.get(position);
        holder.content.setText(": " + comment.getContent());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_comment_content)
        TextView content;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
