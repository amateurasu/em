package com.viettel.ems.repository;

import com.viettel.ems.model.ONU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnuRepository extends JpaRepository<ONU, Long> { }
