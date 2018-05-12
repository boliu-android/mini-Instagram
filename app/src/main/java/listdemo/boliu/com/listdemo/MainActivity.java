package listdemo.boliu.com.listdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

import listdemo.boliu.com.listdemo.VideoConvert.AnimatedGifMaker;
import listdemo.boliu.com.listdemo.VideoConvert.GifShowActivity;
import listdemo.boliu.com.listdemo.adapter.ContactListView;
import listdemo.boliu.com.listdemo.adapter.ContactPresenter;
import listdemo.boliu.com.listdemo.adapter.PhotoAdapter;
import listdemo.boliu.com.listdemo.carmar.CameraActivity;
import listdemo.boliu.com.listdemo.data.ContentResolverHelper;
import listdemo.boliu.com.listdemo.data.DataUtils;
import listdemo.boliu.com.listdemo.model.carmera.Photo;
import listdemo.boliu.com.listdemo.utils.PermissionUtils;

import static listdemo.boliu.com.listdemo.VideoConvert.GifShowActivity.GIF_PATH;
import static listdemo.boliu.com.listdemo.carmar.CameraUtils.dateToString;

public class MainActivity extends AppCompatActivity implements ContactListView, SwipeRefreshLayout.OnRefreshListener {

    public static final int GIF_CONVERT_MSG = 2;

    private ContactPresenter mPresenter;
    private View parentView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PhotoAdapter adapter;
    private String mPhotoName;

    MyInnerHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        parentView = findViewById(R.id.parentLayout);

        // listView
        final ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new PhotoAdapter(MainActivity.this);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Photo photo = adapter.getItem(i);
                new ContentResolverHelper(listView.getContext()).deletePhoto(photo);
                return false;
            }
        });

        // MVP
        mPresenter = new ContactPresenter(this);
        mPresenter.attachView(this);
        mPresenter.startLoadContacts();
    }

    public void init() {
        // check permission
        PermissionUtils.checkPermission(this);

        // refresh to load layout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        final Intent intent = new Intent(this, CameraActivity.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        myHandler = new MyInnerHandler(this);
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
            case R.id.convert:
                new GifThread("test", adapter.getPhotoList()).start();
                break;
            case R.id.showGif:
                launchShowGifActivity(getGifPath("test"));
        }
        return true;
    }

    @Override
    public void onRefresh() {
        mPresenter.startLoadContacts();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String msg) {
        // use snackbar to replace ToastUtils to show Error message

    }

    @Override
    public void showResult(List<Photo> list) {
        adapter.setContacts(list);
        adapter.notifyDataSetChanged();
        hideLoading();
        Log.d("BO", "Main showResult");
    }

    @Override
    public void showTitle(String title) {

    }

    private String getGifPath(String name) {
        return DataUtils.getFilesFolder().toString() + "/Gifs/" + name + ".gif";
    }

    private class GifThread extends Thread {
        private String mName;
        private List<Photo> mPhotos;

        public GifThread(String proj, List<Photo> photos) { // ONLY WORKS AFTER SAVING
            mName = proj;
            mPhotos = photos;
        }

        @Override
        public void run() {
            Log.d("BO", "GifThread start");

            String root = DataUtils.getFilesFolder().toString();
            File myDir = new File(root + "/Gifs/");
            if (!myDir.exists())
                myDir.mkdirs();
            String fname = mName;
            File file = new File(myDir, fname + ".gif");
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                AnimatedGifMaker gifs = new AnimatedGifMaker();
                gifs.start(out);
                gifs.setDelay(200);
                gifs.setRepeat(0);
                gifs.setTransparent(new Color());

                for (Photo photo : mPhotos) {
                    Bitmap bitmap = decodeFile(photo.path);
                    gifs.addFrame(bitmap);
                }
                gifs.finish();

                Message msg = new Message();
                msg.what = GIF_CONVERT_MSG;
                msg.obj = file.getAbsolutePath();

                myHandler.sendMessage(msg);
//                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
//                        Uri.parse("file://"
//                                + Environment.getExternalStorageDirectory()))); // uM

                Log.d("BO", "GifThread success");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap decodeFile(String imgPath) {
        Bitmap b = null;
        int max_size = 1000;
        File f = new File(imgPath);
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int scale = 1;
            if (o.outHeight > max_size || o.outWidth > max_size) {
                scale = (int) Math.pow(2, (int) Math.ceil(Math.log(max_size / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (Exception e) {
        }
        return b;
    }

    private void launchShowGifActivity(@NotNull String name) {
        final Intent intent = new Intent(this, GifShowActivity.class);
        intent.putExtra(GIF_PATH, name);
        startActivity(intent);
    }

    static class MyInnerHandler extends Handler {
        WeakReference<MainActivity> mActivity;

        MyInnerHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            MainActivity activity = mActivity.get();
            switch (message.what) {
                case GIF_CONVERT_MSG:
                    String path = (String) message.obj;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 10;

                    Photo photo = new Photo();
                    photo.uri = Uri.fromFile(new File(path)).toString();;
                    photo.data = dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss");
                    photo.path = path;

                    new ContentResolverHelper(activity).insertPhoto(photo);
                    break;
            }
        }
    }

}
