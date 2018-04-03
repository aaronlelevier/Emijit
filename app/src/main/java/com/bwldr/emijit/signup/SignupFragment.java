package com.bwldr.emijit.signup;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwldr.emijit.R;
import com.bwldr.emijit.data.Email;
import com.bwldr.emijit.onboarding.OnboardingActivity;
import com.bwldr.emijit.util.FirebaseUtil;
import com.bwldr.emijit.util.GeneralUtil;
import com.bwldr.emijit.util.PreferenceUtil;
import com.bwldr.emijit.util.Validators;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Arrays;

import static android.app.Activity.RESULT_CANCELED;


public class SignupFragment extends Fragment {

    private View mView;
    private Button mSubmitBtn;
    private EditText mEmailText;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;

    public SignupFragment() {
        // required empty public constructor
    }

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    /**
     * If the User is not signed in, check if they need to do the Onboarding,
     * else assume that logged in Users have already done the Onboarding.
     *
     * This is to fix a bug where the Onboarding check doesn't happen in time, and
     * there's a loop that keeps sending the Users to the SigninActivity.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean onboardingCompleted = checkOnboardingCompleted();
        if (!onboardingCompleted) {
            return;
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder().setTheme(R.style.AppTheme_SignIn).setLogo(R.mipmap.ic_emijit_logo)
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_signup, container, false);

        mEmailText = (EditText) mView.findViewById(R.id.email_placeholder);

        mSubmitBtn = (Button) mView.findViewById(R.id.submit_btn);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Validators.isValidEmail(mEmailText.getText())) {
                    mEmailText.setError("Invalid Email");
                } else {
                    saveEmail();
                    ((SignupContract.SignupDialog) SignupFragment.this.getActivity())
                            .submitBtnClicked();
                }
            }
        });

        return mView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFirebaseAuth != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mFirebaseAuth != null)
            mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        Context context = getContext();
        if (mView != null) {
            // Title
            ((TextView) mView.findViewById(R.id.title))
                    .setText(PreferenceUtil.getTitle(context));
            // Subtitle
            ((TextView) mView.findViewById(R.id.subtitle))
                    .setText(PreferenceUtil.getSubtitle(context));
            // Email Label
            ((TextView) mView.findViewById(R.id.email_label))
                    .setText(PreferenceUtil.getEmailLabel(context));
            // Email Placeholder
            mEmailText.setHint(PreferenceUtil.getEmailPlaceholder(context));
            mEmailText.getBackground().setColorFilter(
                    PreferenceUtil.getEmailInputLineColor(context), PorterDuff.Mode.SRC_IN);
            // Submit Btn
            mSubmitBtn.setText(PreferenceUtil.getSubmitBtnText(context));
            mSubmitBtn.setBackgroundColor(PreferenceUtil.getSubmitBtnColor(context));

            // Top Background Image
            GeneralUtil.newInstance().setBackgroundImage(
                    getContext(), SignupFragment.this, (ImageView)mView.findViewById(R.id.top_background_image));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_CANCELED) {
                getActivity().finish();
            }
        }
    }

    /**
     * Checks if onboarding has been completed
     * @return boolean
     */
    private boolean checkOnboardingCompleted() {
        if (!PreferenceUtil.getOnboardingCompleted(getContext())) {
            startActivity(new Intent(getActivity(), OnboardingActivity.class));
            return false;
        }
        return true;
    }

    private void saveEmail() {
        DatabaseReference databaseReference = FirebaseUtil.userEmails();
        databaseReference.push().setValue(new Email(mEmailText.getText().toString()));
    }

    void clearEmailText() {
        mEmailText.setText("");
    }
}
