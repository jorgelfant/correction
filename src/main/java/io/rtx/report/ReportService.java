package io.rtx.report;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import io.rtx.sales.SalesEntity;
import io.rtx.sales.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class ReportService {

    @Autowired
    private SalesService salesService;

    public void generateReport(OutputStream output) {
        PdfDocument pdf = new PdfDocument(new PdfWriter(output));
        Document document = new Document(pdf);

        document.add(new Paragraph("Hello World !"));
        document.add(new Paragraph(" Creation date: " + LocalDateTime.now().toString()));
        document.add(new Paragraph("Create with Java."));

        document.close();
    }
    //********************************************Method report*********************************************************
    public void generateActivityReport(OutputStream output, LocalDate start, LocalDate end) {

        PdfDocument pdf = new PdfDocument(new PdfWriter(output));
        Document document = new Document(pdf);
        //Call the method
        document.add(new Paragraph("Rapport d'activité").setTextAlignment(TextAlignment.CENTER).setBold());
        document.add(new Paragraph(" Du " + start + " au " + end));
        document.add(new Paragraph("Tableau d'activités").setTextAlignment(TextAlignment.LEFT).setBold().setItalic());

        //call method 2 and return a table with SalesEntities
        Table table = basicTable(salesService, start, end);
        document.add(table);
        document.close();
    }

    //********************************************Method 1**************************************************************
    private static Table basicTable(SalesService salesService, LocalDate start, LocalDate end) {

        Table table = new Table(6);
        table.setWidth(400);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        String[] tab = {"Date", "Pays", "Produit", "Valeur", "Bénéfice", "Rentabilité"};
        for (String index : tab) {
            table.addCell(new Cell().setBackgroundColor(ColorConstants.CYAN).add(new Paragraph(index)).setTextAlignment(TextAlignment.CENTER).setBold());
        }

        double dob;
        for (SalesEntity sales : salesService.findByDate(start, end)) {
            // Casting and adding
            dob = ((double) (sales.getProfit() * 100) / (double) sales.getValue());
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(1); //arrondi à 2 chiffres apres la virgules
            df.format(dob);

            table.addCell(String.valueOf(sales.getDate()));
            table.addCell(String.valueOf(sales.getCountry()));
            table.addCell(String.valueOf(sales.getProduct()));
            table.addCell(String.valueOf(sales.getValue()));
            table.addCell(String.valueOf(sales.getProfit()));
            Cell sn = new Cell().add(new Paragraph(df.format(dob) + " %"));
            if (dob < 0) {
                sn.setBackgroundColor(ColorConstants.RED);
            } else if (dob <= 2) {
                sn.setBackgroundColor(ColorConstants.ORANGE);
            } else if (dob <= 5) {
                sn.setBackgroundColor(ColorConstants.YELLOW);
            } else if (dob <= 10) {
                sn.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            } else {
                sn.setBackgroundColor(ColorConstants.GREEN);
            }
            table.addCell(sn);

        }
        return table;
    }

    //********************************************Method 2**************************************************************
    public static int[] calcul(SalesService salesService, LocalDate start, LocalDate end) {

        int benefTotal = 0;
        int ca = 0;

        for (SalesEntity salesEntity : salesService.findByDate(start, end)) {
            benefTotal += salesEntity.getProfit();
            ca += salesEntity.getValue();
        }

        int rentGlobale = (benefTotal * 100) / ca;

        return new int[]{salesService.findByDate(start, end).size(), ca, benefTotal, rentGlobale};
    }
}
