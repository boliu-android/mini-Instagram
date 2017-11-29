package listdemo.boliu.com.listdemo.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import listdemo.boliu.com.listdemo.R;

/**
 * Created by boliu on 5/13/18.
 */

public class DetailsInfoActivity extends AppCompatActivity {
    public static String OWNER_NAME = "OwnerName";
    public static String DOG_NAME = "DogName";
    public static String IMAGE_PATH;

    private ImageView mImage;
    private TextView mOwnerName;
    private TextView mDogName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        Bundle bundle = getIntent().getExtras();
        init(bundle.getString(OWNER_NAME), bundle.getString(DOG_NAME), bundle.getString(IMAGE_PATH));
    }

    private void init(@NotNull String owner, @NotNull String dogName, @NotNull String imagePath) {
        mImage = findViewById(R.id.photo);
        mOwnerName = findViewById(R.id.ownNameText);
        mDogName = findViewById(R.id.dogNameText);

        mOwnerName.setText("Owner name is: " + owner);
        mDogName.setText("Dog name is: " + dogName);

        Uri uri = Uri.parse(imagePath);

        Picasso.with(this).load(uri).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(mImage);
    }
}
