package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.model.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByMail(String mail);
}