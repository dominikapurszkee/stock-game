package pl.dominika.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.joda.time.LocalDate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pl.dominika.calendar.CalendarImpl;
import pl.dominika.calendar.MyCalendar;
import pl.dominika.investor.Investor;
import pl.dominika.service.impl.CSVReader;
import pl.dominika.stockbroker.StockBroker;
import pl.dominika.strategy.StrategyInterface;
import pl.dominika.strategy.StrategyRandom;
import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;
import pl.dominika.wallet.Wallet;

public class Game {
	private static final String URL = "C:\\Users\\dpurszke\\Desktop\\workspace\\stock-game\\stock-game-service\\src\\main\\resources\\data\\dane.csv";

	public static final Logger LOG = Logger.getLogger("Game");

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/service-context.xml");

		CSVReader csvReader = (CSVReader) context.getBean(CSVReader.class);
		Investor investor = (Investor) context.getBean(Investor.class);
		MyCalendar calendar = (MyCalendar) context.getBean(MyCalendar.class);

		csvReader.readCSV(URL);

		List<LocalDate> dateList = new ArrayList<LocalDate>();
		dateList = calendar.findAllDates();

		StrategyInterface strategy = new StrategyRandom();
		investor.setStrategy(strategy);
		investor.getWallet().setMoney(new MoneyTo("ZL", 10000.0));

		for (LocalDate localDate : dateList) {
			LOG.info("start of day " + localDate);
			investor.sell(localDate);
			investor.buy(localDate);
			LOG.info("end of day " + localDate);
		}

		Set<MoneyTo> money = investor
				.calculateTotalValueOfAllResources(dateList.get(dateList.size() - 1));
		LOG.info(money.toString());
	}

}