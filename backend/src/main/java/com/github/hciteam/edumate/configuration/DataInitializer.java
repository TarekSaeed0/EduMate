package com.github.hciteam.edumate.configuration;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.github.hciteam.edumate.entity.Role;
import com.github.hciteam.edumate.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {
	private final RoleRepository roleRepository;

	public DataInitializer(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		String[] roleNames = {"STUDENT", "COORDINATOR", "ADMINISTRATOR"};
		Arrays.stream(roleNames).forEach(roleName -> {
			if (!roleRepository.existsByName(roleName)) {
				Role role = new Role(null, roleName);
				roleRepository.save(role);
			}
		});
	}
}
