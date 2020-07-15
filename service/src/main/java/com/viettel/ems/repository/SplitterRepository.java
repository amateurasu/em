package com.viettel.ems.repository;

import com.viettel.ems.model.Splitter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitterRepository extends JpaRepository<Splitter, Long> { }
