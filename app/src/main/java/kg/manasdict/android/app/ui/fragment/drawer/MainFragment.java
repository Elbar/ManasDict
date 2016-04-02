package kg.manasdict.android.app.ui.fragment.drawer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.mikepenz.iconics.view.IconicsCompatButton;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import kg.manasdict.android.R;
import kg.manasdict.android.app.db.HelperFactory;
import kg.manasdict.android.app.db.dao.WordDetailsDao;
import kg.manasdict.android.app.db.model.WordDetails;

/**
 * Created by root on 3/31/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener, TextWatcher{

    private AppCompatSpinner mSourceLangSpinner;
    private AppCompatSpinner mDestinationLangSpinner;
    private IconicsCompatButton mExchangeLangBtn;
    private int mLastSourceLangItemId = 0;
    private int mLastDestinationLangItemId = 1;
    private EditText mSearchWord;
    private Timer mTimer;
    private final long TIMER_DELAY = 1000;
    private WordDetailsDao mWordDetailsDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        try {
            initFragmentElements(rootView);
        } catch (SQLException e) {
            Log.e(MainFragment.class.getName(), e.getMessage());
        }
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length() != 0) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            }, TIMER_DELAY);
        }
    }

    protected void initFragmentElements(View rootView) throws SQLException{

        mSourceLangSpinner = (AppCompatSpinner) rootView.findViewById(R.id.sourceLangSpinner);
        mDestinationLangSpinner = (AppCompatSpinner) rootView.findViewById(R.id.destinationLangSpinner);
        mExchangeLangBtn = (IconicsCompatButton) rootView.findViewById(R.id.exchangeLangBtn);
        mSearchWord = (EditText) rootView.findViewById(R.id.searchText);
        mTimer = new Timer();
        mWordDetailsDao = HelperFactory.getHelper().getWordDetailsDao();
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
        mSearchWord.addTextChangedListener(this);

        mSourceLangSpinner.setSelection(0);
        mDestinationLangSpinner.setSelection(1);
    }

    protected void exchangeSpinnersItems() {
        mSourceLangSpinner.setSelection(mLastDestinationLangItemId);
        mDestinationLangSpinner.setSelection(mLastSourceLangItemId);
    }
}

