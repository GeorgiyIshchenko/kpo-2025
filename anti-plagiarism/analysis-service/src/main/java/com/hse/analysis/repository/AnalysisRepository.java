package com.hse.analysis.repository;

import com.hse.analysis.model.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnalysisRepository extends JpaRepository<AnalysisResult, UUID> { }

