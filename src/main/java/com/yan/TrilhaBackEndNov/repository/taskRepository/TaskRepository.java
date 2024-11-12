package com.yan.TrilhaBackEndNov.repository.taskRepository;

import com.yan.TrilhaBackEndNov.model.Task;
import com.yan.TrilhaBackEndNov.model.user.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TaskRepository extends ListCrudRepository<Task,Long> {
    List<Task> findAllByUser(User user);
}
