package io.rtx.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("api/report/save-on-server")
    public void saveReportOnServer() throws IOException {

        OutputStream output = new FileOutputStream("generated-report.pdf");
        reportService.generateReport(output);
        output.close();

    }

    @GetMapping("api/report/download")
    public void downloadReport(HttpServletResponse response) throws IOException {
        OutputStream output = response.getOutputStream();
        reportService.generateReport(output);
        output.close();
    }

    @GetMapping("api/report/activity")
    public void downloadActivityReport(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end, HttpServletResponse response)
            throws IOException {

        OutputStream output = response.getOutputStream();
        reportService.generateActivityReport(output, start, end);
        output.close();
    }

}
