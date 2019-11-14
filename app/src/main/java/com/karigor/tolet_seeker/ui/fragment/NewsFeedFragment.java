package com.karigor.tolet_seeker.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.karigor.tolet_seeker.R;
import com.karigor.tolet_seeker.adapter.PostAdapter;
import com.karigor.tolet_seeker.data.model.BloodRequestModel;
import com.karigor.tolet_seeker.ui.Filters;
import com.karigor.tolet_seeker.viewmodel.NewsfeedActivityViewModel;

public class NewsFeedFragment extends Fragment implements
        com.karigor.tolet_seeker.ui.FilterDialogFragment.FilterListener,
        PostAdapter.OnPostSelectedListener, View.OnClickListener  {

    public interface NewsfeedListener{

        void openAddRequestFragment();
    }

    private static final int LIMIT = 50;
    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private Toolbar mToolbar;
    private TextView mCurrentSearchView;
    private TextView mCurrentSortByView;
    private RecyclerView mPostsRecycler;
    private ViewGroup mEmptyView;

    private com.karigor.tolet_seeker.ui.FilterDialogFragment mFilterDialog;
    private PostAdapter mAdapter;

    private NewsfeedActivityViewModel mViewModel;

    private NewsfeedListener newsfeedListener = null;

    public NewsFeedFragment() { }


    public NewsFeedFragment(Context context) {

        if(context instanceof NewsfeedListener)
            newsfeedListener = (NewsfeedListener) context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        init(rootView);

        return rootView;
    }

    private void init(final View view) {

        mToolbar = view.findViewById(R.id.toolbar);
        mCurrentSearchView = view.findViewById(R.id.textCurrentSearch);
        mCurrentSortByView = view.findViewById(R.id.textCurrentSortBy);
        mPostsRecycler = view.findViewById(R.id.recyclerPosts);
        mEmptyView = view.findViewById(R.id.viewEmpty);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        view.findViewById(R.id.filterBar).setOnClickListener(this);
        view.findViewById(R.id.buttonClearFilter).setOnClickListener(this);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(newsfeedListener != null)
                   newsfeedListener.openAddRequestFragment();
            }
        });

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mPostsRecycler = view.findViewById(R.id.recyclerPosts);

        // View model
        mViewModel = ViewModelProviders.of(this).get(NewsfeedActivityViewModel.class);

        // Enable Firestore logging
        //FirebaseFirestore.setLoggingEnabled(true);

        // Firestore
        mFirestore = FirebaseFirestore.getInstance();

        // Get ${LIMIT} restaurants
        mQuery = mFirestore.collection(BloodRequestModel.COLLECTION_BLOOD_REQUESTS)
                .limit(LIMIT);

        // RecyclerView
        mAdapter = new PostAdapter(mQuery, this,firebaseUser.getUid()) {
            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    mPostsRecycler.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mPostsRecycler.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(view.findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
            }
        };

        mPostsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mPostsRecycler.setAdapter(mAdapter);

        // Filter Dialog
        mFilterDialog = new com.karigor.tolet_seeker.ui.FilterDialogFragment(this);
    }



    @Override
    public void onStart() {

        super.onStart();

        // Apply filters
        onFilter(mViewModel.getFilters());

        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {

        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    @Override
    public void onFilter(Filters filters) {

        // Construct query basic query
        String fileter = "";
        Query query = mFirestore.collection(BloodRequestModel.COLLECTION_BLOOD_REQUESTS);

        // Category (equality filter)
        if (filters.hasPatientType()) {
            fileter += filters.getPatientType();
            query = query.whereEqualTo(BloodRequestModel.FIELD_PATIENT_TYPE, filters.getPatientType());
        }

        // City (equality filter)
        if (filters.hasDistrict()) {
            fileter += filters.getDistrict();

            query = query.whereEqualTo(BloodRequestModel.FIELD_DISTRICT, filters.getDistrict());
        }

        // Price (equality filter)
        if (filters.hasBloodGroup()) {
            fileter += filters.getBlood_group();

            query = query.whereEqualTo(BloodRequestModel.FIELD_BLOOD_GROUP, filters.getBlood_group());
        }

        // Sort by (orderBy with direction)
        if (filters.hasPatientCondition()) {
            fileter += filters.getPatient_condition();

            query = query.whereEqualTo(BloodRequestModel.FIELD_PATIENT_CONDITION, filters.getPatient_condition());
        }

        Log.w("query on fileter",fileter);
        // Limit items
        query = query.limit(LIMIT);

        // Update the query
        mAdapter.setQuery(query);

        // Set header
        mCurrentSearchView.setText(Html.fromHtml(filters.getSearchDescription(getContext())));
        //mCurrentSortByView.setText(filters.getOrderDescription(this));

        // Save filters
        mViewModel.setFilters(filters);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filterBar:
                onFilterClicked();
                break;
            case R.id.buttonClearFilter:
                onClearFilterClicked();
                break;
        }
    }
    public void onFilterClicked() {
        // Show the dialog containing filter options
        mFilterDialog.show(getFragmentManager(), com.karigor.tolet_seeker.ui.FilterDialogFragment.TAG);
    }

    public void onClearFilterClicked() {
        mFilterDialog.resetFilters();

        onFilter(Filters.getDefault());
    }


    @Override
    public void onRestaurantSelected(DocumentSnapshot restaurant) {
        Toast.makeText(getContext(), "details", Toast.LENGTH_SHORT).show();

    }
}
