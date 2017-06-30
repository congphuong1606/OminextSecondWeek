package picdesignbeauty.android.vn.picdesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import picdesignbeauty.android.vn.picdesign.Fragment.HomeFragment;
import picdesignbeauty.android.vn.picdesign.Fragment.ProfileFragment;
import picdesignbeauty.android.vn.picdesign.Fragment.StatusFragment;


public class MainActivity extends AppCompatActivity {


    BottomNavigationView navigationbar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationbar = (BottomNavigationView) findViewById(R.id.navigationbar);

        navigationbar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        android.support.v4.app.Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.profile_item:
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                            case R.id.edit_item:
                                selectedFragment = StatusFragment.newInstance();
                                break;
                            case R.id.home_item:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.capture_item:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.friend_item:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fl_fragment, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment, HomeFragment.newInstance());
        transaction.commit();
    }


}
