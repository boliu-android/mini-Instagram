package listdemo.boliu.com.listdemo.carmar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;

import listdemo.boliu.com.listdemo.R;
import listdemo.boliu.com.listdemo.data.ContentResolverHelper;
import listdemo.boliu.com.listdemo.data.DataUtils;
import listdemo.boliu.com.listdemo.model.carmera.Photo;
import listdemo.boliu.com.listdemo.utils.ToastUtils;

import static listdemo.boliu.com.listdemo.carmar.CameraUtils.REQUEST_IMAGE;
import static listdemo.boliu.com.listdemo.carmar.CameraUtils.dateToString;
import static listdemo.boliu.com.listdemo.carmar.CameraUtils.getImageDestination;

/**
 * Created by boliu on 3/21/18.
 */

public class CameraActivity extends AppCompatActivity {
    private String mPhotoName;
    private ImageView mImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carmera_activity);
        mImage = findViewById(R.id.photo);

        DataUtils.createFilesFolder(this);
        mPhotoName = dateToString(new Date(),"yyyy-MM-dd-hh-mm-ss");
        startActivityForResult(CameraUtils.getTakePhotoIntent(CameraActivity.this, null,mPhotoName), REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK ){
            File destination = getImageDestination(mPhotoName);

            String path = destination.getAbsolutePath();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bmp = BitmapFactory.decodeFile(path);
            mImage.setImageBitmap(bmp);

            Photo photo = new Photo();
            photo.path = path;
            photo.uri = Uri.fromFile(new File(path)).toString();;
            photo.data = mPhotoName;

            new ContentResolverHelper(this).insertPhoto(photo);
        }
        else{
            ToastUtils.showToast(this, "photo result fails");
        }
    }
}
