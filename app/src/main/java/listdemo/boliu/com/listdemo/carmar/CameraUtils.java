package listdemo.boliu.com.listdemo.carmar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import listdemo.boliu.com.listdemo.BuildConfig;
import listdemo.boliu.com.listdemo.data.DataUtils;

/**
 * Created by boliu on 3/21/18.
 */

public class CameraUtils {
    public static final int REQUEST_IMAGE = 100;
    public static final String MEDIA_TYPE = "jpg";

    public static Intent getTakePhotoIntent(Context context, File path, String photoName) {
        if (path == null) {
            path = getDefaultDestination();
        }
        if (TextUtils.isEmpty(photoName)) {
            photoName = dateToString(new Date(),"yyyy-MM-dd-hh-mm-ss");
        }
        final File destination = new File(path, photoName + "." + MEDIA_TYPE);

        final Uri photoURI = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                destination);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        return intent;
    }

    public static File getDefaultDestination() {
        return DataUtils.getFilesFolder();
    }

    public static File getImageDestination(String name) {
        return new File(DataUtils.getFilesFolder(), name + "." + MEDIA_TYPE);
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

}
