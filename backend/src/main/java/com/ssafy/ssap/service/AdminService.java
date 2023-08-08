package com.ssafy.ssap.service;

import com.ssafy.ssap.dto.ReportListResponseDto;
import com.ssafy.ssap.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ReportRepository reportRepository;

    public List<ReportListResponseDto> getReportList() {
        return reportRepository.findAllReportList();
    }
}
