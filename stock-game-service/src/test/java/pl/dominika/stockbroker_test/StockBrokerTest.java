package pl.dominika.stockbroker_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.dominika.calendar.MyCalendar;
import pl.dominika.service.impl.CSVReader;
import pl.dominika.stockbroker.StockBroker;
import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../../spring/test-context.xml")
public class StockBrokerTest {

	private static final String URL = "C:\\Users\\dpurszke\\Desktop\\workspace\\stock-game\\stock-game-service\\src\\main\\resources\\data\\dane.csv";
	@Autowired
	private StockBroker stockBroker;
	@Autowired
	private CSVReader csvReader;

	@Test
	public void testShouldFindAllShares() {

		// when
		List<ShareTo> sharesList = stockBroker.findAll();
		// then
		assertNotNull(sharesList);

	}

	@Test
	public void testShouldFindShareByDate() {
		// given
		String date = "20130103";
		LocalDate localDate = LocalDate.parse(date);
		// when
		List<ShareTo> sharesList = stockBroker.findSharesByDate(localDate);
		// then
		assertNotNull(sharesList);
	}

	@Test
	public void testShouldBuySharesFromInvestor() {

		// given
		csvReader.readCSV(URL);
		String date = "20130103";
		String name = "KGHM";
		LocalDate localDate = LocalDate.parse(date);
		Map<String, Integer> chosenShares = new HashMap<String, Integer>();
		chosenShares.put(name, 5);
		// when
		MoneyTo receivedMoney = stockBroker.buySharesFromInvestor(chosenShares,
				localDate);
		Double actual = (Double) receivedMoney.getValue();
		// then
		assertEquals((Double) 950.0, actual);
	}

}
