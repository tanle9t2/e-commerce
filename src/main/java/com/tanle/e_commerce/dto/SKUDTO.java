package com.tanle.e_commerce.dto;

import com.tanle.e_commerce.utils.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SKUDTO {
    private int id;
    private String skuName;
    private String itemName;
    private String skuNo;
    @Enumerated(EnumType.STRING)
    private Status status;
    private int stock;
    private double price;
    private LocalDateTime createdAt;
    private List<Integer> optionValueIndex;

}