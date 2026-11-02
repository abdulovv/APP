package com.app.account_service.repositories;

import com.app.account_service.entities.PageSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageSettingRepository extends JpaRepository<PageSetting, String> {
    List<PageSetting> findAllByOrderByPageKeyAsc();
}