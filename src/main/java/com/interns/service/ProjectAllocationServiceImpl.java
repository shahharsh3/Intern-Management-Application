package com.interns.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interns.dto.MentorDTO;
import com.interns.dto.ProjectDTO;
import com.interns.entity.Mentor;
import com.interns.entity.Project;
import com.interns.exception.InternException;
import com.interns.repository.MentorRepository;
import com.interns.repository.ProjectRepository;

@Service(value = "projectService")
@Transactional	
public class ProjectAllocationServiceImpl implements ProjectAllocationService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private MentorRepository mentorRepository;

	@Override
	public Integer allocateProject(ProjectDTO project) throws InternException {
	Optional<Mentor> optional = mentorRepository.findById(project.getMentorDTO().getMentorId());
	Mentor mentor = optional.orElseThrow(() -> new InternException("Service.MENTOR_NOT_FOUND"));
	if (mentor.getNumberOfProjectsMentored() >= 3) {
		throw new InternException("Service.CANNOT_ALLOCATE_PROJECT");
	} 

		
		Project projectEntity = new Project();
		projectEntity.setIdeaOwner(project.getIdeaOwner());
		projectEntity.setProjectName(project.getProjectName());
		projectEntity.setReleaseDate(project.getReleaseDate());
		projectEntity.setMentor(mentor);
		mentor.setNumberOfProjectsMentored(mentor.getNumberOfProjectsMentored()+1);
		projectRepository.save(projectEntity);
		return projectEntity.getProjectId();
	}

	
	@Override
	public List<MentorDTO> getMentors(Integer numberOfProjectsMentored) throws InternException {
		List<Mentor> mentorList = mentorRepository.findByNumberOfProjectsMentored(numberOfProjectsMentored);
		if (mentorList.isEmpty()) {
			throw new InternException("Service.MENTOR_NOT_FOUND");
		}	
		
		return mentorList.stream().map(e-> new MentorDTO(e.getMentorId(), e.getMentorName(), e.getNumberOfProjectsMentored())).collect(Collectors.toList());
		
	}


	@Override
	public void updateProjectMentor(Integer projectId, Integer mentorId) throws InternException {
		Optional<Mentor> optional= mentorRepository.findById(mentorId);
		Mentor mentor = optional.orElseThrow(()-> new InternException("Service.MENTOR_NOT_FOUND"));
		if (mentor.getNumberOfProjectsMentored() >= 3) {
			throw new InternException("Service.CANNOT_ALLOCATE_PROJECT");
		} 
		Optional<Project> projectOptional = projectRepository.findById(projectId);
		Project project = projectOptional.orElseThrow(()-> new InternException("Service.PROJECT_NOT_FOUND"));
		project.setMentor(mentor);
		mentor.setNumberOfProjectsMentored(mentor.getNumberOfProjectsMentored()+1);
		
	}

	@Override
	public void deleteProject(Integer projectId) throws InternException {
		Optional<Project> optional = projectRepository.findById(projectId);
		Project project = optional.orElseThrow(()-> new InternException("Service.PROJECT_NOT_FOUND"));
		Mentor mentor = project.getMentor();
		if ( mentor != null) {
			project.setMentor(null);
			mentor.setNumberOfProjectsMentored(mentor.getNumberOfProjectsMentored()-1);
		}
		projectRepository.delete(project);
	}
}