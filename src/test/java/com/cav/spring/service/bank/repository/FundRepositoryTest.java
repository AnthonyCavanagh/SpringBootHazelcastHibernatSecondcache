package com.cav.spring.service.bank.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import com.cav.spring.service.bank.model.fund.FundId;
import com.cav.spring.service.bank.model.fund.Funds;

@TestPropertySource(locations="classpath:test.properties")
public class FundRepositoryTest extends AbstractRepositoryTest {
	
	//Only updates finds and removes  allowed on fund Repository
	// Finds and removes already tested.
	@Before
	public void setUp(){
		super.setUp();
	}
	
	@After
	public void cleanUp(){
		super.cleanUp();
	}
	@Test
	public void updateFundTest(){
		Funds funds = updateFund(DEFAULT_FUND_ID, "New Fund Name");
		fundService.updateFunds(funds);
		List<FundId> fundIds = createFundsIds(DEFAULT_FUND_ID);
		funds = fundService.listFunds(fundIds);
		assertEquals(DEFAULT_FUND_ID,funds.getFundList().iterator().next().getFundId());
		assertEquals("New Fund Name",funds.getFundList().iterator().next().getFundName());
	}

}
