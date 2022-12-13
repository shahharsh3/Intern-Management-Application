package com.interns.service;

import java.util.List;

import com.interns.dto.MentorDTO;
import com.interns.dto.ProjectDTO;
import com.interns.exception.InternException;

public interface ProjectAllocationService {

	public Integer allocateProject(ProjectDTO projectAllocation) throws InternException;

	public List<MentorDTO> getMentors(Integer numberOfProjectsMentored) throws InternException;
	
	public void updateProjectMentor(Integer projectId, Integer mentorId) throws InternException;
	
	public void deleteProject(Integer projectId) throws InternException;
}
