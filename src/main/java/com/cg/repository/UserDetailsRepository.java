package com.cg.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.UserClass;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserClass, Integer>{

	Optional<UserClass> findByEmailId(String emailId);



}
