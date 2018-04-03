package com.bwldr.emijit.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bwldr.emijit.R;
import com.bwldr.emijit.util.PreferenceUtil;

/**
 * To store Tenant configurable settings
 */

public class SettingsActivity extends AppCompatActivity
        implements SettingsContract.SelectImage {

    private static final int PICK_IMAGE_REQUEST = 1;

    private SettingsFragment mSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            mSettingsFragment = new SettingsFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mSettingsFragment)
                    .commit();
        }
    }

    @Override
    public void fromGallery() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void saveImage(String s) {
        PreferenceUtil.putString(this, getString(R.string.pref_background_image_key), s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            mSettingsFragment.getDialog().setDialogImage(uri);
        }
    }
}
