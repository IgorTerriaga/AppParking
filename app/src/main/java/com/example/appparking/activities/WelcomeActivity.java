package com.example.appparking.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appparking.Fragments.LojasFragment;
import com.example.appparking.Fragments.MaisOcupadasFragment;
import com.example.appparking.Fragments.MeuCarroFragment;
import com.example.appparking.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class WelcomeActivity extends AppCompatActivity {
    private Button logout;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private TextView text;
    private TextView textLojas, textCarros, textVagasMais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);
        getSupportActionBar().setElevation(0);
        logout = findViewById(R.id.buttonLogout);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Lojas", LojasFragment.class)
                        .add("Vaga mais Ocupadas", MaisOcupadasFragment.class)
                        .add("Meu Veiculo", MeuCarroFragment.class)
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
}