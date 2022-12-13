package com.intern.internsdata;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.interns.dto.MentorDTO;
import com.interns.dto.ProjectDTO;
import com.interns.entity.Mentor;
import com.interns.exception.InternException;
import com.interns.repository.MentorRepository;
import com.interns.service.ProjectAllocationService;
import com.interns.service.ProjectAllocationServiceImpl;

@SpringBootTest
public class InternsApplicationTests {

	@Mock
	private MentorRepository mentorRepository;

	@InjectMocks
	private ProjectAllocationService projectAllocationService = new ProjectAllocationServiceImpl();

	@Test
	public void allocateProjectCannotAllocateTest() throws Exception {
		Mentor mentor = new Mentor();
		mentor.setMentorId(1111);
		mentor.setMentorName("Harsh");
		mentor.setNumberOfProjectsMentored(3);
		Optional<Mentor> optional = Optional.ofNullable(mentor);
		Mockito.when(mentorRepository.findById(Mockito.anyInt())).thenReturn(optional);
		
		ProjectDTO projectDTO = new ProjectDTO();
		MentorDTO mentorDTO = new MentorDTO(1111, null, null);
		projectDTO.setIdeaOwner(1000);
		projectDTO.setMentorDTO(mentorDTO);
		projectDTO.setProjectName("My TP");
		
		InternException ex = Assertions.assertThrows(InternException.class, ()-> projectAllocationService.allocateProject(projectDTO));
		Assertions.assertEquals("Service.CANNOT_ALLOCATE_PROJECT", ex.getMessage());

	}

	@Test
	public void allocateProjectMentorNotFoundTest() throws Exception {
		Mockito.when(mentorRepository.findById(1111)).thenReturn(Optional.ofNullable(null));

	}
}
