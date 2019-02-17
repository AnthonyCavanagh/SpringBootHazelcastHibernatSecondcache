package com.cav.spring.service.bank.repository;

import org.springframework.stereotype.Repository;
import com.cav.spring.service.bank.entity.Bank;


import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long>{

}
