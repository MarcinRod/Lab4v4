package com.example.lab4v4;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab4v4.dummy.TasksContent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayTaskFragment extends Fragment {
    TextView taskTitle;
    TextView taskDescription;
    ImageView taskImage;
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
        taskTitle = view.findViewById(R.id.dispTaskTitle);
        taskDescription = view.findViewById(R.id.dispTaskDescription);
        taskImage = view.findViewById(R.id.taskImageView);
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

        TasksContent.TaskItem task = TasksContent.getItem(position);
        taskTitle.setText(task.title);
        taskDescription.setText(task.description);
        String picPath = task.picPath;
        Context context = getContext();
        if(picPath != null && !picPath.isEmpty() ){
            Drawable taskDrawable;

            switch(picPath){
                case "Drawable 2":taskDrawable = context.getDrawable(R.drawable.circle_drawable_orange);break;
                case "Drawable 3":taskDrawable = context.getDrawable(R.drawable.circle_drawable_red);break;
                case "Drawable 1":
                default:
                    taskDrawable = context.getDrawable(R.drawable.circle_drawable_green);
            }
            taskImage.setImageDrawable(taskDrawable);
        }
        else
        {
            taskImage.setImageDrawable(context.getDrawable(R.drawable.circle_drawable_green));
        }

    }
}