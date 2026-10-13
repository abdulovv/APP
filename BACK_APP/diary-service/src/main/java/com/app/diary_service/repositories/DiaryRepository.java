package com.app.diary_service.repositories;

import com.app.diary_service.entities.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    Optional<Diary> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
