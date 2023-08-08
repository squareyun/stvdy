package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.report.ReportCategoryNs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCategoryNsRepository extends JpaRepository<ReportCategoryNs, String> {
    ReportCategoryNs findByName(String name);
}