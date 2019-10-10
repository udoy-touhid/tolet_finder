package com.karigor.bloodseeker.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.karigor.bloodseeker.R;


public class UserProfileFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View rootView;
    //CircularImageView user_photo;
    private EditText input_nick_name, input_full_name, input_email;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private DocumentReference mUserRef;
    FirebaseUser firebaseUser;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        if(firebaseUser !=null)
            initLayout();
        return rootView;
    }

    private void initLayout() {

        //user_photo =  rootView.findViewById(R.id.user_photo);
        input_nick_name = rootView.findViewById(R.id.nick_name);
        input_full_name = rootView.findViewById(R.id.full_name);
        input_email = rootView.findViewById(R.id.email);


        input_full_name.setText(firebaseUser.getDisplayName());
        input_email.setText(firebaseUser.getEmail());





    }

}
