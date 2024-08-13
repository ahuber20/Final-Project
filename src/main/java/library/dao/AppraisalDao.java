package library.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.Appraisal;


public interface AppraisalDao extends JpaRepository<Appraisal, Long> {

	Set<Appraisal> findAllByAppraisalIn(Set<String> appraisals);

}