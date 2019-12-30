package com.karigor.tolet_seeker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.karigor.tolet_seeker.R;
import com.karigor.tolet_seeker.data.model.HouseModel;
import com.karigor.tolet_seeker.listeners.RentSubmitListener;
import com.karigor.tolet_seeker.ui.fragment.AddRequestFragment;
import com.karigor.tolet_seeker.ui.fragment.NewsFeedFragment;
import com.karigor.tolet_seeker.ui.fragment.PreferenceFragment;
import com.karigor.tolet_seeker.ui.fragment.UserProfileFragment;

public class UserActivity extends AppCompatActivity implements RentSubmitListener, NewsFeedFragment.NewsfeedListener {

    private FragmentManager fragmentManager;
    private FirebaseAuth mFirebaseAuth;
    private String userName;
    private String email;
    private String contactNumber;
    private String userId;

    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private CollectionReference requestCollection;

    boolean doublePressedBackToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        fragmentManager = getSupportFragmentManager();
        initNavigation();

        // Enable Firestore logging
        // FirebaseFirestore.setLoggingEnabled(true);
        // Firestore
        initFirebase();

        openNewsFeedFragment();
    }


    void initFirebase(){

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        mFirestore = FirebaseFirestore.getInstance();
        requestCollection = mFirestore.collection(HouseModel.COLLECTION_RENT_REQUESTS);
    }


    private void initNavigation() {

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

//                    case R.id.add_request:
//                        openAddRequestFragment();
//                        return true;

                    case R.id.feed:
                        openNewsFeedFragment();
                        return true;

                    case R.id.notifications:
                        openBloodRequestFragment();
                        return true;

                    case R.id.profile:
                        openPreferenceFragment();
                        return true;
                }
                return false;
            }
        };

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void openBloodRequestFragment() {

        Fragment fragment = new UserProfileFragment();
        //fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }



    private void openNewsFeedFragment() {

        Fragment fragment = new NewsFeedFragment(this);
        //fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }


    private void openPreferenceFragment() {

        Fragment fragment = new PreferenceFragment();
        //fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }


    @Override
    public String addHouseRequest(HouseModel houseAttributeRequest) {

        requestCollection.document().set(houseAttributeRequest)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(UserActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                openNewsFeedFragment();
                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                e.printStackTrace();
                                Toast.makeText(UserActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();

                            }
                        }
                );

        return null;
    }



    @Override
    public void openAddRequestFragment() {

        Fragment fragment = new AddRequestFragment();
        //fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getResources().getString(R.string.action_newsfeed))
                .commitAllowingStateLoss();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_sign_out:
                AuthUI.getInstance().signOut(this);
                startActivity(new Intent(this,MainActivity.class));
                finish();
                //startSignIn();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        // Get FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 1) {
            // Pop previous Fragment
            fm.popBackStack();
        }
        else if (doublePressedBackToExit) {

            // Check if doubleBackToExitPressed is true
            super.onBackPressed();
            finish();
        }
        else {
            this.doublePressedBackToExit = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            // Delay of 2 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    // Set doublePressedBackToExit false after 2 seconds
                    doublePressedBackToExit = false;
                }
            }, 2000);
        }
    }
}
