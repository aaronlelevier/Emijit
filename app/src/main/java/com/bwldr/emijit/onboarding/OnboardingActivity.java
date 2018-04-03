package com.bwldr.emijit.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.bwldr.emijit.R;

public class OnboardingActivity extends FragmentActivity {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // Instantiate a ViewPager and a PagerAdapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new OnboardingPageAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_dots);
        tabLayout.setupWithViewPager(mPager, true);
    }

    /**
     * If it's the 1st page, exit the app on back pressed because the User hasn't gone through
     * the Onboarding, so close the app because they can't use it yet until they complete.
     */
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // rely on default page behavior to handle here
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class OnboardingPageAdapter extends FragmentStatePagerAdapter {

        OnboardingPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getOnboardingFragment(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        /**
         * Returns the correct fragment based upon the onboarding step
         * @param position int      of the step position
         * @return {@link Fragment} used by onboarding step
         */
        private Fragment getOnboardingFragment(int position) {
            Fragment fragment;

            switch (position) {
                case 0:
                    fragment = new OnboardingFragmentStep1();
                    break;
                case 1:
                    fragment = new OnboardingFragmentStep2();
                    break;
                case 2:
                    fragment = new OnboardingFragmentStep3();
                    break;
                default:
                    fragment = new OnboardingFragmentStep1();
                    break;
            }

            return fragment;
        }
    }
}
