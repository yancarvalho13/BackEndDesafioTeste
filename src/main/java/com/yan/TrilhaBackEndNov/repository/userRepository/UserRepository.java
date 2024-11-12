package com.yan.TrilhaBackEndNov.repository.userRepository;

import com.yan.TrilhaBackEndNov.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,Long> {

    UserDetails findByEmail(String email);
}
