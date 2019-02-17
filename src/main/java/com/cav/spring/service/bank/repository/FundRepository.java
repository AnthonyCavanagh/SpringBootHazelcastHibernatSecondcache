package com.cav.spring.service.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cav.spring.service.bank.entity.Fund;

public interface FundRepository extends JpaRepository<Fund, Long> {

}
