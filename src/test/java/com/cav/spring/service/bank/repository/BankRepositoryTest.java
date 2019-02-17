package com.cav.spring.service.bank.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cav.spring.service.bank.entity.Account;
import com.cav.spring.service.bank.entity.Bank;
import com.cav.spring.service.bank.entity.Fund;
import com.cav.spring.service.bank.model.account.AccountId;
import com.cav.spring.service.bank.model.account.Accounts;
import com.cav.spring.service.bank.model.bank.BankId;
import com.cav.spring.service.bank.model.bank.BankPOJO;
import com.cav.spring.service.bank.model.bank.BankRequest;
import com.cav.spring.service.bank.model.bank.Banks;
import com.cav.spring.service.bank.model.fund.FundId;
import com.cav.spring.service.bank.model.fund.Funds;
import com.cav.spring.service.bank.service.BankService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@TestPropertySource(locations="classpath:test.properties")
public class BankRepositoryTest extends AbstractRepositoryTest {

	
	@Before
	public void setUp(){
		super.setUp();
	}
	
	@After
	public void cleanUp(){
		super.cleanUp();
	}
	
	@Test
	public void testCreateBank(){
		Banks banks = bankService.listBanks(createBankIds(DEFAULT_BANK_ID));
		assertEquals(DEFAULT_BANK_ID,banks.getBankList().iterator().next().getBankId());
	}
	
	@Test
	public void testUpdateBank(){
		BankPOJO bank = createBank(DEFAULT_BANK_ID);
		bank.setBankName("UpDatedBankName");
		Banks banks = new Banks();
		banks.getBankList().add(bank);
		bankService.updateBanks(banks);
		
		List<BankId> bankIds = createBankIds(DEFAULT_BANK_ID);
		banks = bankService.listBanks(bankIds);
		bank = banks.getBankList().iterator().next();
		assertEquals(DEFAULT_BANK_ID,bank.getBankId());
		assertEquals("UpDatedBankName",bank.getBankName());
	}
	
	@Test
	public void removeAccountFromBank(){
		bankService.removeAccounts(createBankAccountIds(DEFAULT_BANK_ID, DEFAULT_ACCOUNT_ID));
		Banks banks = bankService.listBanks(createBankIds(DEFAULT_BANK_ID));
		BankPOJO bank = banks.getBankList().iterator().next();
		assertEquals(DEFAULT_BANK_ID,bank.getBankId());
		assertTrue(bank.getAccounts().getAccountsList().isEmpty());
	}
}
