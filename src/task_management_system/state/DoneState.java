package task_management_system.state;

import task_management_system.models.Task;
import task_management_system.status.TaskStatus;

public class DoneState implements TaskState {
    @Override
    public void startProgress(Task task) {
        System.out.println("Cannot start a completed task. Reopen it first.");
    }

    @Override
    public void completeTask(Task task) {
        System.out.println("Task is already done.");
    }

    @Override
    public void blockTask(Task task) {
        System.out.println("Cannot block a completed task.");
    }

    @Override
    public void reopenTask(Task task) {
        task.setState(new TodoState());
    }

    @Override
    public TaskStatus getStatus() {
        return TaskStatus.DONE;
    }
}
