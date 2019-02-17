package com.cav.spring.service.bank.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import com.cav.spring.service.bank.model.account.AccountId;
import com.cav.spring.service.bank.model.account.AccountPOJO;
import com.cav.spring.service.bank.model.account.Accounts;
import com.cav.spring.service.bank.model.bank.BankId;
import com.cav.spring.service.bank.model.bank.BankPOJO;
import com.cav.spring.service.bank.model.bank.BankRequest;
import com.cav.spring.service.bank.model.bank.Banks;
import com.cav.spring.service.bank.model.fund.FundId;
import com.cav.spring.service.bank.model.fund.FundPOJO;
import com.cav.spring.service.bank.model.fund.Funds;
import com.cav.spring.service.bank.service.AccountService;
import com.cav.spring.service.bank.service.BankService;

@TestPropertySource(locations="classpath:test.properties")
public class AccountRepositoryTest extends AbstractRepositoryTest {
	
	@Before
	public void setUp(){
		super.setUp();
	}
	
	@After
	public void cleanUp(){
		super.cleanUp();
	}
	
	@Test
	public void testCreateAccounts(){
		List<BankId> bankIds = createBankIds(DEFAULT_BANK_ID);
		Banks banks = bankService.listBanks(bankIds);
		BankPOJO bank = banks.getBankList().iterator().next();
		assertEquals(DEFAULT_BANK_ID,bank.getBankId());
		Iterator<AccountPOJO> accountIter = bank.getAccounts().getAccountsList().iterator();
		assertEquals(DEFAULT_ACCOUNT_ID, accountIter.next().getAccountid());
	}
	/**
	 * To add a new account, have to add it to a bank.
	 */
	@Test
	public void testCreateNewAccount(){
		List<BankPOJO> bankList = createBankNewAccount(DEFAULT_BANK_ID,NEW_ACCOUNT_ID);
		Banks banks = new Banks();
		banks.setBankList(bankList);
		bankService.updateBanks(banks);
		
		List<BankId> bankIds = createBankIds(DEFAULT_BANK_ID);
		banks = bankService.listBanks(bankIds);
		BankPOJO bank = banks.getBankList().iterator().next();
		assertEquals(DEFAULT_BANK_ID,bank.getBankId());
		List<AccountPOJO> accountList = bank.getAccounts().getAccountsList();
		assertEquals(2, accountList.size());
		
		List<AccountId> accountIds = createAccountsIds(NEW_ACCOUNT_ID);
		accountService.removeAccounts(accountIds);
		Accounts accounts = accountService.listAccounts(accountIds);
		assertTrue(accounts.getAccountsList().isEmpty());
		
		List<FundId> fundIds = createFundsIds(NEW_FUND_ID);
		fundService.removeFunds(fundIds);
		Funds funds = fundService.listFunds(fundIds);
		assertTrue(funds.getFundList().isEmpty());
	}
	
	@Test
	public void testCreateUpdateAccountWithExistingAndNewFund(){
		List<BankPOJO> bankList = createBankNewAccount(DEFAULT_BANK_ID,NEW_ACCOUNT_ID);
		Banks banks = new Banks();
		banks.setBankList(bankList);
		bankService.updateBanks(banks);
		Accounts accounts = createAccountFund(NEW_ACCOUNT_ID, DEFAULT_FUND_ID);
		
		// Updating an existing account with a new fund
		accountService.updateAccounts(accounts);
		
		accounts = accountService.listAccounts(createAccountsIds(NEW_ACCOUNT_ID));
		AccountPOJO account = accounts.getAccountsList().iterator().next();
		List <FundPOJO> accounList = account.getFunds().getFundList();
		
		assertEquals(2,accounList.size());
		
		List<AccountId> accountIds = createAccountsIds(NEW_ACCOUNT_ID);
		accountService.removeAccounts(accountIds);
		accounts = accountService.listAccounts(accountIds);
		assertTrue(accounts.getAccountsList().isEmpty());
	}

}
