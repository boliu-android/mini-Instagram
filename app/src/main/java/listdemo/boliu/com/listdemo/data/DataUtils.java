package listdemo.boliu.com.listdemo.data;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;

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

}
