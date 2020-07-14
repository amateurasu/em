package com.viettel.ems.repository;

import com.viettel.ems.model.OLT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OltRepository extends JpaRepository<OLT, Long> { }
