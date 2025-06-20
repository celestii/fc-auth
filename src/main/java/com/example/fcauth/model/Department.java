package com.example.fcauth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "123", description = "auto increment pk")
    private Long id;

    @Schema(example = "인사팀", description = "부서 이름")
    private String deptName;

    @Schema(example = "123456", description = "담당 조직장 임직원 ID")
    @OneToOne
    @JoinColumn(name = "team_lead_id", referencedColumnName = "id")
    private Employee teamLead;
}
