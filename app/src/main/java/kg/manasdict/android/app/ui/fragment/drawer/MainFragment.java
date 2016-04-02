package kg.manasdict.android.app.ui.fragment.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mikepenz.iconics.view.IconicsCompatButton;

import kg.manasdict.android.R;

/**
 * Created by root on 3/31/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private AppCompatSpinner mSourceLangSpinner;
    private AppCompatSpinner mDestinationLangSpinner;
    private IconicsCompatButton mExchangeLangBtn;
    private int mLastSourceLangItemId = 0;
    private int mLastDestinationLangItemId = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initFragmentElements(rootView);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exchangeLangBtn:
                exchangeSpinnersItems();
                break;
        }
    }

    protected void initFragmentElements(View rootView) {
        mSourceLangSpinner = (AppCompatSpinner) rootView.findViewById(R.id.sourceLangSpinner);
        mDestinationLangSpinner = (AppCompatSpinner) rootView.findViewById(R.id.destinationLangSpinner);
        mExchangeLangBtn = (IconicsCompatButton) rootView.findViewById(R.id.exchangeLangBtn);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                                                            getActivity(),
                                                            R.array.spinner_languages,
                                                            android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSourceLangSpinner.setAdapter(spinnerAdapter);
        mDestinationLangSpinner.setAdapter(spinnerAdapter);
        mSourceLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == mDestinationLangSpinner.getSelectedItemPosition()) {
                    exchangeSpinnersItems();
                }

                mLastSourceLangItemId = position;
                mLastDestinationLangItemId = mDestinationLangSpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mDestinationLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == mSourceLangSpinner.getSelectedItemPosition()) {
                    exchangeSpinnersItems();
                }

                mLastSourceLangItemId = mSourceLangSpinner.getSelectedItemPosition();
                mLastDestinationLangItemId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mExchangeLangBtn.setOnClickListener(this);

        mSourceLangSpinner.setSelection(0);
        mDestinationLangSpinner.setSelection(1);
    }

    protected void exchangeSpinnersItems() {
        mSourceLangSpinner.setSelection(mLastDestinationLangItemId);
        mDestinationLangSpinner.setSelection(mLastSourceLangItemId);
    }
}
