package pl.dominika.stockbroker;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;

public interface StockBroker {
	public static final double COMMISSION = 0.005;
	
	List<ShareTo> findSharesByDate(LocalDate date);

	List<ShareTo> findAll();

	Map<ShareTo, Integer> sellSharesToInvestor(
			Map<String, Integer> chosenShares, LocalDate date);
	MoneyTo buySharesFromInvestor(Map<String,Integer>chosenShares,LocalDate date);
	 Double findPriceByNameAndDate(LocalDate date,String name);
	
}
