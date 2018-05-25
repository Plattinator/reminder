package com.whatever.reminder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReminderItemAdapter extends RecyclerView.Adapter<ReminderItemAdapter.ViewHolder> {
    private ReminderItem[] mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mTextView;

        ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.textView);
        }
    }

    ReminderItemAdapter(ReminderItem[] dataset) {
        mDataset = dataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position].message);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
