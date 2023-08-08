package com.ssafy.ssap.domain.report;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report_category_ns")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCategoryNs {

    @Id
    @Column(length = 20)
    private String name;
}
