package com.whatever.reminder;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    // Usage based on example from https://developer.android.com/guide/topics/ui/layout/recyclerview
    private RecyclerView mMainRecyclerView;
    private AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        final MainActivity mainActivity = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, CreateItemActivity.class);
                startActivity(intent);
            }
        });

        mMainRecyclerView = findViewById(R.id.main_recycler_view);
        mMainRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMainRecyclerView.setLayoutManager(layoutManager);

        mDatabase = Room.databaseBuilder(
                getApplicationContext(), AppDatabase.class, "reminder-database").build();
        ReminderItemDao reminderItemDao = mDatabase.reminderItemDao();

        new LoadAllReminderItemsAsyncTask(reminderItemDao, new LoadAllReminderItemsAsyncTask.ResultListener() {
            @Override
            public void Result(ReminderItem[] reminderItems) {
                mMainRecyclerView.setAdapter(new ReminderItemAdapter(reminderItems));
            }
        }).execute();
    }

    private static class LoadAllReminderItemsAsyncTask extends AsyncTask<Void, Void, ReminderItem[]> {

        private final ReminderItemDao mReminderItemDao;
        private final ResultListener mResultListener;

        LoadAllReminderItemsAsyncTask(ReminderItemDao reminderItemDao, ResultListener resultListener) {
            mReminderItemDao = reminderItemDao;
            mResultListener = resultListener;
        }

        @Override
        protected ReminderItem[] doInBackground(Void... voids) {
            return mReminderItemDao.loadAllReminderItems();
        }

        @Override
        protected void onPostExecute(ReminderItem[] reminderItems) {
            mResultListener.Result(reminderItems);
        }

        public interface ResultListener {
            void Result(ReminderItem[] reminderItems);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDatabase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
