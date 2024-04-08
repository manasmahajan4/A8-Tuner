package adnyey.a8tuner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.brouding.blockbutton.BlockButton;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    UpsPack AllPack;
    UserTune incoming;
    int CLS;
    int ID = 176;
    double speed_coef;
    double per_accel, per_top, per_hand, per_nitro;
    private ProgressDialog dialog;

    double accel_stock, accel_MAX, accel_PRO, accel_tk;
    double top_stock, top_MAX, top_PRO, top_tk;
    double hand_stock, hand_MAX, hand_PRO, hand_tk;
    double nitro_stock, nitro_MAX, nitro_PRO, nitro_tk;
    double rank_stock, rank_MAX, rank_PRO, rank_tk;

    double[] top_MAX_ups, accel_MAX_ups, hand_MAX_ups, nitro_MAX_ups;
    double[] top_MAX_rank, accel_MAX_rank, hand_MAX_rank, nitro_MAX_rank;
    double[][] tires_PRO_ups, susp_PRO_ups, drive_PRO_ups, exha_PRO_ups;
    double[] tires_PRO_rank, susp_PRO_rank, drive_PRO_rank, exha_PRO_rank;


    TextView lvl_top, lvl_accel, lvl_hand, lvl_nitro, lvl_tires, lvl_susp, lvl_drive, lvl_exha, sp_text;

    double curr_accel, curr_top, curr_hand, curr_nitro, forced_cof, sp_modif, curr_rank;
    double PRO_coef[] = {0.1333, 0.1667, 0.200, 0.2333, 0.2667};
    double MAX_coef[] = {0.1, 0.15, 0.2, 0.25, 0.3};

    DecimalFormat format_accel = new DecimalFormat("####0.00");
    DecimalFormat format_hand = new DecimalFormat("####0.000");
    DecimalFormat format_sp = new DecimalFormat("####0.0");
    DecimalFormat format_rank = new DecimalFormat("####0");

    ImageView img_top, img_accel, img_hand, img_nitro, img_tires, img_susp, img_drive, img_exha;

    int count_accel = 0, count_top = 0, count_hand = 0, count_nitro = 0, count_exha = 0, count_tires = 0, count_susp = 0, count_drive = 0;

    TextView dis_rank, dis_accel, dis_top, dis_hand, dis_nitro, dis_total, title_name;

    BlockButton plus_accel, plus_top, plus_hand, plus_nitro, plus_tires, plus_susp, plus_drive, plus_exha, reset, tuning, speed, title, save;

    private CarDBAccess myDbHelper;

    private UserDatabase myUser;

    DiagSelector dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDbHelper = CarDBAccess.getInstance(MainActivity.this);

        myUser = new UserDatabase(MainActivity.this);

        setContentView(R.layout.activity_main);

        linker();
        initiate();
        clicker();

    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private MainActivity activity;

        //private List<Message> messages;
        public ProgressTask(MainActivity activity) {
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
            dialog.setMessage("Validating...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (success) {
                setContentView(R.layout.activity_main);
                linker();
                initiate();
                clicker();
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

    void clicker() {
        dialog1 = new DiagSelector(MainActivity.this);
        dialog1.setDialogResult(new DiagSelector.OnMyDialogResult() {
            public void finish(int result) {
                ID = result;
                dialog1.dismiss();
                initiate();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat format = new DecimalFormat("##.#");
                double tk_top = curr_top, tk_nitro = curr_nitro;
                try {

                    switch (count_top) {
                        case 0:
                            tk_top += top_MAX_ups[0];
                            break;
                        case 1:
                            tk_top += top_MAX_ups[1];
                            break;
                        case 2:
                            tk_top += top_MAX_ups[2];
                            break;
                        case 3:
                            tk_top += top_MAX_ups[3];
                            break;
                        case 4:
                            tk_top += top_MAX_ups[4];
                            break;
                        case 5:
                            tk_top += (top_MAX_ups[3] + top_MAX_ups[4]) / 2;
                            break;
                    }

                    switch (count_nitro) {
                        case 0:
                            tk_nitro += nitro_MAX_ups[0];
                            break;
                        case 1:
                            tk_nitro += nitro_MAX_ups[1];
                            break;
                        case 2:
                            tk_nitro += nitro_MAX_ups[2];
                            break;
                        case 3:
                            tk_nitro += nitro_MAX_ups[3];
                            break;
                        case 4:
                            tk_nitro += nitro_MAX_ups[4];
                            break;
                        case 5:
                            tk_nitro += (nitro_MAX_ups[3] + nitro_MAX_ups[4]) / 2;
                            break;
                    }

                    UserTune obj = new UserTune(ID, Integer.valueOf(format_rank.format(curr_rank)), count_accel, count_top, count_hand, count_nitro, count_tires, count_susp, count_drive, count_exha, format.parse("" + (curr_top + curr_nitro) * speed_coef).doubleValue(), format.parse("" + (tk_nitro + tk_top) * speed_coef).doubleValue(), 0);

                    if (getIntent().getSerializableExtra("package") != null) {
                        myUser.updateTune(obj,incoming.getUniq());
                        Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                    } else
                        myUser.saveTune(obj);

                    Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


        tuning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("TUNING", false)) {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("TUNING", true).apply();
                    refresh();
                } else if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("TUNING", false)) {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("TUNING", false).apply();
                    refresh();
                }
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         dialog1.show();
                                     }
                                 }
        );

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("SPEED", false)) {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("SPEED", true).apply();
                    refresh();
                } else if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("SPEED", false)) {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("SPEED", false).apply();
                    refresh();
                }
            }
        });

        plus_tires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_tires < 5) {
                    Log.d("MAIN", "TIRES INC START" + curr_accel);
                    curr_hand += tires_PRO_ups[count_tires][0];
                    curr_top += tires_PRO_ups[count_tires][1];
                    curr_rank += tires_PRO_rank[count_tires];
                    count_tires++;
                    refresh();
                    if (count_tires == 5)
                        v.setEnabled(false);
                }
                refresh();
            }
        });

        plus_susp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_susp < 5) {
                    Log.d("MAIN", "SUSP INC START" + curr_accel);
                    curr_hand += susp_PRO_ups[count_susp][0];
                    curr_nitro += susp_PRO_ups[count_susp][1];
                    curr_rank += susp_PRO_rank[count_susp];
                    count_susp++;
                    refresh();
                    if (count_susp == 5)
                        v.setEnabled(false);
                }
                refresh();
            }
        });

        plus_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_drive < 5) {
                    Log.d("MAIN", "DRIVE INC START");
                    curr_accel += drive_PRO_ups[count_drive][0];
                    curr_top += drive_PRO_ups[count_drive][1];
                    curr_rank += drive_PRO_rank[count_drive];
                    count_drive++;
                    refresh();
                    if (count_drive == 5)
                        v.setEnabled(false);
                }
                refresh();
            }
        });

        plus_exha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_exha < 5) {
                    Log.d("MAIN", "EXHA INC START");
                    curr_nitro += exha_PRO_ups[count_exha][0];
                    curr_accel += exha_PRO_ups[count_exha][1];
                    curr_rank += exha_PRO_rank[count_exha];
                    count_exha++;
                    refresh();
                    if (count_exha == 5)
                        v.setEnabled(false);
                }
                refresh();
            }
        });

        plus_accel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MAIN", "ACCEL START");
                if (count_accel < 5) {
                    Log.d("MAIN", "ACCEL INC START" + curr_accel);
                    curr_accel += accel_MAX_ups[count_accel];
                    curr_rank += accel_MAX_rank[count_accel];
                    count_accel++;
                    refresh();
                    if (count_accel == 5)
                        v.setEnabled(false);
                }
                refresh();
            }
        });

        plus_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_top < 5) {
                    curr_top += top_MAX_ups[count_top];
                    curr_rank += top_MAX_rank[count_top];
                    count_top++;
                    refresh();
                    if (count_top == 5)
                        v.setEnabled(false);
                }
                refresh();
            }
        });

        plus_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_hand < 5) {
                    curr_hand += hand_MAX_ups[count_hand];
                    curr_rank += hand_MAX_rank[count_hand];
                    count_hand++;
                    refresh();
                    if (count_hand == 5)
                        v.setEnabled(false);
                }
                refresh();
            }
        });

        plus_nitro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_nitro < 5) {
                    curr_nitro += nitro_MAX_ups[count_nitro];
                    curr_rank += nitro_MAX_rank[count_nitro];
                    count_nitro++;
                    refresh();
                    if (count_nitro == 5)
                        v.setEnabled(false);
                }
                refresh();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiate();
            }
        });

    }


    void linker() {

        dis_accel = (TextView) findViewById(R.id.accel);
        dis_hand = (TextView) findViewById(R.id.handling);
        dis_nitro = (TextView) findViewById(R.id.nitro);
        dis_rank = (TextView) findViewById(R.id.rank);
        dis_total = (TextView) findViewById(R.id.total_speed);
        dis_top = (TextView) findViewById(R.id.top_speed);
        sp_text = (TextView) findViewById(R.id.sp_text);
        title_name = (TextView) findViewById(R.id.car_name);

        img_top = (ImageView) findViewById(R.id.img_top);
        img_accel = (ImageView) findViewById(R.id.img_accel);
        img_hand = (ImageView) findViewById(R.id.img_hand);
        img_nitro = (ImageView) findViewById(R.id.img_nitro);
        img_tires = (ImageView) findViewById(R.id.img_tires);
        img_susp = (ImageView) findViewById(R.id.img_susp);
        img_drive = (ImageView) findViewById(R.id.img_drive);
        img_exha = (ImageView) findViewById(R.id.img_exha);

        lvl_top = (TextView) findViewById(R.id.lvl_top);
        lvl_accel = (TextView) findViewById(R.id.lvl_accel);
        lvl_hand = (TextView) findViewById(R.id.lvl_hand);
        lvl_nitro = (TextView) findViewById(R.id.lvl_nitro);
        lvl_tires = (TextView) findViewById(R.id.lvl_tires);
        lvl_susp = (TextView) findViewById(R.id.lvl_susp);
        lvl_drive = (TextView) findViewById(R.id.lvl_drive);
        lvl_exha = (TextView) findViewById(R.id.lvl_exha);

        plus_accel = (BlockButton) findViewById(R.id.plus_accel);
        plus_hand = (BlockButton) findViewById(R.id.plus_hand);
        plus_nitro = (BlockButton) findViewById(R.id.plus_nitro);
        plus_top = (BlockButton) findViewById(R.id.plus_top);
        title = (BlockButton) findViewById(R.id.title);
        save = (BlockButton) findViewById(R.id.save);

        plus_tires = (BlockButton) findViewById(R.id.plus_tires);
        plus_susp = (BlockButton) findViewById(R.id.plus_susp);
        plus_drive = (BlockButton) findViewById(R.id.plus_drive);
        plus_exha = (BlockButton) findViewById(R.id.plus_exha);
        reset = (BlockButton) findViewById(R.id.reset);
        tuning = (BlockButton) findViewById(R.id.tk);
        speed = (BlockButton) findViewById(R.id.real);
    }

    void initiate() {

        try {
            incoming = (UserTune) getIntent().getSerializableExtra("package");
            ID = incoming.getId();
            AllPack = myDbHelper.getData(incoming.getId());
            count_accel = incoming.getAccel();
            count_top = incoming.getTop();
            count_hand = incoming.getHand();
            count_nitro = incoming.getNitro();
            count_tires = incoming.getTires();
            count_susp = incoming.getSusp();
            count_drive = incoming.getDrive();
            count_exha = incoming.getExha();

        } catch (NullPointerException e) {
            AllPack = myDbHelper.getData(ID);
            count_accel = count_top = count_hand = count_nitro = count_tires = count_susp = count_drive = count_exha = 0;
        }

        title_name.setText(AllPack.getName());
        CLS = AllPack.getCLS();

        if (AllPack.getSpeed_coef() > 0)
            speed_coef = AllPack.getSpeed_coef() * 0.01;
        else
            speed_coef = 1;

        per_accel = AllPack.getPer_accel() * 0.01;
        per_top = AllPack.getPer_top() * 0.01;
        per_hand = AllPack.getPer_hand() * 0.01;
        per_nitro = AllPack.getPer_nitro() * 0.01;

        accel_stock = AllPack.getAccel_stock();
        accel_MAX = AllPack.getAccel_MAX();
        accel_PRO = AllPack.getAccel_PRO();
        accel_tk = AllPack.getAccel_tk();

        top_stock = AllPack.getTop_stock();
        top_MAX = AllPack.getTop_MAX();
        top_PRO = AllPack.getTop_PRO();
        top_tk = AllPack.getTop_tk();

        hand_stock = AllPack.getHand_stock();
        hand_MAX = AllPack.getHand_MAX();
        hand_PRO = AllPack.getHand_PRO();
        hand_tk = AllPack.getHand_tk();

        nitro_stock = AllPack.getNitro_stock();
        nitro_MAX = AllPack.getNitro_MAX();
        nitro_PRO = AllPack.getNitro_PRO();
        nitro_tk = AllPack.getNitro_tk();

        rank_stock = AllPack.getRank_stock();
        rank_MAX = AllPack.getRank_MAX();
        rank_PRO = AllPack.getRank_PRO();
        rank_tk = AllPack.getRank_tk();

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("SPEED", false)) {
            sp_text.setText("REAL SPEED");
        } else if (!PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("SPEED", false)) {
            sp_text.setText("DISPLAY SPEED");
        }

        Log.d("MAIN", "INITIATE START");
        plus_accel.setEnabled(true);
        plus_nitro.setEnabled(true);
        plus_top.setEnabled(true);
        plus_hand.setEnabled(true);

        plus_tires.setEnabled(true);
        plus_susp.setEnabled(true);
        plus_drive.setEnabled(true);
        plus_exha.setEnabled(true);


        curr_accel = accel_stock;
        curr_top = top_stock;
        curr_hand = hand_stock;
        curr_nitro = nitro_stock;
        curr_rank = rank_stock;

        top_MAX_ups = new double[5];
        accel_MAX_ups = new double[5];
        hand_MAX_ups = new double[5];
        nitro_MAX_ups = new double[5];

        top_MAX_rank = new double[5];
        accel_MAX_rank = new double[5];
        hand_MAX_rank = new double[5];
        nitro_MAX_rank = new double[5];

        tires_PRO_rank = new double[5];
        susp_PRO_rank = new double[5];
        drive_PRO_rank = new double[5];
        exha_PRO_rank = new double[5];

        tires_PRO_ups = new double[5][2];
        susp_PRO_ups = new double[5][2];
        drive_PRO_ups = new double[5][2];
        exha_PRO_ups = new double[5][2];

        for (int i = 0; i < 5; i++) {


            accel_MAX_rank[i] = (((rank_MAX - rank_stock) * per_accel * MAX_coef[i]));
            top_MAX_rank[i] = (((rank_MAX - rank_stock) * per_top * MAX_coef[i]));
            hand_MAX_rank[i] = (((rank_MAX - rank_stock) * per_hand * MAX_coef[i]));
            nitro_MAX_rank[i] = (((rank_MAX - rank_stock) * per_nitro * MAX_coef[i]));

            accel_MAX_ups[i] = ((accel_MAX - accel_stock) * MAX_coef[i]);
            top_MAX_ups[i] = ((top_MAX - top_stock) * MAX_coef[i]);
            hand_MAX_ups[i] = ((hand_MAX - hand_stock) * MAX_coef[i]);
            nitro_MAX_ups[i] = (nitro_MAX - nitro_stock) * MAX_coef[i];

            tires_PRO_rank[i] = (rank_PRO - rank_MAX) * (per_top + per_hand) * 0.5 * PRO_coef[i];
            susp_PRO_rank[i] = (rank_PRO - rank_MAX) * (per_nitro + per_hand) * 0.5 * PRO_coef[i];
            drive_PRO_rank[i] = (rank_PRO - rank_MAX) * (per_top + per_accel) * 0.5 * PRO_coef[i];
            exha_PRO_rank[i] = (rank_PRO - rank_MAX) * (per_accel + per_nitro) * 0.5 * PRO_coef[i];

            tires_PRO_ups[i][0] = 0.5 * 0.725 * (hand_MAX - hand_stock) * PRO_coef[i] * ((hand_PRO - hand_MAX) / (0.725 * (hand_MAX - hand_stock))); //hand
            tires_PRO_ups[i][1] = 0.5 * 0.725 * (top_MAX - top_stock) * PRO_coef[i] * ((top_PRO - top_MAX) / (0.725 * (top_MAX - top_stock))); //top

            susp_PRO_ups[i][0] = 0.5 * 0.725 * (hand_MAX - hand_stock) * PRO_coef[i] * ((hand_PRO - hand_MAX) / (0.725 * (hand_MAX - hand_stock))); //hand
            susp_PRO_ups[i][1] = 0.5 * 0.725 * (nitro_MAX - nitro_stock) * PRO_coef[i] * ((nitro_PRO - nitro_MAX) / (0.725 * (nitro_MAX - nitro_stock))); //nitro

            drive_PRO_ups[i][0] = 0.5 * 0.725 * (accel_MAX - accel_stock) * PRO_coef[i] * ((accel_PRO - accel_MAX) / (0.725 * (accel_MAX - accel_stock))); //accel
            drive_PRO_ups[i][1] = 0.5 * 0.725 * (top_MAX - top_stock) * PRO_coef[i] * ((top_PRO - top_MAX) / (0.725 * (top_MAX - top_stock))); //top

            exha_PRO_ups[i][0] = 0.5 * 0.725 * (nitro_MAX - nitro_stock) * PRO_coef[i] * ((nitro_PRO - nitro_MAX) / (0.725 * (nitro_MAX - nitro_stock))); //nitro
            exha_PRO_ups[i][1] = 0.5 * 0.725 * (accel_MAX - accel_stock) * PRO_coef[i] * ((accel_PRO - accel_MAX) / (0.725 * (accel_MAX - accel_stock))); //accel
        }

        switch (CLS) {
            case 1:
                img_tires.setImageResource(R.drawable.tire_d);
                img_susp.setImageResource(R.drawable.susp_d);
                img_drive.setImageResource(R.drawable.drive_d);
                img_exha.setImageResource(R.drawable.exha_d);
                break;
            case 2:
                img_tires.setImageResource(R.drawable.tire_c);
                img_susp.setImageResource(R.drawable.susp_c);
                img_drive.setImageResource(R.drawable.drive_c);
                img_exha.setImageResource(R.drawable.exha_c);
                break;
            case 3:
                img_tires.setImageResource(R.drawable.tire_b);
                img_susp.setImageResource(R.drawable.susp_b);
                img_drive.setImageResource(R.drawable.drive_b);
                img_exha.setImageResource(R.drawable.exha_b);
                break;
            case 4:
                img_tires.setImageResource(R.drawable.tire_a);
                img_susp.setImageResource(R.drawable.susp_a);
                img_drive.setImageResource(R.drawable.drive_a);
                img_exha.setImageResource(R.drawable.exha_a);
                break;
            case 5:
                img_tires.setImageResource(R.drawable.tire_s);
                img_susp.setImageResource(R.drawable.susp_s);
                img_drive.setImageResource(R.drawable.drive_s);
                img_exha.setImageResource(R.drawable.exha_s);
                break;
        }

        lvl_top.setText(getString(R.string.lvl) + " 0");
        lvl_accel.setText(getString(R.string.lvl) + " 0");
        lvl_hand.setText(getString(R.string.lvl) + " 0");
        lvl_exha.setText(getString(R.string.lvl) + " 0");
        lvl_nitro.setText(getString(R.string.lvl) + " 0");
        lvl_tires.setText(getString(R.string.lvl) + " 0");
        lvl_susp.setText(getString(R.string.lvl) + " 0");
        lvl_drive.setText(getString(R.string.lvl) + " 0");

        if (getIntent().getSerializableExtra("package") != null) {
            uptomark();
        }

        refresh();

    }

    void uptomark() {

        for (int i = 0; i < count_accel; i++) {
            curr_accel += accel_MAX_ups[i];
            curr_rank += accel_MAX_rank[i];
        }

        for (int i = 0; i < count_top; i++) {
            curr_top += top_MAX_ups[i];
            curr_rank += top_MAX_rank[i];
        }

        for (int i = 0; i < count_hand; i++) {
            curr_hand += hand_MAX_ups[i];
            curr_rank += hand_MAX_rank[i];
        }

        for (int i = 0; i < count_nitro; i++) {
            curr_nitro += nitro_MAX_ups[i];
            curr_rank += nitro_MAX_rank[i];
        }


        for (int i = 0; i < count_tires; i++) {
            curr_hand += tires_PRO_ups[i][0];
            curr_top += tires_PRO_ups[i][1];
            curr_rank += tires_PRO_rank[i];
        }

        for (int i = 0; i < count_susp; i++) {
            curr_hand += susp_PRO_ups[i][0];
            curr_nitro += susp_PRO_ups[i][1];
            curr_rank += susp_PRO_rank[i];
        }

        for (int i = 0; i < count_drive; i++) {
            curr_accel += drive_PRO_ups[i][0];
            curr_top += drive_PRO_ups[i][1];
            curr_rank += drive_PRO_rank[i];
        }

        for (int i = 0; i < count_exha; i++) {
            curr_nitro += exha_PRO_ups[i][0];
            curr_accel += exha_PRO_ups[i][1];
            curr_rank += exha_PRO_rank[i];
        }
    }

    void refresh() {

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("SPEED", false)) {
            sp_text.setText("REAL SPEED");
        } else if (!PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("SPEED", false)) {
            sp_text.setText("DISPLAY SPEED");
        }

        switch (count_top) {
            case 0:
                img_top.setImageResource(R.drawable.top_1);
            case 1:
                img_top.setImageResource(R.drawable.top_1);
                break;
            case 2:
                img_top.setImageResource(R.drawable.top_2);
                break;
            case 3:
                img_top.setImageResource(R.drawable.top_3);
                break;
            case 4:
                img_top.setImageResource(R.drawable.top_4);
                break;
            case 5:
                img_top.setImageResource(R.drawable.top_5);
                break;
        }

        switch (count_accel) {
            case 0:
                img_accel.setImageResource(R.drawable.accel_1);
            case 1:
                img_accel.setImageResource(R.drawable.accel_1);
                break;
            case 2:
                img_accel.setImageResource(R.drawable.accel_2);
                break;
            case 3:
                img_accel.setImageResource(R.drawable.accel_3);
                break;
            case 4:
                img_accel.setImageResource(R.drawable.accel_4);
                break;
            case 5:
                img_accel.setImageResource(R.drawable.accel_5);
                break;
        }

        switch (count_hand) {
            case 0:
                img_hand.setImageResource(R.drawable.hand_1);
            case 1:
                img_hand.setImageResource(R.drawable.hand_1);
                break;
            case 2:
                img_hand.setImageResource(R.drawable.hand_2);
                break;
            case 3:
                img_hand.setImageResource(R.drawable.hand_3);
                break;
            case 4:
                img_hand.setImageResource(R.drawable.hand_4);
                break;
            case 5:
                img_hand.setImageResource(R.drawable.hand_5);
                break;
        }

        switch (count_nitro) {
            case 0:
                img_nitro.setImageResource(R.drawable.nitro_1);
            case 1:
                img_nitro.setImageResource(R.drawable.nitro_1);
                break;
            case 2:
                img_nitro.setImageResource(R.drawable.nitro_2);
                break;
            case 3:
                img_nitro.setImageResource(R.drawable.nitro_3);
                break;
            case 4:
                img_nitro.setImageResource(R.drawable.nitro_4);
                break;
            case 5:
                img_nitro.setImageResource(R.drawable.nitro_5);
                break;
        }

        double disp_accel = curr_accel, disp_top = curr_top, disp_hand = curr_hand, disp_nitro = curr_nitro, disp_total;

        int disp_rank = Integer.valueOf(format_rank.format(curr_rank));

        dis_rank.setText("" + disp_rank + " / " + format_rank.format(rank_PRO));


        dis_accel.setTextColor(Color.WHITE);
        dis_total.setTextColor(Color.WHITE);
        dis_top.setTextColor(Color.WHITE);
        dis_hand.setTextColor(Color.WHITE);
        dis_nitro.setTextColor(Color.WHITE);


        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("TUNING", false)) {

            dis_rank.setText("" + disp_rank + " / " + format_rank.format(rank_PRO) + " +" + format_rank.format((rank_tk - rank_PRO)));

            dis_accel.setTextColor(Color.parseColor("#FF9800"));
            dis_total.setTextColor(Color.parseColor("#FF9800"));
            dis_top.setTextColor(Color.parseColor("#FF9800"));
            dis_hand.setTextColor(Color.parseColor("#FF9800"));
            dis_nitro.setTextColor(Color.parseColor("#FF9800"));


            switch (count_accel) {
                case 0:
                    disp_accel += accel_MAX_ups[0];
                    break;
                case 1:
                    disp_accel += accel_MAX_ups[1];
                    break;
                case 2:
                    disp_accel += accel_MAX_ups[2];
                    break;
                case 3:
                    disp_accel += accel_MAX_ups[3];
                    break;
                case 4:
                    disp_accel += accel_MAX_ups[4];
                    break;
                case 5:
                    disp_accel += (accel_MAX_ups[3] + accel_MAX_ups[4]) / 2;
                    break;
            }

            switch (count_top) {
                case 0:
                    disp_top += top_MAX_ups[0];
                    break;
                case 1:
                    disp_top += top_MAX_ups[1];
                    break;
                case 2:
                    disp_top += top_MAX_ups[2];
                    break;
                case 3:
                    disp_top += top_MAX_ups[3];
                    break;
                case 4:
                    disp_top += top_MAX_ups[4];
                    break;
                case 5:
                    disp_top += (top_MAX_ups[3] + top_MAX_ups[4]) / 2;
                    break;
            }

            switch (count_hand) {
                case 0:
                    disp_hand += hand_MAX_ups[0];
                    break;
                case 1:
                    disp_hand += hand_MAX_ups[1];
                    break;
                case 2:
                    disp_hand += hand_MAX_ups[2];
                    break;
                case 3:
                    disp_hand += hand_MAX_ups[3];
                    break;
                case 4:
                    disp_hand += hand_MAX_ups[4];
                    break;
                case 5:
                    disp_hand += (hand_MAX_ups[3] + hand_MAX_ups[4]) / 2;
                    break;
            }

            switch (count_nitro) {
                case 0:
                    disp_nitro += nitro_MAX_ups[0];
                    break;
                case 1:
                    disp_nitro += nitro_MAX_ups[1];
                    break;
                case 2:
                    disp_nitro += nitro_MAX_ups[2];
                    break;
                case 3:
                    disp_nitro += nitro_MAX_ups[3];
                    break;
                case 4:
                    disp_nitro += nitro_MAX_ups[4];
                    break;
                case 5:
                    disp_nitro += (nitro_MAX_ups[3] + nitro_MAX_ups[4]) / 2;
                    break;
            }

        }

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("SPEED", false)) {

            disp_top = disp_top * speed_coef;
            disp_nitro = disp_nitro * speed_coef;

        }

        disp_total = disp_top + disp_nitro;

        dis_accel.setText("" + format_accel.format(disp_accel) + " s");
        dis_total.setText("" + Unitzer.convert(disp_total, getApplicationContext()));
        dis_top.setText("" + Unitzer.convert(disp_top, getApplicationContext()));
        dis_hand.setText("" + format_hand.format(disp_hand) + " Gs");
        dis_nitro.setText("" + Unitzer.convert(disp_nitro, getApplicationContext()));

        if (count_top == 5)
            plus_top.setEnabled(false);
        if (count_accel == 5)
            plus_accel.setEnabled(false);
        if (count_hand == 5)
            plus_hand.setEnabled(false);
        if (count_nitro == 5)
            plus_nitro.setEnabled(false);
        if (count_tires == 5)
            plus_tires.setEnabled(false);
        if (count_susp == 5)
            plus_susp.setEnabled(false);
        if (count_drive == 5)
            plus_drive.setEnabled(false);
        if (count_exha == 5)
            plus_exha.setEnabled(false);

        lvl_top.setText(getString(R.string.lvl) + " " + count_top);
        lvl_accel.setText(getString(R.string.lvl) + " " + count_accel);
        lvl_hand.setText(getString(R.string.lvl) + " " + count_hand);
        lvl_exha.setText(getString(R.string.lvl) + " " + count_exha);
        lvl_nitro.setText(getString(R.string.lvl) + " " + count_nitro);
        lvl_tires.setText(getString(R.string.lvl) + " " + count_tires);
        lvl_susp.setText(getString(R.string.lvl) + " " + count_susp);
        lvl_drive.setText(getString(R.string.lvl) + " " + count_drive);
        Log.d("MAIN", "REFRESH END");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
