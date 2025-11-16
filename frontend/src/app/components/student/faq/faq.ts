import { Component, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { StudentService, StudentFaq } from '../../../services/student.service';
import { Navbar } from '../../navbar/navbar';

@Component({
  selector: 'app-faq',
  standalone: true,
  imports: [Navbar],
  templateUrl: './faq.html',
  styleUrls: ['./faq.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FAQ {
  // Signal for FAQ list
  faqs = signal<StudentFaq[]>([]);
  // Signal for selected FAQ
  selectedFaq = signal<StudentFaq | null>(null);

  faqCategories = signal<string[]>([]);

  constructor() {
    this.fetchFaqs();
  }

  // Dummy FAQ Categories
  private dummyCategories: string[] = [
    "General Information",
    "Admissions",
    "Departments and Programs",
    "Course Registration",
    "Exams and Grading",
    "Schedules and Timetables",
    "Library and Resources",
    "Student Services",
    "Clubs and Extracurriculars",
    "Internships and Career Guidance",
    "Campus Facilities",
    "Research Opportunities",
    "Scholarships and Financial Aid for undergrate students",
    "Alumni and Networking",
    "Contact and Support"
  ];


  // Dummy FAQ data
  private dummyFaqs: StudentFaq[] = [
  {
    id: 1,
    question: "What are the working hours of the Faculty of Engineering?",
    answer: "The faculty offices are open from 8:00 AM to 3:00 PM, Sunday to Thursday. Lecture times vary depending on the department."
  },
  {
    id: 2,
    question: "How do I register for courses in my first semester?",
    answer: "You can register through the university portal. Make sure to meet with your academic advisor to select the required courses for your track."
  },
  {
    id: 3,
    question: "Where can I find the faculty map?",
    answer: "The faculty map is available on the Alexandria University website under the Engineering Faculty section, and also at the main entrance of the faculty building."
  },
  {
    id: 4,
    question: "What documents do I need for student registration?",
    answer: "You need your national ID, high school certificate, acceptance letter, and a recent photo. Some departments may request additional documents."
  },
  {
    id: 5,
    question: "Are there any student clubs or societies?",
    answer: "Yes, the Faculty of Engineering has several clubs including robotics, programming, and environmental engineering clubs. You can join during the orientation week."
  },
  {
    id: 6,
    question: "Where can I find lecture schedules?",
    answer: "Lecture schedules are posted on the faculty notice boards and also on the Alexandria University portal for each department."
  },
  {
    id: 7,
    question: "How do I access the faculty library?",
    answer: "The library requires your student ID to borrow books. It is open from 8:00 AM to 4:00 PM, Sunday to Thursday."
  },
  {
    id: 8,
    question: "Is there a faculty canteen or cafeteria?",
    answer: "Yes, the faculty has a cafeteria that serves breakfast, lunch, and snacks. It is located near the main entrance."
  },
  {
    id: 9,
    question: "Can I use public transportation to reach the faculty?",
    answer: "Yes, there are several bus and microbus routes that pass near the faculty. Many students also use bikes or walk if living nearby."
  },
  {
    id: 10,
    question: "Who can I contact if I have academic problems or questions?",
    answer: "You should contact your academic advisor first. The department office can also provide guidance for course issues or administrative problems."
  }
];


  // Fetch FAQs (dummy or from service)
  fetchFaqs(): void {
    this.faqs.set(this.dummyFaqs);
    this.faqCategories.set(this.dummyCategories)

    // Example if fetching from service
    /*
    this.studentService.getFaqs().subscribe({
      next: (data: StudentFaq[]) => this.faqs.set(data),
      error: () => this.faqs.set([]),
    });
    */
  }

  // View FAQ handler
  viewFaq(faq: StudentFaq): void {
    this.selectedFaq.set(faq);
  }
}