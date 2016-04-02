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

/**
 * Created by root on 3/31/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener, TextWatcher{

    private AppCompatSpinner mSourceLangSpinner;
    private AppCompatSpinner mDestinationLangSpinner;
    private IconicsCompatButton mExchangeLangBtn;
    private int mLastSourceLangItemPosition = 0;
    private int mLastDestinationLangItemPosition = 1;
    private EditText mSearchWord;
    private Timer mTimer;
    private final long TIMER_DELAY = 1000;
    private WordDetailsDao mWordDetailsDao;
    private CardView mTranslatedTextCV;
    private TextView mTranslatedText;

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
                                translateText(s.toString());
                                mTranslatedTextCV.setVisibility(View.VISIBLE);
                            } catch (SQLException e) {
                                Log.d(MainFragment.class.getName(), e.getMessage());
                            }
                        }
                    });
                }
            }, TIMER_DELAY);
        } else {
            mTranslatedText.setText("");
        }

    }

    private void translateText(String s) throws SQLException {
        WordDetails wordDetails = null;

        s = s.replaceAll("\\s+$", "");
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
                    mTranslatedText.setText(wordDetails.getKgWord());
                    break;
                case 1:
                    mTranslatedText.setText(wordDetails.getTrWord());
                    break;
                case 2:
                    mTranslatedText.setText(wordDetails.getRuWord());
                    break;
                case 3:
                    mTranslatedText.setText(wordDetails.getEnWord());
                    break;

            }

        }


        mTranslatedTextCV.setVisibility(View.VISIBLE);
    }

    protected void initFragmentElements(View rootView) throws SQLException{

        mSourceLangSpinner = (AppCompatSpinner) rootView.findViewById(R.id.sourceLangSpinner);
        mDestinationLangSpinner = (AppCompatSpinner) rootView.findViewById(R.id.destinationLangSpinner);
        mExchangeLangBtn = (IconicsCompatButton) rootView.findViewById(R.id.exchangeLangBtn);
        mSearchWord = (EditText) rootView.findViewById(R.id.searchText);
        mTimer = new Timer();
        mWordDetailsDao = HelperFactory.getHelper().getWordDetailsDao();
        mTranslatedTextCV = (CardView) rootView.findViewById(R.id.translatedTextCV);
        mTranslatedText = (TextView) rootView.findViewById(R.id.translatedText);
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
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

                mLastSourceLangItemPosition = position;
                mLastDestinationLangItemPosition = mDestinationLangSpinner.getSelectedItemPosition();
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

                mLastSourceLangItemPosition = mSourceLangSpinner.getSelectedItemPosition();
                mLastDestinationLangItemPosition = position;

                try {
                    translateText(mSearchWord.getText().toString());
                } catch (SQLException e) {
                    Log.d(MainFragment.class.getName(), e.getMessage());
                }
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
        mSourceLangSpinner.setSelection(mLastDestinationLangItemPosition);
        mDestinationLangSpinner.setSelection(mLastSourceLangItemPosition);
    }
}

