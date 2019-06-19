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
 * Created by sara on 27/02/2019.
 */

public class UnsetPasswordDailog  extends AppCompatDialogFragment {

    private unSetPasswordDialogListener listener;
    private EditText password;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.unset_pass_dailog, null);

        builder.setView(view)
                .setTitle(R.string.RemovePassword)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String enterdPass = password.getText().toString();
                            listener.unSetPassword(null, enterdPass);
                    }
                });

                password = view.findViewById(R.id.edit_txt_unsetpass);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (unSetPasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement SetPasswordDialogListener");
        }
    }

    public interface unSetPasswordDialogListener {
        String unSetPassword(String unsePassword, String ennterPass);
    }

}
