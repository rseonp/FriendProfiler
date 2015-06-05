package rseonp.friendprofiler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 6/4/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "profileManager",
    TABLE_PROFILES = "profiles",
    KEY_ID = "id",
    KEY_NAME = "name",
    KEY_FOCUS = "focus",
    KEY_REMEMBER = "remember",
    KEY_IMAGEURI = "imageUri";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PROFILES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_FOCUS + " TEXT," + KEY_REMEMBER + " TEXT," + KEY_IMAGEURI + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);

        onCreate(db);
    }

    public void createProfile(Profile profile) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profile.getName());
        values.put(KEY_FOCUS, profile.getFocus());
        values.put(KEY_REMEMBER, profile.getRemember());
        values.put(KEY_IMAGEURI, profile.getImageUri().toString());

        db.insert(TABLE_PROFILES, null, values);
        db.close();
    }

    public Profile getProfile(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILES, new String[]{ KEY_ID, KEY_NAME, KEY_FOCUS, KEY_REMEMBER, KEY_IMAGEURI }, KEY_ID + "=?", new String[]{ String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Profile profile = new Profile(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4)));
        db.close();
        cursor.close();
        return profile;
    }

    public void deleteProfile(Profile profile) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PROFILES, KEY_ID + "=?", new String[] { String.valueOf(profile.getId()) });
        db.close();
    }

    public int getProfileCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *  FROM " + TABLE_PROFILES, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateProfile(Profile profile) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profile.getName());
        values.put(KEY_FOCUS, profile.getFocus());
        values.put(KEY_REMEMBER, profile.getRemember());
        values.put(KEY_IMAGEURI, profile.getImageUri().toString());
        db.close();

        int rowsAffected = db.update(TABLE_PROFILES, values, KEY_ID + "=?", new String[]{String.valueOf(profile.getId())});
        db.close();

        return rowsAffected;
    }

    public List<Profile> getAllProfiles() {
        List<Profile> Profiles = new ArrayList<Profile>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROFILES, null);

        if (cursor.moveToFirst()) {
            do {
                Profiles.add(new Profile(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4))));
            }
            while (cursor.moveToNext());
            }
        cursor.close();
        db.close();
        return Profiles;
        }

}
