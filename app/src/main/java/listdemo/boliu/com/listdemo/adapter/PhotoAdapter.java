package listdemo.boliu.com.listdemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import listdemo.boliu.com.listdemo.R;
import listdemo.boliu.com.listdemo.model.carmera.DogInfo;

/**
 * Created by bloiu on 11/28/2017.
 */

public class PhotoAdapter extends ArrayAdapter<DogInfo> {
    List<DogInfo> mDogInfoList;
    Context mContext;
    private LayoutInflater mInflater;

    public PhotoAdapter(@NonNull Context context) {
        super(context, 0);
        mContext = context;
        mDogInfoList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public void setContacts(List<DogInfo> list) {
        mDogInfoList.clear();
        if (list != null) {
            mDogInfoList.addAll(list);
        }
    }

    @Nullable
    @Override
    public DogInfo getItem(int position) {
        return mDogInfoList.get(position);
    }

    @Override
    public int getCount() {
        return mDogInfoList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            viewHolder = ViewHolder.create((RelativeLayout) view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DogInfo item = getItem(position);

        viewHolder.textViewName.setText("Dog name: " + item.dogName);
        viewHolder.textViewOwnerName.setText("Owner name: " + item.ownerName);

        Uri uri = Uri.parse(item.uri);

        Picasso.with(mContext)
                .load(uri)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.imageView);
        return viewHolder.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewName;
        public final TextView textViewOwnerName;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewOwnerName) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewOwnerName = textViewOwnerName;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = rootView.findViewById(R.id.imageView);
            TextView textViewName = rootView.findViewById(R.id.textViewName);
            TextView owner = rootView.findViewById(R.id.textViewOwnName);
            return new ViewHolder(rootView, imageView, textViewName, owner);
        }
    }
}
