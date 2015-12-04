package pl.dominika.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;

public class StrategyRandom implements StrategyInterface {
	@Override
	public MoneyTo chooseCurrency(Set<MoneyTo> moneySet) {
		List<MoneyTo> moneyList = new ArrayList(moneySet);
		return moneyList.get(0);
	}

	@Override
	public Map<String, Integer> chooseSharesToBuy(Set<MoneyTo> moneySet,
			List<ShareTo> listOfSharesAvaliable) {
		MoneyTo money = chooseCurrency(moneySet);
		Map<String, Integer> result = new HashMap<String, Integer>();
		Random random = new Random();
		int chosenIndex = random.nextInt(listOfSharesAvaliable.size() - 1);
		ShareTo shareToBuy = listOfSharesAvaliable.get(chosenIndex);
		String nameOfCompanyToBuy = shareToBuy.getCompanyName();
		double amountOfMoney = money.getValue();
		double priceOfShare = shareToBuy.getPrice();
		int amountToBuy = (int) (amountOfMoney / priceOfShare);
		result.put(nameOfCompanyToBuy, amountToBuy);
		return result;
	}

	@Override
	public Map<String, Integer> chooseSharesToSell(
			Map<String, Integer> ownedShares) {

		Map<String, Integer> result = new HashMap<String, Integer>();
		List<String> sharesList = new ArrayList<String>();

		for (Map.Entry<String, Integer> entry : ownedShares.entrySet()) {
			sharesList.add(entry.getKey());
		}
		String shareToBuy = sharesList.get(0);
		Integer amountToBuy = ownedShares.get(shareToBuy);
		result.put(shareToBuy, amountToBuy);
		return result;
	}

}
