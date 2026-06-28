package task_management_system.state;

import task_management_system.models.Task;
import task_management_system.status.TaskStatus;

public interface TaskState {
    void startProgress(Task task);

    void completeTask(Task task);

    void blockTask(Task task);

    void reopenTask(Task task);

    TaskStatus getStatus();
}
