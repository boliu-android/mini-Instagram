package listdemo.boliu.com.listdemo.data;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import listdemo.boliu.com.listdemo.utils.RealPathUtil;

/**
 * Created by boliu on 3/21/18.
 */

public class DataUtils {

    private static final String TAG = "DataUtils";
    private static final String FOLDER_PATH = "gym_app_folder/";

    /**
     * create a folder in external storage directory.
     */
    public static void createFilesFolder(Activity activity) {
        File f = new File(Environment.getExternalStorageDirectory(), FOLDER_PATH);
        if (!f.exists()) {
            boolean success = f.mkdirs();
            Log.d(TAG, "create sdcard folder state:" + success);
        }
    }

    public static File getFilesFolder() {
        return new File(Environment.getExternalStorageDirectory(), FOLDER_PATH);
    }

    public static String getImageRealPath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT < 19)
            return RealPathUtil.getRealPathFromURI_API11to18(context, uri);
            // SDK > 19 (Android 4.4)
        else
            return RealPathUtil.getRealPathFromURI_API19(context, uri);
    }
}
