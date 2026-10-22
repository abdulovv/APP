package com.app.auth_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "page_settings")
@Entity
public class PageSetting {
    @Id
    @Column(name = "page_key", nullable = false, unique = true)
    private String pageKey;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "reason")
    private String reason;
}
