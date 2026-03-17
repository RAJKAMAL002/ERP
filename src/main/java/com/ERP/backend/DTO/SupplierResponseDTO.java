package com.ERP.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierResponseDTO {
    private Long id;
    private String name;
    private String gstNumber;
    private Long phone;
    private String email;
    private String address;
}
