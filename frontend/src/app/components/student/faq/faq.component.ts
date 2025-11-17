import { Component, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { FAQService, FAQ } from '../../../services/faq.service';
import { Navbar } from '../../navbar/navbar';

@Component({
  selector: 'app-faq',
  standalone: true,
  imports: [Navbar],
  templateUrl: './faq.html',
  styleUrls: ['./faq.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FAQComponent {

  private faqService = inject(FAQService);

  faqs = signal<FAQ[]>([]);
  faqCategories = signal<string[]>([]);
  selectedFaq = signal<FAQ | null>(null);

  constructor() {
    this.loadCategories();
  }

  loadCategories() {
    this.faqService.getCategories().subscribe({
      next: (data) => this.faqCategories.set(data),
      error: () => this.faqCategories.set([]),
    });
  }

  fetchFaqs(category: string) {
    this.faqService.getFAQs(undefined, undefined, [category]).subscribe({
      next: (data) => this.faqs.set(data),
      error: () => this.faqs.set([]),
    });
  }

  viewFaq(faq: FAQ): void {
    this.selectedFaq.set(faq);
  }
}
