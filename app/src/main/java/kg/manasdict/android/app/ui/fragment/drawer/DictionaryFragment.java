package kg.manasdict.android.app.ui.fragment.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;

import kg.manasdict.android.R;
import kg.manasdict.android.app.db.HelperFactory;
import kg.manasdict.android.app.db.dao.WordDetailsDao;
import kg.manasdict.android.app.ui.fragment.dialog.NewWordDialogFragment;
import kg.manasdict.android.app.ui.recyclerview.DictionaryRVAdapter;

/**
 * Created by root on 4/3/16.
 */
public class DictionaryFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private FloatingActionButton mAddNewWordBtn;
    private NewWordDialogFragment mNewWordDialog;
    private CardView mSearchWordCV;
    private RecyclerView mRecyclerView;
    private WordDetailsDao mWordDetailsDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dictionary, container, false);
        try {
            initFragmentElements(rootView);
        } catch (SQLException e) {
            Log.e(DictionaryFragment.class.getName(), e.getMessage());
        }
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

    protected void initFragmentElements(View rootView) throws SQLException{
        mWordDetailsDao = HelperFactory.getHelper().getWordDetailsDao();
        mAddNewWordBtn = (FloatingActionButton) rootView.findViewById(R.id.addNewWordBtn);
        mAddNewWordBtn.setOnClickListener(this);
        mSearchWordCV = (CardView) rootView.findViewById(R.id.searchWord);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.dictionaryRV);
        mNewWordDialog = new NewWordDialogFragment();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new DictionaryRVAdapter(mWordDetailsDao.findAll(), getActivity(), getChildFragmentManager()));

    }

}
