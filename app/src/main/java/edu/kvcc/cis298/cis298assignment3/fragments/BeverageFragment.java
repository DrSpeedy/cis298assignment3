package edu.kvcc.cis298.cis298assignment3.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import edu.kvcc.cis298.cis298assignment3.R;
import edu.kvcc.cis298.cis298assignment3.models.Beverage;
import edu.kvcc.cis298.cis298assignment3.repositories.AbstractBeverageRepository;
import edu.kvcc.cis298.cis298assignment3.repositories.BeverageRepository;

/**
 * Created by doc on 10/24/16.
 */

public class BeverageFragment extends Fragment {

    private static final String ARG_ITEM_NUMBER = "item_number";

    private AbstractBeverageRepository mBeverageRepository;

    private Beverage mBeverage;
    private EditText mBeverageId;
    private EditText mBeverageTitle;
    private EditText mEditPack;
    private EditText mEditPrice;
    private CheckBox mCheckBoxActive;

    public static BeverageFragment newInstance(String itemNumber) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_NUMBER, itemNumber);

        BeverageFragment fragment = new BeverageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the singleton instance of the beverage repository
        mBeverageRepository = BeverageRepository.getInstance(getActivity());
        // Get the item number we use for an index from the fragment arguments
        String itemNumber = getArguments().getString(ARG_ITEM_NUMBER);

        // Get the respective beverage from the repository
        mBeverage = mBeverageRepository.get(itemNumber);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_beverage, container, false);

        mBeverageTitle = (EditText) view.findViewById(R.id.fragment_beverage_edit_name);
        mBeverageId = (EditText) view.findViewById(R.id.fragment_beverage_edit_id);
        mEditPack = (EditText) view.findViewById(R.id.fragment_beverage_edit_pack);
        mEditPrice = (EditText) view.findViewById(R.id.fragment_beverage_edit_price);
        mCheckBoxActive = (CheckBox) view.findViewById(R.id.fragment_beverage_checkbox_active);

        mBeverageTitle.addTextChangedListener(new NameChangedListener());
        mEditPack.addTextChangedListener(new PackChangedListener());
        mEditPrice.addTextChangedListener(new PriceChangedListener());
        mCheckBoxActive.setOnCheckedChangeListener(new ActiveBeverageListener());

        mBeverageTitle.setText(mBeverage.getItemDescription());
        mBeverageId.setText(mBeverage.getItemNumber());
        mEditPack.setText(mBeverage.getPackSize());
        mEditPrice.setText(String.valueOf(mBeverage.getCasePrice()));
        mCheckBoxActive.setChecked(mBeverage.isActive());

        return view;
    }

    private class NameChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing...
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mBeverage.setItemDescription(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class PriceChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing...
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mBeverage.setCasePrice(Double.parseDouble(s.toString()));
        }

        @Override
        public void afterTextChanged(Editable s) {
            // do nothing...
        }
    }

    private class PackChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing...
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mBeverage.setPackSize(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // do nothing...
        }
    }

    private class ActiveBeverageListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mBeverage.setActive(isChecked);
        }
    }
}
