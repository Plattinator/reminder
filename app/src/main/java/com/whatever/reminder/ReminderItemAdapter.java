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
        final TextView mMessageTextView;
        final TextView mDateTimeTextView;

        ViewHolder(View view) {
            super(view);
            mMessageTextView = view.findViewById(R.id.message_text_view);
            mDateTimeTextView = view.findViewById(R.id.date_time_text_view);
        }
    }

    ReminderItemAdapter(ReminderItem[] dataset) {
        mDataset = dataset;
    }

    void updateDataset(ReminderItem[] dataset) {
        mDataset = dataset;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mMessageTextView.setText(mDataset[position].message);
        holder.mDateTimeTextView.setText(new LocalDateTimeConverter().localDateTimeToString(mDataset[position].dateTime));
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
