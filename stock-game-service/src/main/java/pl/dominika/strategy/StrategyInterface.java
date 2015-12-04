package pl.dominika.strategy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;

public interface StrategyInterface {

	MoneyTo chooseCurrency(Set<MoneyTo> moneySet);

	Map<String, Integer> chooseSharesToBuy(Set<MoneyTo> money,
			List<ShareTo> listOfSharesAvaliable);

	Map<String, Integer> chooseSharesToSell(Map<String, Integer> chosenShares);
}
