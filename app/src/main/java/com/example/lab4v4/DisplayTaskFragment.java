package com.example.lab4v4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lab4v4.dummy.TasksContent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayTaskFragment extends Fragment {

    public DisplayTaskFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DisplayTaskFragment newInstance() {
        DisplayTaskFragment fragment = new DisplayTaskFragment();
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
        View view = inflater.inflate(R.layout.fragment_display_task, container, false);
        Bundle arguments = getArguments();
        if(arguments!= null){
            if(arguments.containsKey(getString(R.string.taskPositionKey))){
                int position = arguments.getInt(getString(R.string.taskPositionKey));
                displayTask(position);
            }
        }
        return view;
    }
    public void displayTask(int position){
        TextView taskTitle = getActivity().findViewById(R.id.dispTaskTitle);
        TextView taskDescription = getActivity().findViewById(R.id.dispTaskDescription);
        TasksContent.TaskItem task = TasksContent.getItem(position);
        taskTitle.setText(task.title);
        taskDescription.setText(task.description);
    }
}