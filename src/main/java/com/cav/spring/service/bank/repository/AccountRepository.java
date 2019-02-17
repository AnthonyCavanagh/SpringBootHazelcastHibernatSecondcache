package com.cav.spring.service.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cav.spring.service.bank.entity.Account;


public interface AccountRepository extends JpaRepository<Account, Long>{

}
