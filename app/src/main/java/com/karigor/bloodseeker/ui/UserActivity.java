package com.karigor.bloodseeker.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.karigor.bloodseeker.R;
import com.karigor.bloodseeker.data.model.BloodRequestModel;
import com.karigor.bloodseeker.listeners.BloodRequestSubmitListener;
import com.karigor.bloodseeker.ui.fragment.AddRequestFragment;
import com.karigor.bloodseeker.ui.fragment.NewsFeedFragment;
import com.karigor.bloodseeker.ui.fragment.PreferenceFragment;
import com.karigor.bloodseeker.ui.fragment.UserProfileFragment;

public class UserActivity extends AppCompatActivity implements BloodRequestSubmitListener {

    private FragmentManager fragmentManager;
    private FirebaseAuth mFirebaseAuth;
    private String userName;
    private String email;
    private String contact_number;
    private String userId;

    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private CollectionReference requestCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initNavigation();

        // Enable Firestore logging
        //FirebaseFirestore.setLoggingEnabled(true);
        // Firestore
        mFirestore = FirebaseFirestore.getInstance();
        requestCollection = mFirestore.collection(BloodRequestModel.COLLECTION_BLOOD_REQUESTS);
        fragmentManager = getSupportFragmentManager();

        openNewsFeedFragment();
    }

    private void initNavigation() {

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.add_request:
                        openAddRequestFragment();
                        return true;


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

    private void openAddRequestFragment() {


        Fragment fragment = new AddRequestFragment();
        //fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    @Override
    public String addBloodRequest(BloodRequestModel bloodRequestModel) {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        String response = null;

        if(firebaseUser != null) {

            userName = firebaseUser.getDisplayName();
            email = firebaseUser.getEmail();
            contact_number = firebaseUser.getPhoneNumber();
            userId = firebaseUser.getUid();

            bloodRequestModel.setUser_id(userId);
            bloodRequestModel.setUser_name(userName);
            bloodRequestModel.setUser_email(email);
            bloodRequestModel.setUser_number(contact_number);

            requestCollection.document().set(bloodRequestModel)
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

        }

        return response;
    }

    private void openNewsFeedFragment() {

        Fragment fragment = new NewsFeedFragment();
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

}
