package edu.kvcc.cis298.cis298assignment3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import edu.kvcc.cis298.cis298assignment3.fragments.BeverageFragment;
import edu.kvcc.cis298.cis298assignment3.models.Beverage;
import edu.kvcc.cis298.cis298assignment3.repositories.AbstractBeverageRepository;
import edu.kvcc.cis298.cis298assignment3.repositories.BeverageRepository;

/**
 * Created by doc on 10/26/16.
 */

public class WinePagerActivity extends FragmentActivity {

    private static final String EXTRA_ITEM_NUMBER = "edu.kvcc.cis298.cis298assignment3.item_number";

    private ViewPager mViewPager;
    private AbstractBeverageRepository mBeverageRepository;
    private List<Beverage> mBeverages;

    public static Intent newIntent(Context packageContext, String itemNumber) {
        Intent intent = new Intent(packageContext, WinePagerActivity.class);
        intent.putExtra(EXTRA_ITEM_NUMBER, itemNumber);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_wine_pager);

        mBeverageRepository = BeverageRepository.getInstance(this);

        // Add the values contained in the BeverageRepository to a list to make it easier for the
        // ViewPager to operate
        mBeverages = new ArrayList<>(mBeverageRepository.getAll().values());
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Beverage beverage = mBeverages.get(position);
                return BeverageFragment.newInstance(beverage.getItemNumber());
            }

            @Override
            public int getCount() {
                return mBeverages.size();
            }
        });

        String itemNumber = getIntent().getStringExtra(EXTRA_ITEM_NUMBER);
        Beverage beverage = mBeverageRepository.get(itemNumber);
        mViewPager.setCurrentItem(mBeverages.indexOf(beverage));
    }
}
