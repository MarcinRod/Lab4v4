package com.example.lab4v4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab4v4.dummy.ContactsContent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayContactFragment extends Fragment {
    TextView contactName;
    TextView contactPhoneNumber;
    TextView contactRingtone;
    ImageView taskImage;
    private View fragmentView;

    public DisplayContactFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DisplayContactFragment newInstance() {
        DisplayContactFragment fragment = new DisplayContactFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_display_contact, container, false);
     //   getActivity().invalidateOptionsMenu();
        contactName = fragmentView.findViewById(R.id.dispContactName);
        contactPhoneNumber = fragmentView.findViewById(R.id.dispPhoneNumber);
        contactRingtone = fragmentView.findViewById(R.id.dispRingtone);
        taskImage = fragmentView.findViewById(R.id.contactImageView);
        fragmentView.setVisibility(View.INVISIBLE);
        Bundle arguments = getArguments();
        if(arguments!= null){
            if(arguments.containsKey(getString(R.string.taskPositionKey))){
                int position = arguments.getInt(getString(R.string.taskPositionKey));
                displayTask(position);
            }
        }
        return fragmentView;
    }
    public void hideDisplay(){

    }
    public void displayTask(int position){
        fragmentView.setVisibility(View.VISIBLE);
        ContactsContent.ContactItem contact = ContactsContent.getItem(position);
        contactName.setText(contact.toString());
        contactPhoneNumber.setText(contact.phoneNumber);
        contactRingtone.setText(contact.ringtone);
        String picPath = contact.picPath;
        Context context = getContext();
        if(picPath != null && !picPath.isEmpty() ){
            if(!picPath.contains("JPEG")) {
                Drawable taskDrawable;
                switch(picPath) {
                    case "avatar 1":
                        taskDrawable = context.getDrawable(R.drawable.avatar_1);
                        break;
                    case "avatar 2":
                        taskDrawable = context.getDrawable(R.drawable.avatar_2);
                        break;
                    case "avatar 3":
                        taskDrawable = context.getDrawable(R.drawable.avatar_3);
                        break;
                    case "avatar 4":
                        taskDrawable = context.getDrawable(R.drawable.avatar_4);
                        break;
                    case "avatar 5":
                    default:
                        taskDrawable = context.getDrawable(R.drawable.avatar_5);
                }
                taskImage.setImageDrawable(taskDrawable);
            }else{
                Bitmap bitmap = PicUtils.decodePic(picPath, 400, 400);
                taskImage.setImageBitmap(bitmap);
            }
        }
        else
        {
            taskImage.setImageDrawable(context.getDrawable(R.drawable.avatar_5));
        }

    }
}