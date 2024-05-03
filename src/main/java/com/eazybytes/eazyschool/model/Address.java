package com.eazybytes.eazyschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int addressId;
    @NotBlank(message = "Adrdress1 must not be blank")
    @Size(min = 5,message = "Address must be at least 5 characters")
    private String address1;
    private String address2;
    @NotBlank
    @Size(min = 5,message = "city must be at least5 characters long ")
    private String city;
    @NotBlank(message = "State must not be blank")
    @Size(min = 5,message = "State must be at least 5 characters ")
    private String state;
    @NotBlank(message = "Zip Code must not be blank")
    @Pattern(regexp = "(^$|[0-9]{5})",message = "Zip code must be 5 digits")
    private String zipCode;



}
