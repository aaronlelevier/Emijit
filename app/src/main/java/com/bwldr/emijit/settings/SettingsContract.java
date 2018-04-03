package com.bwldr.emijit.settings;


public interface SettingsContract {

    interface SelectImage {

        void fromGallery();

        void saveImage(String s);
    }
}
