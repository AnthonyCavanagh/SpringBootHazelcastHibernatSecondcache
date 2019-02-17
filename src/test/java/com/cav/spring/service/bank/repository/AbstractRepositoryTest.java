package com.cav.spring.service.bank.repository;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cav.spring.service.bank.AbstractTest;
import com.cav.spring.service.bank.BankWebServiceApp;
import com.cav.spring.service.bank.model.account.AccountId;
import com.cav.spring.service.bank.model.account.AccountPOJO;
import com.cav.spring.service.bank.model.account.AccountRequest;
import com.cav.spring.service.bank.model.account.Accounts;
import com.cav.spring.service.bank.model.bank.BankId;
import com.cav.spring.service.bank.model.bank.BankPOJO;
import com.cav.spring.service.bank.model.bank.BankRequest;
import com.cav.spring.service.bank.model.bank.Banks;
import com.cav.spring.service.bank.model.fund.FundId;
import com.cav.spring.service.bank.model.fund.FundPOJO;
import com.cav.spring.service.bank.model.fund.FundRequest;
import com.cav.spring.service.bank.model.fund.Funds;
import com.cav.spring.service.bank.service.AccountService;
import com.cav.spring.service.bank.service.AccountServiceImpl;
import com.cav.spring.service.bank.service.BankService;
import com.cav.spring.service.bank.service.BankServiceIpl;
import com.cav.spring.service.bank.service.FundService;
import com.cav.spring.service.bank.service.FundServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({BankServiceIpl.class, AccountServiceImpl.class, FundServiceImpl.class})
public class AbstractRepositoryTest extends AbstractTest{
	
	protected static final long DEFAULT_BANK_ID = 123L;
	protected static final long DEFAULT_ACCOUNT_ID = 1234L;
	protected static final long NEW_ACCOUNT_ID = 1235L;
	protected static final long DEFAULT_FUND_ID = 12345L;
	protected static final long NEW_FUND_ID = 12346L;
	
	@Autowired
	protected BankService bankService;
	
	@Autowired
	protected AccountService accountService;
	
	@Autowired
	protected FundService fundService;
	
	public void setUp(){
		List<BankPOJO> bankList = createBanks();
		Banks banks = new Banks();
		banks.setBankList(bankList);
		bankService.createBanks(banks);
	}
	
	public void cleanUp(){
		List<BankId> bankIds = createBankIds(DEFAULT_BANK_ID);
		bankService.removeBanks(bankIds);
		Banks banks = bankService.listBanks(bankIds);
		assertTrue(banks.getBankList().isEmpty());	
		
		List<AccountId> accountIds = createAccountsIds(DEFAULT_ACCOUNT_ID);
		accountService.removeAccounts(accountIds);
		Accounts accounts = accountService.listAccounts(accountIds);
		assertTrue(accounts.getAccountsList().isEmpty());
		
		List<FundId> fundIds = createFundsIds(DEFAULT_FUND_ID);
		fundService.removeFunds(fundIds);
		Funds funds = fundService.listFunds(fundIds);
		assertTrue(funds.getFundList().isEmpty());
	}
	
	protected List<BankPOJO> createBanks() {
		List<BankPOJO> bankList = new ArrayList<BankPOJO>();
		BankPOJO bank = createBank(DEFAULT_BANK_ID);
		List<AccountPOJO> accountList = new ArrayList<AccountPOJO>();
		AccountPOJO account = createaccount(DEFAULT_ACCOUNT_ID);
		List<FundPOJO> fundList = new ArrayList<FundPOJO>();
		fundList.add(createFund(DEFAULT_FUND_ID));
		Funds funds = new Funds();
		funds.setFundList(fundList);
		account.setFunds(funds);
		accountList.add(account);
		Accounts accounts = new Accounts();
		accounts.setAccountsList(accountList);
		bank.setAccounts(accounts);
		bankList.add(bank);
		return bankList;
	}
	
	protected List<BankPOJO> createBankNewAccount(Long bankId, Long accountId) {
		List<BankPOJO> bankList = new ArrayList<BankPOJO>();
		BankPOJO bank = createBank(bankId);
		List<AccountPOJO> accountList = new ArrayList<AccountPOJO>();
		AccountPOJO account = createaccount(accountId);
		List<FundPOJO> fundList = new ArrayList<FundPOJO>();
		fundList.add(createFund(NEW_FUND_ID));
		Funds funds = new Funds();
		funds.setFundList(fundList);
		account.setFunds(funds);
		accountList.add(account);
		Accounts accounts = new Accounts();
		accounts.setAccountsList(accountList);
		bank.setAccounts(accounts);
		bankList.add(bank);
		return bankList;
	}

	protected BankPOJO createBank(Long bankId) {
		BankPOJO bank = new BankPOJO();
		bank.setBankId(bankId);
		bank.setBankCode("CAV101L0");
		bank.setBankName("Natwest");
		bank.setContactDetailsCode(12345);
		bank.setEffectiveDate(LocalDate.now());
		bank.setActive("Y");
		return bank;
	}
	
	protected Accounts createAccountFund(Long accountId, Long fundId){
		List<AccountPOJO> accountList = new ArrayList<AccountPOJO>();
		AccountPOJO account = createaccount(accountId);
		List<FundPOJO> fundList = new ArrayList<FundPOJO>();
		fundList.add(createFund(fundId));
		Funds funds = new Funds();
		funds.setFundList(fundList);
		account.setFunds(funds);
		accountList.add(account);
		Accounts accounts = new Accounts();
		accounts.setAccountsList(accountList);
		return accounts;
	}

	protected AccountPOJO createaccount(Long accountId) {
		AccountPOJO account = new AccountPOJO();
		account.setAccountid(accountId);
		account.setAccountName("BlackRock");
		account.setActive("Y");
		account.setEffectiveDate(LocalDate.now());
		return account;
	}

	protected Funds updateFund(Long fundId, String fundName){
		List<FundPOJO> fundList = new ArrayList<FundPOJO>();
		FundPOJO fund = createFund(fundId);
		fund.setFundName(fundName);
		fundList.add(fund);
		Funds funds = new Funds();
		funds.setFundList(fundList);
		return funds;
	}
	protected FundPOJO createFund(Long fundid) {
		FundPOJO fund = new FundPOJO();
		fund.setFundId(fundid);
		fund.setFundName("Imperial");
		fund.setBuy(new BigDecimal(25.00));
		fund.setSell(new BigDecimal(20.00));
		fund.setYield(5f);
		fund.setEffectiveDate(LocalDate.now());
		return fund;
	}
	
	BankPOJO createBank(int bankId, String bankName, String bankCode){
		BankPOJO bank = new BankPOJO();
		bank.setBankId(bankId);
		bank.setBankName(bankName);
		bank.setBankCode(bankCode);
		bank.setActive("Y");
		bank.setEffectiveDate(LocalDate.now());
		return bank;
	}
	
	protected AccountPOJO createAccount(long accountid, String accountName){
		AccountPOJO account = new AccountPOJO();
		account.setAccountid(accountid);
		account.setAccountName(accountName);
		account.setActive("Y");
		account.setEffectiveDate(LocalDate.now());
		return account;
	}
	
	protected AccountId createAccountId(Long id){
		List <AccountId> ids = new ArrayList <AccountId> ();
		AccountId accountId = new AccountId();
		accountId.setId(id);
		return accountId;
	}

	protected List <AccountId> createAccountsIds(Long id){
		List <AccountId> ids = new ArrayList <AccountId> ();
		AccountId accountId = new AccountId();
		accountId.setId(id);
		ids.add(accountId);
		return ids;
	}
	
	protected List <FundId> createFundsIds(Long id){
		List <FundId> ids = new ArrayList <FundId> ();
		FundId fundId = new FundId();
		fundId.setId(id);
		ids.add(fundId);
		return ids;
	}

}
