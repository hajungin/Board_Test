package com.example.myboard.service;

import com.example.myboard.constant.UserRole;
import com.example.myboard.dto.UserCreateForm;
import com.example.myboard.entity.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    @PersistenceContext //            트랜잭션 처리를 같이 해준다
    EntityManager em;
    @Transactional
    public void createUser(UserCreateForm userCreateForm) {
        UserAccount account = new UserAccount();
        account.setUserId(userCreateForm.getUsername());
        account.setUserPassword(passwordEncoder.encode(
                userCreateForm.getPassword1()
        ));
        account.setEmail(userCreateForm.getEmail());
        account.setNickname(userCreateForm.getNickname());
        if ("ADMIN".equals(userCreateForm.getUsername().toUpperCase())){
            account.setUserRole(UserRole.ADMIN);
        }else {
            account.setUserRole(UserRole.USER);
        }
        em.persist(account);
    }
}
