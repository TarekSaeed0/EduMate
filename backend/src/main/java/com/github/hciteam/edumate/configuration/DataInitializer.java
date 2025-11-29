package com.github.hciteam.edumate.configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.model.FAQ;
import com.github.hciteam.edumate.model.FAQCategory;
import com.github.hciteam.edumate.model.UserRole;
import com.github.hciteam.edumate.model.Semester;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.Task;
import com.github.hciteam.edumate.model.Term;
import com.github.hciteam.edumate.repository.CourseRepository;
import com.github.hciteam.edumate.repository.FAQCategoryRepository;
import com.github.hciteam.edumate.repository.FAQRepository;
import com.github.hciteam.edumate.repository.UserRoleRepository;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.SemesterRepository;
import com.github.hciteam.edumate.repository.TaskRepository;

@Component
public class DataInitializer implements CommandLineRunner {
	private final UserRoleRepository roleRepository;
	private final CourseRepository courseRepository;
	private final SemesterRepository semesterRepository;
	private final CourseOfferingRepository offeringRepository;
	private final TaskRepository taskRepository;
	private final FAQCategoryRepository faqCategoryRepository;
	private final FAQRepository faqRepository;

	public DataInitializer(UserRoleRepository roleRepository,
			CourseRepository courseRepository, SemesterRepository semesterRepository,
			CourseOfferingRepository offeringRepository,
			TaskRepository taskRepository,
			FAQCategoryRepository faqCategoryRepository,
			FAQRepository faqRepository) {
		this.roleRepository = roleRepository;
		this.courseRepository = courseRepository;
		this.semesterRepository = semesterRepository;
		this.offeringRepository = offeringRepository;
		this.taskRepository = taskRepository;
		this.faqCategoryRepository = faqCategoryRepository;
		this.faqRepository = faqRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		String[] roleNames = {"STUDENT", "COORDINATOR", "ADMINISTRATOR"};
		Arrays.stream(roleNames).forEach(roleName -> {
			if (!roleRepository.existsByName(roleName)) {
				UserRole role = new UserRole(null, roleName);
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

			if (!offeringRepository.existsBySemesterIdAndCourseId(semesterId,
					courseId)) {
				offeringRepository
						.save(new CourseOffering(null, semester, course, null, null));
			}
		}

		if (taskRepository.count() == 0) {
			taskRepository.save(new Task(null,
					offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 213").get().getId()).get(),
					"Sheet 2",
					"https://drive.google.com/file/d/1lIeYK2_Wl2R8ovUSZlD_I61G2HR51Z3o/view?usp=drive_link",
					"https://forms.gle/7PdZu7dNPbjdiLJt5",
					LocalDateTime.of(2025, 11, 26, 12, 10), null, null));

			taskRepository.save(new Task(null,
					offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 233").get().getId()).get(),
					"Sheet 5",
					"https://drive.google.com/open?id=1GeseBHSR08TXDqpUqxKrit4U0vlgXI2W&usp=drive_fs",
					"https://forms.gle/kDbyjQprpLziLLo66",
					LocalDateTime.of(2025, 11, 19, 23, 50), null, null));

			taskRepository.save(new Task(null,
					offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 233").get().getId()).get(),
					"Lab 5",
					"https://drive.google.com/file/d/18JTDoG6ro1oqCZvwxfNQLBruiJlZH9OV/view?usp=drivesdk",
					null, LocalDateTime.of(2025, 11, 1, 23, 50), null, null));

			taskRepository.save(new Task(null,
					offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 214").get().getId()).get(),
					"Sheet 3",
					"https://drive.google.com/file/d/1h4XWUnYP4FYSk40eKFX7LbmTZqvhiIDH/view?usp=drive_link",
					null, LocalDateTime.of(2025, 11, 6, 12, 0),
					"This should be submitted through microsoft teams.", null));

			taskRepository.save(new Task(null,
					offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 214").get().getId()).get(),
					"Lab 1",
					"https://drive.google.com/file/d/1NbZzkrRwHxhE_kGl6qz4-wJA6KYWckUt/view?usp=drivesdk",
					null, LocalDateTime.of(2025, 11, 20, 12, 0),
					"This should be submitted through microsoft teams.", null));
		}

		FAQCategory generalInformationCategory =
				new FAQCategory(null, "General Information", null);
		FAQCategory contactAndSupportCategory =
				new FAQCategory(null, "Contact and Support", null);
		FAQCategory campusFacilitiesCategory =
				new FAQCategory(null, "Campus Facilities", null);
		FAQCategory admissionsCategory = new FAQCategory(null, "Admissions", null);
		FAQCategory departmentsAndProgramsCategory =
				new FAQCategory(null, "Departments and Programs", null);
		FAQCategory courseRegistrationCategory =
				new FAQCategory(null, "Course Registration", null);
		FAQCategory studentServicesCategory =
				new FAQCategory(null, "Student Services", null);

		FAQCategory[] categories = {generalInformationCategory,
				contactAndSupportCategory, campusFacilitiesCategory, admissionsCategory,
				departmentsAndProgramsCategory, courseRegistrationCategory,
				studentServicesCategory};

		for (FAQCategory category : categories) {
			if (!faqCategoryRepository.existsByName(category.getName())) {
				faqCategoryRepository.save(category);
			}
		}

		FAQ[] faqs = {new FAQ(null,
				"What are the working hours of the faculty offices?",
				"The faculty offices are open from 8:00 AM to 3:00 PM, Sunday to Thursday. Lecture times may vary by department.",
				Set.of(generalInformationCategory, contactAndSupportCategory)),
				new FAQ(null, "Where is the main faculty building located?",
						"The main faculty building is located near the central campus entrance, adjacent to the library.",
						Set.of(generalInformationCategory, campusFacilitiesCategory)),
				new FAQ(null, "How can I contact the faculty office?",
						"You can contact the faculty office via email, phone, or in person during working hours.",
						Set.of(generalInformationCategory, contactAndSupportCategory)),
				new FAQ(null, "How do I apply to the Faculty of Engineering?",
						"You can apply online via the university portal. Ensure all required documents are submitted before the deadlines.",
						Set.of(admissionsCategory)),
				new FAQ(null, "Are there any entrance exams?",
						"Yes, some departments require an entrance exam or placement test depending on the program.",
						Set.of(admissionsCategory)),
				new FAQ(null,
						"What departments are available in the Faculty of Engineering?",
						"Departments include Electrical, Mechanical, Civil, Computer, and Chemical Engineering.",
						Set.of(courseRegistrationCategory)),
				new FAQ(null, "Which programs are offered at the undergraduate level?",
						"Each department offers a BSc program with specialized tracks and elective courses.",
						Set.of(courseRegistrationCategory, admissionsCategory)),
				new FAQ(null, "How can I register for courses each semester?",
						"Course registration is completed through the university portal. Consult your academic advisor for guidance.",
						Set.of(courseRegistrationCategory, studentServicesCategory)),
				new FAQ(null, "Can I change courses after registration?",
						"Yes, changes are allowed within the first two weeks of the semester.",
						Set.of(courseRegistrationCategory)),
				new FAQ(null, "Who can I contact for academic problems?",
						"Contact your academic advisor or department office for guidance.",
						Set.of(contactAndSupportCategory, studentServicesCategory)),
				new FAQ(null, "Is Wi-Fi available on campus?",
						"Yes, the entire campus has secure Wi-Fi access for students and faculty.",
						Set.of(campusFacilitiesCategory, generalInformationCategory))};

		for (FAQ faq : faqs) {
			if (faqRepository.findByAnswerContainingIgnoreCase(faq.getQuestion())
					.isEmpty()) {
				faqRepository.save(faq);
			}
		}
	}
}
