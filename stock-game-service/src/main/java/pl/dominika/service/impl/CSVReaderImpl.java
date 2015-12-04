package pl.dominika.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.dominika.dao.DataPriceRepository;
import pl.dominika.entity.CompanyEntity;
import pl.dominika.entity.DataPriceEntity;

@Service("stockBroker")
public class CSVReaderImpl implements CSVReader {
	@Autowired
	private DataPriceRepository dataPriceRepository;

	@Override
	@Transactional
	public void readCSV(String filePath) {
		BufferedReader bufferedReader = null;

		try {
			String newLine;
			bufferedReader = new BufferedReader(new FileReader(filePath));

			Map<String, CompanyEntity> companyMap = new HashMap<String, CompanyEntity>();

			while ((newLine = bufferedReader.readLine()) != null) {

				List<String> properties = crunchifyCSVtoArrayList(newLine);
				convertArray2Objects(properties, companyMap);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException crunchifyException) {
				crunchifyException.printStackTrace();
			}
		}

	}

	private void convertArray2Objects(List<String> properties,
			Map<String, CompanyEntity> companyMap) {
		CompanyEntity company = null;
		String companyName = properties.get(0);
		String date = properties.get(1);

		String price = properties.get(2);

		if (companyMap.containsKey(companyName)) {
			company = companyMap.get(companyName);

		} else {
			company = new CompanyEntity();
			company.setName(companyName);

		}

		DataPriceEntity dataPrice = new DataPriceEntity();

		dataPrice.setCompany(company);
		double doublePrice = Double.parseDouble(price);

		dataPrice.setPrice(doublePrice);

		LocalDate localDate = LocalDate.parse(date);

		dataPrice.setDate(localDate);
		DataPriceEntity saved = dataPriceRepository.save(dataPrice);

		System.out.println(company.getName());

		if (!companyMap.containsKey(companyName)) {
			companyMap.put(companyName, saved.getCompany());
		}

	}

	private ArrayList<String> crunchifyCSVtoArrayList(String crunchifyCSV) {
		ArrayList<String> crunchifyResult = new ArrayList<String>();

		if (crunchifyCSV != null) {
			String[] splitData = crunchifyCSV.split(",");
			for (int i = 0; i < splitData.length; i++) {
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					crunchifyResult.add(splitData[i].trim());
				}
			}
		}

		return crunchifyResult;
	}

}
