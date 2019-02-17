package com.cav.spring.service.bank.model.bank;

import java.util.List;

import com.cav.spring.service.bank.model.account.AccountId;



public class BankId {
	
	private Long id;
	private List <AccountId> accountIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AccountId> getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(List<AccountId> accountIds) {
		this.accountIds = accountIds;
	}

}
