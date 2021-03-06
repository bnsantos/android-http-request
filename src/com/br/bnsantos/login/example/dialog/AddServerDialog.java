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
import com.br.bnsantos.login.example.R;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddServerDialog extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface AddServerDialogListener {
        public void onAddServerDialogPositiveClick(AddServerDialog dialog);
    }

    // Use this instance of the interface to deliver action events
    AddServerDialogListener mListener;

    private String server;
    private EditText editTextServer;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
         // Instantiate the NoticeDialogListener so we can send events to the host
            try {
                mListener = (AddServerDialogListener) activity;
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

        View view = inflater.inflate(R.layout.dialog_add_server, null);
        editTextServer = (EditText)view.findViewById(R.id.dialogAddServerServer);

        builder.setView(view)
                .setTitle(R.string.add_server_dialog_title)
                .setPositiveButton(R.string.add_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        server = editTextServer.getText().toString();
                        mListener.onAddServerDialogPositiveClick(AddServerDialog.this);
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

    public String getServer() {
        return server;
    }
}
