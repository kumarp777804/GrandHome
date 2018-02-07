package com.buvanesh.grandhome.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.buvanesh.grandhome.model.TaskModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by buvaneshkumar_p on 10/26/2017.
 */

public class GrandHomeData extends SQLiteOpenHelper {
    public static final String DB_NAME = "GRAND HOME";
    public static final int DB_VERSION = 1;

    public static final String TABLE_TASK = "tabletask";
    public static final String TASK_ID = "taskid";
    public static final String TASK_NAME = "taskname";
    public static final String TASK_TRAIN_NO = "tasktrainno";
    public static final String TASK_AMOUNT = "taskamount";
    public static final String TASK_DATE = "taskdate";
    public static final String TASK_MESSAGE = "taskmessage";
    public static final String TASK_ISBANKING = "taskisbanking";
    public static final String TASK_ISCREDIT = "taskiscredit";
    public static final String TASK_ISTRAIN = "taskistrain";
    public static final String TASK_BOARDING_DATE = "taskboardingdate";
    public static final String TASK_BOARDING_TIME = "taskboardingtime";
    public static final String TASK_SEATNO = "taskseatno";
    public static final String TASK_COACHNO = "taskcoachno";
    public static final String TASK_BOARDING = "taskboarding";
    public static final String TASK_PNR = "taskpnr";
    public static final String TASK_ATTACHMENT = "taskattachment";
    public static final String TASK_STATUS = "taskstatus";
    public SQLiteDatabase database;

    public GrandHomeData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+TABLE_TASK+"("+TASK_ID+" INTEGER PRIMARY KEY, "+
                TASK_NAME+" TEXT, "+TASK_TRAIN_NO+" TEXT, "+TASK_AMOUNT+" TEXT, "+TASK_DATE+" TEXT, "+TASK_MESSAGE+" TEXT, "+
                TASK_ISBANKING+" BIT, "+TASK_ISCREDIT+" BIT, "+TASK_ISTRAIN+" BIT, "+
                TASK_BOARDING_DATE+" TEXT, "+TASK_BOARDING_TIME+" TEXT, "+TASK_SEATNO+" TEXT, "+TASK_PNR+" TEXT, "+
                TASK_COACHNO+" TEXT, "+TASK_BOARDING+" TEXT, "+TASK_ATTACHMENT +" BLOB, "+TASK_STATUS+" BIT )";
        sqLiteDatabase.execSQL(query);
        Log.e("Database: ","created Successfully");
    }

    public void insertNewTask(TaskModel taskModel){
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_NAME,taskModel.getName());
        values.put(TASK_TRAIN_NO,taskModel.getTrainNo());
        values.put(TASK_AMOUNT,taskModel.getAmount());
        values.put(TASK_MESSAGE,taskModel.getMessage());
        values.put(TASK_STATUS,taskModel.isStatus());
        values.put(TASK_ISBANKING,taskModel.isBanking());
        values.put(TASK_ISCREDIT,taskModel.isCredit());
        values.put(TASK_ISTRAIN,taskModel.isTrain());
        values.put(TASK_BOARDING_DATE,taskModel.getBoardingDate());
        values.put(TASK_BOARDING_TIME,taskModel.getBoardingTime());
        values.put(TASK_SEATNO,taskModel.getSeatNo());
        values.put(TASK_COACHNO,taskModel.getCoachNo());
        values.put(TASK_BOARDING,taskModel.getBoarding());
        values.put(TASK_PNR,taskModel.getPnrNo());
        values.put(TASK_DATE,String.valueOf(android.text.format.DateFormat.format("dd-MMM-YYYY",new Date())));
        database.insert(TABLE_TASK,null,values);
        database.close();
        Log.e("Database: ","inserted Successfully: "+values);
    }

    public List<TaskModel> getTaskDetails(){
        database = this.getReadableDatabase();
        List<TaskModel> taskModelList = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_TASK;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    TaskModel taskModel = new TaskModel();
                    taskModel.setId(cursor.getInt(cursor.getColumnIndex(TASK_ID)));
                    taskModel.setName(cursor.getString(cursor.getColumnIndex(TASK_NAME)));
                    taskModel.setTrainNo(cursor.getString(cursor.getColumnIndex(TASK_TRAIN_NO)));
                    taskModel.setMessage(cursor.getString(cursor.getColumnIndex(TASK_MESSAGE)));
                    taskModel.setAmount(cursor.getString(cursor.getColumnIndex(TASK_AMOUNT)));
                    taskModel.setBoardingDate(cursor.getString(cursor.getColumnIndex(TASK_BOARDING_DATE)));
                    taskModel.setBoardingTime(cursor.getString(cursor.getColumnIndex(TASK_BOARDING_TIME)));
                    taskModel.setSeatNo(cursor.getString(cursor.getColumnIndex(TASK_SEATNO)));
                    taskModel.setCoachNo(cursor.getString(cursor.getColumnIndex(TASK_COACHNO)));
                    taskModel.setBoarding(cursor.getString(cursor.getColumnIndex(TASK_BOARDING)));
                    taskModel.setPnrNo(cursor.getString(cursor.getColumnIndex(TASK_PNR)));
                    taskModel.setDate(cursor.getString(cursor.getColumnIndex(TASK_DATE)));
                    boolean status = cursor.getInt(cursor.getColumnIndex(TASK_STATUS)) == 1;
                    boolean isBanking = cursor.getInt(cursor.getColumnIndex(TASK_ISBANKING)) == 1;
                    boolean isCredit = cursor.getInt(cursor.getColumnIndex(TASK_ISCREDIT)) == 1;
                    boolean isTrain = cursor.getInt(cursor.getColumnIndex(TASK_ISTRAIN)) == 1;
                    taskModel.setBanking(isBanking);
                    taskModel.setCredit(isCredit);
                    taskModel.setTrain(isTrain);
                    taskModel.setStatus(status);
                    taskModelList.add(taskModel);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        database.close();
        return taskModelList;
    }

    public void deleteTask(int id){
        database = this.getReadableDatabase();
        database.delete(TABLE_TASK,TASK_ID+" = "+id,null);
        database.close();
    }

    public void updateStatus(int id,boolean value){
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_STATUS,value);
        database.update(TABLE_TASK,contentValues,TASK_ID+" = "+id,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
