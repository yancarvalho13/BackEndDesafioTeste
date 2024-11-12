package com.yan.TrilhaBackEndNov.service.taskService;

import java.util.List;
import com.yan.TrilhaBackEndNov.model.Task;

public interface TaskService {

   void addTask(Long userId,Task task);
   List<Task> findAllByUser(Long id);
   Task findById(Long id);
   void deleteTask(Long userId, Long taskId);


}
