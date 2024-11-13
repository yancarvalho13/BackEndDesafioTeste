package com.yan.TrilhaBackEndNov.repository.taskRepository;

import com.yan.TrilhaBackEndNov.model.task.Task;
import com.yan.TrilhaBackEndNov.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByUser(User user);
    Optional<Task> findByIdAndUserId(Long taskId, Long userId);
}
