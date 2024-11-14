package com.yan.TrilhaBackEndNov.controller;

import com.yan.TrilhaBackEndNov.model.task.Task;
import com.yan.TrilhaBackEndNov.model.user.User;
import com.yan.TrilhaBackEndNov.repository.taskRepository.TaskRepository;
import com.yan.TrilhaBackEndNov.repository.userRepository.UserRepository;
import com.yan.TrilhaBackEndNov.service.taskService.TaskServiceImpl;
import com.yan.TrilhaBackEndNov.service.userService.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final UserServiceImpl userService;

    private final TaskServiceImpl taskService;

    private final TaskRepository taskRepository;


    public TaskController(UserServiceImpl userService, TaskServiceImpl taskService, TaskRepository taskRepository) {
        this.userService = userService;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @PostMapping("/add/{userId}")
    public String addTask(@PathVariable Long userId, @RequestBody Task task){
        try {
            User user = userService.findUserOrThrow(userId);
            userService.validateDuplicateTask(user, task);
            return userService.saveTask(user,task);
        } catch (Exception e) {
            return "Erro ao criar tarefa: " + e.getMessage();

        }
    }

    @GetMapping("/list/{userID}")
    public List<Task> getAllTasks(@PathVariable Long userID){
        return taskService.findAllByUser(userID);
    }

    @DeleteMapping("/delete/{userId}/{taskId}")
    public String deleteTask(@PathVariable Long userId, @PathVariable Long taskId){
        User user = userService.findUserOrThrow(userId);
        Optional<Task> task = taskRepository.findByIdAndUserId(taskId, userId);
        if(task.isEmpty()){
            return "Tarefa não existe para o usuário especificado";
        }
        taskRepository.deleteById(taskId);
        return "Tarefa deletada";
        }
    }

