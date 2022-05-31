package org.saikumo.pwams.repository;

import org.saikumo.pwams.entity.Role;
import org.saikumo.pwams.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	User findUserByUsername(String username);
	List<User> findByRoles(Role name);
}
