package listdemo.boliu.com.listdemo.carmar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import listdemo.boliu.com.listdemo.R;

/**
 * Created by boliu on 5/13/18.
 */

public class DetailsInfoFragment extends Fragment {
    public static String OWNER_NAME = "OwnerName";
    public static String DOG_NAME = "DogName";
    public static String IMAGE_PATH;

    public static Fragment getInstance(String ownerName, String dogName, String realPath) {
        DetailsInfoFragment detailsInfoFragment = new DetailsInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DOG_NAME, dogName);
        bundle.putString(OWNER_NAME, ownerName);
        bundle.putString(IMAGE_PATH, realPath);
        detailsInfoFragment.setArguments(bundle);
        return detailsInfoFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_layout, container, false);
        return view;
    }
}
