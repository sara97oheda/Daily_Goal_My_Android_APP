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
import android.widget.RadioButton;
import com.example.todolistapp.R;

public class categoryDailog extends AppCompatDialogFragment {
    private EditText editTextCategory;
    private ExampleDialogListener listener;
    private RadioButton color1;
    private RadioButton color2;
    private RadioButton color3;
    private int colrNumber;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_category_dailog, null);

        builder.setView(view)
                .setTitle(R.string.CrateCategoryTitle)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = editTextCategory.getText().toString();
                        if (color1.isChecked()){
                           colrNumber = 1;
                        }
                        if (color2.isChecked()){
                            colrNumber = 2;
                        }
                        if (color3.isChecked()){
                            colrNumber = 3;
                        }
                        listener.applyTexts(title, colrNumber);
                    }
                });

        editTextCategory = view.findViewById(R.id.edit_categore);
        color1 = view.findViewById(R.id.first_color);
        color2 = view.findViewById(R.id.seconde_color);
        color3 = view.findViewById(R.id.third_color);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement SetPasswordDialogListener");
        }
    }

    public interface ExampleDialogListener {
        String applyTexts(String title, int color);
    }
}