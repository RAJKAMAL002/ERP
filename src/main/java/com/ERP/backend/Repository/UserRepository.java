package com.ERP.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Constants.AuthProviderType;
import com.ERP.backend.Entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

	Optional<Users> findByUsername(String username);
	Optional<Users> findByProviderIdAndProviderType(String providerId, AuthProviderType authProviderType);
}
