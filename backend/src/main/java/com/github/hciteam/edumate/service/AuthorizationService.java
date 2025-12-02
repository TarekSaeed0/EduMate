package com.github.hciteam.edumate.service;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.model.TeamJoinInvite;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.repository.CourseRegistrationRepository;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.TeamJoinInviteRepository;
import com.github.hciteam.edumate.repository.TeamRepository;

@Service("authorizationService")
public class AuthorizationService {
	StudentRepository studentRepository;
	CourseRegistrationRepository registrationRepository;
	TeamRepository teamRepository;
	TeamJoinInviteRepository inviteRepository;

	public AuthorizationService(StudentRepository studentRepository,
			CourseRegistrationRepository registrationRepository,
			TeamRepository teamRepository,
			TeamJoinInviteRepository inviteRepository) {
		this.studentRepository = studentRepository;
		this.registrationRepository = registrationRepository;
		this.teamRepository = teamRepository;
		this.inviteRepository = inviteRepository;
	}

	public boolean isStudentSelf(Long studentId) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		Optional<Student> student = studentRepository.findByUserId(user.getId());
		if (student.isEmpty()) {
			return false;
		}

		return student.get().getId().equals(studentId);
	}

	public boolean isRegistrationOwner(Long registrationId) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		Optional<Student> student = studentRepository.findByUserId(user.getId());
		if (student.isEmpty()) {
			return false;
		}

		Optional<CourseRegistration> registration =
				registrationRepository.findById(registrationId);
		if (registration.isEmpty()) {
			return false;
		}

		return registration.get().getStudent().getId()
				.equals(student.get().getId());
	}

	public boolean isTeamLeader(Long teamId) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		Optional<Student> student = studentRepository.findByUserId(user.getId());
		if (student.isEmpty()) {
			return false;
		}

		Optional<Team> team = teamRepository.findById(teamId);
		if (team.isEmpty()) {
			return false;
		}

		return team.get().getLeader().getId().equals(student.get().getId());
	}

	public boolean isInviteSender(Long inviteId) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		Optional<Student> student = studentRepository.findByUserId(user.getId());
		if (student.isEmpty()) {
			return false;
		}

		Optional<TeamJoinInvite> invite = inviteRepository.findById(inviteId);
		if (invite.isEmpty()) {
			return false;
		}

		return invite.get().getTeam().getLeader().getId()
				.equals(student.get().getId());
	}

	public boolean isInviteRecipient(Long inviteId) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		Optional<Student> student = studentRepository.findByUserId(user.getId());
		if (student.isEmpty()) {
			return false;
		}

		Optional<TeamJoinInvite> invite = inviteRepository.findById(inviteId);
		if (invite.isEmpty()) {
			return false;
		}

		return invite.get().getStudent().getId().equals(student.get().getId());
	}
}
