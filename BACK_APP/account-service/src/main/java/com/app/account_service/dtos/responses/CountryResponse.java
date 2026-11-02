package com.app.account_service.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryResponse {
    private Long id;
    private String name;
    private Boolean availability;
    private String phoneCode;
    private String currencySymbol;
    private Double exchangeRate;
    private Integer minPhoneLength;
    private Integer maxPhoneLength;
}
