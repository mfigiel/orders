package com.testing.repository;

import com.testing.api.resource.PersonApi;
import com.testing.dto.PersonDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonDto, Long> {

}
