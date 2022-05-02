package com.example.bildergaleriebackend.service;


import com.example.bildergaleriebackend.exception.EmailAlreadyExistsException;
import com.example.bildergaleriebackend.exception.WrongEmailException;
import com.example.bildergaleriebackend.exception.WrongPasswordException;
import com.example.bildergaleriebackend.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public User register(String email, String userName, String passwordNotHashed) {
        User userWithSameEmail = em.find(User.class, email);

        if (userWithSameEmail != null) {
            throw new EmailAlreadyExistsException();
        }

        User newUser = new User();
        String password = passwordHash.generate(passwordNotHashed.toCharArray());

        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUserName(userName);

        em.persist(newUser);

        return newUser;
    }

    public User login(String email, String password) {
        User user = em.find(User.class, email);

        if (user == null) {
            throw new WrongEmailException();
        } else {
            if (passwordHash.verify(password.toCharArray(), user.getPassword())) {
                return user;
            } else {
                throw new WrongPasswordException();
            }
        }
    }

    public User getUserByEmail(String email) {
        return em.find(User.class, email);
    }

}

