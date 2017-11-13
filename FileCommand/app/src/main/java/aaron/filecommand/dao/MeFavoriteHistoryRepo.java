package aaron.filecommand.dao;

/**
 * Created by Yunwen on 2/15/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MeFavoriteHistoryRepo {
    private MeFavoriteHistoryHelper meFavoriteHistoryHelper;

    public MeFavoriteHistoryRepo(Context context){
        meFavoriteHistoryHelper =new MeFavoriteHistoryHelper(context);
    }

    public int insert(MeFavoriteHistory meFavoriteHistory){
        //Open Db，write data
        SQLiteDatabase db= meFavoriteHistoryHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MeFavoriteHistory.KEY_time, meFavoriteHistory.time);
        values.put(MeFavoriteHistory.KEY_content, meFavoriteHistory.content);
        values.put(MeFavoriteHistory.KEY_topic, meFavoriteHistory.topic);
        values.put(MeFavoriteHistory.KEY_lectureId, meFavoriteHistory.lectureId);
        values.put(MeFavoriteHistory.KEY_title, meFavoriteHistory.title);
        values.put(MeFavoriteHistory.KEY_coverImageUri, meFavoriteHistory.coverImageUri);
        values.put(MeFavoriteHistory.KEY_teacher, meFavoriteHistory.teacher);
        //
        long algorithm_Id=db.insert(MeFavoriteHistory.TABLE,null,values);
        db.close();
        return (int)algorithm_Id;
    }

    public void delete(int algorithm_Id){
        SQLiteDatabase db= meFavoriteHistoryHelper.getWritableDatabase();
        db.delete(MeFavoriteHistory.TABLE, MeFavoriteHistory.KEY_ID+"=?", new String[]{String.valueOf(algorithm_Id)});
        db.close();
    }
    public void update(MeFavoriteHistory meFavoriteHistory){
        SQLiteDatabase db= meFavoriteHistoryHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MeFavoriteHistory.KEY_time, meFavoriteHistory.time);
        values.put(MeFavoriteHistory.KEY_content, meFavoriteHistory.content);
        values.put(MeFavoriteHistory.KEY_topic, meFavoriteHistory.topic);
        values.put(MeFavoriteHistory.KEY_lectureId, meFavoriteHistory.lectureId);
        values.put(MeFavoriteHistory.KEY_title, meFavoriteHistory.title);
        values.put(MeFavoriteHistory.KEY_coverImageUri, meFavoriteHistory.coverImageUri);
        values.put(MeFavoriteHistory.KEY_teacher, meFavoriteHistory.teacher);
        db.update(MeFavoriteHistory.TABLE, values, MeFavoriteHistory.KEY_ID + "=?", new String[]{String.valueOf(meFavoriteHistory.dbId)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getAlgorithmList(){
        SQLiteDatabase db= meFavoriteHistoryHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MeFavoriteHistory.KEY_ID+","+
                MeFavoriteHistory.KEY_content+","+
                MeFavoriteHistory.KEY_topic+","+
                MeFavoriteHistory.KEY_lectureId+","+
                MeFavoriteHistory.KEY_title+","+
                MeFavoriteHistory.KEY_coverImageUri+","+
                MeFavoriteHistory.KEY_teacher+","+
                MeFavoriteHistory.KEY_time +" FROM "+ MeFavoriteHistory.TABLE;
        ArrayList<HashMap<String,String>> algorithmList=new ArrayList<>();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> algorithm=new HashMap<>();
                algorithm.put("id",cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_ID)));
                algorithm.put("topic",cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_topic)));
                algorithm.put("lectureId",cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_lectureId)));
                algorithm.put("title",cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_title)));
                algorithm.put("coverImageUri",cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_coverImageUri)));
                algorithm.put("teacher",cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_teacher)));
                algorithm.put("content",cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_content)));
                algorithmList.add(algorithm);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return algorithmList;
    }

    public MeFavoriteHistory getColumnById(int Id){
        SQLiteDatabase db= meFavoriteHistoryHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MeFavoriteHistory.KEY_ID + "," +
                MeFavoriteHistory.KEY_content + "," +
                MeFavoriteHistory.KEY_topic + "," +
                MeFavoriteHistory.KEY_lectureId + "," +
                MeFavoriteHistory.KEY_title + "," +
                MeFavoriteHistory.KEY_coverImageUri + "," +
                MeFavoriteHistory.KEY_teacher + "," +
                MeFavoriteHistory.KEY_time +
                " FROM " + MeFavoriteHistory.TABLE
                + " WHERE " +
                MeFavoriteHistory.KEY_ID + "=?";
        int iCount=0;
        MeFavoriteHistory mefavoriteHistory =new MeFavoriteHistory();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                mefavoriteHistory.dbId =cursor.getInt(cursor.getColumnIndex(MeFavoriteHistory.KEY_ID));
                mefavoriteHistory.content =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_content));
                mefavoriteHistory.topic  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_topic));
                mefavoriteHistory.lectureId  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_lectureId));
                mefavoriteHistory.title  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_title));
                mefavoriteHistory.coverImageUri  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_coverImageUri));
                mefavoriteHistory.teacher  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_teacher));
                mefavoriteHistory.time =cursor.getInt(cursor.getColumnIndex(MeFavoriteHistory.KEY_time));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mefavoriteHistory;
    }

    public MeFavoriteHistory getValueByKey(int Id){
        SQLiteDatabase db= meFavoriteHistoryHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MeFavoriteHistory.KEY_ID + "," +
                MeFavoriteHistory.KEY_content + "," +
                MeFavoriteHistory.KEY_topic + "," +
                MeFavoriteHistory.KEY_lectureId + "," +
                MeFavoriteHistory.KEY_title + "," +
                MeFavoriteHistory.KEY_coverImageUri + "," +
                MeFavoriteHistory.KEY_teacher + "," +
                MeFavoriteHistory.KEY_time +
                " FROM " + MeFavoriteHistory.TABLE
                + " WHERE " +
                MeFavoriteHistory.KEY_ID + "=?";

        int iCount=0;

        MeFavoriteHistory meFavoriteHistory =new MeFavoriteHistory();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                meFavoriteHistory.dbId =cursor.getInt(cursor.getColumnIndex(MeFavoriteHistory.KEY_ID));
                meFavoriteHistory.content =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_content));
                meFavoriteHistory.topic  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_topic));
                meFavoriteHistory.lectureId  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_lectureId));
                meFavoriteHistory.title  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_title));
                meFavoriteHistory.coverImageUri  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_coverImageUri));
                meFavoriteHistory.teacher  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_teacher));
                meFavoriteHistory.time =cursor.getInt(cursor.getColumnIndex(MeFavoriteHistory.KEY_time));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return meFavoriteHistory;
    }

    public MeFavoriteHistory getColumnByTopic(String topic){
        SQLiteDatabase db= meFavoriteHistoryHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                MeFavoriteHistory.KEY_ID + "," +
                MeFavoriteHistory.KEY_content + "," +
                MeFavoriteHistory.KEY_topic + "," +
                MeFavoriteHistory.KEY_lectureId + "," +
                MeFavoriteHistory.KEY_title + "," +
                MeFavoriteHistory.KEY_coverImageUri + "," +
                MeFavoriteHistory.KEY_teacher + "," +
                MeFavoriteHistory.KEY_time +
                " FROM " + MeFavoriteHistory.TABLE
                + " WHERE " +
                MeFavoriteHistory.KEY_topic + "=?";
        int iCount=0;
        MeFavoriteHistory searchHistory =new MeFavoriteHistory();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(topic)});
        if(cursor.moveToFirst()){
            do{
                searchHistory.dbId =cursor.getInt(cursor.getColumnIndex(MeFavoriteHistory.KEY_ID));
                searchHistory.content =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_content));
                searchHistory.topic  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_topic));
                searchHistory.lectureId  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_lectureId));
                searchHistory.title  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_title));
                searchHistory.coverImageUri  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_coverImageUri));
                searchHistory.teacher  =cursor.getString(cursor.getColumnIndex(MeFavoriteHistory.KEY_teacher));
                searchHistory.time =cursor.getInt(cursor.getColumnIndex(MeFavoriteHistory.KEY_time));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return searchHistory;
    }
}
