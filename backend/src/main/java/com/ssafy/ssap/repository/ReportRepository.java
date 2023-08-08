package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.report.Report;
import com.ssafy.ssap.dto.ReportListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("select new com.ssafy.ssap.dto.ReportListResponseDto" +
            "(r.id, r.detail, r.reporter.id, r.reporter.nickname, r.target.id, r.target.nickname, r.category.name, r.registTime)" +
            " from Report r order by r.id desc")
    List<ReportListResponseDto> findAllReportList();
}