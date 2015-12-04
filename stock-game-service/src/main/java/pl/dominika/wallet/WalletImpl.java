package pl.dominika.wallet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import pl.dominika.exception.GameOverException;
import pl.dominika.stockbroker.StockBroker;
import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;

@Component
public class WalletImpl implements Wallet {

	private Set<MoneyTo> moneySet = new HashSet<MoneyTo>();
	private Map<String, Integer> mapOfOwnedShares = new HashMap<String, Integer>();

	public WalletImpl() {
	}

	@Override
	public void addShares(Map<ShareTo, Integer> boughtShares) {
		for (Map.Entry<ShareTo, Integer> entry : boughtShares.entrySet()) {
			String boughtCompany = entry.getKey().getCompanyName();
			if (mapOfOwnedShares.containsKey(boughtCompany)) {
				Integer totalAmount = mapOfOwnedShares.get(boughtCompany)
						+ boughtShares.get(entry.getKey());
				mapOfOwnedShares.put(boughtCompany, totalAmount);
			} else {
				Integer addedAmount = boughtShares.get(entry.getKey());
				mapOfOwnedShares.put(entry.getKey().getCompanyName(),
						addedAmount);
			}
		}

	}

	@Override
	public void deleteShares(Map<String, Integer> soldShares) {
		for (Map.Entry<String, Integer> entry : soldShares.entrySet()) {
			String nameToDelete = entry.getKey();
			Integer existingAmount = mapOfOwnedShares.get(nameToDelete);
			Integer deletedAmount = soldShares.get(nameToDelete);
			Integer totalAmount = existingAmount - deletedAmount;
			mapOfOwnedShares.put(nameToDelete, totalAmount);

			if (totalAmount == 0) {
				mapOfOwnedShares.remove(nameToDelete);
			}
		}
	}

	
	
	
	@Override
	public MoneyTo calculatePrice(Map<ShareTo, Integer> boughtShares) {

		double totalPrice = 0;
		for (Map.Entry<ShareTo, Integer> entry : boughtShares.entrySet()) {
			Double price = entry.getKey().getPrice();
			totalPrice = totalPrice + (price * entry.getValue());
		}

		return new MoneyTo("ZL", totalPrice);
	}

	
	
	
	@Override
	public void addMoney(MoneyTo addedMoney) {

		for (MoneyTo money : moneySet) {
			if (money.getCurrency().equals(addedMoney.getCurrency())) {
				double totalValue = addedMoney.getValue() + money.getValue();
				money.setValue(totalValue);
			} else {
				moneySet.add(addedMoney);
			}
		}
	}

	@Override
	public void deleteMoney(MoneyTo deletedMoney) {
		for (MoneyTo money : moneySet) {
			if (money.equals(deletedMoney)) {
				double totalValue = money.getValue() - deletedMoney.getValue();
				money.setValue(totalValue);
			}
			else {
				throw new GameOverException("Gracza zabila mafia");
			}

		}
	}
	
	@Override
public MoneyTo calculateMoneyWithSellingCommission(MoneyTo receivedMoney){
	return new MoneyTo(receivedMoney.getCurrency(),receivedMoney.getValue() *(1-StockBroker.COMMISSION));			
}
	
	@Override
	public MoneyTo calculateMoneyWithBuyingCommission(MoneyTo receivedMoney){
		return new MoneyTo(receivedMoney.getCurrency(),receivedMoney.getValue() *(1+StockBroker.COMMISSION));			
	}
	
	@Override
	public Set<MoneyTo> getMoney() {
		return moneySet;
	}

	@Override
	public void setMoney(MoneyTo money) {
		moneySet.add(money);
	}

	@Override
	public Map<String, Integer> getOwnedShares() {
		return mapOfOwnedShares;
	}
}
