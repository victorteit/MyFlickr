package ca.teitandroid.myflickr.db;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import ca.teitandroid.myflickr.model.PhotoFavorite;

public class DatabaseDemo {
    ArrayList<PhotoFavorite> favoriteArrayList;
    DBManager dbManager;
    Cursor cursor;

    public DatabaseDemo(DBManager dbManager) {
        this.dbManager = dbManager;
        favoriteArrayList = new ArrayList<>();
    }
    public void insertDummyDataToDB(ArrayList<PhotoFavorite> favoriteArrayList) {
        ArrayList<ContentValues> contentValuesArrayList = dbManager.javaObjectToContentValue(favoriteArrayList);
        try {
            dbManager.insertInTable(SQLCommands.TABLE_NAME, contentValuesArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertDataToDB(PhotoFavorite photoFavorite) {
        ArrayList<PhotoFavorite> favoriteArrayList = readFromDB();
        favoriteArrayList.add(photoFavorite);
        ArrayList<ContentValues> contentValuesArrayList = dbManager.javaObjectToContentValue(favoriteArrayList);
        try {
            dbManager.insertInTable(SQLCommands.TABLE_NAME, contentValuesArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFromDB(int id) {
        dbManager.deleteRowFromTable(SQLCommands.TABLE_NAME,
                SQLCommands.COLUMN_ID + "=?"
                , new String[]{String.valueOf(id)});

      //  readFromDB();
    }
    public ArrayList<PhotoFavorite> readFromDB() {
        Cursor cursor = dbManager.queryInTable(
                SQLCommands.TABLE_NAME,
                SQLCommands.TABLE_COLUMNS,
                null,
                null);

        ArrayList<PhotoFavorite> favoriteArrayListFromDB = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int photoId = cursor.getInt(cursor.getColumnIndex(SQLCommands.COLUMN_ID));
                String author = cursor.getString(cursor.getColumnIndex(SQLCommands.COLUMN_OWNER));
                String title = cursor.getString(cursor.getColumnIndex(SQLCommands.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(SQLCommands.COLUMN_DESCRIPTION));
                String views = String.valueOf(cursor.getInt(cursor.getColumnIndex(SQLCommands.COLUMN_VIEWS)));
                String dateTaken = cursor.getString(cursor.getColumnIndex(SQLCommands.COLUMN_DATE_TAKEN));
                String url = cursor.getString(cursor.getColumnIndex(SQLCommands.COLUMN_URL_PHOTO));

                PhotoFavorite photoFavorite = new PhotoFavorite(photoId, author, title, description,  views, dateTaken, url);
                favoriteArrayListFromDB.add(photoFavorite);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return favoriteArrayListFromDB;
    }

    public void closeCursor() {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}
