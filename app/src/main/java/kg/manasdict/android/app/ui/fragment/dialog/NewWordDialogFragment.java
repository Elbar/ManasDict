package kg.manasdict.android.app.ui.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import kg.manasdict.android.R;
import kg.manasdict.android.app.db.HelperFactory;
import kg.manasdict.android.app.db.dao.WordDetailsDao;
import kg.manasdict.android.app.db.model.WordDetails;

/**
 * Created by fukuro on 4/3/16.
 */

public class NewWordDialogFragment extends DialogFragment implements TextWatcher {

    private AlertDialog mDialog;
    private WordDetailsDao mWordDetailsDao;
    private EditText mKgWordET;
    private EditText mRuWordET;
    private EditText mEnWordET;
    private EditText mTrWordET;

    public NewWordDialogFragment() {
        try {
            mWordDetailsDao = HelperFactory.getHelper().getWordDetailsDao();
        } catch (SQLException e) {
            Log.e(NewWordDialogFragment.class.getName(), e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_new_word, null);

        initFragmentElements(rootView);

        builder.setView(rootView);
        builder.setTitle("Добавить слово");
        builder.setPositiveButton("Да", null);
        builder.setNegativeButton("Нет", null);

        AlertDialog dialog = builder.create();

        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveWord(formatWord(mKgWordET.getText().toString()), formatWord(mRuWordET.getText().toString()),
                             formatWord(mEnWordET.getText().toString()), formatWord(mTrWordET.getText().toString()));
                } catch (SQLException e) {
                    Log.e(NewWordDialogFragment.class.getName(), e.getMessage());
                }
                dismiss();
                Toast.makeText(getActivity(), "Слово успешно сохранено", Toast.LENGTH_LONG).show();
            }
        });
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);

        return (mDialog = dialog);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if ((mKgWordET.getText().length() > 0) && (mEnWordET.getText().length() > 0)
            && (mTrWordET.getText().length() > 0) && (mRuWordET.getText().length() > 0)) {
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
        } else {
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    protected void initFragmentElements(View rootView) {
        mKgWordET = (EditText) rootView.findViewById(R.id.kgWordET);
        mEnWordET = (EditText) rootView.findViewById(R.id.enWordET);
        mTrWordET = (EditText) rootView.findViewById(R.id.trWordET);
        mRuWordET = (EditText) rootView.findViewById(R.id.ruWordET);

        mKgWordET.addTextChangedListener(this);
        mEnWordET.addTextChangedListener(this);
        mTrWordET.addTextChangedListener(this);
        mRuWordET.addTextChangedListener(this);
    }

    protected void saveWord(String s1, String s2, String s3, String s4) throws SQLException {
        mWordDetailsDao.create(new WordDetails(s1, s2, s3, s4));
    }

    protected String formatWord(String s) {
        return s.toLowerCase().replaceAll("\\s+$", "");
    }
}
