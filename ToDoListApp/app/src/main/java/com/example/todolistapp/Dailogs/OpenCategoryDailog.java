package com.example.todolistapp.Dailogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.todolistapp.R;

/**
 * Created by sara on 25/02/2019.
 */

public class OpenCategoryDailog extends AppCompatDialogFragment {
    private EditText editTextCategory;
    private OpenDailogListner listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.open_category_dailog, null);

        builder.setView(view)
                .setTitle(R.string.EnterPasswordTitle)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String password = editTextCategory.getText().toString();
                        listener.interCategory(password);
                    }
                });

        editTextCategory = view.findViewById(R.id.inter_password_txt);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OpenDailogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement SetPasswordDialogListener");
        }
    }

    public interface OpenDailogListner {
        String interCategory(String password);

    }
}

