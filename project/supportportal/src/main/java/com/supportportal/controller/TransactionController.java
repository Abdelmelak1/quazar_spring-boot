package com.supportportal.controller;


import com.supportportal.domain.User;
import com.supportportal.model.Customer;
import com.supportportal.model.Transaction;
import com.supportportal.repository.CustomerRepo;
import com.supportportal.repository.TransactionRepo;
import com.supportportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(){
        return transactionRepo.findAll();
    }

    @PostMapping("/transaction")
    public Transaction addTransaction(@RequestBody Transaction transaction){
        return transactionRepo.save(transaction);
    }

    @PostMapping("transaction/{id}")
    public Customer addTransactionToEmployee(@PathVariable ("id") Long id, @RequestBody Transaction trx) {
        Customer customer =  customerRepo.findById(id).get();
        customer.getTransactions().add(trx);
        return customerRepo.save(customer);
    }

    @GetMapping("/sumTransactions/{id}")
    public float[] sumTransactionByCustomer(@PathVariable("id") Long id){

        float[] transactionsFeed = new float[3];
        final float[] sum = {0};
        final float[] in = {0};
        final float[] out = {0};
       Customer customer =  customerRepo.findById(id).get();

           List<Transaction> tnxs = customer.getTransactions();
           tnxs.forEach((txn) ->{
               if(txn.getAmount()>0)
                   in[0] += txn.getAmount();
               else
                   out[0] += txn.getAmount();
               sum[0] += txn.getAmount();
           });


        transactionsFeed[0]= sum[0];
        transactionsFeed[1]= in[0];
        transactionsFeed[2]= out[0];
        return transactionsFeed;

    }
    @GetMapping("/generalBalance")
    public float generalBalance(){
        float sum = 0;
        List<Transaction> list =  transactionRepo.findAll();
        for(Transaction t:list){
            sum += t.getAmount();
        }
        return sum;
    }
    @GetMapping("/userAllCustomersTransactionsTrack/{username}")
    public float[] getUserAllCustomersTransactionsTrack(@PathVariable("username") String username){

        float[] transactionsFeed = new float[3];
        final float[] sum = {0};
        final float[] in = {0};
        final float[] out = {0};
        User user = userRepository.findUserByUsername(username);
        List<Customer> customers = user.getCustomers();
        customers.forEach((customer) ->{
          float[] feed =  sumTransactionByCustomer(customer.getId());
          sum[0] += feed[0];
          in[0] += feed[1];
          out[0] += feed[2];

        } );
        transactionsFeed[0]= sum[0];
        transactionsFeed[1]= in[0];
        transactionsFeed[2]= out[0];
        return transactionsFeed;
    }

}
