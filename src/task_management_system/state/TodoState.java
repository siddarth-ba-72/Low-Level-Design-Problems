package task_management_system.state;

import task_management_system.models.Task;
import task_management_system.status.TaskStatus;

public class TodoState implements TaskState {
    @Override
    public void startProgress(Task task) {
        task.setState(new InProgressState());
    }

    @Override
    public void completeTask(Task task) {
        System.out.println("Cannot complete a task that is not in progress.");
    }

    @Override
    public void blockTask(Task task) {
        task.setState(new BlockedState());
    }

    @Override
    public void reopenTask(Task task) {
        System.out.println("Task is already in TO-DO state.");
    }

    @Override
    public TaskStatus getStatus() {
        return TaskStatus.TODO;
    }
}
