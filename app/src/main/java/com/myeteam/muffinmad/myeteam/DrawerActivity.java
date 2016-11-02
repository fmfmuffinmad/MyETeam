package com.myeteam.muffinmad.myeteam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.myeteam.muffinmad.myeteam.fragments.LoginFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamDiscFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamMembersFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamStratsFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamsFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyPoolFragment;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        OnCompleteListener,
        LoginFragment.OnFragmentInteractionListener,
        MyETeamsFragment.OnFragmentInteractionListener,
        MyETeamMembersFragment.OnFragmentInteractionListener,
        MyETeamDiscFragment.OnFragmentInteractionListener,
        MyETeamStratsFragment.OnFragmentInteractionListener,
        MyPoolFragment.OnFragmentInteractionListener {


    // finals
    private static final String AUTHTAG = "FirebaseAuth";
    private static final int RC_SIGN_IN = 99;
    private static final String GAUTHTAG = "GoogleAuth";
    private static final String MYETEAMS = "MyETeams";
    private static final String MYPOOL = "MyPool";
    private static final String SIGNIN = "Signin";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount acct;
    private FirebaseUser user;
    private NavigationView navigationView;
    private FragmentManager fragManager;
    private DrawerLayout drawer;
    private String currentMode;


    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentMode = "init";

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Firebase setup
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(AUTHTAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(DrawerActivity.this, "Signed in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    LoginFragment frag = (LoginFragment) getSupportFragmentManager().findFragmentByTag(SIGNIN);
                    if (frag != null && frag.isVisible()) {
//                        FragmentTransaction fragTran = fragManager.beginTransaction();
//                        fragTran.replace(R.id.content_drawer, new MyETeamsFragment(), MYETEAMS);
//                        fragTran.commit();
                        changeFragments(new MyETeamsFragment(), MYETEAMS, "My E-Teams");
                    }

                } else {
                    // User is signed out
                    Log.d(AUTHTAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(DrawerActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                    FragmentTransaction fragTran = fragManager.beginTransaction();
//                    fragTran.replace(R.id.content_drawer, new LoginFragment(), SIGNIN);
//                    fragTran.commit();
                    changeFragments(new LoginFragment(), SIGNIN, "Sign in to RULE!");
                }
                // ...
            }
        };

        // google services setup

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentMode){
                    case MYETEAMS:
                        Toast.makeText(DrawerActivity.this, "Criar e-team", Toast.LENGTH_SHORT).show();
                        break;
                    case MYPOOL:
                        Toast.makeText(DrawerActivity.this, "Criar Hero", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragManager = getSupportFragmentManager();

//        FragmentTransaction fragTran = fragManager.beginTransaction();
//        fragTran.replace(R.id.content_drawer, new MyETeamsFragment(), MYETEAMS);
//        fragTran.commit();
        changeFragments(new MyETeamsFragment(), MYETEAMS, "My E-Teams");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fragTran = fragManager.beginTransaction();

        if (id == R.id.nav_eteams) {
//            fragTran.replace(R.id.content_drawer, new MyETeamsFragment());
//            fragTran.commit();
            changeFragments(new MyETeamsFragment(), MYETEAMS, "My E-Teams");
        } else if (id == R.id.nav_pool) {
//            fragTran.replace(R.id.content_drawer, new MyPoolFragment());
//            fragTran.commit();
            changeFragments(new MyPoolFragment(), MYPOOL, "My Pool");
        } else if (id == R.id.nav_signout) {
            signOut();
            mAuth.signOut();

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragments(Fragment frag, String tag, String title){
        FragmentTransaction fragTran = fragManager.beginTransaction();
        fragTran.replace(R.id.content_drawer, frag, tag);
        fragTran.commit();
        setTitle(title);
        currentMode = tag;
        switch (currentMode){
            case SIGNIN:
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                break;
            default:
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(GAUTHTAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            firebaseAuthWithGoogle(acct);
        } else {
            // TODO: 24/10/2016 implement UI update for failed sign in/on
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(AUTHTAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(AUTHTAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(AUTHTAG, "signInWithCredential", task.getException());
                            // TODO: 24/10/2016 handle firebase sign in fail
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onComplete(@NonNull Task task) {

    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
