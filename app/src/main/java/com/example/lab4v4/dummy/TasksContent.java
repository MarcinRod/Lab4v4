package com.example.lab4v4.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TasksContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<TaskItem> ITEMS = new ArrayList<TaskItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, TaskItem> ITEM_MAP = new HashMap<String, TaskItem>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for(int i = 1; i <= COUNT; i++) {
            addItem(createTaskItem(i));
        }
    }

    public static void addItem(TaskItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    public static TaskItem getItem(int position){
        return ITEMS.get(position);
    }
    private static TaskItem createTaskItem(int position) {
        return new TaskItem(String.valueOf(position), "Task " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for(int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class TaskItem {
        public final String id;
        public final String title;
        public final String description;

        public TaskItem(String id, String title, String description) {
            this.id = id;
            this.title = title;
            this.description = description;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}