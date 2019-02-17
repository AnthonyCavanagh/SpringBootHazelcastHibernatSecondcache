package com.cav.spring.service.bank.service;

import java.util.List;

import com.cav.spring.service.bank.model.account.AccountId;
import com.cav.spring.service.bank.model.account.Accounts;


public interface AccountService {
	
	public void updateAccounts(Accounts accounts);
	public Accounts listAccounts(List<AccountId> accountIds);
	public void removeAccounts(List<AccountId> accountIds);
	void removeFunds(List<AccountId> accountIds);

}
