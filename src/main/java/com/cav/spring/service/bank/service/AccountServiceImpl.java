package com.cav.spring.service.bank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cav.spring.service.bank.entity.Account;
import com.cav.spring.service.bank.model.account.AccountId;
import com.cav.spring.service.bank.model.account.AccountPOJO;
import com.cav.spring.service.bank.model.account.Accounts;
import com.cav.spring.service.bank.model.fund.FundId;
import com.cav.spring.service.bank.repository.AccountRepository;
import com.cav.spring.service.bank.repository.FundRepository;

@Service
public class AccountServiceImpl extends MappEntitiesAbstract  implements AccountService {

	@Autowired
	AccountRepository repository;
	
	@Autowired
	FundRepository fundRepository;

	@Override
	public void updateAccounts(Accounts accounts) {
		List<Account> entities = new ArrayList<Account>();
		List<AccountPOJO> accountList = accounts.getAccountsList();
		for(AccountPOJO account: accountList){
			Account accountEntity = repository.findOne(account.getAccountid());
			if(accountEntity != null){
				entities.add(updateAccountEntity(account, accountEntity));
			} else {
				// Errors Account Id dont exist
			}
		}
		repository.save(entities);
	}

	/**
	 * Have to use repository.findOne, to load an entity into the second cache
	 */
	@Override
	public Accounts listAccounts(List<AccountId> accountIds) {
		List<Account> accountList = new ArrayList<Account>();
		for(AccountId accountId : accountIds){
			Account accountEntity = repository.findOne(accountId.getId());
			if(accountEntity != null){
				accountList.add(accountEntity);
			}
		}
		return mapAccounts(accountList);
	}

	@Override
	public void removeAccounts(List<AccountId> accountIds) {
		for(AccountId accountId: accountIds){
			try {
				repository.delete(accountId.getId());
			} catch (EmptyResultDataAccessException e) {
				//Do nothing
			}
		}
	}
	
	@Override
	public void removeFunds(List<AccountId> accountIds) {
		for (AccountId accountId : accountIds) {
			Account accountEntity = repository.findOne(accountId.getId());
			if (accountEntity != null) {
				for (FundId fundId : accountId.getFundIds()) {
					accountEntity.removeFunds(fundRepository.findOne(fundId.getId()));
				}
			}
			repository.save(accountEntity);
		}
	}
}
