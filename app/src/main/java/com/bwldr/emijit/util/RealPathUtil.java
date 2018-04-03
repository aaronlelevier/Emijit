package com.bwldr.emijit.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

/**
 * Function object used to decode the absolute path from a {@link Uri}
 */
public class RealPathUtil {

    private RealPathUtil() {
    }

    /**
     * Resolve the absolute file path based upon the Uri. This is the Only
     * exported method of this class.
     *
     * @param context Context
     * @param uri Uri
     * @return String absolute file path based upon Uri
     */
    public static String getAbsoluteFilePath(Context context, Uri uri) {
        int currentApiVersion = Build.VERSION.SDK_INT;

        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            return getRealPathFromURI_API19(context, uri);
        } else if (currentApiVersion >= Build.VERSION_CODES.HONEYCOMB && currentApiVersion < Build.VERSION_CODES.KITKAT) {
            return getRealPathFromURI_API11to18(context, uri);
        }
        return getRealPathFromURI_BelowAPI11(context, uri);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(column[0]);
                filePath = cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return filePath;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);

        Cursor cursor = null;
        try {
            cursor = cursorLoader.loadInBackground();
            if(cursor != null){
                int column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    private static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String filepath = "";
        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor != null) {
                int column_index
                        = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                filepath = cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return filepath;
    }
}
