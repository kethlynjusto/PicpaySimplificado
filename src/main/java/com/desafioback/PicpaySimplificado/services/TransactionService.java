package com.desafioback.PicpaySimplificado.services;

import com.desafioback.PicpaySimplificado.domain.user.User;
import com.desafioback.PicpaySimplificado.dtos.TransactionDTO;
import com.desafioback.PicpaySimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    public void createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
    }


}
