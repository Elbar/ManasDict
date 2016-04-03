package kg.manasdict.android.app.ui.fragment.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.manasdict.android.R;
import kg.manasdict.android.app.ui.fragment.dialog.NewWordDialogFragment;

/**
 * Created by root on 4/3/16.
 */
public class DictionaryFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private FloatingActionButton mAddNewWordBtn;
    private NewWordDialogFragment mNewWordDialog = new NewWordDialogFragment();
    private CardView mSearchWord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dictionary, container, false);
        initFragmentElements(rootView);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNewWordBtn:
                mNewWordDialog.show(getChildFragmentManager(), "NEW_WORD_DIALOG");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(final Editable s) {

    }

    protected void initFragmentElements(View rootView) {
        mAddNewWordBtn = (FloatingActionButton) rootView.findViewById(R.id.addNewWordBtn);
        mAddNewWordBtn.setOnClickListener(this);
        mSearchWord = (CardView) rootView.findViewById(R.id.searchWord);
    }
}
