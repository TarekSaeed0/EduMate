import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { TimePeriod, TimePeriodService } from './time-period.service';

export type WeekDay =
  | 'SATURDAY'
  | 'SUNDAY'
  | 'MONDAY'
  | 'TUESDAY'
  | 'WEDNESDAY'
  | 'THURSDAY'
  | 'FRIDAY';

export interface TimeSlot {
  id: number;
  period: TimePeriod;
  weekDay: WeekDay;
}

@Injectable({
  providedIn: 'root',
})
export class TimeSlotService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/time-slots';

  static slotMapper = (slot: TimeSlot): TimeSlot => ({
    ...slot,
    period: TimePeriodService.periodMapper(slot.period),
  });

  getSlots(): Observable<TimeSlot[]> {
    return this.http
      .get<TimeSlot[]>(`${this.baseUrl}`, { withCredentials: true })
      .pipe(map((slots) => slots.map(TimeSlotService.slotMapper)));
  }

  createSlot(slot: Omit<TimeSlot, 'id'>): Observable<TimeSlot> {
    return this.http
      .post<TimeSlot>(`${this.baseUrl}`, slot, {
        withCredentials: true,
      })
      .pipe(map(TimeSlotService.slotMapper));
  }

  getSlot(slotId: number): Observable<TimeSlot> {
    return this.http
      .get<TimeSlot>(`${this.baseUrl}/${slotId}`, {
        withCredentials: true,
      })
      .pipe(map(TimeSlotService.slotMapper));
  }

  updateSlot(slotId: number, slot: TimeSlot): Observable<TimeSlot> {
    return this.http
      .put<TimeSlot>(`${this.baseUrl}/${slotId}`, slot, {
        withCredentials: true,
      })
      .pipe(map(TimeSlotService.slotMapper));
  }

  deleteSlot(slotId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${slotId}`, {
      withCredentials: true,
    });
  }
}
