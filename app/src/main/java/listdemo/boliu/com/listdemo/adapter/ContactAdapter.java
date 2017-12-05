package listdemo.boliu.com.listdemo.adapter;

import android.content.Context;
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
import listdemo.boliu.com.listdemo.model.Contact;

/**
 * Created by bloiu on 11/28/2017.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
    List<Contact> mContactList;
    Context mContext;
    private LayoutInflater mInflater;

    public ContactAdapter(@NonNull Context context) {
        super(context, 0);
        mContext = context;
        mContactList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public void setContacts(List<Contact> list) {
        mContactList.clear();
        mContactList.addAll(list);
    }

    @Nullable
    @Override
    public Contact getItem(int position) {
        return mContactList.get(position);
    }

    @Override
    public int getCount() {
        return mContactList.size();
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

        Contact item = getItem(position);

        viewHolder.textViewName.setText(item.getName());
        viewHolder.textViewEmail.setText(item.getEmail());

        Picasso.with(mContext)
                .load(item.getProfilePic())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.imageView);
        return viewHolder.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewName;
        public final TextView textViewEmail;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewEmail) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewEmail = textViewEmail;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewEmail = (TextView) rootView.findViewById(R.id.textViewEmail);
            return new ViewHolder(rootView, imageView, textViewName, textViewEmail);
        }
    }
}
