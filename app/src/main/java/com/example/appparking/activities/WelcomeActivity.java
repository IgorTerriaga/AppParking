package com.example.appparking.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.appparking.Fragments.MaisOcupadasFragment;
import com.example.appparking.Fragments.MeuCarroFragment;
import com.example.appparking.Fragments.SearchFragment;
import com.example.appparking.R;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeActivity extends AppCompatActivity {

    //MapFragment mapFragment;
    CircleImageView profile;
    private Button logout;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    FloatingActionButton change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);
        //getSupportActionBar().setElevation(0);
        profile = findViewById(R.id.profile_image);
        change = findViewById(R.id.ChangePhoto);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(WelcomeActivity.this)

                        .start(10);
            }
        });

        logout = findViewById(R.id.buttonLogout);
        //mapFragment = new MapFragment();
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Procurar Vaga", SearchFragment.class)
                        //.add("Vaga mais Ocupadas", MaisOcupadasFragment.class)
                        .add("Meu Veiculo", MeuCarroFragment.class)
                        //.add("Localização", MapsFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        logout = findViewById(R.id.buttonLogout);
        logout.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        profile.setImageURI(uri);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
