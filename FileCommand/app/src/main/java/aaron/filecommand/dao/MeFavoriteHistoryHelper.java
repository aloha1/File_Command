package aaron.filecommand.dao;

/**
 * Created by Yunwen on 2/15/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ifanr on 2015/3/29.
 */
public class MeFavoriteHistoryHelper extends SQLiteOpenHelper {
    //Db version
    private static final int DATABASE_VERSION = 1;
    //name of the db
    private static final String DATABASE_NAME = "me.favorite.db";

    public MeFavoriteHistoryHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table
        String CREATE_TABLE_STUDENT="CREATE TABLE "+ MeFavoriteHistory.TABLE+"("
                + MeFavoriteHistory.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MeFavoriteHistory.KEY_topic+" TEXT, "
                + MeFavoriteHistory.KEY_time +" INTEGER, "
                + MeFavoriteHistory.KEY_lectureId+" TEXT, "
                + MeFavoriteHistory.KEY_title+" TEXT, "
                + MeFavoriteHistory.KEY_coverImageUri+" TEXT, "
                + MeFavoriteHistory.KEY_teacher+" TEXT, "
                + MeFavoriteHistory.KEY_content+" TEXT)";
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if the table exist, delete it
        db.execSQL("DROP TABLE IF EXISTS "+ MeFavoriteHistory.TABLE);
        //recreate the table
        onCreate(db);
    }
}
