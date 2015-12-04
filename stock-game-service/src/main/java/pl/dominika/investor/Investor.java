package pl.dominika.investor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dominika.stockbroker.StockBroker;
import pl.dominika.strategy.StrategyInterface;
import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;
import pl.dominika.wallet.Wallet;

@Service
public class Investor {

	public static final Logger LOG = Logger.getLogger("Investor");

	@Autowired
	private StockBroker stockBroker;
	@Autowired
	private Wallet wallet;

	private StrategyInterface strategy;

	public void buy(LocalDate date) {
		Set<MoneyTo> moneyAvaliable = wallet.getMoney();
		List<ShareTo> listOfSharesAvaliable = stockBroker
				.findSharesByDate(date);
		Map<String, Integer> chosenShares = strategy.chooseSharesToBuy(
				moneyAvaliable, listOfSharesAvaliable);
		Map<ShareTo, Integer> newShares = stockBroker.sellSharesToInvestor(
				chosenShares, date);

		for (Map.Entry<ShareTo, Integer> entry : newShares.entrySet()) {
			LOG.info("bought " + entry.getKey().getCompanyName()
					+ " with Price " + entry.getKey().getPrice()
					+ " from broker " + " aount " + entry.getValue());
		}
		wallet.addShares(newShares);
		MoneyTo spentMoney = wallet.calculatePrice(newShares);
		LOG.info("spent " + spentMoney + " for shares");
		wallet.deleteMoney(wallet
				.calculateMoneyWithBuyingCommission(spentMoney));
	}

	public void sell(LocalDate date) {

		if (!wallet.getOwnedShares().isEmpty()) {
			Map<String, Integer> chosenShares = strategy
					.chooseSharesToSell(wallet.getOwnedShares());
			MoneyTo receivedMoney = stockBroker.buySharesFromInvestor(
					chosenShares, date);

			for (Map.Entry<String, Integer> entry : chosenShares.entrySet()) {
				LOG.info("sold " + entry.getKey() + " to broker " + " amount "
						+ entry.getValue());
			}
			LOG.info("recived " + receivedMoney + "from broker ");

			wallet.deleteShares(chosenShares);
			wallet.addMoney(wallet
					.calculateMoneyWithSellingCommission(receivedMoney));

		}
	}

	public Set<MoneyTo> calculateTotalValueOfAllResources(LocalDate date) {

		Map<String, Integer> mapOfOwnedShares = wallet.getOwnedShares();

		for (Map.Entry<String, Integer> entry : mapOfOwnedShares.entrySet()) {
			Double currentPrice = stockBroker.findPriceByNameAndDate(date,
					entry.getKey());
			Double valueOfShares = entry.getValue() * currentPrice;
			wallet.addMoney(new MoneyTo("ZL", valueOfShares));
		}
		return wallet.getMoney();

	}

	public StrategyInterface getStrategy() {
		return strategy;
	}

	public void setStrategy(StrategyInterface strategy) {
		this.strategy = strategy;
	}

	public Wallet getWallet() {
		return wallet;
	}

}
