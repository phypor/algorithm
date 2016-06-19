package cn.podger.springboot.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.podger.springboot.jpa.entity.User;
@Component
public interface UserRepository extends JpaRepository<User, Long> {
 
}
