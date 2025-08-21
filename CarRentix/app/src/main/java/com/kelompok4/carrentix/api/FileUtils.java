package com.kelompok4.carrentix.api;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.os.Environment;
import android.content.ContentUris;
import android.os.Build;
import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {

    @SuppressLint("Range")
    public static String getPath(Context context, Uri uri) {
        try {
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
                if (returnCursor != null && returnCursor.moveToFirst()) {
                    String name = returnCursor.getString(returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    File file = new File(context.getCacheDir(), name);
                    try {
                        InputStream inputStream = context.getContentResolver().openInputStream(uri);
                        FileOutputStream outputStream = new FileOutputStream(file);
                        byte[] buffers = new byte[1024];
                        int read;
                        while ((read = inputStream.read(buffers)) != -1) {
                            outputStream.write(buffers, 0, read);
                        }
                        inputStream.close();
                        outputStream.close();
                        return file.getAbsolutePath();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
