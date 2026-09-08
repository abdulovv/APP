package com.app.auth_service.dtos.common;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDTO {
    private Long id;
    private String name;
    private Boolean availability;
    private String phoneCode;
    private String currencySymbol;
    private Float exchangeRate;
    private Integer minPhoneLength;
    private Integer maxPhoneLength;

}
