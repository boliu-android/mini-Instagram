package listdemo.boliu.com.listdemo.VideoConvert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import listdemo.boliu.com.listdemo.R;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by boliu on 4/10/18.
 */

public class GifShowActivity extends AppCompatActivity {

    public static final String GIF_PATH = "GifPath";
    private GifImageView mGifImageView;
    private String mGifFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_activity);
        mGifImageView = findViewById(R.id.gifImage);
        mGifFilePath = getIntent().getStringExtra(GIF_PATH);

        GifDrawable gifFromPath = null;
        try {
            gifFromPath = new GifDrawable(mGifFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mGifImageView.setImageDrawable(gifFromPath);
    }
}
