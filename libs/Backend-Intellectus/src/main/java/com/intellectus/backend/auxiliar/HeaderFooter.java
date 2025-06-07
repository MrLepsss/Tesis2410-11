package com.intellectus.backend.auxiliar;

import org.springframework.core.io.ClassPathResource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
        private final Image logo1;
        private final Image logo2;
        private final Image logo3;
        private final Font footerFont;

        public HeaderFooter(Font footerFont) throws Exception {
            this.footerFont = footerFont;
            logo1 = Image.getInstance(new ClassPathResource("logo1.png").getURL());
            logo2 = Image.getInstance(new ClassPathResource("logo2.png").getURL());
            logo3 = Image.getInstance(new ClassPathResource("logo3.png").getURL());
            logo1.scaleToFit(80, 60);
            logo2.scaleToFit(80, 60);
            logo3.scaleToFit(80, 60);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte cb = writer.getDirectContent();
                PdfPTable table = new PdfPTable(3);
                table.setWidths(new float[] { 1f, 1f, 1f });
                table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
                table.addCell(createImageCell(logo1));
                table.addCell(createImageCell(logo2));
                table.addCell(createImageCell(logo3));
                table.writeSelectedRows(0, -1,
                        document.leftMargin(),
                        document.getPageSize().getHeight() - document.topMargin() + 50,
                        cb);

                float cx = document.getPageSize().getWidth() / 2;
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                        new Phrase("Calle 93 No. 19B-94   PBX: 5946159 - 5946186   Celular: 320 2975862", footerFont),
                        cx, document.bottomMargin() - 20, 0);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                        new Phrase("Correo Electrónico: intellectus@husi.org.co", footerFont),
                        cx, document.bottomMargin() - 32, 0);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                        new Phrase("www.husi.org.co/intellectus", footerFont),
                        cx, document.bottomMargin() - 44, 0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private PdfPCell createImageCell(Image img) {
            PdfPCell cell = new PdfPCell(img, false);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            return cell;
        }
    }
