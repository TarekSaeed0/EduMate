package com.github.hciteam.edumate.model;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyDiscriminator;
import org.hibernate.annotations.AnyDiscriminatorValue;
import org.hibernate.annotations.AnyKeyJavaClass;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Any
	@AnyDiscriminator(DiscriminatorType.STRING)
	@AnyDiscriminatorValue(discriminator = "Course", entity = Course.class)
	@AnyDiscriminatorValue(discriminator = "Semester", entity = Semester.class)
	@AnyKeyJavaClass(Long.class)
	@Column(name = "scope_type")
	@JoinColumn(name = "scope_id")
	private Object scope;
}
