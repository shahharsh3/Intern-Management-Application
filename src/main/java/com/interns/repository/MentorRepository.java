package com.interns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.interns.entity.Mentor;

public interface MentorRepository extends CrudRepository<Mentor, Integer>
{
    // add methods if required
	@Modifying
	@Transactional
//	Integer projectMentored(@Param("numberOfProjectsMentored") Integer noProjectMentored);
	public List<Mentor> findByNumberOfProjectsMentored(Integer numberOfProjectsMentored);
}
