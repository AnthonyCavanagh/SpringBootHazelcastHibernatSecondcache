package com.cav.spring.service.bank.service;



import java.util.List;

import com.cav.spring.service.bank.model.bank.BankId;
import com.cav.spring.service.bank.model.bank.Banks;

public interface BankService {
	
	public void createBanks(Banks bank);
	public void updateBanks(Banks bank);
	public Banks listBanks(List <BankId> bankIds);
	public void removeBanks(List <BankId> bankIds);
	public void removeAccounts(List<BankId> bankIds);
}
