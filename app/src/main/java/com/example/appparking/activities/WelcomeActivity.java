package com.example.appparking.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.appparking.Fragments.LojasFragment;
import com.example.appparking.Fragments.MaisOcupadasFragment;
import com.example.appparking.Fragments.MapsFragment;
import com.example.appparking.Fragments.MeuCarroFragment;
import com.example.appparking.R;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.maps.MapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeActivity extends AppCompatActivity {

    MapFragment mapFragment;
    CircleImageView profile;
    private ImageView search;
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
        //imageView = findViewById(R.id.CoverImage);
        //floatingActionButton = findViewById(R.id.floatingActionButton);
        change = findViewById(R.id.ChangePhoto);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(WelcomeActivity.this)

                        .start(10);
            }
        });

        ImageView image = (ImageView) findViewById(R.id.imageSearchVaga);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VacanceActivity.class);
                startActivity(intent);
            }
        });

        logout = findViewById(R.id.buttonLogout);
        mapFragment = new MapFragment();
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Lojas", LojasFragment.class)
                        .add("Vaga mais Ocupadas", MaisOcupadasFragment.class)
                        .add("Meu Veiculo", MeuCarroFragment.class)
                        .add("Localização", MapsFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        logout = findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
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
