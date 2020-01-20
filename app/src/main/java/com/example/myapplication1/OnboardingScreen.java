package com.example.myapplication1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class OnboardingScreen extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout dots;
    private ImageView[] dotsView;
    private Button btnGetStarted;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);
//        Untuk transparan dengan latar belakang
        statusBarTranslucent(this);


        viewPager       = findViewById(R.id.viewPager);
        dots            = findViewById(R.id.dots);
        btnGetStarted   = findViewById(R.id.btnGetSt);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_animation);

//        Untuk handle jika sudah ada user yang login
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(OnboardingScreen.this, DashboardActivity.class));
            finish();
        }
        animateSlider();
    }

    //        Untuk transparan dengan latar belakang
    public static void statusBarTranslucent(Activity activity){
        Window window = activity.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void getStarted(View view) {
        if (viewPager.getAdapter().getCount() == viewPager.getCurrentItem() +1) {
            btnGetStarted.setVisibility(View.VISIBLE);
//            Add animation to button
            btnGetStarted.setAnimation(btnAnim);
            startActivity(new Intent(OnboardingScreen.this,MainActivity.class));
            finish();
        } else viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
    }

    //    Untuk animate slider
    private void animateSlider() {
        List<Adapter.Slide> slides = new ArrayList<>();

        slides.add(new Adapter.Slide(R.drawable.img1,"Let's Travelling", getString(R.string.lorem)));
        slides.add(new Adapter.Slide(R.drawable.img2, "Navigation", getString(R.string.lorem2)));
        slides.add(new Adapter.Slide(R.drawable.img3, "Destination", getString(R.string.lorem3)));
        final Adapter adapter = new Adapter(this, slides);
        viewPager.setAdapter(adapter);

        dotsView = new ImageView[adapter.getCount()];

        for(int i = 0; i < adapter.getCount(); i++){

            dotsView[i] = new ImageView(this);
            dotsView[i].setImageDrawable(getDrawable(R.drawable.circle_slide));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(16,0,0,0);

            dots.addView(dotsView[i], params);
        }

        dotsView[0].setImageDrawable(getDrawable(R.drawable.circle_slide_active));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i < adapter.getCount(); i++){
                    dotsView[i].setImageDrawable(getDrawable(R.drawable.circle_slide));
                }
                dotsView[position].setImageDrawable(getDrawable(R.drawable.circle_slide_active));
                if (viewPager.getAdapter().getCount() == viewPager.getCurrentItem() +1) {
                    btnGetStarted.setVisibility(View.VISIBLE);
//                    Add animation to button
                    btnGetStarted.setAnimation(btnAnim);
                    dots.setVisibility(View.INVISIBLE);
                } else {
                    btnGetStarted.setVisibility(View.INVISIBLE);
                    dots.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //    Adapter untuk slide
    public static class Adapter extends PagerAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Slide> slides;

        public Adapter(Context context, List<Slide> slides){
            this.context = context;
            this.slides = slides;
        }

        @Override
        public int getCount() {
            return slides.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.item_onboarding, container, false);

            Slide slide = slides.get(position);

            ImageView image = view.findViewById(R.id.imgOB);
            TextView title = view.findViewById(R.id.txtOB1);
            TextView caption = view.findViewById(R.id.txtOB2);

            title.setText(slide.getTitle());
            caption.setText(slide.getCaption());
            image.setImageResource(slide.getImage());

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        public static class Slide{
            private int image;
            private String title;
            private String caption;

            public Slide(int image, String title, String caption) {
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
