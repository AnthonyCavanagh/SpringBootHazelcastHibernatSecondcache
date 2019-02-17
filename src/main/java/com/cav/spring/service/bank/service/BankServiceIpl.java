package com.cav.spring.service.bank.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cav.spring.service.bank.entity.Account;
import com.cav.spring.service.bank.entity.Bank;
import com.cav.spring.service.bank.model.account.AccountId;
import com.cav.spring.service.bank.model.bank.BankId;
import com.cav.spring.service.bank.model.bank.BankPOJO;
import com.cav.spring.service.bank.model.bank.BankRequest;
import com.cav.spring.service.bank.model.bank.Banks;
import com.cav.spring.service.bank.repository.AccountRepository;
import com.cav.spring.service.bank.repository.BankRepository;

@Service
public class BankServiceIpl extends MappEntitiesAbstract implements BankService {
	
	@Autowired
	BankRepository bankRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	
	@Override
	public void createBanks(Banks banks) {
		List <Bank> entities = new ArrayList<Bank>();
		List<BankPOJO> bankList = banks.getBankList();
		for(BankPOJO bank : bankList) {
			Bank bankEntity = bankRepository.findOne(bank.getBankId());
			if(bankEntity == null){
				entities.add(createBankEntity(bank));
			} else {
				//Log error bank exists.
				entities.add(updateBankEntity(bank, bankEntity));
			}
		}
		bankRepository.save(entities);
	}

	@Override
	public void updateBanks(Banks banks) {
		List <Bank> entities = new ArrayList<Bank>();
		List<BankPOJO> bankList = banks.getBankList();
		for(BankPOJO bank : bankList) {
			Bank bankEntity = bankRepository.findOne(bank.getBankId());
			if(bankEntity != null){
				entities.add(updateBankEntity(bank, bankEntity));
			} else {
				//Log error doesnt exists.
				
			}
		}
		bankRepository.save(entities);
	}

	/**
	 * Have to use repository.findOne, to load an entity into the second cache
	 */
	public Banks listBanks(List<BankId> bankIds) {
		List<Bank> bankList = new ArrayList<Bank>();
		for(BankId bankId :bankIds){
			Bank bankEntity = bankRepository.findOne(bankId.getId());
			if(bankEntity != null){
				bankList.add(bankEntity);
			}
		}
		return mapdBanks(bankList);
	}
	
	@Override
	public void removeBanks(List<BankId> bankIds) {
		for(BankId bankId: bankIds){
			try {
				bankRepository.delete(bankId.getId());
			} catch (EmptyResultDataAccessException e) {
				//Do nothing
			}
		}
	}
	
	@Override
	public void removeAccounts(List<BankId> bankIds) {
		for (BankId bankId : bankIds) {
			Bank bank = bankRepository.findOne(bankId.getId());
			if (bank != null) {
				for (AccountId accountId : bankId.getAccountIds()) {
					Account account = accountRepository.findOne(accountId.getId());
					bank.removeAccounts(account);
				}
			}
			bankRepository.save(bank);
		}
	}
}
