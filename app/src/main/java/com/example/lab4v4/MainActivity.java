package com.example.lab4v4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lab4v4.dummy.ContactsContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private int mDestination;

    public interface ActivityTestInterface {
        public void testEvent();
    }
    ActivityTestInterface mListener;
    private Toolbar toolbar;
    private final String CONTACTS_SHARED_PREFS = "TasksSharedPrefs";
    private final String TASKS_TEXT_FILE = "tasks.txt";
    private final String TASKS_JSON_FILE = "tasks.json";
    private final String NUM_CONTACTS = "NumOfTasks";
    private final String CONTACT_NAME = "task_";
    private final String CONTACT_SURNAME = "surname_";
    private final String CONTACT_RINGTONE = "ring_";
    private final String CONTACT_PICPATH = "pic_";
    private final String CONTACT_ID = "id_";
    private final String CONTACT_PHONE = "phone_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  toolbar = findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        NavController navController = NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.mainFragment));
        navController.addOnDestinationChangedListener(this);
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        //  Toast.makeText(this,"navigation", Toast.LENGTH_LONG).show();
        mDestination = destination.getId();
        invalidateOptionsMenu();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if((mDestination == R.id.taskFragment || mDestination == 0)){
            if(menu.size() == 0)
                getMenuInflater().inflate(R.menu.menu_main,menu);
        }else{
            menu.clear();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.restore_json_menu:
                clearList();
                restoreFromJson();
                break;
            case R.id.restore_shared_menu:
                clearList();
                restoreFromSharedPrefs();
                break;
            case R.id.restore_text_menu:
                break;
            case R.id.store_json_menu:
                savetoJson();
                break;
            case R.id.store_shared_menu:
                saveTaskstoSharedPref();
                break;
            case R.id.store_text_menu:
                break;
            case R.id.clear_list:
                clearList();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        return super.onOptionsItemSelected(item);
    }
    private void saveTaskstoSharedPref(){
        SharedPreferences contacts = getSharedPreferences(CONTACTS_SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = contacts.edit();
        editor.clear();
        editor.putInt(NUM_CONTACTS, ContactsContent.ITEMS.size());
        for(int i = 0; i < ContactsContent.ITEMS.size();i++)
        {
            ContactsContent.ContactItem contactItem = ContactsContent.ITEMS.get(i);
            editor.putString(CONTACT_NAME + i,contactItem.name);
            editor.putString(CONTACT_SURNAME + i,contactItem.surname);
            editor.putString(CONTACT_PHONE + i,contactItem.phoneNumber);
            editor.putString(CONTACT_RINGTONE + i,contactItem.ringtone);
            editor.putString(CONTACT_PICPATH + i,contactItem.picPath);
            editor.putString(CONTACT_ID + i,contactItem.id);
        }
        editor.apply();
    }
    private void restoreFromSharedPrefs(){
        SharedPreferences contacts = getSharedPreferences(CONTACTS_SHARED_PREFS,MODE_PRIVATE);
        int numContacs = contacts.getInt(NUM_CONTACTS,0);
        if(numContacs != 0){

            for(int i = 0; i < numContacs; i++){
                ContactsContent.addItem(new ContactsContent.ContactItem(contacts.getString(CONTACT_ID + i,"0"),
                        contacts.getString(CONTACT_NAME + i,"JOE"),
                        contacts.getString(CONTACT_SURNAME + i,"DOE"),
                        contacts.getString(CONTACT_PICPATH + i,"avatar 1"),
                        contacts.getString(CONTACT_PHONE + i,"0"),
                        contacts.getString(CONTACT_RINGTONE + i,"0")
                        ));
            }
        }
    }
    private void savetoJson(){
        Gson gson = new Gson();
        String listJson = gson.toJson(ContactsContent.ITEMS);
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(TASKS_JSON_FILE,MODE_PRIVATE);
            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(listJson);
            writer.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private void restoreFromJson(){
        FileInputStream inputStream;
        int BUFFER_SIZE = 10000;
        Gson gson = new Gson();
        String readJson;
        try {
            inputStream = openFileInput(TASKS_JSON_FILE);
            FileReader reader = new FileReader(inputStream.getFD());
            char [] buf = new char[BUFFER_SIZE];
            int n;
            StringBuilder strBuilder = new StringBuilder();
            while((n = reader.read(buf)) >= 0){
                String tmp = String.valueOf(buf);
                String substring = (n < BUFFER_SIZE) ? tmp.substring(0,n):tmp;
                strBuilder.append(substring);
            }
            reader.close();
            readJson = strBuilder.toString();
            Type collectionType = new TypeToken<List<ContactsContent.ContactItem>>(){}.getType();
            List<ContactsContent.ContactItem> o = gson.fromJson(readJson,collectionType);
            if(o!= null){
                for(ContactsContent.ContactItem contact : o){
                    ContactsContent.addItem(contact);
                }
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void clearList(){
        ContactsContent.clearList();
        if(mListener != null)
            mListener.testEvent();


    }
    public void setmListener(ActivityTestInterface listener){
        mListener = listener;
    }

}