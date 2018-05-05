package com.example.asadaboomtham.agcapplication.Activity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asadaboomtham.agcapplication.R;

/**
 * Created by asada boomtham on 26/4/2561.
 */

public class SliderAdapter extends PagerAdapter{
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slideImages = {
            R.drawable.ico,
            R.drawable.icon_weather,
            R.drawable.icon_code
    };

    public String[] slideHeadings = {
            "News",
            "Weather",
            "Sharing"
    };

    public String[] slideDescriptions = {
            "News Update Realtime.",
            "Weather.",
            "Share Your News With Facebook ."
    };


    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (ConstraintLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.iv_image_icon);
        TextView slideHeading = (TextView) view.findViewById(R.id.tv_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.tv_description);

        slideImageView.setImageResource(slideImages[position]);
        slideHeading.setText(slideHeadings[position]);
        slideDescription.setText(slideDescriptions[position]);

        container.addView(view);

        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);  //todo: RelativeLayout??
    }
}


