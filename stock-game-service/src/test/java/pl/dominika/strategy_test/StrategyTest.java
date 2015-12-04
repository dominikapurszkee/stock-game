package pl.dominika.strategy_test;

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

import pl.dominika.stockbroker.StockBroker;
import pl.dominika.strategy.StrategyInterface;
import pl.dominika.strategy.StrategyRandom;
import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../../spring/test-context.xml")
public class StrategyTest {

	ShareTo st1 = new ShareTo("KGHM", 190.0);
	ShareTo st2 = new ShareTo("PGNIG", 180.5);
	List<ShareTo> sharesList = new ArrayList<ShareTo>();
	MoneyTo money = new MoneyTo("ZL", 500);
	Set<MoneyTo> moneySet = new HashSet<MoneyTo>();

	@Test
	public void testRandomStrategyShouldChooseOnlyOneShare() {

		// given
		sharesList.add(st1);
		sharesList.add(st2);
		moneySet.add(money);
		// when
		StrategyInterface strategy = new StrategyRandom();
		Map<String, Integer> sharesToBuy = strategy.chooseSharesToBuy(moneySet,
				sharesList);
		// then
		Assert.assertNotNull(sharesToBuy);
		Assert.assertEquals(1, sharesToBuy.size());
	}

	@Test
	public void testRandomStrategyShouldSpendMaxAmountOfMoney() {
		// given
		sharesList.add(st1);

		sharesList.add(st2);
		moneySet.add(money);
		// when

		StrategyInterface strategy = new StrategyRandom();
		Map<String, Integer> sharesToBuy = strategy.chooseSharesToBuy(moneySet,
				sharesList);
		ShareTo chosen = sharesToBuy.get("KGHM") != null ? st1 : st2;
		double amount = sharesToBuy.get(chosen.getCompanyName());
		double price = chosen.getPrice();
		// then

		Assert.assertNotNull(chosen);
		Assert.assertTrue(money.getValue() - amount * price < price);
		Assert.assertNotNull(sharesToBuy);
		Assert.assertEquals(1, sharesToBuy.size());
	}

	@Test
	public void testRandomStrategyShouldChooseSharesToSell() {

		// given
		Map<String, Integer> sharesMap = new HashMap<String, Integer>();
		sharesMap.put("KGHM", 5);
		sharesMap.put("PGING", 4);
		// when
		StrategyInterface strategy = new StrategyRandom();
		Map<String, Integer> chosenShares = strategy
				.chooseSharesToSell(sharesMap);
		// then
		Assert.assertNotNull(chosenShares);
		Assert.assertEquals(1, chosenShares.size());

	}

}
