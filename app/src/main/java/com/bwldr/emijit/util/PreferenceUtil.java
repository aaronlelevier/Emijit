package com.bwldr.emijit.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bwldr.emijit.R;

/**
 * Utility class for retrieving SharedPreferences for views.
 */

public class PreferenceUtil {

    // get

    public static String getTitle(Context context) {
        return getStringPref(context, R.string.pref_title_key, R.string.pref_title_default);
    }

    public static String getSubtitle(Context context) {
        return getStringPref(context, R.string.pref_subtitle_key, R.string.pref_subtitle_default);
    }

    public static String getEmailLabel(Context context) {
        return getStringPref(context, R.string.pref_email_label_key, R.string.pref_email_label_default);
    }

    public static String getEmailPlaceholder(Context context) {
        return getStringPref(context, R.string.pref_email_placeholder_key, R.string.pref_email_placeholder_default);
    }

    public static String getSubmitBtnText(Context context) {
        return getStringPref(context, R.string.pref_submit_btn_key, R.string.pref_submit_btn_default);
    }

    public static String getThankYou(Context context) {
        return getStringPref(context, R.string.pref_thank_you_key, R.string.pref_thank_you_default);
    }

    public static String getBackgroundImagePath(Context context) {
        return getStringPref(context, R.string.pref_background_image_key, R.string.blank);
    }

    private static String getStringPref(Context context, int key, int def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(key), context.getString(def));
    }

    public static int getEmailInputLineColor(Context context) {
        return getIntPref(context, R.string.pref_email_input_line_color_key, R.integer.colorPrimaryLight);
    }

    public static int getSubmitBtnColor(Context context) {
        return getIntPref(context, R.string.pref_submit_btn_color_key, R.integer.md_deep_purple_900);
    }

    private static int getIntPref(Context context, int key, int def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(context.getString(key), def);
    }

    public static boolean getOnboardingCompleted(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(context.getString(R.string.onboarding_completed_key),
                context.getResources().getBoolean(R.bool.onboarding_completed));
    }

    // put

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Updates the "onboarding_completed" {@link SharedPreferences} preference to {@code true}
     * @param context {@link Context}
     */
    public static void putOnboardingCompleted(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(context.getString(R.string.onboarding_completed_key), true);
        editor.apply();
    }
}
