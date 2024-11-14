package com.yan.TrilhaBackEndNov.service.userService;

import com.yan.TrilhaBackEndNov.model.task.Task;
import com.yan.TrilhaBackEndNov.model.user.User;

import java.util.Optional;

public interface UserService {

    User getUser(Long userid);
    User findUserOrThrow(Long userId);
    void validateDuplicateTask(User user, Task task);
    boolean hasSameDescription(Task task1, Task task2);
    String saveTask(User user, Task task);
}
