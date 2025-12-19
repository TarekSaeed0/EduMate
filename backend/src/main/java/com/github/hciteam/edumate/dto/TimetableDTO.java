package com.github.hciteam.edumate.dto;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.github.hciteam.edumate.model.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimetableDTO {
	private List<WeekDay> weekDays;
	private List<TimePeriodDTO> periods;
	private List<List<SessionDTO>> sessions;

	public static TimetableDTO fromSessions(List<SessionDTO> sessions) {
		List<WeekDay> weekDays = List.of(WeekDay.values());

		List<TimePeriodDTO> periods =
				sessions.stream().map(s -> s.getSlot().getPeriod()).distinct()
						.sorted(Comparator.comparing(TimePeriodDTO::getStartTime)).toList();

		Map<WeekDay, List<SessionDTO>> sessionsByWeekDay = sessions.stream()
				.collect(Collectors.groupingBy(s -> s.getSlot().getWeekDay()));

		List<List<SessionDTO>> sessionsByWeekDayAndPeriod =
				weekDays.stream().map(day -> {
					Map<Long, SessionDTO> periodToSession = sessionsByWeekDay
							.getOrDefault(day, List.of()).stream().collect(Collectors.toMap(
									s -> s.getSlot().getPeriod().getId(), Function.identity()));

					return periods.stream().map(p -> periodToSession.get(p.getId()))
							.toList();
				}).toList();

		return TimetableDTO.builder().weekDays(weekDays).periods(periods)
				.sessions(sessionsByWeekDayAndPeriod).build();
	}
}
