package pl.dominika.investor_test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.dominika.investor.Investor;
import pl.dominika.service.impl.CSVReader;
import pl.dominika.strategy.StrategyInterface;
import pl.dominika.strategy.StrategyRandom;
import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../../spring/test-context.xml")
public class InvestorTest {

	private static final String URL = "C:\\Users\\dpurszke\\Desktop\\workspace\\stock-game\\stock-game-service\\src\\main\\resources\\data\\dane.csv";
	@Autowired
	private Investor investor;
	@Autowired
	private CSVReader csvReader;

	@Test
	public void testShouldReturnValueOfAllResources() {
		// given
		csvReader.readCSV(URL);
		LocalDate localDate = LocalDate.parse("20130103");
		Map<ShareTo, Integer> ownedShares = new HashMap<ShareTo, Integer>();
		ownedShares.put(new ShareTo("KGHM", 190.0), 5);
		investor.getWallet().addShares(ownedShares);
		investor.getWallet().setMoney(new MoneyTo("ZL", 500));
		// when
		List<MoneyTo> moneyList = new ArrayList<MoneyTo>(
				investor.calculateTotalValueOfAllResources(localDate));
		// then
		assertEquals((Double) 1450.0, (Double) moneyList.get(0).getValue());
	}

	@Test
	public void testShouldBuyRandomShares() {
		// given
		csvReader.readCSV(URL);
		LocalDate localDate = LocalDate.parse("20130103");
		StrategyInterface strategy = new StrategyRandom();
		investor.setStrategy(strategy);
		// when

		investor.buy(localDate);
		Map<String, Integer> mapOfShares = investor.getWallet()
				.getOwnedShares();

		// then
		assertFalse(mapOfShares.isEmpty());
	}

	@Test
	public void testShouldSellShares() {
		// given
		csvReader.readCSV(URL);
		LocalDate localDate = LocalDate.parse("20130103");
		StrategyInterface strategy = new StrategyRandom();
		investor.setStrategy(strategy);

		Map<ShareTo, Integer> mapOfOwnedShares = new HashMap<ShareTo, Integer>();
		mapOfOwnedShares.put(new ShareTo("KGHM", 190.0), 5);
		investor.getWallet().addShares(mapOfOwnedShares);
		// when
		investor.sell(localDate);
		Map<String, Integer> mapOfShares = investor.getWallet()
				.getOwnedShares();
		// then
		assertFalse(mapOfShares.isEmpty());
	}

}
