package com.example.lab4v4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lab4v4.dummy.TasksContent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment {



    public AddTaskFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddTaskFragment newInstance() {
        AddTaskFragment fragment = new AddTaskFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NavHostFragment.findNavController(AddTaskFragment.this).popBackStack();
                NavHostFragment.findNavController(AddTaskFragment.this)
                        .navigate(R.id.action_addTaskFragment_to_taskFragment);
            }
        });
        view.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String title = ((EditText) view.findViewById(R.id.task_title)).getText().toString();
                String description = ((EditText) view.findViewById(R.id.task_description)).getText().toString();
                String drawable = ((Spinner)view.findViewById(R.id.choseDrawable)).getSelectedItem().toString();
                if(title.isEmpty())
                    title = "Task";
                if(description.isEmpty())
                    description = "My task description";
                bundle.putString(getString(R.string.taskTitleKey),
                        title);
                bundle.putString(getString(R.string.taskDescriptionKey),
                        description);
                bundle.putString(getString(R.string.taskDrawablKey),drawable);
                TasksContent.addItem(new TasksContent.TaskItem(String.valueOf(TasksContent.ITEMS.size()+1),
                        title,
                        description,
                        drawable));
                NavHostFragment.findNavController(AddTaskFragment.this)
                        .navigate(R.id.action_addTaskFragment_to_taskFragment);
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
        return view;
    }
}