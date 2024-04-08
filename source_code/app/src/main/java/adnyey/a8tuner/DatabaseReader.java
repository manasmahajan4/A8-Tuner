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

public class DatabaseReader extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/adnyey.a8tuner/databases/";

    private static String DB_NAME = "databases/dataup1.db";

    private SQLiteDatabase myDataBase;

    private static int CURRENT_VERSION = 18;

    private final Context myContext;


    public DatabaseReader(Context context) {
        super(context, DB_NAME, null, CURRENT_VERSION);
        Log.d("DATABASE", "Database reader");
        this.myContext = context;
    }

    public void createDataBase() throws IOException {
        Log.d("DATABASE", "Create Database");
        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
            Log.d("DATABASE", "No need");
        } else {
            Log.d("DATABASE", "Creating empty database");
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                Log.d("DATABASE", "starting copy");
                copyDataBase();
                Log.d("DATABASE", "copy complete");
            } catch (IOException e) {
                Log.d("DATABASE", "ERROR COPYING DATABASE");
                throw new Error("Error copying database");
            }
        }

    }

    private boolean checkDataBase() {
        Log.d("DATABASE", "checking database");
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            Log.d("DATABASE", "database ver "+checkDB.getVersion());
        } catch (SQLiteException e) {
            Log.d("DATABASE", "No database found!");
            //database does't exist yet.

        }

        if (checkDB != null) {
            if (checkDB.getVersion() < CURRENT_VERSION) {
                Log.d("DATABASE", "old database");
                checkDB.close();
                return false;
            }
            checkDB.close();
            return true;
        } else
            return false;
    }

    private void copyDataBase() throws IOException {
        Log.d("DATABASE", "copyfunction");
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        Log.d("DATABASE", "opening database");
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

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
        Log.d("DATABASE", "onCreate DATABASE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DATABASE", "onUpgrade Database");
        try {
            if (newVersion > oldVersion) {
                copyDataBase();
                Log.d("DATABASE", "upgrade complete");
            }
        } catch (IOException e) {
        }

    }

    public List<List<GridItem>> getLists() {
        List<List<GridItem>> card = new ArrayList<List<GridItem>>();
        try {
            String query1 = "SELECT main_table.id,name FROM main_table, stats_table, normal_upgrades, upper WHERE main_table.id=stats_table.id AND main_table.id=normal_upgrades.id AND main_table.id=upper.id AND class =1 AND type=0 AND t2>10 AND r2>10 AND tire1>10 AND tire2>10 AND tire3>10 AND tire4>10 AND tire5>10 AND p_t>1 ORDER BY rank";
            String query2 = "SELECT main_table.id,name FROM main_table, stats_table, normal_upgrades, upper WHERE main_table.id=stats_table.id AND main_table.id=normal_upgrades.id AND main_table.id=upper.id AND class =2 AND type=0 AND t2>10 AND r2>10 AND tire1>10 AND tire2>10 AND tire3>10 AND tire4>10 AND tire5>10 AND p_t>1 ORDER BY rank";
            String query3 = "SELECT main_table.id,name FROM main_table, stats_table, normal_upgrades, upper WHERE main_table.id=stats_table.id AND main_table.id=normal_upgrades.id AND main_table.id=upper.id AND class =3 AND type=0 AND t2>10 AND r2>10 AND tire1>10 AND tire2>10 AND tire3>10 AND tire4>10 AND tire5>10 AND p_t>1 ORDER BY rank";
            String query4 = "SELECT main_table.id,name FROM main_table, stats_table, normal_upgrades, upper WHERE main_table.id=stats_table.id AND main_table.id=normal_upgrades.id AND main_table.id=upper.id AND class =4 AND type=0 AND t2>10 AND r2>10 AND tire1>10 AND tire2>10 AND tire3>10 AND tire4>10 AND tire5>10 AND p_t>1 ORDER BY rank";
            String query5 = "SELECT main_table.id,name FROM main_table, stats_table, normal_upgrades, upper WHERE main_table.id=stats_table.id AND main_table.id=normal_upgrades.id AND main_table.id=upper.id AND class =5 AND type=0 AND t2>10 AND r2>10 AND tire1>10 AND tire2>10 AND tire3>10 AND tire4>10 AND tire5>10 AND p_t>1 ORDER BY rank";

            List<GridItem> class_d = new ArrayList<GridItem>();
            List<GridItem> class_c = new ArrayList<GridItem>();
            List<GridItem> class_b = new ArrayList<GridItem>();
            List<GridItem> class_a = new ArrayList<GridItem>();
            List<GridItem> class_s = new ArrayList<GridItem>();

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor1 = db.rawQuery(query1, null);
            Cursor cursor2 = db.rawQuery(query2, null);
            Cursor cursor3 = db.rawQuery(query3, null);
            Cursor cursor4 = db.rawQuery(query4, null);
            Cursor cursor5 = db.rawQuery(query5, null);

            if (cursor1 != null && cursor1.moveToFirst()) {
                do {
                    class_d.add(new GridItem(cursor1.getInt(0), cursor1.getString(1)));
                } while (cursor1.moveToNext());
            }

            card.add(class_d);

            if (cursor2 != null && cursor2.moveToFirst()) {
                do {
                    class_c.add(new GridItem(cursor2.getInt(0), cursor2.getString(1)));
                } while (cursor2.moveToNext());
            }

            card.add(class_c);

            if (cursor3 != null && cursor3.moveToFirst()) {
                do {
                    class_b.add(new GridItem(cursor3.getInt(0), cursor3.getString(1)));
                } while (cursor3.moveToNext());
            }

            card.add(class_b);

            if (cursor4 != null && cursor4.moveToFirst()) {
                do {
                    class_a.add(new GridItem(cursor4.getInt(0), cursor4.getString(1)));
                } while (cursor4.moveToNext());
            }

            card.add(class_a);

            if (cursor5 != null && cursor5.moveToFirst()) {
                do {
                    class_s.add(new GridItem(cursor5.getInt(0), cursor5.getString(1)));
                } while (cursor5.moveToNext());
            }

            card.add(class_s);
            try {
                cursor1.close();
                cursor2.close();
                cursor3.close();
                cursor4.close();
                cursor5.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            db.close();
        }catch(SQLException e){e.printStackTrace();}

        return card;
    }

    public String getName(int ID) {
        String query = "SELECT name FROM main_table WHERE main_table.id=" + ID;
        String name = "N/A";
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                cursor.moveToFirst();

                name = cursor.getString(0);
            }
            try {
                cursor.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            db.close();
        }catch(SQLException e){e.printStackTrace();}
        return name;

    }

    public UpsPack getData(int ID) {
        UpsPack card=new UpsPack();
        try {
            String query = "SELECT " +
                    "name, class, modi, " +
                    "p_a,p_t,p_h,p_n, " +
                    "a1,a2,a3,a4," +
                    "t1,t2,t3,t4," +
                    "h1,h2,h3,h4," +
                    "n1,n2,n3,n4," +
                    "r1,r2,r3,r4" +
                    " FROM main_table, stats_table, upper WHERE main_table.id=" + ID + " AND stats_table.id=" + ID + " AND upper.id=" + ID;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                cursor.moveToFirst();

                card = new UpsPack(
                        cursor.getInt(1),
                        cursor.getString(0),
                        cursor.getDouble(2),

                        cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6),

                        cursor.getDouble(7), cursor.getDouble(8), cursor.getDouble(9), cursor.getDouble(10),
                        cursor.getDouble(11), cursor.getDouble(12), cursor.getDouble(13), cursor.getDouble(14),
                        cursor.getDouble(15), cursor.getDouble(16), cursor.getDouble(17), cursor.getDouble(18),
                        cursor.getDouble(19), cursor.getDouble(20), cursor.getDouble(21), cursor.getDouble(22),
                        cursor.getDouble(23), cursor.getDouble(24), cursor.getDouble(25), cursor.getDouble(26)
                );
            } else
                card = new UpsPack();
            try {
                cursor.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            db.close();
        }catch(SQLiteException e){e.printStackTrace();}

        return card;
    }
}
