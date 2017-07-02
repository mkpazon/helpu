package com.mamaai.angelhack2017.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.mamaai.angelhack2017.R;

/**
 * Created by mkpazon on 02/07/2017.
 */

public class InfoDialogFragment extends DialogFragment {
    private static final String ARGUMENT_TITLE = "title";
    private static final String ARGUMENT_MESSAGE = "message";
    private DialogInterface.OnClickListener onClickListener;

    public InfoDialogFragment() {
    }

    public static InfoDialogFragment newInstance(final String title, final String message) {
        final InfoDialogFragment infoDialog = new InfoDialogFragment();
        final Bundle args = new Bundle();
        args.putString(ARGUMENT_TITLE, title);
        args.putString(ARGUMENT_MESSAGE, message);
        infoDialog.setArguments(args);
        return infoDialog;
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle arguments = getArguments();
        final String title = arguments.getString(ARGUMENT_TITLE);
        final String message = arguments.getString(ARGUMENT_MESSAGE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (InfoDialogFragment.this.onClickListener != null) {
                    InfoDialogFragment.this.onClickListener.onClick(getDialog(), which);
                }
            }
        };
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, onClickListener);

        return builder.create();
    }
}
