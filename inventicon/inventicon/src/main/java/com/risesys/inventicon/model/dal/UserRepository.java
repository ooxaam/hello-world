package com.risesys.inventicon.model.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.risesys.inventicon.model.User;

/**
 * @author Osamam
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
