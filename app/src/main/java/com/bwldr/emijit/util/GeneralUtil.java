package com.bwldr.emijit.util;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * For "general" utility methods that don't fall into any of the other Utils.
 *
 * Singleton
 */
public class GeneralUtil {

    private GeneralUtil() {}

    public static GeneralUtil newInstance() {
        return new GeneralUtil();
    }

    /**
     * Used to set the Background image for the Dialog, ListPreference, and SignupFragment.
     *
     * @param context Context
     * @param fragment Object b/c may vary
     * @param view ImageView to set the background image on
     */
    public void setBackgroundImage(Context context, Object fragment, ImageView view) {
        try {
            String savedImagePath = RealPathUtil.getAbsoluteFilePath(
                    context,
                    Uri.parse(PreferenceUtil.getBackgroundImagePath(context))
            );
            if (savedImagePath.length() > 0) {
                if (fragment instanceof Fragment) {
                    Glide.with((Fragment)fragment).load(savedImagePath).into(view);
                } else if (fragment instanceof android.app.Fragment) {
                    Glide.with((android.app.Fragment)fragment).load(savedImagePath).into(view);
                } else if (fragment instanceof Context) {
                    Glide.with((Context)fragment).load(savedImagePath).into(view);
                }
            }
        } catch (Exception e) {
            Log.e(fragment.getClass().getSimpleName(), e.toString());
        }
    }

}
