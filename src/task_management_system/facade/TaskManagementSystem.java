package task_management_system.facade;

import task_management_system.data.TaskList;
import task_management_system.models.Task;
import task_management_system.models.User;
import task_management_system.sort.TaskSortStrategy;
import task_management_system.status.TaskPriority;
import task_management_system.status.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TaskManagementSystem {
    private final Map<String, User> users;
    private final Map<String, Task> tasks;
    private final Map<String, TaskList> taskLists;

    public TaskManagementSystem() {
        users = new ConcurrentHashMap<>();
        tasks = new ConcurrentHashMap<>();
        taskLists = new ConcurrentHashMap<>();
    }

    public User createUser(String name, String email) {
        User user = new User(name, email);
        users.put(user.getId(), user);
        return user;
    }

    public TaskList createTaskList(String listName) {
        TaskList taskList = new TaskList(listName);
        taskLists.put(taskList.getId(), taskList);
        return taskList;
    }

    public Task createTask(String title, String description, LocalDate dueDate,
                           TaskPriority priority, String createdByUserId) {
        User createdBy = users.get(createdByUserId);
        if (createdBy == null)
            throw new IllegalArgumentException("User not found.");

        Task task = new Task.TaskBuilder(title)
                .description(description)
                .dueDate(dueDate)
                .priority(priority)
                .createdBy(createdBy)
                .build();


        tasks.put(task.getId(), task);
        return task;
    }

    public List<Task> listTasksByUser(String userId) {
        User user = users.get(userId);
        if (user == null) {
            return new ArrayList<>();
        }
        return tasks.values().stream()
                .filter(task -> user.equals(task.getAssignee()))
                .sorted(Comparator.comparing(Task::getTitle))
                .toList();
    }

    public List<Task> listTasksByStatus(TaskStatus status) {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == status)
                .sorted(Comparator.comparing(Task::getTitle))
                .collect(Collectors.toList());
    }

    public void deleteTask(String taskId) {
        tasks.remove(taskId);
    }

    public List<Task> searchTasks(String keyword, TaskSortStrategy sortingStrategy) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            String kw = keyword.toLowerCase();
            if (task.getTitle().toLowerCase().contains(kw)
                    || task.getDescription().toLowerCase().contains(kw)) {
                matchingTasks.add(task);
            }
        }
        sortingStrategy.sort(matchingTasks);
        return matchingTasks;
    }
}
