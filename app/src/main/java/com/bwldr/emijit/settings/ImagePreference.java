package com.bwldr.emijit.settings;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwldr.emijit.R;
import com.bwldr.emijit.util.GeneralUtil;


public class ImagePreference extends Preference {

    private View mView;

    public ImagePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.pref_image_widget);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        mView = super.onCreateView(parent);
        setImageIndicator();
        return mView;
    }

    void setImageIndicator() {
        GeneralUtil.newInstance().setBackgroundImage(
                getContext(), getContext(), (ImageView) mView.findViewById(R.id.image_indicator)
        );
    }
}
