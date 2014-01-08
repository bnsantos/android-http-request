package com.br.bnsantos.login.example.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.br.bnsantos.login.example.R;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/8/14
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortPickerDialog extends DialogFragment {
    private NumberPicker portPicker;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface PortPickerDialogListener {
        public void onPortPickerDialogPositiveClick(PortPickerDialog dialog);
    }

    // Use this instance of the interface to deliver action events
    PortPickerDialogListener mListener;

    private String server;
    private EditText editTextServer;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        // Instantiate the NoticeDialogListener so we can send events to the host
        try {
            mListener = (PortPickerDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement AddServerDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_port_picker, null);

        portPicker = (NumberPicker) view.findViewById(R.id.dialogPortPickerNumberPicker);
        portPicker.setMaxValue(65535);
        portPicker.setMinValue(1);

        builder.setView(view)
                .setTitle(getString(R.string.pick_port))
                .setPositiveButton(R.string.add_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onPortPickerDialogPositiveClick(PortPickerDialog.this);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });


        // Create the AlertDialog object and return it
        return builder.create();
    }

    public int getPort(){
        return portPicker.getValue();
    }
}
