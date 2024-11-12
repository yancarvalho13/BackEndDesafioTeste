package com.yan.TrilhaBackEndNov.service.userService;

import com.yan.TrilhaBackEndNov.model.user.User;
import com.yan.TrilhaBackEndNov.repository.userRepository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
