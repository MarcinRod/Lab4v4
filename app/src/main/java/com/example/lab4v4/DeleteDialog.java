package com.example.lab4v4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteDialog extends DialogFragment {

    public DeleteDialog(OnDeleteDialogInteractionListener listener) {
        mListener = listener;
    }

    public interface OnDeleteDialogInteractionListener{
        void onDialogPositiveClick();
        void onDialogNegativeClick();
    }
    private OnDeleteDialogInteractionListener mListener;
    public DeleteDialog() {
        // Required empty public constructor
    }


    public static DeleteDialog newInstance(OnDeleteDialogInteractionListener listener) {
        DeleteDialog fragment = new DeleteDialog(listener);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
         builder.setMessage(getString(R.string.delete_question));
         builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 if(mListener!= null) mListener.onDialogPositiveClick();
             }
         });

         builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 if(mListener!= null) mListener.onDialogNegativeClick();
             }
         });
        return builder.create();
    }
}