package com.github.hciteam.edumate.configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.github.hciteam.edumate.dto.UserDTO;
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
import com.github.hciteam.edumate.service.UserService;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.SemesterRepository;
import com.github.hciteam.edumate.repository.TaskRepository;
import com.github.hciteam.edumate.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
	private final UserRoleRepository roleRepository;
	private final CourseRepository courseRepository;
	private final SemesterRepository semesterRepository;
	private final CourseOfferingRepository offeringRepository;
	private final TaskRepository taskRepository;
	private final FAQCategoryRepository faqCategoryRepository;
	private final FAQRepository faqRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	public DataInitializer(UserRoleRepository roleRepository,
			CourseRepository courseRepository, SemesterRepository semesterRepository,
			CourseOfferingRepository offeringRepository,
			TaskRepository taskRepository,
			FAQCategoryRepository faqCategoryRepository, FAQRepository faqRepository,
			UserRepository userRepository, UserService userService) {
		this.roleRepository = roleRepository;
		this.courseRepository = courseRepository;
		this.semesterRepository = semesterRepository;
		this.offeringRepository = offeringRepository;
		this.taskRepository = taskRepository;
		this.faqCategoryRepository = faqCategoryRepository;
		this.faqRepository = faqRepository;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@Override
	public void run(String... args) throws Exception {
		String[] roleNames = {"STUDENT", "COORDINATOR", "ADMINISTRATOR"};
		Arrays.stream(roleNames).forEach(roleName -> {
			if (!roleRepository.existsByName(roleName)) {
				UserRole role = UserRole.builder().name(roleName).build();
				roleRepository.save(role);
			}
		});

		if (!userRepository.existsByEmail("admin@gmail.com")) {
			userService.createUser(new UserDTO(null, "admin@gmail.com", "123456",
					Set.of("ADMINISTRATOR"), null));
		}

		Course[] courses = {
				Course.builder().code("CSE 282").name("Human Computer Interaction")
						.credits(2).build(),
				Course.builder().code("HUM x64").name("Communication Skills").credits(2)
						.build(),
				Course.builder().code("CSE 213").name("Numerical Computations")
						.credits(3).build(),
				Course.builder().code("CSE 214").name("Discrete Structures").credits(3)
						.build(),
				Course.builder().code("CSE 223").name("Programming II").credits(3)
						.build(),
				Course.builder().code("CSE 233").name("Computer Organization")
						.credits(3).build(),};

		for (Course course : courses) {
			if (!courseRepository.existsByCode(course.getCode())) {
				courseRepository.save(course);
			}
		}

		Semester semester = Semester.builder().term(Term.FALL).year(2025)
				.startDate(LocalDate.of(2025, 9, 20)).endDate(LocalDate.of(2026, 1, 3))
				.build();

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
				offeringRepository.save(
						CourseOffering.builder().semester(semester).course(course).build());
			}
		}

		if (taskRepository.count() == 0) {
			taskRepository.save(Task.builder()
					.offering(offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 213").get().getId()).get())
					.title("Sheet 2")
					.requirements(
							"https://drive.google.com/file/d/1lIeYK2_Wl2R8ovUSZlD_I61G2HR51Z3o/view?usp=drive_link")
					.submissionUrl("https://forms.gle/7PdZu7dNPbjdiLJt5")
					.dueDate(LocalDateTime.of(2025, 11, 26, 12, 10)).build());

			taskRepository.save(Task.builder()
					.offering(offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 233").get().getId()).get())
					.title("Sheet 5")
					.requirements(
							"https://drive.google.com/open?id=1GeseBHSR08TXDqpUqxKrit4U0vlgXI2W&usp=drive_fs")
					.submissionUrl("https://forms.gle/kDbyjQprpLziLLo66")
					.dueDate(LocalDateTime.of(2025, 11, 19, 23, 50)).build());

			taskRepository.save(Task.builder()
					.offering(offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 233").get().getId()).get())
					.title("Lab 5")
					.requirements(
							"https://drive.google.com/file/d/18JTDoG6ro1oqCZvwxfNQLBruiJlZH9OV/view?usp=drivesdk")
					.dueDate(LocalDateTime.of(2025, 11, 1, 23, 50)).build());

			taskRepository.save(Task.builder()
					.offering(offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 214").get().getId()).get())
					.title("Sheet 3")
					.requirements(
							"https://drive.google.com/file/d/1h4XWUnYP4FYSk40eKFX7LbmTZqvhiIDH/view?usp=drive_link")
					.dueDate(LocalDateTime.of(2025, 11, 6, 12, 0))
					.notes("This should be submitted through microsoft teams.").build());

			taskRepository.save(Task.builder()
					.offering(offeringRepository.findBySemesterIdAndCourseId(semesterId,
							courseRepository.findByCode("CSE 214").get().getId()).get())
					.title("Lab 1")
					.requirements(
							"https://drive.google.com/file/d/1NbZzkrRwHxhE_kGl6qz4-wJA6KYWckUt/view?usp=drivesdk")
					.dueDate(LocalDateTime.of(2025, 11, 20, 12, 0))
					.notes("This should be submitted through microsoft teams.").build());
		}

		FAQCategory generalInformationCategory =
				FAQCategory.builder().name("General Information").build();
		FAQCategory contactAndSupportCategory =
				FAQCategory.builder().name("Contact and Support").build();
		FAQCategory campusFacilitiesCategory =
				FAQCategory.builder().name("Campus Facilities").build();
		FAQCategory admissionsCategory =
				FAQCategory.builder().name("Admissions").build();
		FAQCategory departmentsAndProgramsCategory =
				FAQCategory.builder().name("Departments and Programs").build();
		FAQCategory courseRegistrationCategory =
				FAQCategory.builder().name("Course Registration").build();
		FAQCategory studentServicesCategory =
				FAQCategory.builder().name("Student Services").build();

		FAQCategory[] categories = {generalInformationCategory,
				contactAndSupportCategory, campusFacilitiesCategory, admissionsCategory,
				departmentsAndProgramsCategory, courseRegistrationCategory,
				studentServicesCategory};

		for (FAQCategory category : categories) {
			if (!faqCategoryRepository.existsByName(category.getName())) {
				faqCategoryRepository.save(category);
			}
		}

		FAQ[] faqs = {FAQ.builder()
				.question("What are the working hours of the faculty offices?")
				.answer(
						"The faculty offices are open from 8:00 AM to 3:00 PM, Sunday to Thursday. Lecture times may vary by department.")
				.categories(
						Set.of(generalInformationCategory, contactAndSupportCategory))
				.build(),
				FAQ.builder().question("Where is the main faculty building located?")
						.answer(
								"The main faculty building is located near the central campus entrance, adjacent to the library.")
						.categories(
								Set.of(generalInformationCategory, campusFacilitiesCategory))
						.build(),
				FAQ.builder().question("How can I contact the faculty office?").answer(
						"You can contact the faculty office via email, phone, or in person during working hours.")
						.categories(
								Set.of(generalInformationCategory, contactAndSupportCategory))
						.build(),
				FAQ.builder().question("How do I apply to the Faculty of Engineering?")
						.answer(
								"You can apply online via the university portal. Ensure all required documents are submitted before the deadlines.")
						.categories(Set.of(admissionsCategory)).build(),
				FAQ.builder().question("Are there any entrance exams?").answer(
						"Yes, some departments require an entrance exam or placement test depending on the program.")
						.categories(Set.of(admissionsCategory)).build(),
				FAQ.builder()
						.question(
								"What departments are available in the Faculty of Engineering?")
						.answer(
								"Departments include Electrical, Mechanical, Civil, Computer, and Chemical Engineering.")
						.categories(Set.of(courseRegistrationCategory)).build(),
				FAQ.builder()
						.question("Which programs are offered at the undergraduate level?")
						.answer(
								"Each department offers a BSc program with specialized tracks and elective courses.")
						.categories(Set.of(courseRegistrationCategory, admissionsCategory))
						.build(),
				FAQ.builder().question("How can I register for courses each semester?")
						.answer(
								"Course registration is completed through the university portal. Consult your academic advisor for guidance.")
						.categories(
								Set.of(courseRegistrationCategory, studentServicesCategory))
						.build(),
				FAQ.builder().question("Can I change courses after registration?")
						.answer(
								"Yes, changes are allowed within the first two weeks of the semester.")
						.categories(Set.of(courseRegistrationCategory)).build(),
				FAQ.builder().question("Who can I contact for academic problems?")
						.answer(
								"Contact your academic advisor or department office for guidance.")
						.categories(
								Set.of(contactAndSupportCategory, studentServicesCategory))
						.build(),
				FAQ.builder().question("Is Wi-Fi available on campus?").answer(
						"Yes, the entire campus has secure Wi-Fi access for students and faculty.")
						.categories(
								Set.of(campusFacilitiesCategory, generalInformationCategory))
						.build()};

		for (FAQ faq : faqs) {
			if (faqRepository.findByAnswerContainingIgnoreCase(faq.getQuestion())
					.isEmpty()) {
				faqRepository.save(faq);
			}
		}
	}
}
