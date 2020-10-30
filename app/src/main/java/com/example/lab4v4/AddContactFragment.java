package com.example.lab4v4;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.lab4v4.dummy.ContactsContent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddContactFragment extends Fragment {



    public AddContactFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddContactFragment newInstance() {
        AddContactFragment fragment = new AddContactFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        //getActivity().invalidateOptionsMenu();
        view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NavHostFragment.findNavController(AddTaskFragment.this).popBackStack();
                NavHostFragment.findNavController(AddContactFragment.this)
                        .navigate(R.id.action_addTaskFragment_to_taskFragment);
                hideKeyboard(v);
            }
        });
        view.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String name = ((EditText) view.findViewById(R.id.contact_name)).getText().toString();
                String surname = ((EditText) view.findViewById(R.id.contact_surname)).getText().toString();
                String drawable = ((Spinner)view.findViewById(R.id.choseDrawable)).getSelectedItem().toString();
                String ringtone = ((Spinner)view.findViewById(R.id.choseRingtone)).getSelectedItem().toString();
                String phoneNumber = ((EditText)view.findViewById(R.id.editTextPhone)).getText().toString();
                if(name.isEmpty())
                    name = "John";
                if(surname.isEmpty())
                    surname = "Doe";
                if(phoneNumber.isEmpty())
                    phoneNumber = "123456789";
                if(drawable.equals("photo")){
                    drawable = currentPhotoPath;
                }
                ContactsContent.addItem(new ContactsContent.ContactItem(String.valueOf(ContactsContent.ITEMS.size()+1),
                        name,
                        surname,
                        drawable,ringtone,phoneNumber));
                NavOptions.Builder builder = new NavOptions.Builder();
                NavHostFragment.findNavController(AddContactFragment.this)
                        .navigate(R.id.action_addTaskFragment_to_taskFragment);
                hideKeyboard(v);
               /* AddTaskFragmentDirections.ActionAddTaskFragmentToTaskFragment addTaskAction = AddTaskFragmentDirections.actionAddTaskFragmentToTaskFragment();
                addTaskAction.setValidTaskArg(true);
                addTaskAction.setTitleArg(title);
                addTaskAction.setTaskDescriptionArg(description);
                NavHostFragment.findNavController(AddTaskFragment.this)
                        .navigate(addTaskAction);
                //TaskFragmentDirections
           //     AddTaskFragmentDirections*/
            }
        });
        view.findViewById(R.id.photoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();
            }
        });
        preview = view.findViewById(R.id.previewView);
        Spinner spinner = (Spinner) view.findViewById(R.id.choseDrawable);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
                Object itemAtPosition = adapterView.getItemAtPosition(i);
                if(itemAtPosition instanceof String)
                {
                    String avatar = (String)itemAtPosition;
                    if(avatar.equals("photo")){
                        ((Button) view.findViewById(R.id.photoButton)).setEnabled(true);
                    } else{
                        switch(avatar){
                            case "avatar 1":preview.setImageDrawable(getContext().getDrawable(R.drawable.avatar_1));break;
                            case "avatar 2":preview.setImageDrawable(getContext().getDrawable(R.drawable.avatar_2));break;
                            case "avatar 3":preview.setImageDrawable(getContext().getDrawable(R.drawable.avatar_3));break;
                            case "avatar 4":preview.setImageDrawable(getContext().getDrawable(R.drawable.avatar_4));break;
                            case "avatar 5":
                            default:
                                preview.setImageDrawable(getContext().getDrawable(R.drawable.avatar_5));break;
                        }
                        ((Button) view.findViewById(R.id.photoButton)).setEnabled(false);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
    ImageView preview;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.lab4v4.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
          /*  Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");*/
            Bitmap bitmap = PicUtils.decodePic(currentPhotoPath, preview.getWidth(), preview.getHeight());
            preview.setImageBitmap(bitmap);
        }
    }
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);

    }
}