package com.eazybytes.eazyschool.model;

import com.eazybytes.eazyschool.annotation.FieldsValueMatch;
import com.eazybytes.eazyschool.annotation.PasswordValidator;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;


@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match!"
        )
})
@Getter
@Setter
public class Person extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int personId;

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Confirm Email must not be blank")
    @Email(message = "Please provide a valid confirm email address" )
    @Transient
    private String confirmEmail;

    @NotBlank(message="Password must not be blank")
    @PasswordValidator
    private String pwd;

    @NotBlank(message="Confirm Password must not be blank")
    @Transient
    private String confirmPwd;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,targetEntity = Roles.class)
    @JoinColumn(name = "role_id",referencedColumnName = "roleId",nullable = false)
    private Roles Role;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity =Address.class )
    @JoinColumn(name = "address_id",referencedColumnName ="addressId" ,nullable = true)
    private Address address;

    @ManyToOne(targetEntity = NarayanaClass.class,fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "class_id",referencedColumnName = "classId",nullable = true)
    private NarayanaClass narayanaClass;

   @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
   @JoinTable(name = "person_courses",joinColumns = {
           @JoinColumn(name = "person_id",referencedColumnName = "personId")},
           inverseJoinColumns = {
           @JoinColumn(name = "course_id",referencedColumnName = "courseId")})
    private Set<Courses> courses=new HashSet<>();




}
