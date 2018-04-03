package com.bwldr.emijit.util;


public class Validators {

    private Validators() {
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
