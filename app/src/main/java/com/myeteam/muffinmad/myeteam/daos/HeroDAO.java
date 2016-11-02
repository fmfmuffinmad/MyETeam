package com.myeteam.muffinmad.myeteam.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.myeteam.muffinmad.myeteam.classes.Hero;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muffinmad on 02/11/2016.
 */

public class HeroDAO extends DBControl{

    private final FirebaseAuth mAuth;
    private final FirebaseUser user;

    public HeroDAO(Context context) {
        super(context);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void insert(Hero hero) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValues(hero);
        db.insert("HEROES", null, dados);
    }

    @NonNull
    private ContentValues getContentValues(Hero hero) {
        ContentValues dados = new ContentValues();

        dados.put("USERID", user.getUid());
        dados.put("NAME", hero.getName());
        dados.put("SKILLLEVEL", hero.getSkillLevel());
        dados.put("CARRY", hero.isCarry());
        dados.put("MID", hero.isMid());
        dados.put("OFFLANE", hero.isOfflane());
        dados.put("SUPPORT", hero.isSupport());
        dados.put("JUNGLEROAM", hero.isJungleroam());

        return dados;
    }

    public void deletar(Hero hero) {

        SQLiteDatabase db = getWritableDatabase();
        String[] params = {user.getUid(), hero.getName()};
        db.delete("HEROES", "USERID = ? AND NAME = ?", params);
    }


    public void altera(Hero hero) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValues(hero);
        String[] params = {user.getUid(), hero.getName()};
        db.update("HEROES", dados, "USERID = ? AND NAME = ?", params);
    }

    public List<Hero> buscaPinuts() {
        String sql = "SELECT * FROM HEROES";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Hero> heroes = new ArrayList<Hero>();
        Hero hero = null;
        while (c.moveToNext()) {
            hero = new Hero();
            hero.setName(c.getString(c.getColumnIndex("NAME")));
            hero.setSkillLevel(c.getString(c.getColumnIndex("SKILLLEVEL")));
            hero.setCarry((c.getInt(c.getColumnIndex("CARRY"))) == 1);
            hero.setMid((c.getInt(c.getColumnIndex("MID"))) == 1);
            hero.setOfflane((c.getInt(c.getColumnIndex("OFFLANE"))) == 1);
            hero.setSupport((c.getInt(c.getColumnIndex("SUPPORT"))) == 1);
            hero.setJungleroam((c.getInt(c.getColumnIndex("JUNGLEROAM"))) == 1);

            heroes.add(hero);
        }

        c.close();
        return heroes;
    }


//
//    public boolean isAluno(String telefone){
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT TELEFONE FROM ALUNOS WHERE TELEFONE = ?", new String[]{telefone});
//        int res = cursor.getCount();
//        cursor.close();
//        if (res > 0)
//        {
//            return true;
//        }
//        return false;
//    }
//


}
