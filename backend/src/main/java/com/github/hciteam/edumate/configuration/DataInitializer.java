package com.github.hciteam.edumate.configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.github.hciteam.edumate.entity.Course;
import com.github.hciteam.edumate.entity.FAQ;
import com.github.hciteam.edumate.entity.FAQCategory;
import com.github.hciteam.edumate.entity.Role;
import com.github.hciteam.edumate.entity.Semester;
import com.github.hciteam.edumate.entity.SemesterCourse;
import com.github.hciteam.edumate.model.Term;
import com.github.hciteam.edumate.repository.CourseRepository;
import com.github.hciteam.edumate.repository.FAQCategoryRepository;
import com.github.hciteam.edumate.repository.FAQRepository;
import com.github.hciteam.edumate.repository.RoleRepository;
import com.github.hciteam.edumate.repository.SemesterCourseRepository;
import com.github.hciteam.edumate.repository.SemesterRepository;

@Component
public class DataInitializer implements CommandLineRunner {
	private final RoleRepository roleRepository;
	private final CourseRepository courseRepository;
	private final SemesterRepository semesterRepository;
	private final SemesterCourseRepository semesterCourseRepository;
	private final FAQCategoryRepository faqCategoryRepository;
	private final FAQRepository faqRepository;

	public DataInitializer(RoleRepository roleRepository,
			CourseRepository courseRepository, SemesterRepository semesterRepository,
			SemesterCourseRepository semesterCourseRepository,
			FAQCategoryRepository faqCategoryRepository,
			FAQRepository faqRepository) {
		this.roleRepository = roleRepository;
		this.courseRepository = courseRepository;
		this.semesterRepository = semesterRepository;
		this.semesterCourseRepository = semesterCourseRepository;
		this.faqCategoryRepository = faqCategoryRepository;
		this.faqRepository = faqRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		String[] roleNames = {"STUDENT", "COORDINATOR", "ADMINISTRATOR"};
		Arrays.stream(roleNames).forEach(roleName -> {
			if (!roleRepository.existsByName(roleName)) {
				Role role = new Role(null, roleName);
				roleRepository.save(role);
			}
		});

		Course[] courses =
				{new Course(null, "CSE 282", "Human Computer Interaction", 2, null),
						new Course(null, "HUM x64", "Communication Skills", 2, null),
						new Course(null, "CSE 213", "Numerical Computations", 3, null),
						new Course(null, "CSE 214", "Discrete Structures", 3, null),
						new Course(null, "CSE 223", "Programming II", 3, null),
						new Course(null, "CSE 233", "Computer Organization", 3, null),};

		for (Course course : courses) {
			if (!courseRepository.existsByCode(course.getCode())) {
				courseRepository.save(course);
			}
		}

		Semester semester = new Semester(null, Term.FALL, 2025,
				LocalDate.of(2025, 9, 20), LocalDate.of(2026, 1, 3), null);

		if (!semesterRepository.existsByTermAndYear(semester.getTerm(),
				semester.getYear())) {
			semesterRepository.save(semester);
		}

		Long semesterId =
				semesterRepository.findByTermAndYear(Term.FALL, 2025).get().getId();

		for (Course course : courses) {
			Long courseId =
					courseRepository.findByCode(course.getCode()).get().getId();

			if (!semesterCourseRepository.existsBySemesterIdAndCourseId(semesterId,
					courseId)) {
				semesterCourseRepository
						.save(new SemesterCourse(null, semester, course, null, null));
			}
		}

		FAQCategory firstYearCategory = new FAQCategory(null, "First Year", null);
		FAQCategory secondYearCategory = new FAQCategory(null, "Second Year", null);
		FAQCategory summerCategory = new FAQCategory(null, "Summer", null);
		FAQCategory studyCategory = new FAQCategory(null, "Study", null);
		FAQCategory sheetsCategory = new FAQCategory(null, "Sheets", null);
		FAQCategory examsCategory = new FAQCategory(null, "Exams", null);
		FAQCategory midtermCategory = new FAQCategory(null, "Midterm", null);

		FAQCategory[] categories =
				{firstYearCategory, secondYearCategory, summerCategory, studyCategory,
						sheetsCategory, examsCategory, midtermCategory};

		for (FAQCategory category : categories) {
			if (!faqCategoryRepository.existsByName(category.getName())) {
				faqCategoryRepository.save(category);
			}
		}

		FAQ[] faqs =
				{new FAQ(null, "Question 1.", "Answer 1.", Set.of(firstYearCategory)),
						new FAQ(null, "Question 2.", "Answer 2.",
								Set.of(secondYearCategory, summerCategory)),
						new FAQ(null, "Question 3.", "Answer 3.",
								Set.of(studyCategory, sheetsCategory)),
						new FAQ(null, "Question 4.", "Answer 4.",
								Set.of(studyCategory, examsCategory, midtermCategory)),};

		for (FAQ faq : faqs) {
			if (faqRepository.findByAnswerContainingIgnoreCase(faq.getQuestion())
					.isEmpty()) {
				faqRepository.save(faq);
			}
		}
	}
}
