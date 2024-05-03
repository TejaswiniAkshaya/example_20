package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.Roles;
import com.eazybytes.eazyschool.repository.PersonRepository;
import com.eazybytes.eazyschool.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean createNewPerson(Person person) {
        boolean isSaved=false;
        Roles roles=rolesRepository.getByRoleName(EazySchoolConstants.STUDENT_ROLE);
        person.setRole(roles);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person=personRepository.save(person);
        if(person!=null && person.getPersonId()>0){
            isSaved=true;
        }
       return isSaved;
    }
}
