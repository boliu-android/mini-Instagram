package listdemo.boliu.com.listdemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by boliu on 3/24/18.
 */

public class ToastUtils {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
