package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    @Query("SELECT u FROM UserLogin u WHERE u.loginId = :loginId AND u.password = :password AND lower(u.status) = lower(:status)")
    UserLogin findByLoginIdAndPassword(String loginId, String password, String status);

}
