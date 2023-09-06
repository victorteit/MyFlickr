package ca.teitandroid.myflickr.db;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ca.teitandroid.myflickr.model.PhotoFavorite;

public class DBManager extends Application {
    public DBOpenHelper dbOpenHelper;
    public SQLiteDatabase sqLiteDatabase;

    public DBManager() {
    }
    private static DBManager instance;

    public static DBManager getInstance() {
        return instance;
    }

    public DBManager(Context context) {
        super.onCreate();
        dbOpenHelper = new DBOpenHelper(context);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dbOpenHelper = new DBOpenHelper(this);
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
       // sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public void insertInTable(String tableName,
                              ArrayList<ContentValues> values) {
        for (ContentValues value : values) {
            insertInTable(tableName, value);
        }
    }

    public long insertInTable(String tableName, ContentValues values) {
        return sqLiteDatabase.insert(tableName, null, values);
    }

    public Cursor queryInTable(String tableName,
                               String[] columns,
                               String selection,
                               String[] selectionArgs) {

        return sqLiteDatabase.query(tableName,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

    public int deleteRowFromTable(String tableName,
                                  String selection,
                                  String[] selectionArgs) {

        return sqLiteDatabase.delete(tableName,
                selection,
                selectionArgs);
    }

    public int updateTable(String tableName,
                           ContentValues values,
                           String whereClause,
                           String[] whereArgs) {

        return sqLiteDatabase.update(tableName,
                values,
                whereClause,
                whereArgs);
    }

    public ContentValues javaObjectToOneContentValue(PhotoFavorite photoFavorite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLCommands.COLUMN_ID, photoFavorite.getPhotoID());
        contentValues.put(SQLCommands.COLUMN_TITLE, photoFavorite.getTitlePhoto());
        contentValues.put(SQLCommands.COLUMN_OWNER, photoFavorite.getAuthorPhoto());
        contentValues.put(SQLCommands.COLUMN_DESCRIPTION, photoFavorite.getDescriptionPhoto());
        contentValues.put(SQLCommands.COLUMN_VIEWS, photoFavorite.getViewsPhoto());
        contentValues.put(SQLCommands.COLUMN_DATE_TAKEN, photoFavorite.getDateTakenPhoto());
        contentValues.put(SQLCommands.COLUMN_URL_PHOTO, photoFavorite.getUrlPhoto());

        return contentValues;
    }

    public ArrayList<ContentValues> javaObjectToContentValue(ArrayList<PhotoFavorite> favoriteArrayList) {
        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();

        for (PhotoFavorite photoFavorite : favoriteArrayList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQLCommands.COLUMN_ID, photoFavorite.getPhotoID());
            contentValues.put(SQLCommands.COLUMN_TITLE, photoFavorite.getTitlePhoto());
            contentValues.put(SQLCommands.COLUMN_OWNER, photoFavorite.getAuthorPhoto());
            contentValues.put(SQLCommands.COLUMN_DESCRIPTION, photoFavorite.getDescriptionPhoto());
            contentValues.put(SQLCommands.COLUMN_VIEWS, photoFavorite.getViewsPhoto());
            contentValues.put(SQLCommands.COLUMN_DATE_TAKEN, photoFavorite.getDateTakenPhoto());
            contentValues.put(SQLCommands.COLUMN_URL_PHOTO, photoFavorite.getUrlPhoto());
            contentValuesArrayList.add(contentValues);
        }
        return contentValuesArrayList;
    }

    public ArrayList<PhotoFavorite> cursorToArrayList(Cursor cursor) {
        ArrayList<PhotoFavorite> photoFavorites = new ArrayList<>();
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            do {
                PhotoFavorite photoFavorite = new PhotoFavorite();
                photoFavorite.setPhotoID(cursor.getInt(0));
                photoFavorite.setAuthorPhoto(cursor.getString(1));
                photoFavorite.setTitlePhoto(cursor.getString(2));
                photoFavorite.setDescriptionPhoto(cursor.getString(3));
                photoFavorite.setViewsPhoto(cursor.getString(4));
                photoFavorite.setDateTakenPhoto(cursor.getString(5));
                photoFavorite.setUrlPhoto(cursor.getString(6));

                photoFavorites.add(photoFavorite);
            } while (cursor.moveToNext());
        }
        return photoFavorites;
    }
}
