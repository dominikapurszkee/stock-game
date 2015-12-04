package pl.dominika.wallet;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;

public interface Wallet {
	void addShares(Map<ShareTo, Integer> boughtShares);

	void deleteShares(Map<String, Integer> soldShares);

	Map<String, Integer> getOwnedShares();

	void addMoney(MoneyTo addedMoney);

	void deleteMoney(MoneyTo deletedMoney);

	Set<MoneyTo> getMoney();

	void setMoney(MoneyTo money);

	MoneyTo calculatePrice(Map<ShareTo, Integer> boughtShares);

	MoneyTo calculateMoneyWithSellingCommission(MoneyTo receivedMoney);

	MoneyTo calculateMoneyWithBuyingCommission(MoneyTo receivedMoney);
}
