package com.example.myapplication1;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ViewPager viewPagerHome;
    private LinearLayout dotsHome;
    private ImageView[] dotsViewImage;
//    Initiate context
    private Context mContext;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewPagerHome = view.findViewById(R.id.viewPagerHome);
        this.dotsHome = view.findViewById(R.id.dotsHome);
        
        animateSlider();
    }


    private void animateSlider() {
        List<SliderAdapter.Slider> sliderAdapters = new ArrayList<>();

        sliderAdapters.add(new SliderAdapter.Slider(R.drawable.medan1,"WALK + \nEXPLORE",getString(R.string.hf1)));
        sliderAdapters.add(new SliderAdapter.Slider(R.drawable.medan2,"DISCOVER \nLOCATIONS",getString(R.string.hf2)));
        sliderAdapters.add(new SliderAdapter.Slider(R.drawable.medan3,"ADDITIONAL \nINFORMATION",getString(R.string.hf3)));
        final SliderAdapter sliderAdapter = new SliderAdapter(mContext,sliderAdapters);

        viewPagerHome.setAdapter(sliderAdapter);

        dotsViewImage = new ImageView[sliderAdapter.getCount()];

        for (int i=0;i<sliderAdapter.getCount();i++){
            dotsViewImage[i] = new ImageView(mContext);
            dotsViewImage[i].setImageDrawable(mContext.getDrawable(R.drawable.circle_slide_home));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(16,0,0,0);

            dotsHome.addView(dotsViewImage[i], params);
        }
        dotsViewImage[0].setImageDrawable(mContext.getDrawable(R.drawable.circle_slide));

        viewPagerHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i < sliderAdapter.getCount(); i++){
                    dotsViewImage[i].setImageDrawable(mContext.getDrawable(R.drawable.circle_slide_home));
                }
                dotsViewImage[position].setImageDrawable(mContext.getDrawable(R.drawable.circle_slide));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static class SliderAdapter extends PagerAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Slider> sliders;

        public SliderAdapter(Context contextSliderAdapter, List<Slider> slider){
            this.context = contextSliderAdapter;
            this.sliders = slider;
        }


        @Override
        public int getCount() {
            return sliders.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.item_home, container, false);

            Slider slider = sliders.get(position);

            ImageView imageHome = view.findViewById(R.id.imgHF);
            TextView titleHome = view.findViewById(R.id.txtHF1);
            TextView captionHome = view.findViewById(R.id.txtHF2);

            titleHome.setText(slider.getTitle());
            captionHome.setText(slider.getCaption());
            imageHome.setImageResource(slider.getImage());

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        public static class Slider {
            private int image;
            private String title;
            private String caption;

            public Slider(int image, String title, String caption) {
                this.image = image;
                this.title = title;
                this.caption = caption;
            }

            public int getImage() {
                return image;
            }

            public String getTitle() {
                return title;
            }

            public String getCaption() {
                return caption;
            }
        }
    }

}
