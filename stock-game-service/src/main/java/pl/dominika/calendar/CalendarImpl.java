package pl.dominika.calendar;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dominika.dao.DataPriceRepository;
import pl.dominika.entity.DataPriceEntity;

@Service
public class CalendarImpl implements MyCalendar {

	@Autowired
	private DataPriceRepository dataPriceRepository;

	@Override
	public List<LocalDate> findAllDates() {
		Set<LocalDate> datesSet = new TreeSet<LocalDate>();
		for (DataPriceEntity dataPriceEntity : dataPriceRepository.findAll()) {
			datesSet.add(dataPriceEntity.getDate());
		}
		List<LocalDate> datesList = new LinkedList<LocalDate>(datesSet);
		return datesList;
	}

}