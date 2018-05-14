package listdemo.boliu.com.listdemo.utils;

import android.content.Intent;

/**
 * Created by boliu on 5/13/18.
 */

public class PickImageUtils {
    public static final int REQUEST_IMAGE = 100;
    public static final int PICK_IMAGE = 200;
    public static final String MEDIA_TYPE = "jpg";

    public static Intent getPickImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }
}
