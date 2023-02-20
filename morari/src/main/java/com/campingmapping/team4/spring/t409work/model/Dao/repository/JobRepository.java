package com.campingmapping.team4.spring.t409work.model.Dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campingmapping.team4.spring.t409work.model.entity.JobBean;

public interface JobRepository extends JpaRepository<JobBean, Integer> {

	@Query(value = "from JobBean where job like concat('%',?1,'%')")
	public List<JobBean> findByJobisLike(String job);

	@Query(value = "from JobBean where type like concat('%',?1,'%')")
	public List<JobBean> findByTypeisLike(String type);

	@Query(value = "SELECT * FROM job WHERE rackid IN (SELECT rackid FROM resume_job WHERE rackid = :rackid)", nativeQuery = true)
	List<JobBean> findByResume(@Param("rackid") Integer rackid);

	@Modifying
	@Query(value = "ALTER TABLE resume_job NOCHECK CONSTRAINT ALL", nativeQuery = true)
	void disableForeignKeyConstraints();

	@Modifying
	@Query(value = "ALTER TABLE resume_job CHECK CONSTRAINT ALL", nativeQuery = true)
	void enableForeignKeyConstraints();

}
