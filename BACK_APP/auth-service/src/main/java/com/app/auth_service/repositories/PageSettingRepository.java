package com.app.auth_service.repositories;

import com.app.auth_service.entities.PageSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageSettingRepository extends JpaRepository<PageSetting, String> {
    List<PageSetting> findAllByOrderByPageKeyAsc();
}