package listdemo.boliu.com.listdemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by bloiu on 5/13/2017.
 */

public class ToastUtils {
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
