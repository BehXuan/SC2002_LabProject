package src.controller;

import java.util.List;

import src.entity.Internship;
import src.report.ReportCriteria;


public interface IReportGenerator {
    public List<Internship> generateReport(ReportCriteria criteria);
}
