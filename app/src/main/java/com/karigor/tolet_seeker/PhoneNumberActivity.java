package com.karigor.tolet_seeker;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.karigor.tolet_seeker.firebase.FirebaseAuthError;
import com.karigor.tolet_seeker.ui.fragment.PhoneVerificationFragment;

public class PhoneNumberActivity extends AppCompatActivity {

    private String TAG = "PhoneNumberActivity";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        fragmentManager = getSupportFragmentManager();
        initCallback();
        initPhoneVerification();
    }

    private void initPhoneVerification() {

        Fragment fragment = new PhoneVerificationFragment();
        //fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    private void initCallback() {

        onVerificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                phoneVerificationSuccessHandler(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                handleError(e);
                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                openOTPEntryFragment();

                // ...
            }
        };
    }

    private void openOTPEntryFragment() {

        Toast.makeText(this,"OTP sent",Toast.LENGTH_SHORT).show();
    }

    private void phoneVerificationSuccessHandler(PhoneAuthCredential credential) {

        //already verified, no need to input OTP
        Toast.makeText(this,"Verified Without code",Toast.LENGTH_SHORT).show();

    }

    private void handleError(@Nullable Exception e) {

        String errorMsg;

        if (e instanceof FirebaseAuthException) {
            errorMsg = getErrorMessage(
                    FirebaseAuthError.fromException((FirebaseAuthException) e));
        } else if (e != null) {
            errorMsg = e.getLocalizedMessage();
        } else {
            errorMsg = "Unknown Error Occured";
        }

        Log.w("handleError",errorMsg);

    }

    private String getErrorMessage(FirebaseAuthError error) {
        switch (error) {
            case ERROR_INVALID_PHONE_NUMBER:
                return getString(R.string.fui_invalid_phone_number);
            case ERROR_TOO_MANY_REQUESTS:
                return getString(R.string.fui_error_too_many_attempts);
            case ERROR_QUOTA_EXCEEDED:
                return getString(R.string.fui_error_quota_exceeded);
            case ERROR_INVALID_VERIFICATION_CODE:
                return getString(R.string.fui_incorrect_code_dialog_body);
            case ERROR_SESSION_EXPIRED:
                return getString(R.string.fui_error_session_expired);
            default:
                return "Error";
        }
    }


}
