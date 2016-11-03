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
    private ArrayList<ListModelSimple> itemList;
    private ListModelSimple item1;
    private ListModelSimple item2;
    private ListModelSimpleAdapter adapter;
    private ArrayList mSelectedRoles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hero);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buildDialogs();
        mSelectedRoles = new ArrayList();

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
            setTitle("New Hero");
            hero = new Hero();
        } else {
            setTitle(hero.getName());
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
                switch (i){
                    case 0:
                        roleDialog.show();
                        break;
                    case 1:
                        skillDialog.show();
                        break;
                }
            }
        });
        
        loadOptionList();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(MyHeroActivity.this, item.getItemId(), Toast.LENGTH_SHORT).show();
//        if (item.getItemId() == android.R.id.home){
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void buildDialogs() {

        buildHeroDialog();
        buildRoleDialog();
        buildSkillDialog();

    }

    private void buildSkillDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Choose your skill level")
                .setSingleChoiceItems(R.array.skill_level, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //// TODO: 02/11/2016 Ao selecionar o heroi

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // // TODO: 02/11/2016 Ao dar OK no dialogo
                        ListView lw = ((AlertDialog)skillDialog).getListView();
                        if(lw.getCheckedItemPosition()>-1){
                            Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                            hero.setSkillLevel((String) checkedItem);
                            loadOptionList();
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //// TODO: 02/11/2016 Ao dar cancelar no dialogo
                    }
                });

        skillDialog = builder.create();
    }

    private void buildRoleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Check your roles with the hero")
                .setMultiChoiceItems(R.array.roles, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedRoles.add(which);
                        } else if (mSelectedRoles.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedRoles.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // // TODO: 02/11/2016 Ao dar OK no dialogo
                        hero.setCarry(false);
                        hero.setMid(false);
                        hero.setOfflane(false);
                        hero.setSupport(false);
                        hero.setJungleroam(false);
                        for (Object o:mSelectedRoles){
                            switch ((int) o){
                                case 0:
                                    hero.setCarry(true);
                                    break;
                                case 1:
                                    hero.setMid(true);
                                    break;
                                case 2:
                                    hero.setOfflane(true);
                                    break;
                                case 3:
                                    hero.setSupport(true);
                                    break;
                                case 4:
                                    hero.setJungleroam(true);
                                    break;
                            }
                        }
                        loadOptionList();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //// TODO: 02/11/2016 Ao dar cancelar no dialogo
                        mSelectedRoles.clear();
                    }
                });

        roleDialog = builder.create();
    }



    private void buildHeroDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Choose your hero")
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

                        ListView lw = ((AlertDialog)heroDialog).getListView();
                        if(lw.getCheckedItemPosition()>-1){
                            Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                            changeHeroPortait((String) checkedItem);
                        }

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
        itemList = new ArrayList<ListModelSimple>();
        item1 = new ListModelSimple("Roles", setRolesSubtitle(), 1);
        itemList.add(item1);
        item2 = new ListModelSimple("Skill Level with the Hero", hero.getSkillLevel(), 2);
        itemList.add(item2);

        adapter = new ListModelSimpleAdapter(itemList, this);
        optionList.setAdapter(adapter);
    }

    private String setRolesSubtitle() {
        String s = "";
        if(hero.isCarry()){
            s = s + "Carry";
        }
        if(hero.isMid()){
            if(s != ""){
                s = s + ", ";
            }
            s = s + "Mid";
        }
        if(hero.isOfflane()){
            if(s != ""){
                s = s + ", ";
            }
            s = s + "Offlane";
        }
        if(hero.isSupport()){
            if(s != ""){
                s = s + ", ";
            }
            s = s + "Support";
        }
        if(hero.isJungleroam()){
            if(s != ""){
                s = s + ", ";
            }
            s = s + "Roaming/Jungle";
        }

        return s;
    }

    private void changeHeroPortait(String checkedItem) {
        setTitle(checkedItem);
        hero.setName(checkedItem);
        switch(checkedItem){
            case " Abaddon​":
                heroPortrait.setImageResource(R.drawable.abaddon_full);
                break;
            case " Alchemist​":
                heroPortrait.setImageResource(R.drawable.alchemist_full);
                break;
            case " Ancient Apparition​":
                heroPortrait.setImageResource(R.drawable.ancient_apparition_full);
                break;
            case " Anti-Mage​":
                heroPortrait.setImageResource(R.drawable.antimage_full);
                break;
            case " Arc Warden​":
                heroPortrait.setImageResource(R.drawable.arc_warden_full);
                break;
            case " Axe​":
                heroPortrait.setImageResource(R.drawable.axe_full);
                break;
            case " Bane​":
                heroPortrait.setImageResource(R.drawable.bane_full);
                break;
            case " Batrider​":
                heroPortrait.setImageResource(R.drawable.batrider_full);
                break;
            case " Beastmaster​":
                heroPortrait.setImageResource(R.drawable.beastmaster_full);
                break;
            case " Bloodseeker​":
                heroPortrait.setImageResource(R.drawable.bloodseeker_full);
                break;
            case " Bounty Hunter​":
                heroPortrait.setImageResource(R.drawable.bounty_hunter_full);
                break;
            case " Brewmaster​":
                heroPortrait.setImageResource(R.drawable.brewmaster_full);
                break;
            case " Bristleback​":
                heroPortrait.setImageResource(R.drawable.bristleback_full);
                break;
            case " Broodmother​":
                heroPortrait.setImageResource(R.drawable.broodmother_full);
                break;
            case " Centaur Warrunner​":
                heroPortrait.setImageResource(R.drawable.centaur_full);
                break;
            case " Chaos Knight​":
                heroPortrait.setImageResource(R.drawable.chaos_knight_full);
                break;
            case " Chen​":
                heroPortrait.setImageResource(R.drawable.chen_full);
                break;
            case " Clinkz​":
                heroPortrait.setImageResource(R.drawable.clinkz_full);
                break;
            case " Clockwerk​":
                heroPortrait.setImageResource(R.drawable.rattletrap_full);
                break;
            case " Crystal Maiden​":
                heroPortrait.setImageResource(R.drawable.crystal_maiden_full);
                break;
            case " Dark Seer​":
                heroPortrait.setImageResource(R.drawable.dark_seer_full);
                break;
            case " Dazzle​":
                heroPortrait.setImageResource(R.drawable.dazzle_full);
                break;
            case " Death Prophet​":
                heroPortrait.setImageResource(R.drawable.death_prophet_full);
                break;
            case " Disruptor​":
                heroPortrait.setImageResource(R.drawable.disruptor_full);
                break;
            case " Doom​":
                heroPortrait.setImageResource(R.drawable.doom_bringer_full);
                break;
            case " Dragon Knight​":
                heroPortrait.setImageResource(R.drawable.dragon_knight_full);
                break;
            case " Drow Ranger​":
                heroPortrait.setImageResource(R.drawable.drow_ranger_full);
                break;
            case " Earth Spirit​":
                heroPortrait.setImageResource(R.drawable.earth_spirit_full);
                break;
            case " Earthshaker​":
                heroPortrait.setImageResource(R.drawable.earthshaker_full);
                break;
            case " Elder Titan​":
                heroPortrait.setImageResource(R.drawable.elder_titan_full);
                break;
            case " Ember Spirit​":
                heroPortrait.setImageResource(R.drawable.ember_spirit_full);
                break;
            case " Enchantress​":
                heroPortrait.setImageResource(R.drawable.enchantress_full);
                break;
            case " Enigma​":
                heroPortrait.setImageResource(R.drawable.enigma_full);
                break;
            case " Faceless Void​":
                heroPortrait.setImageResource(R.drawable.faceless_void_full);
                break;
            case " Gyrocopter​":
                heroPortrait.setImageResource(R.drawable.gyrocopter_full);
                break;
            case " Huskar​":
                heroPortrait.setImageResource(R.drawable.huskar_full);
                break;
            case " Invoker​":
                heroPortrait.setImageResource(R.drawable.invoker_full);
                break;
            case " Io​":
                heroPortrait.setImageResource(R.drawable.wisp_full);
                break;
            case " Jakiro​":
                heroPortrait.setImageResource(R.drawable.jakiro_full);
                break;
            case " Juggernaut​":
                heroPortrait.setImageResource(R.drawable.juggernaut_full);
                break;
            case " Keeper of the Light​":
                heroPortrait.setImageResource(R.drawable.keeper_of_the_light_full);
                break;
            case " Kunkka​":
                heroPortrait.setImageResource(R.drawable.kunkka_full);
                break;
            case " Legion Commander​":
                heroPortrait.setImageResource(R.drawable.legion_commander_full);
                break;
            case " Leshrac​":
                heroPortrait.setImageResource(R.drawable.leshrac_full);
                break;
            case " Lich​":
                heroPortrait.setImageResource(R.drawable.lich_full);
                break;
            case " Lifestealer​":
                heroPortrait.setImageResource(R.drawable.life_stealer_full);
                break;
            case " Lina​":
                heroPortrait.setImageResource(R.drawable.lina_full);
                break;
            case " Lion​":
                heroPortrait.setImageResource(R.drawable.lion_full);
                break;
            case " Lone Druid​":
                heroPortrait.setImageResource(R.drawable.lone_druid_full);
                break;
            case " Luna​":
                heroPortrait.setImageResource(R.drawable.luna_full);
                break;
            case " Lycan​":
                heroPortrait.setImageResource(R.drawable.lycan_full);
                break;
            case " Magnus​":
                heroPortrait.setImageResource(R.drawable.magnataur_full);
                break;
            case " Medusa​":
                heroPortrait.setImageResource(R.drawable.medusa_full);
                break;
            case " Meepo​":
                heroPortrait.setImageResource(R.drawable.meepo_full);
                break;
            case " Mirana​":
                heroPortrait.setImageResource(R.drawable.mirana_full);
                break;
//            case " Monkey King​":

            case " Morphling​":
                heroPortrait.setImageResource(R.drawable.morphling_full);
                break;
            case " Naga Siren​":
                heroPortrait.setImageResource(R.drawable.naga_siren_full);
                break;
            case " Nature's Prophet​":
                heroPortrait.setImageResource(R.drawable.furion_full);
                break;
            case " Necrophos​":
                heroPortrait.setImageResource(R.drawable.necrolyte_full);
                break;
            case " Night Stalker​":
                heroPortrait.setImageResource(R.drawable.night_stalker_full);
                break;
            case " Nyx Assassin​":
                heroPortrait.setImageResource(R.drawable.nyx_assassin_full);
                break;
            case " Ogre Magi​":
                heroPortrait.setImageResource(R.drawable.ogre_magi_full);
                break;
            case " Omniknight​":
                heroPortrait.setImageResource(R.drawable.omniknight_full);
                break;
            case " Oracle​":
                heroPortrait.setImageResource(R.drawable.oracle_full);
                break;
            case " Outworld Devourer​":
                heroPortrait.setImageResource(R.drawable.obsidian_destroyer_full);
                break;
            case " Phantom Assassin​":
                heroPortrait.setImageResource(R.drawable.phantom_assassin_full);
                break;
            case " Phantom Lancer​":
                heroPortrait.setImageResource(R.drawable.phantom_lancer_full);
                break;
            case " Phoenix​":
                heroPortrait.setImageResource(R.drawable.phoenix_full);
                break;
            case " Puck​":
                heroPortrait.setImageResource(R.drawable.puck_full);
                break;
            case " Pudge​":
                heroPortrait.setImageResource(R.drawable.pudge_full);
                break;
            case " Pugna​":
                heroPortrait.setImageResource(R.drawable.pugna_full);
                break;
            case " Queen of Pain​":
                heroPortrait.setImageResource(R.drawable.queenofpain_full);
                break;
            case " Razor​":
                heroPortrait.setImageResource(R.drawable.razor_full);
                break;
            case " Riki​":
                heroPortrait.setImageResource(R.drawable.riki_full);
                break;
            case " Rubick​":
                heroPortrait.setImageResource(R.drawable.rubick_full);
                break;
            case " Sand King​":
                heroPortrait.setImageResource(R.drawable.sand_king_full);
                break;
            case " Shadow Demon​":
                heroPortrait.setImageResource(R.drawable.shadow_demon_full);
                break;
            case " Shadow Fiend​":
                heroPortrait.setImageResource(R.drawable.nevermore_full);
                break;
            case " Shadow Shaman​":
                heroPortrait.setImageResource(R.drawable.shadow_shaman_full);
                break;
            case " Silencer​":
                heroPortrait.setImageResource(R.drawable.silencer_full);
                break;
            case " Skywrath Mage​":
                heroPortrait.setImageResource(R.drawable.skywrath_mage_full);
                break;
            case " Slardar​":
                heroPortrait.setImageResource(R.drawable.slardar_full);
                break;
            case " Slark​":
                heroPortrait.setImageResource(R.drawable.slark_full);
                break;
            case " Sniper​":
                heroPortrait.setImageResource(R.drawable.sniper_full);
                break;
            case " Spectre​":
                heroPortrait.setImageResource(R.drawable.spectre_full);
                break;
            case " Spirit Breaker​":
                heroPortrait.setImageResource(R.drawable.spirit_breaker_full);
                break;
            case " Storm Spirit​":
                heroPortrait.setImageResource(R.drawable.storm_spirit_full);
                break;
            case " Sven​":
                heroPortrait.setImageResource(R.drawable.sven_full);
                break;
            case " Techies​":
                heroPortrait.setImageResource(R.drawable.techies_full);
                break;
            case " Templar Assassin​":
                heroPortrait.setImageResource(R.drawable.templar_assassin_full);
                break;
            case " Terrorblade​":
                heroPortrait.setImageResource(R.drawable.terrorblade_full);
                break;
            case " Tidehunter​":
                heroPortrait.setImageResource(R.drawable.tidehunter_full);
                break;
            case " Timbersaw​":
                heroPortrait.setImageResource(R.drawable.shredder_full);
                break;
            case " Tinker​":
                heroPortrait.setImageResource(R.drawable.tinker_full);
                break;
            case " Tiny​":
                heroPortrait.setImageResource(R.drawable.tiny_full);
                break;
            case " Treant Protector​":
                heroPortrait.setImageResource(R.drawable.treant_full);
                break;
            case " Troll Warlord​":
                heroPortrait.setImageResource(R.drawable.troll_warlord_full);
                break;
            case " Tusk​":
                heroPortrait.setImageResource(R.drawable.tusk_full);
                break;
            case " Underlord​":
                heroPortrait.setImageResource(R.drawable.abyssal_underlord_full);
                break;
            case " Undying​":
                heroPortrait.setImageResource(R.drawable.undying_full);
                break;
            case " Ursa​":
                heroPortrait.setImageResource(R.drawable.ursa_full);
                break;
            case " Vengeful Spirit​":
                heroPortrait.setImageResource(R.drawable.vengefulspirit_full);
                break;
            case " Venomancer​":
                heroPortrait.setImageResource(R.drawable.venomancer_full);
                break;
            case " Viper​":
                heroPortrait.setImageResource(R.drawable.viper_full);
                break;
            case " Visage​":
                heroPortrait.setImageResource(R.drawable.visage_full);
                break;
            case " Warlock​":
                heroPortrait.setImageResource(R.drawable.warlock_full);
                break;
            case " Weaver​":
                heroPortrait.setImageResource(R.drawable.weaver_full);
                break;
            case " Windranger​":
                heroPortrait.setImageResource(R.drawable.windrunner_full);
                break;
            case " Winter Wyvern​":
                heroPortrait.setImageResource(R.drawable.winter_wyvern_full);
                break;
            case " Witch Doctor​":
                heroPortrait.setImageResource(R.drawable.witch_doctor_full);
                break;
            case " Wraith King​":
                heroPortrait.setImageResource(R.drawable.skeleton_king_full);
                break;
            case " Zeus​":
                heroPortrait.setImageResource(R.drawable.zuus_full);
                break;

        }
    }




}
