package com.github.hciteam.edumate.configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.github.hciteam.edumate.dto.StudentDTO;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.model.FAQ;
import com.github.hciteam.edumate.model.FAQCategory;
import com.github.hciteam.edumate.model.Gender;
import com.github.hciteam.edumate.model.Role;
import com.github.hciteam.edumate.model.WeekDay;
import com.github.hciteam.edumate.model.Semester;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.CourseSession;
import com.github.hciteam.edumate.model.CourseSessionType;
import com.github.hciteam.edumate.model.Task;
import com.github.hciteam.edumate.model.Term;
import com.github.hciteam.edumate.model.TimePeriod;
import com.github.hciteam.edumate.model.TimeSlot;
import com.github.hciteam.edumate.model.University;
import com.github.hciteam.edumate.repository.CourseRepository;
import com.github.hciteam.edumate.repository.CourseSessionRepository;
import com.github.hciteam.edumate.repository.FAQCategoryRepository;
import com.github.hciteam.edumate.repository.FAQRepository;
import com.github.hciteam.edumate.repository.RoleRepository;
import com.github.hciteam.edumate.service.UserService;
import jakarta.transaction.Transactional;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.SemesterRepository;
import com.github.hciteam.edumate.repository.TaskRepository;
import com.github.hciteam.edumate.repository.TimePeriodRepository;
import com.github.hciteam.edumate.repository.TimeSlotRepository;
import com.github.hciteam.edumate.repository.UniversityRepository;
import com.github.hciteam.edumate.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
	private final UniversityRepository universityRepository;
	private final RoleRepository roleRepository;
	private final CourseRepository courseRepository;
	private final SemesterRepository semesterRepository;
	private final CourseOfferingRepository offeringRepository;
	private final TaskRepository taskRepository;
	private final FAQCategoryRepository faqCategoryRepository;
	private final FAQRepository faqRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final TimePeriodRepository periodRepository;
	private final TimeSlotRepository slotRepository;
	private final CourseSessionRepository sessionRepository;

	public DataInitializer(UniversityRepository universityRepository,
			RoleRepository roleRepository, CourseRepository courseRepository,
			SemesterRepository semesterRepository,
			CourseOfferingRepository offeringRepository,
			TaskRepository taskRepository,
			FAQCategoryRepository faqCategoryRepository, FAQRepository faqRepository,
			UserRepository userRepository, UserService userService,
			TimePeriodRepository periodRepository, TimeSlotRepository slotRepository,
			CourseSessionRepository sessionRepository) {
		this.universityRepository = universityRepository;
		this.roleRepository = roleRepository;
		this.courseRepository = courseRepository;
		this.semesterRepository = semesterRepository;
		this.offeringRepository = offeringRepository;
		this.taskRepository = taskRepository;
		this.faqCategoryRepository = faqCategoryRepository;
		this.faqRepository = faqRepository;
		this.userRepository = userRepository;
		this.userService = userService;
		this.periodRepository = periodRepository;
		this.slotRepository = slotRepository;
		this.sessionRepository = sessionRepository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		University university = University.builder()
				.name("Faculty of Engineering, Alexandria University").build();

		University persistedUniversity =
				universityRepository.findByName(university.getName())
						.orElseGet(() -> universityRepository.save(university));

		String[] roleNames = {"STUDENT", "COORDINATOR", "ADMINISTRATOR"};
		Arrays.stream(roleNames).forEach(roleName -> {
			if (!roleRepository.existsByName(roleName)) {
				Role role = Role.builder().name(roleName).build();
				roleRepository.save(role);
			}
		});

		// TODO: initialize permissions

		if (!userRepository.existsByEmail("admin@gmail.com")) {
			userService.createUser(new UserDTO(null, "admin@gmail.com", "1234",
					Set.of("ADMINISTRATOR"), null));
		}

		if (!userRepository.existsByEmail("coordinator@gmail.com")) {
			userService.createUser(new UserDTO(null, "coordinator@gmail.com", "1234",
					Set.of("STUDENT", "COORDINATOR"), new StudentDTO(null, null,
							"John Doe", Gender.MALE, "es.john.doe2023@alexu.edu.eg", null)));
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

		Map<String, Course> persistedCourses = new HashMap<>();

		for (Course course : courses) {
			persistedCourses.put(course.getCode(),
					courseRepository.findByCode(course.getCode())
							.orElseGet(() -> courseRepository.save(course)));
		}

		Semester semester = Semester.builder().term(Term.FALL).year(2025)
				.startDate(LocalDate.of(2025, 9, 20)).endDate(LocalDate.of(2026, 1, 3))
				.build();

		Semester persistedSemester = semesterRepository
				.findByTermAndYear(semester.getTerm(), semester.getYear())
				.orElseGet(() -> semesterRepository.save(semester));

		Map<String, CourseOffering> persistedOfferings = new HashMap<>();

		for (Course course : courses) {
			persistedOfferings.put(course.getCode(),
					offeringRepository
							.findBySemesterIdAndCourseId(persistedSemester.getId(),
									persistedCourses.get(course.getCode()).getId())
							.orElseGet(() -> offeringRepository.save(CourseOffering.builder()
									.course(persistedCourses.get(course.getCode()))
									.semester(persistedSemester).build())));
		}

		if (taskRepository.count() == 0) {
			Task[] tasks = {Task.builder().offering(persistedOfferings.get("CSE 213"))
					.title("Sheet 2")
					.requirements(
							"https://drive.google.com/file/d/1lIeYK2_Wl2R8ovUSZlD_I61G2HR51Z3o/view?usp=drive_link")
					.submissionUrl("https://forms.gle/7PdZu7dNPbjdiLJt5")
					.dueDate(LocalDateTime.of(2025, 11, 26, 12, 10)).build(),
					Task.builder().offering(persistedOfferings.get("CSE 233"))
							.title("Sheet 5")
							.requirements(
									"https://drive.google.com/open?id=1GeseBHSR08TXDqpUqxKrit4U0vlgXI2W&usp=drive_fs")
							.submissionUrl("https://forms.gle/kDbyjQprpLziLLo66")
							.dueDate(LocalDateTime.of(2025, 11, 19, 23, 50)).build(),
					Task.builder().offering(persistedOfferings.get("CSE 233"))
							.title("Lab 5")
							.requirements(
									"https://drive.google.com/file/d/18JTDoG6ro1oqCZvwxfNQLBruiJlZH9OV/view?usp=drivesdk")
							.dueDate(LocalDateTime.of(2025, 11, 1, 23, 50)).build(),
					Task.builder().offering(persistedOfferings.get("CSE 214"))
							.title("Sheet 3")
							.requirements(
									"https://drive.google.com/file/d/1h4XWUnYP4FYSk40eKFX7LbmTZqvhiIDH/view?usp=drive_link")
							.dueDate(LocalDateTime.of(2025, 11, 6, 12, 0))
							.notes("This should be submitted through microsoft teams.")
							.build(),
					Task.builder().offering(persistedOfferings.get("CSE 214"))
							.title("Lab 1")
							.requirements(
									"https://drive.google.com/file/d/1NbZzkrRwHxhE_kGl6qz4-wJA6KYWckUt/view?usp=drivesdk")
							.dueDate(LocalDateTime.of(2025, 11, 20, 12, 0))
							.notes("This should be submitted through microsoft teams.")
							.build(),};

			for (Task task : tasks) {
				taskRepository.save(task);
			}
		}

		String[] categoryNames =
				{"General Information", "Contact and Support", "Campus Facilities",
						"Admissions", "Course Registration", "Student Services"};

		Map<String, FAQCategory> persistedCategories = new HashMap<>();
		for (String categoryName : categoryNames) {
			FAQCategory category = faqCategoryRepository.findByName(categoryName)
					.orElseGet(() -> faqCategoryRepository
							.save(FAQCategory.builder().name(categoryName).build()));
			persistedCategories.put(category.getName(), category);
		}

		FAQ[] faqs = {FAQ.builder()
				.question("What are the working hours of the faculty offices?")
				.answer(
						"The faculty offices are open from 8:00 AM to 3:00 PM, Sunday to Thursday. Lecture times may vary by department.")
				.categories(Set.of(persistedCategories.get("General Information"),
						persistedCategories.get("Contact and Support")))
				.build(),
				FAQ.builder().question("Where is the main faculty building located?")
						.answer(
								"The main faculty building is located near the central campus entrance, adjacent to the library.")
						.categories(Set.of(persistedCategories.get("General Information"),
								persistedCategories.get("Campus Facilities")))
						.build(),
				FAQ.builder().question("How can I contact the faculty office?").answer(
						"You can contact the faculty office via email, phone, or in person during working hours.")
						.categories(Set.of(persistedCategories.get("General Information"),
								persistedCategories.get("Contact and Support")))
						.build(),
				FAQ.builder().question("How do I apply to the Faculty of Engineering?")
						.answer(
								"You can apply online via the university portal. Ensure all required documents are submitted before the deadlines.")
						.categories(Set.of(persistedCategories.get("Admissions"))).build(),
				FAQ.builder().question("Are there any entrance exams?").answer(
						"Yes, some departments require an entrance exam or placement test depending on the program.")
						.categories(Set.of(persistedCategories.get("Admissions"))).build(),
				FAQ.builder()
						.question(
								"What departments are available in the Faculty of Engineering?")
						.answer(
								"Departments include Electrical, Mechanical, Civil, Computer, and Chemical Engineering.")
						.categories(Set.of(persistedCategories.get("Course Registration")))
						.build(),
				FAQ.builder()
						.question("Which programs are offered at the undergraduate level?")
						.answer(
								"Each department offers a BSc program with specialized tracks and elective courses.")
						.categories(Set.of(persistedCategories.get("Course Registration"),
								persistedCategories.get("Admissions")))
						.build(),
				FAQ.builder().question("How can I register for courses each semester?")
						.answer(
								"Course registration is completed through the university portal. Consult your academic advisor for guidance.")
						.categories(Set.of(persistedCategories.get("Course Registration"),
								persistedCategories.get("Student Services")))
						.build(),
				FAQ.builder().question("Can I change courses after registration?")
						.answer(
								"Yes, changes are allowed within the first two weeks of the semester.")
						.categories(Set.of(persistedCategories.get("Course Registration")))
						.build(),
				FAQ.builder().question("Who can I contact for academic problems?")
						.answer(
								"Contact your academic advisor or department office for guidance.")
						.categories(Set.of(persistedCategories.get("Contact and Support"),
								persistedCategories.get("Student Services")))
						.build(),
				FAQ.builder().question("Is Wi-Fi available on campus?").answer(
						"Yes, the entire campus has secure Wi-Fi access for students and faculty.")
						.categories(Set.of(persistedCategories.get("Campus Facilities"),
								persistedCategories.get("General Information")))
						.build()};

		for (FAQ faq : faqs) {
			if (faqRepository.findByAnswerContainingIgnoreCase(faq.getQuestion())
					.isEmpty()) {
				faqRepository.save(faq);
			}
		}

		faqCategoryRepository
				.deleteAll(faqCategoryRepository.findUnusedCategories());

		List<TimePeriod> periods = List.of(
				TimePeriod.builder().startTime(LocalTime.of(8, 30))
						.endTime(LocalTime.of(10, 10)).build(),
				TimePeriod.builder().startTime(LocalTime.of(10, 20))
						.endTime(LocalTime.of(12, 0)).build(),
				TimePeriod.builder().startTime(LocalTime.of(12, 10))
						.endTime(LocalTime.of(13, 50)).build(),
				TimePeriod.builder().startTime(LocalTime.of(14, 00))
						.endTime(LocalTime.of(15, 40)).build(),
				TimePeriod.builder().startTime(LocalTime.of(15, 50))
						.endTime(LocalTime.of(17, 30)).build(),
				TimePeriod.builder().startTime(LocalTime.of(17, 40))
						.endTime(LocalTime.of(19, 20)).build());

		List<TimePeriod> persistedPeriods = periods.stream().map(period -> {
			return periodRepository
					.findByStartTimeAndEndTime(period.getStartTime(), period.getEndTime())
					.orElseGet(() -> periodRepository.save(period));
		}).toList();

		Map<WeekDay, List<TimeSlot>> slotMap = new HashMap<>();

		List<WeekDay> weekDays = List.of(WeekDay.values());
		for (WeekDay weekDay : weekDays) {
			slotMap.put(weekDay, persistedPeriods.stream().map(period -> {
				return slotRepository.findByPeriodIdAndWeekDay(period.getId(), weekDay)
						.orElseGet(() -> slotRepository.save(
								TimeSlot.builder().period(period).weekDay(weekDay).build()));
			}).toList());
		}

		CourseSession[] sessions = {
				CourseSession.builder().offering(persistedOfferings.get("CSE 282"))
						.slot(slotMap.get(WeekDay.SATURDAY).get(0)).location("Room 103")
						.type(CourseSessionType.LECTURE).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 233"))
						.slot(slotMap.get(WeekDay.SATURDAY).get(1)).location("Room 103")
						.type(CourseSessionType.LECTURE).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 213"))
						.slot(slotMap.get(WeekDay.SATURDAY).get(2)).location("Room 103")
						.type(CourseSessionType.LECTURE).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 233"))
						.slot(slotMap.get(WeekDay.SATURDAY).get(3))
						.type(CourseSessionType.LAB).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 213"))
						.slot(slotMap.get(WeekDay.SATURDAY).get(3))
						.type(CourseSessionType.LAB).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 233"))
						.slot(slotMap.get(WeekDay.SATURDAY).get(4))
						.type(CourseSessionType.LAB).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 213"))
						.slot(slotMap.get(WeekDay.SATURDAY).get(4))
						.type(CourseSessionType.LAB).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 233"))
						.slot(slotMap.get(WeekDay.SATURDAY).get(5))
						.type(CourseSessionType.LAB).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 213"))
						.slot(slotMap.get(WeekDay.SATURDAY).get(5))
						.type(CourseSessionType.LAB).build(),

				CourseSession.builder().offering(persistedOfferings.get("CSE 214"))
						.slot(slotMap.get(WeekDay.SUNDAY).get(0)).location("Room 103")
						.type(CourseSessionType.TUTORIAL).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 214"))
						.slot(slotMap.get(WeekDay.SUNDAY).get(1))
						.type(CourseSessionType.LAB).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 213"))
						.slot(slotMap.get(WeekDay.SUNDAY).get(2)).location("Room 503")
						.type(CourseSessionType.TUTORIAL).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 223"))
						.slot(slotMap.get(WeekDay.SUNDAY).get(3)).location("Room 103")
						.type(CourseSessionType.LECTURE).build(),

				CourseSession.builder().offering(persistedOfferings.get("CSE 282"))
						.slot(slotMap.get(WeekDay.TUESDAY).get(0)).location("Room 503")
						.type(CourseSessionType.TUTORIAL).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 214"))
						.slot(slotMap.get(WeekDay.TUESDAY).get(1)).location("Room 104")
						.type(CourseSessionType.LECTURE).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 213"))
						.slot(slotMap.get(WeekDay.TUESDAY).get(2)).location("Room 503")
						.type(CourseSessionType.LECTURE).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 223"))
						.slot(slotMap.get(WeekDay.TUESDAY).get(3)).location("Room 503")
						.type(CourseSessionType.TUTORIAL).build(),

				CourseSession.builder().offering(persistedOfferings.get("CSE 233"))
						.slot(slotMap.get(WeekDay.WEDNESDAY).get(0)).location("Room 103")
						.type(CourseSessionType.TUTORIAL).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 223"))
						.slot(slotMap.get(WeekDay.WEDNESDAY).get(1))
						.type(CourseSessionType.LAB).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 223"))
						.slot(slotMap.get(WeekDay.WEDNESDAY).get(2))
						.type(CourseSessionType.LAB).build(),
				CourseSession.builder().offering(persistedOfferings.get("CSE 223"))
						.slot(slotMap.get(WeekDay.WEDNESDAY).get(3))
						.type(CourseSessionType.LAB).build(),};

		for (CourseSession session : sessions) {
			if (sessionRepository.findByOfferingIdAndSlotId(
					session.getOffering().getId(), session.getSlot().getId()).isEmpty()) {
				sessionRepository.save(session);
			}
		}
	}
}
