package com.karigor.bloodseeker.viewmodel;

import androidx.lifecycle.ViewModel;

import com.karigor.bloodseeker.ui.Filters;


public class NewsfeedActivityViewModel extends ViewModel {

    private boolean mIsSigningIn;
    private Filters mFilters;

    public NewsfeedActivityViewModel() {
        mIsSigningIn = false;
        mFilters = Filters.getDefault();
    }

    public boolean getIsSigningIn() {
        return mIsSigningIn;
    }

    public void setIsSigningIn(boolean mIsSigningIn) {
        this.mIsSigningIn = mIsSigningIn;
    }

    public Filters getFilters() {
        return mFilters;
    }

    public void setFilters(Filters mFilters) {
        this.mFilters = mFilters;
    }
}
