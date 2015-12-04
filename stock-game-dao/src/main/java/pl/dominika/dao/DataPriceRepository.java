package pl.dominika.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.dominika.entity.DataPriceEntity;


@Repository
public interface DataPriceRepository extends JpaRepository<DataPriceEntity, Long>{
	  @Query("select dataPrice from DataPriceEntity dataPrice where dataPrice.date=:date")
	    public List<DataPriceEntity> findDataPricebyDate(@Param("date")LocalDate date);
	  @Query("select dataPrice from DataPriceEntity dataPrice ")
	    public List<DataPriceEntity> findAll();
	  @Query("select dataPrice.price from DataPriceEntity dataPrice join dataPrice.company company "
		   		+ "where dataPrice.date=:date and company.name = :company")
	  public Double findCurrentPrice(@Param("date")LocalDate date,@Param("company")String company);
	 
}
