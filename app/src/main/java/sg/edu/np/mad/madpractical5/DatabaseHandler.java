package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDB.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String USER_NAME = "name";
    public static final String USER_DESCRIPTION = "description";
    public static final String USER_FOLLOWED = "followed";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_USERS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " TEXT," + USER_DESCRIPTION + " TEXT," + USER_FOLLOWED + " BOOLEAN)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void initWithUsers() {
        // Initialise Database with 20 Random User Data
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            int randNum = rand.nextInt(999999);
            String name = "Name" + randNum;
            String description = "Description" + randNum;
            Boolean followed =  randNum % 2 == 0;

            this.addUser(new User(name, description, i, followed));
        }
    }

    public void updateUser(String name, Boolean followed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_FOLLOWED, followed);

        db.update(TABLE_USERS, contentValues, USER_NAME + " = ?", new String[]{name});
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, user.getName());
        contentValues.put(USER_DESCRIPTION, user.getDescription());
        contentValues.put(USER_FOLLOWED, user.getFollowed());

        db.insert(TABLE_USERS, null, contentValues);
        db.close();
    }

    public ArrayList<User> getAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();

        String getQuery = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(getQuery, null);
        ArrayList<User> users = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int followed = cursor.getInt(3);

                users.add(new User(name, description, id, followed > 0));
                cursor.moveToNext();
            }
        }
        return users;
    }
}
