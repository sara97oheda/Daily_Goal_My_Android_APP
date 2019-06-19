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
 * Created by sara on 24/02/2019.
 */

public class SetPasswordDialog extends AppCompatDialogFragment {
    private EditText setPassword;
    private EditText resetPassword;
    private SetPasswordDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.set_pass_dialog, null);

        builder.setView(view)
                .setTitle(R.string.SetPassword)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String pass1 = setPassword.getText().toString();
                        String pass2 = resetPassword.getText().toString();
                        if (pass1.equals(pass2))
                        listener.applyPassword(pass1);
                    }
                });

        setPassword = view.findViewById(R.id.set_password);
        resetPassword = view.findViewById(R.id.reset_password);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (SetPasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement SetPasswordDialogListener");
        }
    }

    public interface SetPasswordDialogListener {
        String applyPassword(String password);
    }

}
