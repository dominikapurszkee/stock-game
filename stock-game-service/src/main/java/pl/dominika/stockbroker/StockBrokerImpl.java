package pl.dominika.stockbroker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.dominika.dao.DataPriceRepository;
import pl.dominika.entity.DataPriceEntity;
import pl.dominika.mapper.Mapper;
import pl.dominika.service.impl.CSVReader;
import pl.dominika.to.MoneyTo;
import pl.dominika.to.ShareTo;


@Service
@Transactional(readOnly = true)
public class StockBrokerImpl implements StockBroker{
	@Autowired
	private DataPriceRepository dataPriceRepository;
	
	public static final double COMMISSION = 0.005;
	@Override
	public List<ShareTo> findSharesByDate(LocalDate date) {
		
		return Mapper.convertFromEntity2ToList(dataPriceRepository.findDataPricebyDate(date));
				
	}            
	@Override
	public List<ShareTo> findAll() {
		return Mapper.convertFromEntity2ToList(dataPriceRepository.findAll());
	}
	@Override
	public Double findPriceByNameAndDate(LocalDate date,String name){
		return dataPriceRepository.findCurrentPrice(date, name);
	}
	@Override
	public Map<ShareTo,Integer> sellSharesToInvestor( Map<String,Integer>chosenShares,LocalDate date){
		Map<ShareTo,Integer> soldShares=new HashMap<ShareTo,Integer>();
		for (Map.Entry<String, Integer> entry : chosenShares.entrySet()) {
			soldShares.put(new ShareTo(entry.getKey(),findPriceByNameAndDate(date,entry.getKey())), entry.getValue());
	}
	return soldShares;
}
	
	@Override
	public MoneyTo buySharesFromInvestor(Map<String,Integer>chosenShares,LocalDate date){
		double totalPrice=0;
		for (Map.Entry<String, Integer> entry : chosenShares.entrySet()) {
			Double price=(findPriceByNameAndDate(date,entry.getKey())*(entry.getValue()));
			totalPrice+=price;
	}
	return new MoneyTo("ZL", totalPrice);
}
		
}