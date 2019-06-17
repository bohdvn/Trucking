package by.itechart.server.report;

import by.itechart.server.entity.WayBill;
import by.itechart.server.repository.WayBillRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReportBuilder {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private WayBillRepository wayBillRepository;

    public ReportBuilder(WayBillRepository wayBillRepository) {
        this.wayBillRepository = wayBillRepository;
    }

    public Optional<byte[]> buildWaybillReport(String startDate, String endDate) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        CellStyle titleStyle;
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setBold(true);

        titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setFont(font);

        Sheet sheet = workbook.createSheet("Отчет о путевых листах");
        sheet.createRow(0);
        sheet.getRow(0).createCell(0).setCellValue("Название клиента");
        sheet.getRow(0).getCell(0).setCellStyle(titleStyle);
        sheet.getRow(0).createCell(1).setCellValue("Название машины");
        sheet.getRow(0).getCell(1).setCellStyle(titleStyle);
        sheet.getRow(0).createCell(2).setCellValue("Тип машины");
        sheet.getRow(0).getCell(2).setCellStyle(titleStyle);
        sheet.getRow(0).createCell(3).setCellValue("ФИО Водителя");
        sheet.getRow(0).getCell(3).setCellStyle(titleStyle);
        sheet.getRow(0).createCell(4).setCellValue("Номер ТТН");
        sheet.getRow(0).getCell(4).setCellStyle(titleStyle);
        sheet.getRow(0).createCell(5).setCellValue("Дата начала перевозки");
        sheet.getRow(0).getCell(5).setCellStyle(titleStyle);
        sheet.getRow(0).createCell(6).setCellValue("Дата окончания перевозки");
        sheet.getRow(0).getCell(6).setCellStyle(titleStyle);
        sheet.setColumnWidth(0, 7000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 7000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 7000);
        sheet.setColumnWidth(6, 7000);
        List<WayBill> wayBills = getWaybillsByDate(startDate, endDate);
        for (int i = 0; i < wayBills.size(); i++) {
            sheet.createRow(i + 1);
            sheet.getRow(i + 1).createCell(0)
                    .setCellValue(wayBills.get(i).getInvoice().getRequest().getClientCompanyFrom().getName());
            sheet.getRow(i + 1).createCell(1)
                    .setCellValue(wayBills.get(i).getInvoice().getRequest().getCar().getName());
            sheet.getRow(i + 1).createCell(2)
                    .setCellValue(String.valueOf(wayBills.get(i).getInvoice().getRequest().getCar().getCarType()));
            sheet.getRow(i + 1).createCell(3)
                    .setCellValue(wayBills.get(i).getInvoice().getRequest().getDriver().getSurname() + " " +
                            wayBills.get(i).getInvoice().getRequest().getDriver().getName() + " " +
                            wayBills.get(i).getInvoice().getRequest().getDriver().getPatronymic());
            sheet.getRow(i + 1).createCell(4)
                    .setCellValue(wayBills.get(i).getInvoice().getNumber());
            sheet.getRow(i + 1).createCell(5)
                    .setCellValue(String.valueOf(wayBills.get(i).getDateFrom()));
            sheet.getRow(i + 1).createCell(6)
                    .setCellValue(String.valueOf(wayBills.get(i).getDateTo()));
        }
        workbook.write(out);
        return Optional.of(out.toByteArray());
    }

    private List<WayBill> getWaybillsByDate(String start, String end) {
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<WayBill> wayBills = wayBillRepository.findAll();
        wayBills.removeIf(wayBill -> wayBill.getDateFrom().isBefore(startDate) || wayBill.getDateFrom().isAfter(endDate));
        return wayBills;
    }

}
