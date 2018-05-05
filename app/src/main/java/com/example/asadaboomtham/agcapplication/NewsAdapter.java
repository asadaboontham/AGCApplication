package com.example.asadaboomtham.agcapplication;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ravi on 16/11/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<NewsItem> contactList;
    private List<NewsItem> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView news_des, news_head, news_ref;
        public ImageView news_pics;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            news_head = view.findViewById(R.id.news_head);
            news_des = view.findViewById(R.id.news_des);
            news_ref = view.findViewById(R.id.news_ref);
            news_pics = view.findViewById(R.id.news_pics);
            linearLayout = itemView.findViewById(R.id.linearLayout);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public NewsAdapter(Context context, List<NewsItem> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NewsItem contact = contactListFiltered.get(position);
        holder.news_head.setText(contact.getNews_head());
        holder.news_des.setText(contact.getNews_des());
        holder.news_ref.setText(contact.getNews_ref());

        Glide.with(context)
                .load(contact.getNews_pics())
//                .apply(RequestOptions.circleCropTransform())
                .into(holder.news_pics);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getNews_link()));
//                context.startActivity(browserIntent);


                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder().setShowTitle(true);
                // For adding menu item
//                builder.addMenuItem("Share", getItem());

                // add share action to menu list
                builder.addDefaultShareMenuItem();
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "http://www.codepath.com");
                int requestCode = 100;

                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setActionButton(bitmap, "Share Link", pendingIntent, true);

                CustomTabsIntent customTabsIntent = builder.build();
                // Change the background color of the Toolbar.
                builder.setToolbarColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));

                //Open the Custom Tab
                customTabsIntent.launchUrl(context, Uri.parse(contactList.get(position).getNews_link()));
                // Hide status bar once the user scrolls down the content
                builder.enableUrlBarHiding();



            }

//            private PendingIntent getItem () {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Droidmentor is a site, which contains android tutorials");
//                Log.d(TAG, "setMenuItem: " + contactListFiltered);
//                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_TEXT, (ArrayList<? extends Parcelable>) contactListFiltered);
//                return PendingIntent.getActivity(context, 0, shareIntent, 0);
//            }
        });


    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<NewsItem> filteredList = new ArrayList<>();
                    for (NewsItem row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNews_head().toLowerCase().contains(charString.toLowerCase()) || row.getNews_des().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<NewsItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(NewsItem contact);
    }

    private PendingIntent getItem() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Droidmentor is a site, which contains android tutorials");
        Log.d(TAG, "setMenuItem: " + contactListFiltered);
        shareIntent.putExtra(Intent.EXTRA_TEXT, (Parcelable) contactListFiltered);
        return PendingIntent.getActivity(context, 0, shareIntent, 0);
    }
}
