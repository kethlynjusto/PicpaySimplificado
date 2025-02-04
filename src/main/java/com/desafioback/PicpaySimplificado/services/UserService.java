package com.desafioback.PicpaySimplificado.services;

import com.desafioback.PicpaySimplificado.domain.user.User;
import com.desafioback.PicpaySimplificado.domain.user.UserType;
import com.desafioback.PicpaySimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHAN){
            throw new Exception("Usuário não suportado");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuario não encontrado") );
    }

    public void saveUser(User user){
        this.repository.save(user);
    }
}
