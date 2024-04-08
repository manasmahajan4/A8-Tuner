package adnyey.a8tuner;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahajan-PC on 2017-11-02.
 */

public class UserDatabase extends SQLiteOpenHelper {

    private static String USER_DB = "user.db";

    private static String CUSTOM_TABLE = "CREATE TABLE tunes(id INTEGER PRIMARY KEY AUTOINCREMENT, car_id INTEGER, car_rank INTEGER, max_accel INTEGER, max_top INTEGER, max_hand INTEGER, max_nitro INTEGER, pro_tires INTEGER, pro_susp INTEGER, pro_drive INTEGER, pro_exha INTEGER, car_top REAL, car_tk REAL)";

    private SQLiteDatabase myDataBase;

    private final Context myContext;


    public UserDatabase(Context context) {
        super(context, USER_DB, null, 1);
        Log.d("DATABASE", "Database reader");
        this.myContext = context;
    }

    @Override
    public synchronized void close() {
        Log.d("DATABASE", "closing DATABASE");
        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CUSTOM_TABLE);
        Log.d("DATABASE", "onCreate DATABASE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DATABASE", "onUpgrade Database");

    }


    public List<UserTune> getTunes() {
        List<UserTune> card = new ArrayList<>();
        try {
            String query = "SELECT * FROM tunes";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    card.add(new UserTune(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
                            cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7),
                            cursor.getInt(8), cursor.getInt(9), cursor.getInt(10), cursor.getDouble(11), cursor.getDouble(12), cursor.getInt(0)));
                } while (cursor.moveToNext());
            }
            try {
                db.close();
                cursor.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    public void remove(UserTune obj) {
        try {
            String query = "DELETE FROM tunes WHERE id=" + obj.getUniq();
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveTune(UserTune obj) {
        try {
            String query = "INSERT INTO tunes(car_id, car_rank, max_accel, max_top, max_hand, max_nitro, pro_tires, pro_susp, pro_drive, pro_exha, car_top, car_tk) VALUES("
                    + obj.getId() + "," + obj.getRank() + "," + obj.getAccel() + "," + obj.getTop() + "," + obj.getHand() + "," + obj.getNitro() + "," + obj.getTires() + "," + obj.getSusp() + "," + obj.getDrive() + "," + obj.getExha() + "," + obj.getSpeed() + "," + obj.getTk() + ")";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTune(UserTune obj, int UID) {
        try {
            String query = "UPDATE tunes SET car_rank=" + obj.getRank() + ", max_accel=" + obj.getAccel() + ", max_top=" + obj.getTop() + ", max_hand=" + obj.getHand() + ", max_nitro=" + obj.getNitro() + ", pro_tires=" + obj.getTires() + ", pro_susp=" + obj.getSusp() + ", pro_drive=" + obj.getDrive() + ", pro_exha=" + obj.getExha() + ", car_top=" + obj.getSpeed() + ", car_tk=" + obj.getTk() + " WHERE id=" + UID;
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
