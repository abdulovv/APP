package com.app.account_service.repositories;

import com.app.account_service.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.firstname) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(u.lastname) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))
           OR u.phoneNumber LIKE CONCAT('%', :query, '%')
        ORDER BY u.id
        """)
    List<User> searchByNameEmailOrPhone(@Param("query") String query, Pageable pageable);
}
