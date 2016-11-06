package edu.kvcc.cis298.cis298assignment3.repositories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import edu.kvcc.cis298.cis298assignment3.R;
import edu.kvcc.cis298.cis298assignment3.models.Beverage;

/**
 * Created by doc on 11/6/16.
 */

public class BeverageRepository extends AbstractBeverageRepository {

    @SuppressLint("StaticFieldLeak")
    private static BeverageRepository mBeverageRepository;

    private Map<String, Beverage> mBeverageMap;

    public static BeverageRepository getInstance(Context context) {
        if (mBeverageRepository == null) {
            return new BeverageRepository(context);
        } else {
            return mBeverageRepository;
        }
    }

    private BeverageRepository(Context context) {
        super(context);
        mBeverageMap = new HashMap<>();
        reloadBeverages();
    }

    public void reloadBeverages() {
        Resources resources = getContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.beverage_list);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] tokens = line.split(",");
                    Beverage beverage = Beverage.fromTokens(tokens);
                    if (beverage != null) {
                        put(beverage.getItemNumber(), beverage);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Beverage get(String id) {
        return mBeverageMap.get(id);
    }

    @Override
    public Map<String, Beverage> getAll() {
        return mBeverageMap;
    }

    @Override
    public void put(String id, Beverage beverage) {
        mBeverageMap.put(id, beverage);
    }

    @Override
    public void putAll(Map<String, Beverage> map) {
        mBeverageMap.putAll(map);
    }

    @Override
    public void delete(String id) {
        mBeverageMap.remove(id);
    }

    @Override
    public int size() {
        return mBeverageMap.size();
    }
}
