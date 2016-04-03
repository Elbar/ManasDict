package kg.manasdict.android.app.ui.fragment.drawer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.manasdict.android.R;
import kg.manasdict.android.app.ui.fragment.dialog.NewWordDialogFragment;

/**
 * Created by root on 4/3/16.
 */
public class DictionaryFragment extends Fragment  implements View.OnClickListener {

    private FloatingActionButton mAddNewWordBtn;
    private NewWordDialogFragment mNewWordDialog = new NewWordDialogFragment();

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

    protected void initFragmentElements(View rootView) {
        mAddNewWordBtn = (FloatingActionButton) rootView.findViewById(R.id.addNewWordBtn);
        mAddNewWordBtn.setOnClickListener(this);
    }

}
