package com.karigor.bloodseeker.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.material.textfield.TextInputLayout;
import com.karigor.bloodseeker.R;
import com.karigor.bloodseeker.firebase.CountryListSpinner;
import com.karigor.bloodseeker.firebase.ImeHelper;
import com.karigor.bloodseeker.firebase.PhoneNumberUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneVerificationFragment extends Fragment implements View.OnClickListener{



    private ProgressBar mProgressBar;
    private Button mSubmitButton;
    private CountryListSpinner mCountryListSpinner;
    private TextInputLayout mPhoneInputLayout;
    private EditText mPhoneEditText;
    private TextView mSmsTermsText;
    private TextView mFooterText;



    public static PhoneVerificationFragment newInstance(Bundle params) {
        PhoneVerificationFragment fragment = new PhoneVerificationFragment();
        Bundle args = new Bundle();
        args.putBundle(ExtraConstants.PARAMS, params);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phone_verification, container, false);


        mProgressBar = view.findViewById(R.id.top_progress_bar);
        mSubmitButton = view.findViewById(R.id.send_code);
        mCountryListSpinner = view.findViewById(R.id.country_list);
        mPhoneInputLayout = view.findViewById(R.id.phone_layout);
        mPhoneEditText = view.findViewById(R.id.phone_number);
        mSmsTermsText = view.findViewById(R.id.send_sms_tos);
        mFooterText = view.findViewById(R.id.email_footer_tos_and_pp_text);

        mSmsTermsText.setText(getString(R.string.fui_sms_terms_of_service,
                getString(R.string.fui_verify_phone_number)));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && getFlowParams().enableHints) {
//            mPhoneEditText.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
//        }
        requireActivity().setTitle(getString(R.string.fui_verify_phone_number_title));

        ImeHelper.setImeOnDoneListener(mPhoneEditText, new ImeHelper.DonePressedListener() {
            @Override
            public void onDonePressed() {
                onNext();
            }
        });
        mSubmitButton.setOnClickListener(this);

        setupPrivacyDisclosures();
        setupCountrySpinner();

        return view;
    }


    @Override
    public void onClick(View v) {
        onNext();
    }

    private void setupPrivacyDisclosures() {
//        FlowParameters params = getFlowParams();
//
//        boolean termsAndPrivacyUrlsProvided = params.isTermsOfServiceUrlProvided()
//                && params.isPrivacyPolicyUrlProvided();
//
//        if (!params.shouldShowProviderChoice() && termsAndPrivacyUrlsProvided) {
//            PrivacyDisclosureUtils.setupTermsOfServiceAndPrivacyPolicySmsText(requireContext(),
//                    params,
//                    mSmsTermsText);
//        } else {
//            PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(),
//                    params,
//                    mFooterText);
//
//            String verifyText = getString(R.string.fui_verify_phone_number);
//            mSmsTermsText.setText(getString(R.string.fui_sms_terms_of_service, verifyText));
//        }
    }
    private void onNext() {
        String phoneNumber = getPseudoValidPhoneNumber();
        if (phoneNumber == null) {
            mPhoneInputLayout.setError(getString(R.string.fui_invalid_phone_number));
        } else {
            //mVerificationHandler.verifyPhoneNumber(phoneNumber, false);
            //send phone number for verification via activity callback
        }
    }


    @Nullable
    private String getPseudoValidPhoneNumber() {
        String everythingElse = mPhoneEditText.getText().toString();

        if (TextUtils.isEmpty(everythingElse)) {
            return null;
        }

        return PhoneNumberUtils.format(
                everythingElse, mCountryListSpinner.getSelectedCountryInfo());
    }

    private void setupCountrySpinner() {
        Bundle params = getArguments().getBundle(ExtraConstants.PARAMS);
        mCountryListSpinner.init(params);

        // Clear error when spinner is clicked on
        mCountryListSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneInputLayout.setError(null);
            }
        });
    }


}
