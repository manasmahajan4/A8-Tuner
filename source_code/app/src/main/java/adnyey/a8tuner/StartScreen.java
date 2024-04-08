package adnyey.a8tuner;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.brouding.blockbutton.BlockButton;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.io.IOException;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Mahajan-PC on 2017-11-02.
 */

/**
 * TODO
 * Long press the list to modify and delete
 * Show real speed with TK on saved tunes
 *
 */

public class StartScreen extends AppCompatActivity {

    BlockButton start_tune, about, butt_set;
    RecyclerView usertunes;
    SavedTunesAdapter mAdapter;
    UserDatabase myUser;
    List<UserTune> myList;
    CarDBAccess myDbHelper;
    DIagSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        myDbHelper = CarDBAccess.getInstance(StartScreen.this);
        myDbHelper.open();

        setContentView(R.layout.main_screen);

        AppUpdater appUpdater = new AppUpdater(StartScreen.this);
        appUpdater.setUpdateFrom(UpdateFrom.JSON)
                .setButtonDoNotShowAgain(null)
                .setUpdateJSON("https://raw.githubusercontent.com/adnyey/A8-Tuner/master/update_changelog.json")
                .start();

        linkIt();
        clickIt();
    }

    void linkIt() {
        start_tune = (BlockButton) findViewById(R.id.start_tuning);
        butt_set = (BlockButton) findViewById(R.id.settings);
        about = (BlockButton) findViewById(R.id.about);
        usertunes = (RecyclerView) findViewById(R.id.saved_tunes);
        //warning=(TextView)findViewById(R.id.warning);
    }

    void clickIt() {
        usertunes = (RecyclerView) findViewById(R.id.saved_tunes);
        myUser = new UserDatabase(StartScreen.this);

        myList = myUser.getTunes();
        //if(!myList.isEmpty())
            //warning.setVisibility(GONE);
        mAdapter = new SavedTunesAdapter(myList, StartScreen.this);

        usertunes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        usertunes.setItemAnimator(new DefaultItemAnimator());

        usertunes.setAdapter(mAdapter);

        settings = new DIagSettings(StartScreen.this);
        butt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.show();
            }
        });

        settings.setDialogResult(new DiagSelector.OnMyDialogResult() {
            @Override
            public void finish(int result) {
                usertunes = (RecyclerView) findViewById(R.id.saved_tunes);
                myUser = new UserDatabase(StartScreen.this);

                myList = myUser.getTunes();

                //warning=(TextView)findViewById(R.id.warning);
                //if(!myList.isEmpty())
                   // warning.setVisibility(GONE);

                mAdapter = new SavedTunesAdapter(myList, StartScreen.this);

                usertunes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                usertunes.setItemAnimator(new DefaultItemAnimator());

                usertunes.setAdapter(mAdapter);
            }
        });


        start_tune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), About.class);
                startActivity(i);
            }
        });
    }





    @Override
    protected void onResume() {
        super.onResume();


        usertunes = (RecyclerView) findViewById(R.id.saved_tunes);
        myUser = new UserDatabase(StartScreen.this);

        myList = myUser.getTunes();

        //warning=(TextView)findViewById(R.id.warning);
        //if(!myList.isEmpty())
            //warning.setVisibility(GONE);

        mAdapter = new SavedTunesAdapter(myList, StartScreen.this);

        usertunes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        usertunes.setItemAnimator(new DefaultItemAnimator());

        usertunes.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.notif_exit), Toast.LENGTH_SHORT).show();

            mHandler.postDelayed(mRunnable, 2000);

    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;
        private StartScreen activity;

        //private List<Message> messages;
        public ProgressTask(StartScreen activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }


        /** progress dialog to show user that the backup is processing. */

        /**
         * application context.
         */
        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Fetching database...");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (success) {
                myDbHelper = CarDBAccess.getInstance(StartScreen.this);

                setContentView(R.layout.main_screen);
                linkIt();
                clickIt();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setMessage("Unable to fetch database. No internet connection detected!");
                dialog.setCancelable(false);
                dialog.show();
            }
        }

        protected Boolean doInBackground(final String... args) {
            return DetectConnection.hasInternetAccess(activity);
            //return true;
        }

    }


}
