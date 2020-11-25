package com.team.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.team.myapplication.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarLayout appBarLayout;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        init();
        initToolbar();
        appBarlayoutHandeler();

    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Menu Item click listener
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.more:
                Toast.makeText(this, "More", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void appBarlayoutHandeler() {
        final Drawable upArrow = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_previous);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitleEnabled(false);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                double percentage = (double) Math.abs(verticalOffset) / appBarLayout.getHeight();
                Log.d("TAG", "onOffsetChanged: "+percentage);
                Drawable search,more;
                if (menu != null) {
                    search = menu.getItem(0).getIcon();
                    more = menu.getItem(1).getIcon();
                } else {
                    search = null;
                    more = null;
                }

                if(percentage< 0.75){
                    collapsingToolbarLayout.setTitleEnabled(false);
                    collapsingToolbarLayout.setTitle(" ");
                    binding.toolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.white_transparent)));
                    binding.toolbar.setTitle(" ");
                    upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                    getSupportActionBar().setHomeAsUpIndicator(upArrow);
                    if (search != null && more!= null) {
                        search.mutate();
                        search.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                        more.mutate();
                        more.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                    }
                }
                else {
                    collapsingToolbarLayout.setTitleEnabled(false);
                    binding.toolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.white_100)));
                    upArrow.setColorFilter(getResources().getColor(R.color.toolbar_collasped), PorterDuff.Mode.SRC_ATOP);
                    getSupportActionBar().setHomeAsUpIndicator(upArrow);
                    if (search != null && more!= null) {
                        search.mutate();
                        search.setColorFilter(getResources().getColor(R.color.toolbar_collasped), PorterDuff.Mode.SRC_ATOP);
                        more.mutate();
                        more.setColorFilter(getResources().getColor(R.color.toolbar_collasped), PorterDuff.Mode.SRC_ATOP);
                    }
                }
            }
        });
    }
}