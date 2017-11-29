package listdemo.boliu.com.listdemo.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

/**
 * Created by bloiu on 5/13/2017.
 */

public class DataUtils {
    /**
     * get image real path
     * @param context
     * @param uri
     * @return
     */
    public static String getImageRealPath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT < 19)
            return RealPathUtil.getRealPathFromURI_API11to18(context, uri);
            // SDK > 19 (Android 4.4)
        else
            return RealPathUtil.getRealPathFromURI_API19(context, uri);
    }
}
