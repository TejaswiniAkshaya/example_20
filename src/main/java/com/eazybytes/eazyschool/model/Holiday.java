package com.eazybytes.eazyschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity{
    @Id
    private  String day;
    private  String reason;
    @Enumerated(EnumType.STRING)
    private  Type type;
    public enum Type {
        FESTIVAL, FEDERAL
    }
}
