package com.myeteam.muffinmad.myeteam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.myeteam.muffinmad.myeteam.Adapters.ListModelSimpleAdapter;
import com.myeteam.muffinmad.myeteam.classes.Hero;
import com.myeteam.muffinmad.myeteam.classes.ListModelSimple;

import java.util.ArrayList;

public class MyHeroActivity extends AppCompatActivity{

    private Hero hero;
    private ListView optionList;
    private ImageView heroPortrait;
    private AlertDialog heroDialog;
    private AlertDialog roleDialog;
    private AlertDialog skillDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hero);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        buildDialogs();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Intent intent = getIntent();
        hero = (Hero) intent.getSerializableExtra("hero");
        if(hero == null){
            Toast.makeText(MyHeroActivity.this, "New Hero", Toast.LENGTH_SHORT).show();
        }

        heroPortrait = (ImageView) findViewById(R.id.myhero_heroportrait);

        heroPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heroDialog.show();
            }
        });

        optionList = (ListView) findViewById(R.id.myhero_optionslist);

        optionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //// TODO: 02/11/2016 AlertDialog para cada opção
            }
        });
        
        loadOptionList();
    }

    private void buildDialogs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Choose a hero")
                .setSingleChoiceItems(R.array.hero_list, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //// TODO: 02/11/2016 Ao selecionar o heroi
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // // TODO: 02/11/2016 Ao dar OK no dialogo
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //// TODO: 02/11/2016 Ao dar cancelar no dialogo
                    }
                });

        heroDialog = builder.create();

    }

    private void loadOptionList() {
        ArrayList<ListModelSimple> itemList = new ArrayList<ListModelSimple>();
        ListModelSimple item1 = new ListModelSimple("Roles", "Top, Mid, Support", 1);
        itemList.add(item1);
        ListModelSimple item2 = new ListModelSimple("Skill Level with the Hero", "I'm godlike with it", 2);
        itemList.add(item2);

        ListModelSimpleAdapter adapter = new ListModelSimpleAdapter(itemList, this);
        optionList.setAdapter(adapter);
    }


}
