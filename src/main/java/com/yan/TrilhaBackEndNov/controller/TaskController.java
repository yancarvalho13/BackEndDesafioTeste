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

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;


    public TaskController(UserServiceImpl userService, TaskServiceImpl taskService, UserRepository userRepository, TaskRepository taskRepository) {
        this.userService = userService;
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @PostMapping("/add/{userId}")
    public String addTask(@PathVariable Long userId, @RequestBody Task task){
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                return "Usuário não encontrado";
            }
            List<Task> tasks = taskRepository.findAllByUser(user.get());
            for (Task t : tasks) {
                if (task.getDescription().equals(t.getDescription())) {
                    return "Já existe uma tarefa com esse nome";
                }
            }

            task.setUser(user.orElse(null));
            taskRepository.save(task);
            return "Tarefa salva";

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
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return "Usuário nao foi encontrado";
        }
        Optional<Task> task = taskRepository.findByIdAndUserId(taskId, userId);
        if(task.isEmpty()){
            return "Tarefa não existe para o usuário especificado";
        }

        taskRepository.deleteById(taskId);
        return "Tarefa deletada";
        }
    }

