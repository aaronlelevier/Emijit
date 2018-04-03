package com.bwldr.emijit.signup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.bwldr.emijit.util.PreferenceUtil;


/**
 * Show Dialog after User submits Email.
 */

public class SignupDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(PreferenceUtil.getThankYou(getActivity()))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mSignupDialogListener.onExitDialog(SignupDialogFragment.this);
                    }
                });

        return builder.create();
    }

    interface SignupDialogListener {

        void onExitDialog(SignupDialogFragment dialog);
    }

    private SignupDialogListener mSignupDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSignupDialogListener = (SignupDialogListener) context;
    }

    @Override
    public void onStop() {
        mSignupDialogListener.onExitDialog(SignupDialogFragment.this);
        super.onStop();
    }

}
