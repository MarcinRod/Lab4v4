package com.example.lab4v4;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab4v4.dummy.ContactsContent;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/**
 * A fragment representing a list of Items.
 */
public class ContactFragment extends Fragment implements MyContactRecyclerViewAdapter.InputEventsListener,
        DeleteDialog.OnDeleteDialogInteractionListener,
        CallDialog.OnCallDialogInteractionListener,
        MainActivity.ActivityTestInterface {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    MyContactRecyclerViewAdapter myContactRecyclerViewAdapter;
    private int currentItemPosition = -1;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactFragment() {
    }

    @Override
    public void testEvent() {
        notifyDataChange();
    }



    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ContactFragment newInstance(int columnCount) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
        //    mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        FragmentActivity activity = getActivity();
        if(activity instanceof MainActivity){
            ((MainActivity)activity).setmListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        //getActivity().invalidateOptionsMenu();
        Bundle arguments = getArguments();
        /*if(arguments!= null){
            if(arguments.containsKey(getString(R.string.taskTitleKey))){
                TasksContent.addItem(new TasksContent.TaskItem(String.valueOf(TasksContent.ITEMS.size()+1),
                        arguments.getString(getString(R.string.taskTitleKey)),
                        arguments.getString(getString(R.string.taskDescriptionKey)),
                        arguments.getString(getString(R.string.taskDrawablKey))));
            }
        }*/

        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.list);
        if(recyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            if(mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            myContactRecyclerViewAdapter = new MyContactRecyclerViewAdapter(ContactsContent.ITEMS);
            recyclerView.setAdapter(myContactRecyclerViewAdapter);
            myContactRecyclerViewAdapter.setInputEventListener(this);
        view.findViewById(R.id.addFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ContactFragment.this)
                        .navigate(R.id.action_taskFragment_to_addTaskFragment);
            }
        });
        }

        return view;

    }

    public void notifyDataChange(){
        myContactRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickEvent(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.taskPositionKey),position);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            NavHostFragment.findNavController(ContactFragment.this)
                    .navigate(R.id.action_taskFragment_to_displayTaskFragment, bundle);
        }else{
            FragmentManager childFragmentManager = getChildFragmentManager();

            DisplayContactFragment fragmentById = (DisplayContactFragment) childFragmentManager.findFragmentById(R.id.displayFragment);
            fragmentById.displayTask(position);
        }
    }

    @Override
    public void onLongClickEvent(int position) {
        currentItemPosition  = position;
        showCallDialog();
    }

    @Override
    public void onBinClickEvent(int position) {
        showDeleteDialog();
        currentItemPosition  = position;
    }

    public void showDeleteDialog(){
        DeleteDialog.newInstance(this).show(getParentFragmentManager(),"deleteDialogTag");
    }
    public void showCallDialog(){
        CallDialog.newInstance(this,currentItemPosition).show(getParentFragmentManager(),"callDialogTag");
    }

    @Override
    public void onDialogPositiveClick() {
        if(currentItemPosition != -1 && currentItemPosition < ContactsContent.ITEMS.size()){
            ContactsContent.removeItem(currentItemPosition);
            notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick() {
        Snackbar.make(getActivity().findViewById(R.id.mainFragment),"Cancel delete?", BaseTransientBottomBar.LENGTH_LONG).setAction("retry?", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog();
            }
        }).show();
    }

    @Override
    public void onCallPositiveClick() {
        if(currentItemPosition != -1 && currentItemPosition < ContactsContent.ITEMS.size()) {
            Intent intent = new Intent(getContext(), CallActivity.class);
            intent.putExtra("ContactPosition", currentItemPosition);
            startActivity(intent);
        }
    }

    @Override
    public void onCallNegativeClick() {
        Snackbar.make(getActivity().findViewById(R.id.mainFragment),"Cancel call?", BaseTransientBottomBar.LENGTH_LONG).setAction("retry?", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCallDialog();
            }
        }).show();
    }
}