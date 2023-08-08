package com.ssafy.ssap.controller;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.ReportListResponseDto;
import com.ssafy.ssap.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    /**
     * 신고 목록 조회
     */
    @GetMapping("/report")
    public ResponseEntity<Map<String, Object>> getReportList() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        try {
            List<ReportListResponseDto> reportList = adminService.getReportList();
            logger.debug("신고 목록 조회 성공, count: {}", reportList.size());
            response.put("reports", reportList);
            response.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("답변 채택 실패", e);
            response.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(response);
    }
}

