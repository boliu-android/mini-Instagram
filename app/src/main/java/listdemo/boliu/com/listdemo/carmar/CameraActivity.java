package listdemo.boliu.com.listdemo.carmar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import listdemo.boliu.com.listdemo.R;
import listdemo.boliu.com.listdemo.data.ContentResolverHelper;
import listdemo.boliu.com.listdemo.model.carmera.DogInfo;
import listdemo.boliu.com.listdemo.utils.RealPathUtil;
import listdemo.boliu.com.listdemo.utils.ToastUtils;

import static listdemo.boliu.com.listdemo.carmar.CameraUtils.PICK_IMAGE;
import static listdemo.boliu.com.listdemo.carmar.CameraUtils.getPickImageIntent;

/**
 * Created by boliu on 3/21/18.
 */

public class CameraActivity extends AppCompatActivity {
    private String mPhotoName;
    private ImageView mImage;
    private EditText mOwnerNameEdit;
    private EditText mDogNameEdit;
    private Button mAddImage;
    private Button mDeleteImage;
    private String mPickedImagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carmera_activity);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.save:
                save();
                break;
            case R.id.showGif:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            final Uri selectedImage = data.getData();
            // SDK >= 11 && SDK < 19
            String realPath;
            if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, selectedImage);
                // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(this, selectedImage);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bmp = BitmapFactory.decodeFile(realPath);
            mImage.setImageBitmap(bmp);
            mPickedImagePath = realPath;
        } else {
            ToastUtils.showToast(this, "photo result fails");
        }
    }

    private void init() {
        mImage = findViewById(R.id.photo);
        mOwnerNameEdit = findViewById(R.id.ownNameEditText);
        mDogNameEdit = findViewById(R.id.dogNameEditText);
        mAddImage = findViewById(R.id.addImage);
        mDeleteImage = findViewById(R.id.deleteImage);
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(Intent.createChooser(getPickImageIntent(), "Select Picture"), PICK_IMAGE);
            }
        });
        mDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void save() {
        // check all vaild.

        DogInfo dogInfo = new DogInfo();
        dogInfo.imagePath = mPickedImagePath;
        dogInfo.dogName = mDogNameEdit.getText().toString();
        dogInfo.OwnerName = mOwnerNameEdit.getText().toString();
        dogInfo.uri = Uri.fromFile(new File(mPickedImagePath)).toString();;

        new ContentResolverHelper(this).insertInfo(dogInfo);

        this.finish();
    }
}
