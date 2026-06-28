package task_management_system.state;

import task_management_system.models.Task;
import task_management_system.status.TaskStatus;

public class BlockedState implements TaskState {
    @Override
    public void startProgress(Task task) {
        task.setState(new InProgressState());
    }

    @Override
    public void completeTask(Task task) {
        System.out.println("Cannot complete a blocked task. Unblock it first.");
    }

    @Override
    public void blockTask(Task task) {
        System.out.println("Task is already blocked.");
    }

    @Override
    public void reopenTask(Task task) {
        task.setState(new TodoState());
    }

    @Override
    public TaskStatus getStatus() {
        return TaskStatus.BLOCKED;
    }
}
