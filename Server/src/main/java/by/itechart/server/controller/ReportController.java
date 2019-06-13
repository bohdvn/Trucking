package by.itechart.server.controller;

import by.itechart.server.report.ReportBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    private ReportBuilder reportBuilder;

    public ReportController(ReportBuilder reportBuilder) {
        this.reportBuilder = reportBuilder;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @PutMapping("/download")
    public ResponseEntity<byte[]> download(@RequestBody Map<String, String> dates) throws IOException {
        LOGGER.info("REST request to get a xls report for dates: {} - {}", dates.get("startDate"), dates.get("endDate"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.xls");
        return reportBuilder
                .buildWaybillReport(dates.get("startDate"), dates.get("endDate"))
                .map(report -> new ResponseEntity<>(report, headers, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
