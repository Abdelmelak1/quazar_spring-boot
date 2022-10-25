package com.supportportal.controller;


import com.fasterxml.jackson.core.JsonParser;
import com.supportportal.domain.User;
import com.supportportal.model.Customer;
import com.supportportal.model.Transaction;
import com.supportportal.repository.CustomerRepo;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/customers")
    public List<Customer> allCustomers(){
        return customerRepo.findAll();
    }

    @PostMapping("/customer")
    public Customer addCustomer(@RequestBody Customer customer){
        return customerRepo.save(customer);
    }
    @GetMapping("customer/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerRepo.findById(id).get();
        return ResponseEntity.ok(customer);
    }
    @PutMapping("/customer/{id}")
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long id){
        Customer updateCustomer = customerRepo.findById(id).get();


       updateCustomer.setEmail(customer.getEmail());
       updateCustomer.setFirstName(customer.getFirstName());
       updateCustomer.setLastName(customer.getLastName());
       updateCustomer.setPhoneNumber(customer.getPhoneNumber());
       //updateCustomer.setTransactions(customer.getTransactions());
       return customerRepo.save(updateCustomer);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Customer customer = customerRepo.findById(id).get();
        customerRepo.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/customertouser/{username}/{id_customer}")
    public User addTransactionToEmployee(@PathVariable ("id") String username, @PathVariable ("id_customer") Long id_customer) {
        //not working
        User user =  userService.findUserByUsername(username);
        user.getCustomers().add(customerRepo.findById(id_customer).get());
        return userRepository.save(user);
    }

    @PostMapping("/customertouser/{username}")
    public User addTransactionToEmployee2(@PathVariable ("username") String username, @RequestBody Customer customer) {
        User user =  userService.findUserByUsername(username);
        user.getCustomers().add(customer);
        return userRepository.save(user);
    }
    @GetMapping("/customertouser/{id}")
    public List<Transaction> getTransactions(@PathVariable ("id") Long id){
        return customerRepo.findById(id).get().getTransactions();
    }
}
