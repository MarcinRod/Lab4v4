package com.example.lab4v4;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab4v4.dummy.TasksContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TaskFragment extends Fragment implements MyTaskRecyclerViewAdapter.InputEventsListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    MyTaskRecyclerViewAdapter myTaskRecyclerViewAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskFragment newInstance(int columnCount) {
        TaskFragment fragment = new TaskFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        Bundle arguments = getArguments();
        if(arguments!= null){
            if(arguments.containsKey(getString(R.string.taskTitleKey))){
                TasksContent.addItem(new TasksContent.TaskItem(String.valueOf(TasksContent.ITEMS.size()+1),
                        arguments.getString(getString(R.string.taskTitleKey)),
                        arguments.getString(getString(R.string.taskDescriptionKey))));
            }
        }

        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.list);
        if(recyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            if(mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            myTaskRecyclerViewAdapter = new MyTaskRecyclerViewAdapter(TasksContent.ITEMS);
            recyclerView.setAdapter(myTaskRecyclerViewAdapter);
            myTaskRecyclerViewAdapter.setInputEventListener(this);
        view.findViewById(R.id.addFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TaskFragment.this)
                        .navigate(R.id.action_taskFragment_to_addTaskFragment);
            }
        });
        }
        return view;

    }
    public void notifyDataChange(){
        myTaskRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickEvent(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.taskPositionKey),position);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            NavHostFragment.findNavController(TaskFragment.this)
                    .navigate(R.id.action_taskFragment_to_displayTaskFragment, bundle);
        }else{
            FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
            List<Fragment> fragments = supportFragmentManager.getFragments();
            DisplayTaskFragment fragmentById = (DisplayTaskFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.displayFragment);
            fragmentById.displayTask(position);
        }
    }
}