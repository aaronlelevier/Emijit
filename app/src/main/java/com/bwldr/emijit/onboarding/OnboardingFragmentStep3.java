package com.bwldr.emijit.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bwldr.emijit.R;
import com.bwldr.emijit.signup.SignupActivity;
import com.bwldr.emijit.util.PreferenceUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class OnboardingFragmentStep3 extends Fragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_onboarding_step3, container, false);
        initGetStartedBtn();
        return mView;
    }

    private void initGetStartedBtn() {
        Button button = (Button) mView.findViewById(R.id.id_get_started_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtil.putOnboardingCompleted(getContext());
                startActivity(new Intent(getActivity(), SignupActivity.class));
            }
        });
    }
}
