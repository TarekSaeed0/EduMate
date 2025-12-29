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
import com.github.hciteam.edumate.dto.UniversityDTO;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.model.FAQ;
import com.github.hciteam.edumate.model.FAQCategory;
import com.github.hciteam.edumate.model.Permission;
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
import com.github.hciteam.edumate.repository.PermissionRepository;
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
	private final PermissionRepository permissionRepository;
	private final RoleRepository roleRepository;
	private final UniversityRepository universityRepository;
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

	public DataInitializer(PermissionRepository permissionRepository,
			RoleRepository roleRepository, UniversityRepository universityRepository,
			CourseRepository courseRepository, SemesterRepository semesterRepository,
			CourseOfferingRepository offeringRepository,
			TaskRepository taskRepository,
			FAQCategoryRepository faqCategoryRepository, FAQRepository faqRepository,
			UserRepository userRepository, UserService userService,
			TimePeriodRepository periodRepository, TimeSlotRepository slotRepository,
			CourseSessionRepository sessionRepository) {
		this.permissionRepository = permissionRepository;
		this.roleRepository = roleRepository;
		this.universityRepository = universityRepository;
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
		University university =
				new University("Faculty of Engineering, Alexandria University");

		University persistedUniversity =
				universityRepository.findByName(university.getName())
						.orElseGet(() -> universityRepository.save(university));

		String[] permissionNames =
				{"COURSE_CREATE", "COURSE_READ", "COURSE_UPDATE", "COURSE_DELETE",};

		Map<String, Permission> persistedPermissions = new HashMap<>();
		for (String permissionName : permissionNames) {
			Permission permission =
					permissionRepository.findByName(permissionName).orElseGet(
							() -> permissionRepository.save(new Permission(permissionName)));
			persistedPermissions.put(permission.getName(), permission);
		}

		String[] roleNames = {"STUDENT", "COORDINATOR", "ADMINISTRATOR"};
		Arrays.stream(roleNames).forEach(roleName -> {
			if (!roleRepository.existsByName(roleName)) {
				Role role = new Role(roleName, null);
				roleRepository.save(role);
			}
		});

		if (!userRepository.existsByEmail("admin@gmail.com")) {
			userService.createUser(new UserDTO(null, "admin@gmail.com", "1234",
					Set.of("ADMINISTRATOR"), null));
		}

		if (!userRepository.existsByEmail("coordinator@gmail.com")) {
			userService
					.createUser(
							new UserDTO(null, "coordinator@gmail.com", "1234",
									Set.of("STUDENT", "COORDINATOR"),
									new StudentDTO(null,
											new UniversityDTO(persistedUniversity.getId(),
													persistedUniversity.getName(), null),
											"John Doe", null)));
		}

		Course[] courses = {
				new Course(persistedUniversity, "CSE 282", "Human Computer Interaction",
						2),
				new Course(persistedUniversity, "HUM x64", "Communication Skills", 2),
				new Course(persistedUniversity, "CSE 213", "Numerical Computations", 3),
				new Course(persistedUniversity, "CSE 214", "Discrete Structures", 3),
				new Course(persistedUniversity, "CSE 223", "Programming II", 3),
				new Course(persistedUniversity, "CSE 233", "Computer Organization",
						3),};

		Map<String, Course> persistedCourses = new HashMap<>();

		for (Course course : courses) {
			persistedCourses.put(course.getCode(),
					courseRepository.findByCode(course.getCode())
							.orElseGet(() -> courseRepository.save(course)));
		}

		Semester semester = new Semester(Term.FALL, 2025, LocalDate.of(2025, 9, 20),
				LocalDate.of(2026, 1, 3));

		Semester persistedSemester = semesterRepository
				.findByTermAndYear(semester.getTerm(), semester.getYear())
				.orElseGet(() -> semesterRepository.save(semester));

		Map<String, CourseOffering> persistedOfferings = new HashMap<>();

		for (Course course : courses) {
			persistedOfferings.put(course.getCode(),
					offeringRepository
							.findBySemesterIdAndCourseId(persistedSemester.getId(),
									persistedCourses.get(course.getCode()).getId())
							.orElseGet(() -> offeringRepository.save(new CourseOffering(
									persistedCourses.get(course.getCode()), persistedSemester))));
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
			FAQCategory category =
					faqCategoryRepository.findByName(categoryName).orElseGet(
							() -> faqCategoryRepository.save(new FAQCategory(categoryName)));
			persistedCategories.put(category.getName(), category);
		}

		FAQ[] faqs = {new FAQ("What are the working hours of the faculty offices?",
				"The faculty offices are open from 8:00 AM to 3:00 PM, Sunday to Thursday. Lecture times may vary by department.",
				Set.of(persistedCategories.get("General Information"),
						persistedCategories.get("Contact and Support"))),
				new FAQ("Where is the main faculty building located?",
						"The main faculty building is located near the central campus entrance, adjacent to the library.",
						Set.of(persistedCategories.get("General Information"),
								persistedCategories.get("Campus Facilities"))),
				new FAQ("How can I contact the faculty office?",
						"You can contact the faculty office via email, phone, or in person during working hours.",
						Set.of(persistedCategories.get("General Information"),
								persistedCategories.get("Contact and Support"))),
				new FAQ("How do I apply to the Faculty of Engineering?",
						"You can apply online via the university portal. Ensure all required documents are submitted before the deadlines.",
						Set.of(persistedCategories.get("Admissions"))),
				new FAQ("Are there any entrance exams?",
						"Yes, some departments require an entrance exam or placement test depending on the program.",
						Set.of(persistedCategories.get("Admissions"))),
				new FAQ("What departments are available in the Faculty of Engineering?",
						"Departments include Electrical, Mechanical, Civil, Computer, and Chemical Engineering.",
						Set.of(persistedCategories.get("Course Registration"))),
				new FAQ("Which programs are offered at the undergraduate level?",
						"Each department offers a BSc program with specialized tracks and elective courses.",
						Set.of(persistedCategories.get("Course Registration"),
								persistedCategories.get("Admissions"))),
				new FAQ("How can I register for courses each semester?",
						"Course registration is completed through the university portal. Consult your academic advisor for guidance.",
						Set.of(persistedCategories.get("Course Registration"),
								persistedCategories.get("Student Services"))),
				new FAQ("Can I change courses after registration?",
						"Yes, changes are allowed within the first two weeks of the semester.",
						Set.of(persistedCategories.get("Course Registration"))),
				new FAQ("Who can I contact for academic problems?",
						"Contact your academic advisor or department office for guidance.",
						Set.of(persistedCategories.get("Contact and Support"),
								persistedCategories.get("Student Services"))),
				new FAQ("Is Wi-Fi available on campus?",
						"Yes, the entire campus has secure Wi-Fi access for students and faculty.",
						Set.of(persistedCategories.get("Campus Facilities"),
								persistedCategories.get("General Information")))};

		for (FAQ faq : faqs) {
			if (faqRepository.findByAnswerContainingIgnoreCase(faq.getQuestion())
					.isEmpty()) {
				faqRepository.save(faq);
			}
		}

		faqCategoryRepository
				.deleteAll(faqCategoryRepository.findUnusedCategories());

		List<TimePeriod> periods =
				List.of(new TimePeriod(LocalTime.of(8, 30), LocalTime.of(10, 10)),
						new TimePeriod(LocalTime.of(10, 20), LocalTime.of(12, 0)),
						new TimePeriod(LocalTime.of(12, 10), LocalTime.of(13, 50)),
						new TimePeriod(LocalTime.of(14, 00), LocalTime.of(15, 40)),
						new TimePeriod(LocalTime.of(15, 50), LocalTime.of(17, 30)),
						new TimePeriod(LocalTime.of(17, 40), LocalTime.of(19, 20)));

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
						.orElseGet(
								() -> slotRepository.save(new TimeSlot(period, weekDay)));
			}).toList());
		}

		CourseSession[] sessions = {
				new CourseSession(persistedOfferings.get("CSE 282"),
						slotMap.get(WeekDay.SATURDAY).get(0), "Room 103",
						CourseSessionType.LECTURE),
				new CourseSession(persistedOfferings.get("CSE 233"),
						slotMap.get(WeekDay.SATURDAY).get(1), "Room 103",
						CourseSessionType.LECTURE),
				new CourseSession(persistedOfferings.get("CSE 213"),
						slotMap.get(WeekDay.SATURDAY).get(2), "Room 103",
						CourseSessionType.LECTURE),
				new CourseSession(persistedOfferings.get("CSE 233"),
						slotMap.get(WeekDay.SATURDAY).get(3), null, CourseSessionType.LAB),
				new CourseSession(persistedOfferings.get("CSE 213"),
						slotMap.get(WeekDay.SATURDAY).get(3), null, CourseSessionType.LAB),
				new CourseSession(persistedOfferings.get("CSE 233"),
						slotMap.get(WeekDay.SATURDAY).get(4), null, CourseSessionType.LAB),
				new CourseSession(persistedOfferings.get("CSE 213"),
						slotMap.get(WeekDay.SATURDAY).get(4), null, CourseSessionType.LAB),
				new CourseSession(persistedOfferings.get("CSE 233"),
						slotMap.get(WeekDay.SATURDAY).get(5), null, CourseSessionType.LAB),
				new CourseSession(persistedOfferings.get("CSE 213"),
						slotMap.get(WeekDay.SATURDAY).get(5), null, CourseSessionType.LAB),

				new CourseSession(persistedOfferings.get("CSE 214"),
						slotMap.get(WeekDay.SUNDAY).get(0), "Room 103",
						CourseSessionType.TUTORIAL),
				new CourseSession(persistedOfferings.get("CSE 214"),
						slotMap.get(WeekDay.SUNDAY).get(1), null, CourseSessionType.LAB),
				new CourseSession(persistedOfferings.get("CSE 213"),
						slotMap.get(WeekDay.SUNDAY).get(2), "Room 503",
						CourseSessionType.TUTORIAL),
				new CourseSession(persistedOfferings.get("CSE 223"),
						slotMap.get(WeekDay.SUNDAY).get(3), "Room 103",
						CourseSessionType.LECTURE),

				new CourseSession(persistedOfferings.get("CSE 282"),
						slotMap.get(WeekDay.TUESDAY).get(0), "Room 503",
						CourseSessionType.TUTORIAL),
				new CourseSession(persistedOfferings.get("CSE 214"),
						slotMap.get(WeekDay.TUESDAY).get(1), "Room 104",
						CourseSessionType.LECTURE),
				new CourseSession(persistedOfferings.get("CSE 213"),
						slotMap.get(WeekDay.TUESDAY).get(2), "Room 503",
						CourseSessionType.LECTURE),
				new CourseSession(persistedOfferings.get("CSE 223"),
						slotMap.get(WeekDay.TUESDAY).get(3), "Room 503",
						CourseSessionType.TUTORIAL),

				new CourseSession(persistedOfferings.get("CSE 233"),
						slotMap.get(WeekDay.WEDNESDAY).get(0), "Room 103",
						CourseSessionType.TUTORIAL),
				new CourseSession(persistedOfferings.get("CSE 223"),
						slotMap.get(WeekDay.WEDNESDAY).get(1), null, CourseSessionType.LAB),
				new CourseSession(persistedOfferings.get("CSE 223"),
						slotMap.get(WeekDay.WEDNESDAY).get(2), null, CourseSessionType.LAB),
				new CourseSession(persistedOfferings.get("CSE 223"),
						slotMap.get(WeekDay.WEDNESDAY).get(3), null,
						CourseSessionType.LAB),};

		for (CourseSession session : sessions) {
			if (sessionRepository.findByOfferingIdAndSlotId(
					session.getOffering().getId(), session.getSlot().getId()).isEmpty()) {
				sessionRepository.save(session);
			}
		}
	}
}
