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

public class EditWordDialogFragment extends DialogFragment implements TextWatcher {

    private AlertDialog mDialog;
    private WordDetailsDao mWordDetailsDao;
    private WordDetails mWordDetails;
    private EditText mKgWord;
    private EditText mRuWord;
    private EditText mEnWord;
    private EditText mTrWord;

    public EditWordDialogFragment() {
        try {
            mWordDetailsDao = HelperFactory.getHelper().getWordDetailsDao();
        } catch (SQLException e) {
            Log.e(EditWordDialogFragment.class.getName(), e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_edit_word, null);

        initFragmentElements(rootView);

        builder.setView(rootView);
        builder.setTitle("Редактировать слово");
        builder.setPositiveButton("Да", null);
        builder.setNegativeButton("Нет", null);

        AlertDialog dialog = builder.create();

        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editWord(formatWord(mKgWord.getText().toString()), formatWord(mRuWord.getText().toString()),
                            formatWord(mEnWord.getText().toString()), formatWord(mTrWord.getText().toString()));
                } catch (SQLException e) {
                    Log.e(EditWordDialogFragment.class.getName(), e.getMessage());
                }
                dismiss();
                Toast.makeText(getActivity(), "Слово успешно обновлено", Toast.LENGTH_LONG).show();
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
        if ((mKgWord.getText().length() > 0) && (mEnWord.getText().length() > 0)
                && (mTrWord.getText().length() > 0) && (mRuWord.getText().length() > 0)) {
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
        } else {
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    public void setWordDetails(WordDetails wordDetails) {
        mWordDetails = wordDetails;
    }

    protected void setWords() {
        mKgWord.setText(mWordDetails.getKgWord());
        mRuWord.setText(mWordDetails.getRuWord());
        mEnWord.setText(mWordDetails.getEnWord());
        mTrWord.setText(mWordDetails.getTrWord());
    }

    protected void initFragmentElements(View rootView) {
        mKgWord = (EditText) rootView.findViewById(R.id.kgWordET);
        mEnWord = (EditText) rootView.findViewById(R.id.enWordET);
        mTrWord = (EditText) rootView.findViewById(R.id.trWordET);
        mRuWord = (EditText) rootView.findViewById(R.id.ruWordET);

        setWords();

        mKgWord.addTextChangedListener(this);
        mEnWord.addTextChangedListener(this);
        mTrWord.addTextChangedListener(this);
        mRuWord.addTextChangedListener(this);
    }

    protected void editWord(String s1, String s2, String s3, String s4) throws SQLException {
        mWordDetails.setKgWord(s1);
        mWordDetails.setRuWord(s2);
        mWordDetails.setEnWord(s3);
        mWordDetails.setTrWord(s4);

        mWordDetailsDao.createOrUpdate(mWordDetails);
    }

    protected String formatWord(String s) {
        return s.toLowerCase().replaceAll("\\s+$", "");
    }
}
