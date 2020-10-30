package com.example.lab4v4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.lab4v4.dummy.ContactsContent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallDialog extends DialogFragment {

    public CallDialog(OnCallDialogInteractionListener listener, int pos) {
        mListener = listener; this.pos = pos;
    }
    int pos;
    public interface OnCallDialogInteractionListener {
        void onCallPositiveClick();
        void onCallNegativeClick();
    }
    private OnCallDialogInteractionListener mListener;
    public CallDialog() {
        // Required empty public constructor
    }


    public static CallDialog newInstance(OnCallDialogInteractionListener listener, int pos) {
        CallDialog fragment = new CallDialog(listener,pos);
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
         builder.setMessage("Call " + ContactsContent.ITEMS.get(pos).phoneNumber + "?");
         builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 if(mListener!= null) mListener.onCallPositiveClick();
             }
         });

         builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 if(mListener!= null) mListener.onCallNegativeClick();
             }
         });
        return builder.create();
    }
}