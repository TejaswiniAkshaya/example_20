package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.controller.ContactController;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.repository.ContactRepository;
import com.eazybytes.eazyschool.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {
    @Autowired
    private final ContactRepository contactRepository;
    public boolean saveMessageDetails(Contact contact){
       boolean isSaved=true;
       contact.setStatus(EazySchoolConstants.OPEN);
       Contact contact1=contactRepository.save(contact);
       if(contact1!=null && contact1.getContactId()>0)
       {
           isSaved=true;
       }
       return isSaved;

    }

   /* public List<Contact> findMsgsWithOpenStatus() {
        List<Contact> contactList=contactRepository.findByStatus(EazySchoolConstants.OPEN);
        return contactList;

    }*/
    


    public boolean updateMsgStatus(int contactId) {
        boolean isUpdated=false;
        Contact contact=contactRepository.getReferenceById(contactId);
        if(contact!=null){
            contact.setStatus(EazySchoolConstants.CLOSE);
            Contact contact1=contactRepository.save(contact);
            if(contact1!=null && contact1.getUpdatedBy()!=null){
              isUpdated=true;
            }

        }
        return isUpdated;


    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir) {
        int pageSize=5;
        Pageable pageable= PageRequest.of(pageNum-1,pageSize,sortDir.equals("acs")?
                Sort.by(sortField).ascending():Sort.by(sortField).descending());
        Page<Contact> msgPage=contactRepository.findByStatus(EazySchoolConstants.OPEN,pageable);
        return msgPage;
    }
}
