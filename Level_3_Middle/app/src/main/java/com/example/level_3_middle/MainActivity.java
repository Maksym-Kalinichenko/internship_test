package com.example.level_3_middle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.level_3_middle.classes.ViewPagerAdapter;
import com.example.level_3_middle.fragments.FavoritesFragment;
import com.example.level_3_middle.fragments.PhotosFragment;

public class MainActivity extends AppCompatActivity {
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.add(new PhotosFragment(), "Photos");
        viewPagerAdapter.add(new FavoritesFragment(), "Favorites");

        viewPager.setAdapter(viewPagerAdapter);
    }
}