package kg.manasdict.android.app.ui.fragment.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsCompatButton;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import kg.manasdict.android.R;
import kg.manasdict.android.app.db.HelperFactory;
import kg.manasdict.android.app.db.dao.WordDetailsDao;
import kg.manasdict.android.app.db.model.WordDetails;
import kg.manasdict.android.lib.util.SystemHelper;

/**
 * Created by root on 3/31/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener, TextWatcher, AdapterView.OnItemSelectedListener {

    private AppCompatSpinner mSourceLangSpinner;
    private AppCompatSpinner mDestinationLangSpinner;
    private IconicsCompatButton mExchangeLangBtn;
    private int mLastSourceLangItemPosition = 0;
    private int mLastDestinationLangItemPosition = 1;
    private EditText mSourceWordToTranslateET;
    private Timer mTimer;
    private final long TIMER_DELAY = 1000;
    private WordDetailsDao mWordDetailsDao;
    private CardView mTranslatedWordCV;
    private TextView mTranslatedWordTV;
    private String mWordNotFound;

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
    public void afterTextChanged(final Editable s) {
        if (s.length() != 0) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                translateWord(s.toString());
                            } catch (SQLException e) {
                                Log.d(MainFragment.class.getName(), e.getMessage());
                            }
                        }
                    });
                }
            }, TIMER_DELAY);
        } else {
            mTranslatedWordTV.setText("");
            mTranslatedWordCV.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (mSourceLangSpinner.getSelectedItemPosition() == mDestinationLangSpinner.getSelectedItemPosition()) {
            exchangeSpinnersItems();
        }

        mLastSourceLangItemPosition = mSourceLangSpinner.getSelectedItemPosition();
        mLastDestinationLangItemPosition = mDestinationLangSpinner.getSelectedItemPosition();

        try {
            if (mSourceWordToTranslateET.getText().length() != 0) {
                translateWord(mSourceWordToTranslateET.getText().toString());
            }
        } catch (SQLException e) {
            Log.d(MainFragment.class.getName(), e.getMessage());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void translateWord(String s) throws SQLException {
        WordDetails wordDetails = null;

        s = s.replaceAll("\\s+$", "").toLowerCase();
        switch (mLastSourceLangItemPosition) {
            case 0:
                wordDetails = mWordDetailsDao.findByKgWord(s);
                break;
            case 1:
                wordDetails = mWordDetailsDao.findByTrWord(s);
                break;
            case 2:
                wordDetails = mWordDetailsDao.findByRuWord(s);
                break;
            case 3:
                wordDetails = mWordDetailsDao.findByEnWord(s);
                break;
        }

        if (wordDetails != null) {
            switch (mLastDestinationLangItemPosition) {
                case 0:
                    mTranslatedWordTV.setText(wordDetails.getKgWord());
                    break;
                case 1:
                    mTranslatedWordTV.setText(wordDetails.getTrWord());
                    break;
                case 2:
                    mTranslatedWordTV.setText(wordDetails.getRuWord());
                    break;
                case 3:
                    mTranslatedWordTV.setText(wordDetails.getEnWord());
                    break;
            }
        } else {
            mTranslatedWordTV.setText(mWordNotFound);
        }
        mTranslatedWordCV.setVisibility(View.VISIBLE);
    }

    protected void initFragmentElements(View rootView) throws SQLException{
        mSourceLangSpinner = (AppCompatSpinner) rootView.findViewById(R.id.sourceLangSpinner);
        mDestinationLangSpinner = (AppCompatSpinner) rootView.findViewById(R.id.destinationLangSpinner);
        mExchangeLangBtn = (IconicsCompatButton) rootView.findViewById(R.id.exchangeLangBtn);
        mSourceWordToTranslateET = (EditText) rootView.findViewById(R.id.sourceWordToTranslateET);
        mTimer = new Timer();
        mWordDetailsDao = HelperFactory.getHelper().getWordDetailsDao();
        mTranslatedWordCV = (CardView) rootView.findViewById(R.id.translatedWordCV);
        mTranslatedWordTV = (TextView) rootView.findViewById(R.id.translatedWordTV);
        mWordNotFound = getResources().getString(R.string.info_wordNotFound);

        mSourceWordToTranslateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    SystemHelper.hideKeyboard(getActivity(), v);
                }

            }
        });

        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.spinner_languages,
                android.R.layout.simple_spinner_dropdown_item);
        mSourceLangSpinner.setAdapter(spinnerAdapter);
        mDestinationLangSpinner.setAdapter(spinnerAdapter);
        mSourceLangSpinner.setOnItemSelectedListener(this);
        mDestinationLangSpinner.setOnItemSelectedListener(this);

        mExchangeLangBtn.setOnClickListener(this);
        mSourceWordToTranslateET.addTextChangedListener(this);

        mSourceLangSpinner.setSelection(0);
        mDestinationLangSpinner.setSelection(1);
    }

    protected void exchangeSpinnersItems() {
        mSourceLangSpinner.setSelection(mLastDestinationLangItemPosition);
        mDestinationLangSpinner.setSelection(mLastSourceLangItemPosition);

        if(mTranslatedWordTV.getText() != mWordNotFound) {
            mSourceWordToTranslateET.setText(mTranslatedWordTV.getText());
        }
    }
}

