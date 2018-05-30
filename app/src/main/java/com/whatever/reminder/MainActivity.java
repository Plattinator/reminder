package com.whatever.reminder;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
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

    private ReminderItemAdapter mReminderItemAdapter;
    private AppDatabase mDatabase;

    private static final int CREATE_ITEM_REQUEST = 0;

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
                startActivityForResult(intent, CREATE_ITEM_REQUEST);
            }
        });

        RecyclerView mainRecyclerView = findViewById(R.id.main_recycler_view);
        mainRecyclerView.setHasFixedSize(true);
        mReminderItemAdapter = new ReminderItemAdapter(new ReminderItem[0]);
        mainRecyclerView.setAdapter(mReminderItemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);

        RoomDatabase.Builder<AppDatabase> builder = Room.databaseBuilder(
                getApplicationContext(), AppDatabase.class, "reminder-database");
        builder.fallbackToDestructiveMigration();
        mDatabase = builder.build();

        ReloadReminderItems();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDatabase.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CREATE_ITEM_REQUEST:
                if (resultCode == RESULT_OK) {
                    ReminderItem reminderItem = data.getParcelableExtra(CreateItemActivity.RESULT_KEY);

                    new InsertReminderItemsAsyncTask(mDatabase.reminderItemDao(), new InsertReminderItemsAsyncTask.ResultListener() {
                        @Override
                        public void Result() {
                            ReloadReminderItems();
                        }
                    }, reminderItem).execute();
                }
                break;
        }
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

    private void ReloadReminderItems() {
        ReminderItemDao reminderItemDao = mDatabase.reminderItemDao();

        new LoadAllReminderItemsAsyncTask(reminderItemDao, new LoadAllReminderItemsAsyncTask.ResultListener() {
            @Override
            public void Result(ReminderItem[] reminderItems) {
                mReminderItemAdapter.updateDataset(reminderItems);
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

    private static class InsertReminderItemsAsyncTask extends AsyncTask<Void, Void, Void> {

        private final ReminderItemDao mReminderItemDao;
        private final ResultListener mResultListener;
        private final ReminderItem[] mReminderItems;

        InsertReminderItemsAsyncTask(ReminderItemDao reminderItemDao, ResultListener resultListener, ReminderItem... reminderItems) {
            mReminderItemDao = reminderItemDao;
            mResultListener = resultListener;
            mReminderItems = reminderItems;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mReminderItemDao.insertReminderItems(mReminderItems);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mResultListener.Result();
        }

        public interface ResultListener {
            void Result();
        }
    }
}
