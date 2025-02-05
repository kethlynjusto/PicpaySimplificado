package com.desafioback.PicpaySimplificado.services;

import com.desafioback.PicpaySimplificado.domain.transaction.Transaction;
import com.desafioback.PicpaySimplificado.domain.user.User;
import com.desafioback.PicpaySimplificado.dtos.TransactionDTO;
import com.desafioback.PicpaySimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository repository;
    private final NotificationService notificationService;
    private final RestTemplate restTemplate;

    @Autowired
    public TransactionService(UserService userService,
                              TransactionRepository repository,
                              NotificationService notificationService,
                              RestTemplate restTemplate) {
        this.userService = userService;
        this.repository = repository;
        this.notificationService = notificationService;
        this.restTemplate = restTemplate;
    }

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private TransactionRepository repository;
//
//    @Autowired
//    private NotificationService notificationService;
//
//    @Autowired
//    private RestTemplate restTemplate;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if(!isAuthorized){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Não autorizado");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

        return newTransaction;

    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK
                && "success".equals(authorizationResponse.getBody().get("status"))){
//                && Boolean.TRUE.equals(authorizationResponse.getBody().get("authorization"))){

//        if((authorizationResponse.getStatusCode() == HttpStatus.OK) && (authorizationResponse.getBody().get("status") == "true")){
            return true;
        } return false;
    }

}
