package edu.kvcc.cis298.cis298assignment3.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.kvcc.cis298.cis298assignment3.R;
import edu.kvcc.cis298.cis298assignment3.WinePagerActivity;
import edu.kvcc.cis298.cis298assignment3.models.Beverage;
import edu.kvcc.cis298.cis298assignment3.repositories.AbstractBeverageRepository;
import edu.kvcc.cis298.cis298assignment3.repositories.BeverageRepository;

/**
 * Created by doc on 11/1/16.
 */

public class BeverageListFragment extends Fragment {

    private RecyclerView mBeverageRecyclerView;
    private BeverageAdapter mBeverageAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beverage_list, container, false);

        mBeverageRecyclerView = (RecyclerView) view.findViewById(R.id.beverage_recycler_view);

        mBeverageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        if (mBeverageAdapter == null) {
            AbstractBeverageRepository repository = BeverageRepository.getInstance(getActivity());
            Log.d("List", repository.getAll().size() > 0 ? "> 0" : "== 0");

            mBeverageAdapter = new BeverageAdapter(new ArrayList<>(repository.getAll().values()));
            mBeverageRecyclerView.setAdapter(mBeverageAdapter);
        } else {
            mBeverageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        updateUI();
        super.onResume();
    }

    private class BeverageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Beverage mBeverage;
        private TextView mTitleTextView;
        private TextView mItemNumberTextView;
        private TextView mPriceTextView;

        public BeverageHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.beverage_item_title);
            mItemNumberTextView = (TextView) itemView.findViewById(R.id.beverage_item_number);
            mPriceTextView = (TextView) itemView.findViewById(R.id.beverage_item_price);
        }

        public void bindBeverage(Beverage beverage) {
            mBeverage = beverage;
            mTitleTextView.setText(mBeverage.getItemDescription());
            mItemNumberTextView.setText(String.valueOf(mBeverage.getItemNumber()));
            mPriceTextView.setText("$" + String.valueOf(mBeverage.getCasePrice()));
        }

        @Override
        public void onClick(View v) {
            Log.i("BeverageListFragment", "onClick()");
            Intent intent = WinePagerActivity.newIntent(getActivity(), mBeverage.getItemNumber());
            startActivity(intent);
        }
    }

    private class BeverageAdapter extends RecyclerView.Adapter<BeverageHolder> {
        private List<Beverage> mBeverages;

        public BeverageAdapter(List<Beverage> beverages) {
            mBeverages = beverages;
        }

        @Override
        public BeverageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_beverage, parent, false);

            return new BeverageHolder(view);
        }

        @Override
        public void onBindViewHolder(BeverageHolder holder, int position) {
            Beverage beverage = mBeverages.get(position);

            holder.bindBeverage(beverage);
        }

        @Override
        public int getItemCount() {
            return mBeverages.size();
        }
    }
}
