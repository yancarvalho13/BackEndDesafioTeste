package com.yan.TrilhaBackEndNov.service.taskService;

import com.yan.TrilhaBackEndNov.model.Task;
import com.yan.TrilhaBackEndNov.model.user.User;
import com.yan.TrilhaBackEndNov.repository.taskRepository.TaskRepository;
import com.yan.TrilhaBackEndNov.repository.userRepository.UserRepository;
import com.yan.TrilhaBackEndNov.service.userService.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{


    private final TaskRepository taskRepository;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserServiceImpl userServiceImpl, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userServiceImpl = userServiceImpl;
        this.userRepository = userRepository;
    }


    @Override
    public void addTask(Long userId,Task task) {
        User user = userServiceImpl.getUser(userId);
        task.setUser(user);
        taskRepository.save(task);

    }


    @Override
    public List<Task> findAllByUser(Long id) {
       return taskRepository.findAllByUser(userServiceImpl.getUser(id));
    }

    @Override
    public Task findById(Long id) {
        Task task;
        Optional<Task> optional = taskRepository.findById(id);
        if (optional.isPresent()){
            task = optional.get();
        }else {
            throw new RuntimeException("Task not found by id" + id);
        }

        return task;
    }

    @Override
    public void deleteTask(Long userId,Long taskId) {

    }


}
