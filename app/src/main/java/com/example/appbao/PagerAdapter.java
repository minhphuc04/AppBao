package com.example.appbao;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    int tabcount;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount= behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return new HomeActivity();
            case 1:
                return new SportsActivity();
            case 2:
                return new HealthyActivity();
            case 3:
                return new ScienceActivity();
            case 4:
                return new EntertainmentActivity();
            case 5:
                return new TechnologyActivity();
            default:
                return  null;

        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
