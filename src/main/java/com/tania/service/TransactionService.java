package com.tania.service;

import java.util.List;

import com.tania.model.Order;
import com.tania.model.Seller;
import com.tania.model.Transaction;
import com.tania.model.User;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySeller(Seller seller);
    List<Transaction>getAllTransactions();
}
