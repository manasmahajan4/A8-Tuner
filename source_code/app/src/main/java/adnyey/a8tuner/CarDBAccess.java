package adnyey.a8tuner;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CarDBAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static CarDBAccess instance;
    private Context mContext;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private CarDBAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
        mContext = context;
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static CarDBAccess getInstance(Context context) {
        if (instance == null) {
            instance = new CarDBAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
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

            Cursor cursor1 = database.rawQuery(query1, null);
            Cursor cursor2 = database.rawQuery(query2, null);
            Cursor cursor3 = database.rawQuery(query3, null);
            Cursor cursor4 = database.rawQuery(query4, null);
            Cursor cursor5 = database.rawQuery(query5, null);

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
        }catch(SQLException e){e.printStackTrace();}

        return card;
    }

    public String getName(int ID) {
        String query = "SELECT name FROM main_table WHERE main_table.id=" + ID;
        String name = "N/A";
        try {
            Cursor cursor = database.rawQuery(query, null);

            if (cursor != null) {
                cursor.moveToFirst();

                name = cursor.getString(0);
            }
            try {
                cursor.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
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
            Cursor cursor = database.rawQuery(query, null);

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
        }catch(SQLiteException e){e.printStackTrace();}

        return card;
    }
}
