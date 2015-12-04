package pl.dominika.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.dominika.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>{
	
}
