package kg.manasdict.android.app.ui.fragment.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import kg.manasdict.android.R;

/**
 * Created by fukuro on 4/3/16.
 */

public class NewWordDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_new_word, null);


        builder.setView(rootView);
        builder.setTitle("Добавить слово");
        builder.setPositiveButton("Да", null);
        builder.setNegativeButton("Нет", null);

        AlertDialog dialog = builder.create();

        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return dialog;
    }
}
