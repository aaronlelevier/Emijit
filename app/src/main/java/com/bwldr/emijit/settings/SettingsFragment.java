package com.bwldr.emijit.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bwldr.emijit.R;
import com.bwldr.emijit.util.GeneralUtil;


public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private static final int RC_PERMISSIONS = 1;
    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE};

    private ImagePickerDialogBuilder mDialogBuilder;
    private Preference mBackgroundImagePref;
    private View mDialogView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        int[] stringResIds = new int[]{
                R.string.pref_title_key,
                R.string.pref_subtitle_key,
                R.string.pref_email_label_key,
                R.string.pref_email_placeholder_key,
                R.string.pref_submit_btn_key,
                R.string.pref_thank_you_key,
                R.string.pref_background_image_key
        };
        for (int resId : stringResIds) {
            bindStringPreferenceToValue(findPreference(getString(resId)));
        }

        int[] intResIds = new int[]{
                R.string.pref_email_input_line_color_key,
                R.string.pref_submit_btn_color_key
        };
        for (int resId : intResIds) {
            bindIntPreferenceToValue(findPreference(getString(resId)));
        }

        initDialogOnClickListener();
    }

    private void bindStringPreferenceToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    private void bindIntPreferenceToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getInt(preference.getKey(), R.integer.colorPrimary));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();
        preference.setSummary(stringValue);
        return true;
    }

    private void initDialogOnClickListener() {
        mBackgroundImagePref = findPreference(getString(R.string.pref_background_image_key));
        mBackgroundImagePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                checkPermissions();

                // open AlertDialog
                mDialogBuilder = new ImagePickerDialogBuilder(getActivity());
                mDialogBuilder.build();
                return true;
            }
        });
    }

    private void checkPermissions() {
        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, RC_PERMISSIONS);
        }
    }

    private boolean hasPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext() != null && PERMISSIONS != null) {
            for (String permission : PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public ImagePickerDialogBuilder getDialog() {
        return mDialogBuilder;
    }

    /**
     * Dialog inflated by {@link ImagePreference}
     */
    class ImagePickerDialogBuilder {

        AlertDialog.Builder builder;
        Context mContext;
        ImageView mImageView;

        ImagePickerDialogBuilder(Context context) {
            mContext = context;
            initWith(context);
        }

        void initWith(Context context) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            mDialogView = inflater.inflate(R.layout.dialog_signup_image, null);
            Button addImageBtn = (Button) mDialogView.findViewById(R.id.button);
            addImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SettingsContract.SelectImage) getActivity()).fromGallery();
                }
            });

            builder = new AlertDialog.Builder(context);
            builder.setView(mDialogView);
            builder.setTitle(R.string.pref_background_image_label);

            GeneralUtil.newInstance().setBackgroundImage(
                    getContext(), SettingsFragment.this, (ImageView)mDialogView.findViewById(R.id.button_image));

            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // w/o this if check, may be a glide.GenericRequest in the case
                    // that the User just hit 'ok' and didn't select a new photo
                    mImageView = (ImageView) mDialogView.findViewById(R.id.button_image);
                    if (mImageView.getTag() instanceof String) {
                        String imagePath = (String) mImageView.getTag();
                        if (imagePath != null) {
                            ((SettingsContract.SelectImage) getActivity()).saveImage(imagePath);
                            // set updated image widget in preference list
                            ((ImagePreference) findPreference(getString(R.string.pref_background_image_key)))
                                    .setImageIndicator();
                        }
                    }
                }
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
        }

        void build() {
            builder.create().show();
        }

        /**
         * Called by `onActivityResult` to set image uri/tag after an image is selected
         * from the Gallery.
         *
         * @param uri Uri of image
         */
        void setDialogImage(Uri uri) {
            mImageView = (ImageView) mDialogView.findViewById(R.id.button_image);
            mImageView.setImageURI(uri);

            // so can be later retrieved if User selects 'ok' to set as background image
            mImageView.setTag(uri.toString());
        }
    }
}
