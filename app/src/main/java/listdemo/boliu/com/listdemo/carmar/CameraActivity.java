package listdemo.boliu.com.listdemo.carmar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static listdemo.boliu.com.listdemo.carmar.CameraUtils.REQUEST_IMAGE;
import static listdemo.boliu.com.listdemo.carmar.CameraUtils.getImageDestination;

/**
 * Created by boliu on 3/21/18.
 */

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if( requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK ){
//            try {
//                File destination = getImageDestination(mPhotoName);
//
//                FileInputStream in = null;
//                try {
//                    in = new FileInputStream(destination);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 10;
//                String imagePath = destination.getAbsolutePath();
//                Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
//                picture.setImageBitmap(bmp);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        }
//        else{
//            tvPath.setText("Request cancelled");
//        }
    }
}
