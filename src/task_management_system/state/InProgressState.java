package task_management_system.state;

import task_management_system.models.Task;
import task_management_system.status.TaskStatus;

public class InProgressState implements TaskState {
    @Override
    public void startProgress(Task task) {
        System.out.println("Task is already in progress.");
    }

    @Override
    public void completeTask(Task task) {
        if (!task.canComplete()) {
            System.out.println("Cannot complete: subtasks are not all done.");
            return;
        }
        task.setState(new DoneState());
    }

    @Override
    public void blockTask(Task task) {
        task.setState(new BlockedState());
    }

    @Override
    public void reopenTask(Task task) {
        task.setState(new TodoState());
    }

    @Override
    public TaskStatus getStatus() {
        return TaskStatus.IN_PROGRESS;
    }
}
