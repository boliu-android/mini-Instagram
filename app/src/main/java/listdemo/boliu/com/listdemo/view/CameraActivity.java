package listdemo.boliu.com.listdemo.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import listdemo.boliu.com.listdemo.R;
import listdemo.boliu.com.listdemo.data.ContentResolverHelper;
import listdemo.boliu.com.listdemo.model.Dog.DogInfo;
import listdemo.boliu.com.listdemo.utils.ToastUtils;

import static listdemo.boliu.com.listdemo.utils.PickImageUtils.PICK_IMAGE;
import static listdemo.boliu.com.listdemo.utils.PickImageUtils.getPickImageIntent;
import static listdemo.boliu.com.listdemo.utils.DataUtils.getImageRealPath;

/**
 * Created by boliu on 5/13/18.
 */

public class CameraActivity extends AppCompatActivity {
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
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            String realPath = getImageRealPath(this, data.getData());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bmp = BitmapFactory.decodeFile(realPath);
            mImage.setImageBitmap(bmp);
            mPickedImagePath = realPath;
        } else {
            ToastUtils.showToast(this, "result fails");
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
                mPickedImagePath = null;
                mImage.setImageDrawable(null);
            }
        });
    }

    private void save() {
        if (isVaild()) {
            DogInfo dogInfo = new DogInfo();
            dogInfo.imagePath = mPickedImagePath;
            dogInfo.dogName = mDogNameEdit.getText().toString();
            dogInfo.ownerName = mOwnerNameEdit.getText().toString();
            dogInfo.uri = Uri.fromFile(new File(mPickedImagePath)).toString();
            new ContentResolverHelper(this).insertInfo(dogInfo);
            this.finish();
        } else {
            ToastUtils.showToast(this, "plz check your dog name or owner name or dog photo can not be empty");
        }
    }

    public boolean isVaild() {
        return !TextUtils.isEmpty(mPickedImagePath) &&
                !TextUtils.isEmpty(mDogNameEdit.getText().toString()) &&
                !TextUtils.isEmpty(mOwnerNameEdit.getText().toString());
    }
}
