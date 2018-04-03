package com.bwldr.emijit.signup;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.bwldr.emijit.R;
import com.bwldr.emijit.emails.EmailsActivity;
import com.bwldr.emijit.onboarding.OnboardingActivity;
import com.bwldr.emijit.settings.SettingsActivity;
import com.firebase.ui.auth.AuthUI;

/**
 * This is the standard entry point for new "Contacts" to enter the app.
 * <p>
 * A "Contact" is a User who signs up by email, etc... with the "Business"
 */

public class SignupActivity extends AppCompatActivity
        implements SignupContract.SignupDialog, SignupDialogFragment.SignupDialogListener {

    private DrawerLayout mDrawerLayout;
    private SignupFragment mSignupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        setUpMenuButton();

        if (savedInstanceState == null) {
            mSignupFragment = SignupFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mSignupFragment)
                    .commit();
        }
    }

    private void setUpMenuButton() {
        ImageButton menuBtn = (ImageButton) findViewById(R.id.nav_menu);

        // Only can do translucent system bar in Version >= 19, in which case the
        // menu button's offset needs to be adjusted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            menuBtn.setPadding(0, getStatusBarHeight(), 0, 0);
        }

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.settings_navigation_menu_item:
                                startActivity(new Intent(SignupActivity.this, SettingsActivity.class));
                                break;
                            case R.id.emails_navigation_menu_item:
                                startActivity(new Intent(SignupActivity.this, EmailsActivity.class));
                                break;
                            case R.id.sign_out_navigation_menu_item:
                                AuthUI.getInstance().signOut(SignupActivity.this);
                                break;
                            case R.id.onboarding_navigation_menu_item:
                                startActivity(new Intent(SignupActivity.this, OnboardingActivity.class));
                                break;
                            default:
                                break;
                        }
                        // close the Navigation Drawer
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    @Override
    public void submitBtnClicked() {
        SignupDialogFragment dialogFragment = new SignupDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "SignupDialogFragment");
    }

    @Override
    public void onExitDialog(SignupDialogFragment dialog) {
        mSignupFragment.clearEmailText();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
