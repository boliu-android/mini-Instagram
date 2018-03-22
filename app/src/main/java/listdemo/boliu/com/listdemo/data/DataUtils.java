package listdemo.boliu.com.listdemo.data;

import android.os.Environment;

import java.io.File;

/**
 * Created by boliu on 3/21/18.
 */

public class DataUtils {

    private static final String FOLDER_PATH = "gym_app_folder";

    /**
     * create a folder in external storage directory.
     */
    public static void createFilesFolder() {
        File f = new File(Environment.getExternalStorageDirectory(), FOLDER_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public static File getFilesFolder() {
        return new File(Environment.getExternalStorageDirectory(), FOLDER_PATH);
    }

}
