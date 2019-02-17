package com.cav.spring.service.bank.model.fund;

import java.util.ArrayList;
import java.util.List;

public class Funds {

	private List <FundPOJO> fundList = new ArrayList<FundPOJO>();

	public List<FundPOJO> getFundList() {
		return fundList;
	}

	public void setFundList(List<FundPOJO> fundList) {
		this.fundList = fundList;
	}
}
