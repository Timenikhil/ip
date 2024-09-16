package ava;

import ava.commands.Command;
import ava.commands.Parser;
import ava.task.Task;
import ava.task.TaskManager;

import java.io.PrintStream;
import java.util.List;

import static ava.commands.Parser.parseToDo;

public class AVA {

    /**
     * Current user input being processed by AVA.
     */
    private String currentInput;

    /**
     * TaskManager for AVA
     * <br>
     * initialized on ava's creation
     * <br><br>
     * <code>final</code> to avoid unnecessary modifications
     */
    private final TaskManager taskManager;

    /**
     * default constructor for AVA
     */
    public AVA() {
        taskManager = new TaskManager();
    }

    /**
     * Decides if AVA is running or not
     * //TODO: switch to a state based system like Operating System Threads
     * @return <span color="green">true</span> if AVA is running <span color="red">false</span> otherwise
     */
    public final boolean isRunning(){
        return !currentInput.equals("bye");
    }

    /**
     * Updates the currentInput with the user input
     * @param s the user input
     */
    public void tellAva(String s) {
        currentInput = s;
    }

    //todo:have a non printstream version
    /**
     * Prints AVA's response to given PrintStream
     * @param out PrintStream to print AVA's response to
     * //TODO:refactor mark and unmark to remove redundancy
     */
    public void respond(PrintStream out) {
        Command userInput = Parser.parseCommand(currentInput);
        switch (userInput) {
        case LIST: {
            List<Task> list = taskManager.getTasks();
            out.println("Here are your tasks:");
            out.println(list);
            break;
        }
        case MARK: {
            int taskId;
            try {
                taskId = Integer.parseInt(currentInput.substring(5));
            } catch (NumberFormatException e) {
                out.println("I am sorry, but you need to provide me a task id to mark or unmark something.");
                return;
            }
            Task task = taskManager.getTasks().get(taskId - 1);
            task.markDone();
            //TODO: use string format
            out.println("Alright I have marked this task as done");
            out.println(taskId + ". " + task);

            break;
        }
        case UNMARK: {
            int taskId;
            try {
                taskId = Integer.parseInt(currentInput.substring(5));
            } catch (NumberFormatException e) {
                out.println("I am sorry, but you need to provide me a task id to mark or unmark something.");
                return;
            }
            Task task = taskManager.getTasks().get(taskId - 1);
            task.markNotDone();
            //TODO: use string format
            out.println("Alright I have marked this task as not done");
            out.println(taskId + ". " + task);
            break;
        }
        case DELETE: {
            int taskId;
            try {
                taskId = Integer.parseInt(currentInput.substring(7));
            } catch (NumberFormatException e) {
                out.println("I am sorry, but you need to provide me a task id to delete something.");
                return;
            }
            Task task = taskManager.getTasks().get(taskId - 1);
            taskManager.removeTask(taskId);
            //TODO: use string format
            out.println("Alright I have deleted this task");
            out.println(taskId + ". " + task);
            break;
        } case TODO:{
            Parser.parseToDo(currentInput,taskManager);
            break;
        } case EVENT:{
            Parser.parseEvent(currentInput,taskManager);
            break;
        } case DEADLINE: {
            Parser.parseDeadline(currentInput,taskManager);
            break;
        } case FIND:{
            Parser.parseFind(currentInput);
            break;
        }
        default: {
            taskManager.addTask(currentInput);
            out.println("----------------------------------------------------------------");
            out.println("Added " + currentInput);
            out.println("----------------------------------------------------------------");
        }
        }
    }


    public void streamResponse(){
        //TODO: implement
        //stream a response to output
    }

    /**
     *  Runs AVA
     *
     *  <br>
     *  Main driver method running AVA
     */
    public static void main(String[] args) {
        TextUI.run();
    }
}

