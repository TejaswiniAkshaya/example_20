package com.eazybytes.eazyschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "contact_msg")
public class Contact extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int contactId;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Mobile Number must not be Blank")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNum;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid Email")
    private String email;
    @NotBlank(message = "message must not be blank")
    @Size(min = 10,message = "message must be atleast 10 characters long")
    private String message;
    @NotBlank(message = "Subject must not be blank")
    @Size(min = 5,message = "Subject must be atleast 5 characters long")
    private String subject;
    private String status;


}