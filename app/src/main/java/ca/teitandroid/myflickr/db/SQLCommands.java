package ca.teitandroid.myflickr.db;

public class SQLCommands {
    final public static String DATABASE_NAME = "photodb";
    final public static String SCHEMA_VERSION      = "1";
    final public static String TABLE_NAME          = "phototable";
    final public static String COLUMN_ID           = "id";
    final public static String COLUMN_TITLE     = "titlePhoto";
    final public static String COLUMN_OWNER = "authorPhoto";
    final public static String COLUMN_DESCRIPTION = "descriptionPhoto";
    final public static String COLUMN_VIEWS = "viewsPhoto";
    final public static String COLUMN_DATE_TAKEN = "dateTakenPhoto";
    final public static String COLUMN_URL_PHOTO = "urlPhoto";
    final public static String[] TABLE_COLUMNS = {"id","titlePhoto", "authorPhoto", "descriptionPhoto", "viewsPhoto", "dateTakenPhoto", "urlPhoto"};
    final public static String CREATE_TABLE =
            "CREATE TABLE phototable (id INTEGER, titlePhoto TEXT, authorPhoto TEXT, descriptionPhoto TEXT, viewsPhoto TEXT, dateTakenPhoto TEXT, urlPhoto TEXT);";
}

