package com.yan.TrilhaBackEndNov.service.userService;

import com.yan.TrilhaBackEndNov.model.task.Task;
import com.yan.TrilhaBackEndNov.model.user.User;
import com.yan.TrilhaBackEndNov.repository.taskRepository.TaskRepository;
import com.yan.TrilhaBackEndNov.repository.userRepository.UserRepository;
import com.yan.TrilhaBackEndNov.service.exception.DuplicateTaskException;
import com.yan.TrilhaBackEndNov.service.exception.UserNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }


    @Override
    public User getUser(Long userId) {
        User user;
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }else{
            throw new RuntimeException("User not found by id"+ userId);
        }
        return user;
    }

    @Override
    public User findUserOrThrow(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public void validateDuplicateTask(User user, Task task) throws DuplicateTaskException {
        boolean hasDuplicity = taskRepository.findAllByUser(user).stream()
                .anyMatch(existTask -> hasSameDescription(existTask, task));

        if(hasDuplicity){
            throw new DuplicateTaskException();
        }

    }

    @Override
    public boolean hasSameDescription(Task task1, Task task2) {
        return task1.getDescription().equals(task2.getDescription());
    }

    @Override
    public String saveTask(User user, Task task) {
        task.setUser(user);
        taskRepository.save(task);
        return "tarefa salva";

    }



}
