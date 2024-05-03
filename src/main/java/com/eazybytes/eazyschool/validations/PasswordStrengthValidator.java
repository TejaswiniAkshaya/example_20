package com.eazybytes.eazyschool.validations;

import com.eazybytes.eazyschool.annotation.PasswordValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PasswordStrengthValidator implements
        ConstraintValidator<PasswordValidator, String> {

    /*List<String> weakPasswords;*/
    boolean hasLower, hasUpper ,hasDigit,  specialChar;
    Set<Character> set;
    @Override
    public void initialize(PasswordValidator passwordValidator) {
        /*weakPasswords = Arrays.asList("12345", "password", "qwerty");*/
        set = new HashSet<Character>(
                Arrays.asList('!', '@', '#', '$', '%', '^', '&',
                        '*', '(', ')', '-', '+'));
        hasLower=false;
        hasDigit=false;
        hasUpper=false;
        specialChar=false;

    }

    @Override
    public boolean isValid(String input,
                           ConstraintValidatorContext cxt) {
       /* return passwordField != null && (!weakPasswords.contains(passwordField));*/
        int n = input.length();
        for (char i : input.toCharArray())
        {
            if (Character.isLowerCase(i))
                hasLower = true;
            if (Character.isUpperCase(i))
                hasUpper = true;
            if (Character.isDigit(i))
                hasDigit = true;
            if (set.contains(i))
                specialChar = true;
        }
        //strength of password
        if (hasDigit && hasLower && hasUpper && specialChar && (n >= 8)){
            return true;
        }
        else if ((hasLower || hasUpper || specialChar) && (n >= 6)){
            return true;
        }
        else{
            return false;
        }


    }
}
