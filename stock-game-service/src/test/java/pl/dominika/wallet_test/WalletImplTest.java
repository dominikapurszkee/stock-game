package pl.dominika.wallet_test;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.dominika.investor.Investor;
import pl.dominika.stockbroker.StockBroker;
import pl.dominika.strategy.StrategyInterface;
import pl.dominika.strategy.StrategyRandom;
import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;
import pl.dominika.wallet.Wallet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../../spring/test-context.xml")
public class WalletImplTest {
	@Autowired
	private Wallet wallet;
	// given
	Map<ShareTo, Integer> sharesToAdd = new HashMap<ShareTo, Integer>();
	Map<String, Integer> sharesToDelete = new HashMap<String, Integer>();

	ShareTo st1 = new ShareTo("KGHM", 190.0);
	ShareTo st2 = new ShareTo("PGNIG", 180.5);

	MoneyTo money2 = new MoneyTo("ZL", 250.0);
	MoneyTo money1 = new MoneyTo("ZL", 350.0);
	MoneyTo money3 = new MoneyTo("ZL", 100.0);

	@Test
	public void testShouldAddShares() {

		// given
		sharesToAdd.put(st1, 5);
		sharesToAdd.put(st2, 4);
		// when
		wallet.addShares(sharesToAdd);
		Map<String, Integer> ownedShares = wallet.getOwnedShares();

		// then
		Assert.assertEquals(2, ownedShares.size());
	}

	@Test
	public void testShouldDeleteShares() {

		// given
		sharesToAdd.put(st1, 5);
		sharesToAdd.put(st2, 4);
		sharesToDelete.put("KGHM", 5);
		// when
		wallet.deleteShares(sharesToDelete);
		Map<String, Integer> ownedShares = wallet.getOwnedShares();
		// then
		Assert.assertEquals(1, ownedShares.size());
	}

	@Test
	public void testShouldCalculatePrice() {

		// given
		sharesToAdd.put(st1, 5);
		sharesToAdd.put(st2, 4);

		// when
		MoneyTo money = wallet.calculatePrice(sharesToAdd);

		// then
		Assert.assertEquals((Double) 1672.0, (Double) money.getValue());
	}

	@Test
	public void testShouldAddMoney() {

		// given
		wallet.setMoney(money1);
		wallet.addMoney(money2);
		// when
		Set<MoneyTo> money = wallet.getMoney();
		List<MoneyTo> moneyList = new ArrayList<MoneyTo>(money);
		// then
		Assert.assertEquals(1, money.size());
		Assert.assertEquals((Double) 600.0, (Double) moneyList.get(0)
				.getValue());
	}

	@Test
	public void testShouldDeleteMoney() {

		// given
		wallet.deleteMoney(money2);
		// when
		Set<MoneyTo> money = wallet.getMoney();
		List<MoneyTo> moneyList = new ArrayList<MoneyTo>(money);
		// then
		Assert.assertEquals(1, money.size());
		Assert.assertEquals((Double) 350.0, (Double) moneyList.get(0)
				.getValue());
	}

	@Test
	public void testShouldCalculateMoneyWithSellingComission() {
		// when
		MoneyTo money = wallet.calculateMoneyWithSellingCommission(money2);
		// then
		Assert.assertEquals((Double) 248.75, (Double) money.getValue());
	}

	@Test
	public void testShouldCalculateMoneyWithBuyingComission() {
		// when
		MoneyTo money = wallet.calculateMoneyWithBuyingCommission(money3);
		// then
		assertEquals((Double) money.getValue(), (Double) 100.49999999999999);
	}
}
