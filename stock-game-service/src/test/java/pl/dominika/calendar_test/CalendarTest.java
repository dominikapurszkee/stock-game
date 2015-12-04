package pl.dominika.calendar_test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.dominika.calendar.CalendarImpl;
import pl.dominika.calendar.MyCalendar;
import pl.dominika.service.impl.CSVReader;
import pl.dominika.wallet.Wallet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../../spring/test-context.xml")
public class CalendarTest {
	private static final String URL = "C:\\Users\\dpurszke\\Desktop\\workspace\\stock-game\\stock-game-service\\src\\main\\resources\\data\\dane.csv";
	@Autowired
	private MyCalendar calendar;
	@Autowired
	private CSVReader csvReader;

	@Test
	public void testShouldAdddates() {

		// given
		csvReader.readCSV(URL);
		// when
		List<LocalDate> dateList = calendar.findAllDates();
		// then
		assertNotNull(dateList);
	}

}
