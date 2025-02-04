package com.desafioback.PicpaySimplificado.repositories;

import com.desafioback.PicpaySimplificado.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
