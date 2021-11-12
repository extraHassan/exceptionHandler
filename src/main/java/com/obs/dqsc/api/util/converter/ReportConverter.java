package com.obs.dqsc.api.util.converter;

import com.obs.dqsc.api.domain.document.Report;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ReportConverter {
    private ReportConverter() {

    }

    public static ByteArrayResource convertToCSV(List<Report> reports) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Report");
            Row header = sheet.createRow(0);
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("Costumer Entity Perimeter");
            headerCell = header.createCell(1);
            headerCell.setCellValue("Costumer Name");
            headerCell = header.createCell(2);
            headerCell.setCellValue("Siren");
            headerCell = header.createCell(3);
            headerCell.setCellValue("Recource Type");
            headerCell = header.createCell(4);
            headerCell.setCellValue("Device Name");
            headerCell = header.createCell(5);
            headerCell.setCellValue("Ressource ID");
            headerCell = header.createCell(6);
            headerCell.setCellValue("Provisioned Status");
            headerCell = header.createCell(7);
            headerCell.setCellValue("Provisioning Status");
            headerCell = header.createCell(8);
            headerCell.setCellValue("Polling Result");
            headerCell = header.createCell(9);
            headerCell.setCellValue("Detected Chassis Type");
            headerCell = header.createCell(10);
            headerCell.setCellValue("Detected Main IP Address");
            headerCell = header.createCell(11);
            headerCell.setCellValue("OS Version");
            headerCell = header.createCell(12);
            headerCell.setCellValue("Serial Number");
            headerCell = header.createCell(13);
            headerCell.setCellValue("Detected IP Brandwidth");
            headerCell = header.createCell(14);
            headerCell.setCellValue("Site ID");
            headerCell = header.createCell(15);
            headerCell.setCellValue("Site Name");
            headerCell = header.createCell(16);
            headerCell.setCellValue("Costumer Site Name");
            headerCell = header.createCell(17);
            headerCell.setCellValue("Site Status");
            headerCell = header.createCell(18);
            headerCell.setCellValue("Street");
            headerCell = header.createCell(19);
            headerCell.setCellValue("ZIP Code");
            headerCell = header.createCell(20);
            headerCell.setCellValue("Last Order Reference");
            headerCell = header.createCell(21);
            headerCell.setCellValue("Offer");
            for (Report report : reports) {
                Row row = sheet.createRow(reports.indexOf(report) + 1);
                Cell cell = row.createCell(0);
                cell.setCellValue(report.getCustomer().getEntityPerimeter());
                cell = row.createCell(1);
                cell.setCellValue(report.getCustomer().getName());
                cell = row.createCell(2);
                cell.setCellValue(report.getCustomer().getSiren());
                cell = row.createCell(3);
                cell.setCellValue(report.getInstalledResource().getIrType());
                cell = row.createCell(4);
                cell.setCellValue(report.getInstalledResource().getRouterName());
                cell = row.createCell(5);
                cell.setCellValue(report.getInstalledResource().getInstalledResourceId());
                cell = row.createCell(6);
                cell.setCellValue(report.getInstalledResource().getProvisionedStatus());
                cell = row.createCell(7);
                cell.setCellValue(report.getInstalledResource().getProvisioningStatus());
                cell = row.createCell(8);
                cell.setCellValue(report.getInstalledResource().getFinvPollingStatus());
                cell = row.createCell(9);
                cell.setCellValue(report.getInstalledResource().getDetectedChassisType());
                cell = row.createCell(10);
                cell.setCellValue(report.getInstalledResource().getDetectedMainIpAddress());
                cell = row.createCell(11);
                cell.setCellValue(report.getInstalledResource().getOsVersion());
                cell = row.createCell(12);
                cell.setCellValue(report.getInstalledResource().getSerialNumber());
                cell = row.createCell(13);
                cell.setCellValue(report.getInstalledResource().getLocalBandWidthPoll());
                cell = row.createCell(14);
                cell.setCellValue(report.getSite().getCostumerSiteName());
                cell = row.createCell(15);
                cell.setCellValue(report.getSite().getName());
                cell = row.createCell(16);
                cell.setCellValue(report.getSite().getId());
                cell = row.createCell(17);
                cell.setCellValue(report.getSite().getStatus());
                cell = row.createCell(18);
                cell.setCellValue(report.getSite().getStreetName());
                cell = row.createCell(19);
                cell.setCellValue(report.getSite().getPostalCode());
                cell = row.createCell(20);
                cell.setCellValue(report.getInstalledOfferLastOrderRefio());
                cell = row.createCell(21);
                cell.setCellValue(report.getOfferSpecificationOfferLabel());
            }
            workbook.write(out);
            return new ByteArrayResource(out.toByteArray());
        }
    }
}
