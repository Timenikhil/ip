package ava.task;

import ava.files.FileManager;
import ava.task.tasks.Deadline;
import ava.task.tasks.Event;
import ava.task.tasks.Todo;

import java.util.List;

public class TaskManager {

    private final List<Task> tasks;
    private final FileManager fileManager;

    /**
     * Creates a new TaskManager
     */
    public TaskManager() {
        this.fileManager = new FileManager();
        this.tasks = fileManager.getTasks();
    }


    public List<Task> getTasks() {
        // in the future return a view of the list
        // rather than the list to avoid errors
        return tasks;
    }


    public List<Task> getTasks(String s) {
        // in the future return a view of the list
        // rather than the list to avoid errors
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addTask(String task) {
        tasks.add(new Todo(task));
    }

    public void addTask(String task,String time){
        tasks.add(new Deadline(task, time));
    }

    public void addTask(String task,String startTime,String endTime){
        tasks.add(new Event(task, startTime, endTime));
    }



    public void removeTask(int taskId) {
        if(taskId > 0 && taskId <= tasks.size()) {
            tasks.remove(taskId - 1);
        } else {
            throw new IllegalArgumentException("Trying to delete nonexistent task");
        }
    }
}
