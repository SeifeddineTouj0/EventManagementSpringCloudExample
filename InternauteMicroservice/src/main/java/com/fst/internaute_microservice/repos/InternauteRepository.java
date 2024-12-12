package com.fst.internaute_microservice.repos;

import com.fst.internaute_microservice.domain.Internaute;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InternauteRepository extends JpaRepository<Internaute, Long> {
}
