package com.cav.spring.service.bank.model.account;

import java.util.List;

import com.cav.spring.service.bank.model.fund.FundId;

public class AccountId {

	private Long id;
	private List <FundId> fundIds;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<FundId> getFundIds() {
		return fundIds;
	}

	public void setFundIds(List<FundId> fundIds) {
		this.fundIds = fundIds;
	}
}
