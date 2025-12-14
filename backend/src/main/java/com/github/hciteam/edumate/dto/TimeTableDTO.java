package com.github.hciteam.edumate.dto;

import java.util.List;
import com.github.hciteam.edumate.model.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableDTO {
	private List<TimePeriodDTO> periods;
	private List<WeekDay> weekDays;
	private List<List<SessionDTO>> sessions;
}
