package com.myeteam.muffinmad.myeteam.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by muffinmad on 02/11/2016.
 */

public class DBControl extends SQLiteOpenHelper {

    public static final int DBVERSION = 1;
    public static final String DBNAME = "PinutDB";

    public DBControl(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql =
                //tabela PINUTS
                "CREATE TABLE HEROES (" +
                        "USERID TEXT," +
                        "NAME TEXT," +
                        "SKILLLEVEL TEXT," +
                        "CARRY INTEGER," +
                        "MID INTEGER," +
                        "OFFLANE INTEGER," +
                        "SUPPORT INTEGER," +
                        "JUNGLEROAM INTEGER," +
                        "PRIMARY KEY(USERID, NAME)); ";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS HEROES;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
//        switch (i){
//            case 1:
//                sql = "ALTER TABLE ALUNOS ADD COLUMN CAMINHOFOTO TEXT";
//                sqLiteDatabase.execSQL(sql);
//        }
    }
}
