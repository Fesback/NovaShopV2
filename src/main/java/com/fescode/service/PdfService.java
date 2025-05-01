package com.fescode.service;

import com.fescode.entity.DetallePedido;
import com.fescode.entity.Pedido;
import com.fescode.entity.Usuario;
import com.fescode.repository.PedidoRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfService {

    private final PedidoRepository pedidoRepository; // Inyectar el repositorio de pedidos

    // Inyectar el repositorio de pedidos
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(50, 50, 50));
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(70, 130, 180));
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private static final Font BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font TOTAL_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, new BaseColor(255, 69, 0));

    public byte[] generarBoletaPedido(Pedido pedido) throws Exception {
        validarPedido(pedido);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 40, 40, 60, 40);
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            agregarEventosPersonalizados(writer);

            document.open();
            try {
                agregarContenidoDocumento(document, pedido);
            } finally {
                document.close();
            }

            validarPdfGenerado(baos);
            return baos.toByteArray();
        }
    }

    // ========== MÉTODOS DE VALIDACIÓN ==========
    private void validarPedido(Pedido pedido) {
        if (pedido == null) throw new IllegalArgumentException("El pedido no puede ser nulo");
        if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
            throw new IllegalStateException("El pedido no contiene items");
        }
    }

    private void agregarEventosPersonalizados(PdfWriter writer) {
        writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                // Opcional: lógica para encabezados/pies de página
            }
        });
    }

    // ========== ESTRUCTURA PRINCIPAL ==========
    private void agregarContenidoDocumento(Document document, Pedido pedido) throws Exception {
        agregarHeader(document, pedido);
        agregarCustomerInfo(document, pedido.getUsuario(), pedido.getDireccionEnvio());
        agregarItemsTable(document, pedido.getItems());
        agregarTotales(document, pedido.getTotal());
        agregarFooter(document);
    }

    // ========== ENCABEZADO REDISEÑADO ==========
private void agregarHeader(Document document, Pedido pedido) throws Exception {
    // 1. Configuración inicial
    float marginRight = 40f;  // Margen derecho aumentado
    float logoSize = 150f;     // Tamaño reducido para equilibrio visual

    // 2. Posicionamiento PRECISO del logo
    try {
        URL logoUrl = getClass().getResource("/images/NovaShop-Logo01.png");
        if (logoUrl != null) {
            Image logo = Image.getInstance(logoUrl);

            // A. Tamaño proporcional al encabezado
            logo.scaleToFit(logoSize, logoSize);

            // B. Cálculo de posición (coordenadas exactas)
            float logoX = document.getPageSize().getWidth() - logo.getScaledWidth() - marginRight;
            float logoY = document.getPageSize().getHeight() - 200f; // 65px desde arriba

            // C. Aplicar posición
            logo.setAbsolutePosition(logoX, logoY);
            document.add(logo);
        }
    } catch (Exception e) {
        System.err.println("Error logo: " + e.getMessage());
    }

    // 3. Texto del encabezado (alineado con el logo)
    Paragraph headerText = new Paragraph();
    headerText.add(new Chunk("NOVASHOP\n", HEADER_FONT));
    headerText.add(new Chunk("COMPROBANTE DE PEDIDO\n\n", TITLE_FONT));
    headerText.setAlignment(Element.ALIGN_LEFT);
    document.add(headerText);

    // 3. Información del pedido
    Paragraph infoPedido = new Paragraph();
    infoPedido.add(new Chunk("Pedido n.º: ", BOLD_FONT));
    infoPedido.add(new Chunk(pedido.getId().toString() + "\n", NORMAL_FONT));
    infoPedido.add(new Chunk("Fecha: ", BOLD_FONT));
    infoPedido.add(new Chunk(formatDate(pedido.getFechaPedido()), NORMAL_FONT));
    infoPedido.setSpacingBefore(10f);
    document.add(infoPedido);
}

    // ========== INFORMACIÓN DEL CLIENTE (VERSIÓN MODIFICADA) ==========
private void agregarCustomerInfo(Document document, Usuario usuario, String direccion) throws DocumentException {
    // 1. Crear espacio hasta la mitad de la página
    Paragraph espacio = new Paragraph("\n\n\n\n\n\n\n\n"); // 5 saltos de línea (ajustable)
    document.add(espacio);

    // 2. Contenedor con fondo y bordes opcionales
    PdfPTable container = new PdfPTable(1);
    container.setWidthPercentage(60); // Ancho del 60% de la página
    container.setHorizontalAlignment(Element.ALIGN_LEFT);
    container.setSpacingBefore(0f);

    // 3. Celda con información del cliente
    PdfPCell cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setPadding(8f);

    Paragraph customerInfo = new Paragraph();
    customerInfo.add(new Chunk("Cliente: ", BOLD_FONT));
    customerInfo.add(new Chunk(usuario.getNombre() != null ? usuario.getNombre().toUpperCase() : "N/A", NORMAL_FONT));
    customerInfo.add(Chunk.NEWLINE);
    customerInfo.add(new Chunk("Dirección: ", BOLD_FONT));
    customerInfo.add(new Chunk(direccion != null ? direccion : "N/A", NORMAL_FONT));
    customerInfo.add(Chunk.NEWLINE);
    customerInfo.add(new Chunk("Teléfono: ", BOLD_FONT));
    customerInfo.add(new Chunk(usuario.getTelefono() != null ? usuario.getTelefono() : "N/A", NORMAL_FONT));

    cell.addElement(customerInfo);
    container.addCell(cell);
    document.add(container);

    // 4. Espacio antes de la tabla
    document.add(new Paragraph("\n")); // Espacio adicional
}

    // ========== TABLA DE ÍTEMS OPTIMIZADA ==========
    private void agregarItemsTable(Document document, List<DetallePedido> items) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{50f, 15f, 20f, 25f});
        table.setSpacingBefore(15f);
        table.setSpacingAfter(20f);

        // Encabezados
        BaseColor headerBgColor = new BaseColor(70, 130, 180);
        agregarCeldaEncabezado(table, "Artículo", headerBgColor, Element.ALIGN_LEFT);
        agregarCeldaEncabezado(table, "Cantidad", headerBgColor, Element.ALIGN_CENTER);
        agregarCeldaEncabezado(table, "Precio Unitario", headerBgColor, Element.ALIGN_RIGHT);
        agregarCeldaEncabezado(table, "Subtotal", headerBgColor, Element.ALIGN_RIGHT);

        // Filas
        boolean isEvenRow = false;
        BaseColor rowEvenColor = new BaseColor(255, 255, 255);
        BaseColor rowOddColor = new BaseColor(240, 240, 240);
        BaseColor borderColor = new BaseColor(200, 200, 200);

        for (DetallePedido item : items) {
            BaseColor rowColor = isEvenRow ? rowEvenColor : rowOddColor;
            agregarCeldaContenido(table, obtenerNombreProducto(item), rowColor, borderColor, Element.ALIGN_LEFT);
            agregarCeldaContenido(table, String.valueOf(item.getCantidad()), rowColor, borderColor, Element.ALIGN_CENTER);
            agregarCeldaContenido(table, formatCurrency(item.getPrecioUnitario()), rowColor, borderColor, Element.ALIGN_RIGHT);
            agregarCeldaContenido(table, formatCurrency(item.getSubtotal()), rowColor, borderColor, Element.ALIGN_RIGHT);
            isEvenRow = !isEvenRow;
        }
        document.add(table);
    }

    // ========== SECCIÓN DE TOTALES ==========
    private void agregarTotales(Document document, BigDecimal total) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(40);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.setSpacingBefore(15f);

        // Línea separadora
        PdfPCell separator = new PdfPCell(new Phrase(" "));
        separator.setBorder(Rectangle.TOP);
        separator.setFixedHeight(10f);
        separator.setColspan(2);
        table.addCell(separator);

        // Contenido
        agregarCeldaTotal(table, "Subtotal:", true);
        agregarCeldaTotal(table, formatCurrency(total), false);
        agregarCeldaTotal(table, "Impuestos (0%):", true);
        agregarCeldaTotal(table, formatCurrency(BigDecimal.ZERO), false);

        // Línea antes del total
        separator = new PdfPCell(new Phrase(" "));
        separator.setBorder(Rectangle.TOP);
        separator.setFixedHeight(2f);
        separator.setColspan(2);
        table.addCell(separator);

        agregarCeldaTotal(table, "Total:", true);
        agregarCeldaTotal(table, formatCurrency(total), true);

        document.add(table);
    }

    // ========== FOOTER CON LÍNEA SEPARADORA ==========
    private void agregarFooter(Document document) throws DocumentException {
    // 1. Generar espacio con saltos de línea controlados
    for (int i = 0; i < 10; i++) {  // 8 saltos ≈ espacio suficiente
        document.add(new Paragraph(" ")); // Salto de línea simple
    }

    // 2. Línea separadora
    PdfPTable separator = new PdfPTable(1);
    separator.setWidthPercentage(100);
    PdfPCell lineCell = new PdfPCell(new Phrase(" "));
    lineCell.setBorder(Rectangle.TOP);
    lineCell.setFixedHeight(1f);
    separator.addCell(lineCell);
    document.add(separator);

    // 3. Contenido del footer (texto centrado)
    Paragraph footer = new Paragraph();
    footer.add(new Chunk("¡Gracias por su compra!", BOLD_FONT));
    footer.add(Chunk.NEWLINE);
    footer.add(new Chunk("NOVASHOP - Av. Camino Real 1281, Lima, Perú", NORMAL_FONT));
    footer.add(Chunk.NEWLINE);
    footer.add(new Chunk("Teléfono: (01) 987654321 - Email: contacto@novashop.com", NORMAL_FONT));
    footer.setAlignment(Element.ALIGN_CENTER);
    footer.setSpacingBefore(5f); // Espacio después de la línea
    document.add(footer);
}

    // ========== MÉTODOS AUXILIARES ==========
    private void agregarCeldaEncabezado(PdfPTable table, String texto, BaseColor bgColor, int alineacion) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(alineacion);
        cell.setPadding(8);
        cell.setBorderWidth(1f);
        cell.setBorderColor(bgColor);
        table.addCell(cell);
    }

    private void agregarCeldaContenido(PdfPTable table, String texto, BaseColor bgColor, BaseColor borderColor, int alineacion) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, NORMAL_FONT));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(alineacion);
        cell.setPadding(6);
        cell.setBorderWidth(0.5f);
        cell.setBorderColor(borderColor);
        table.addCell(cell);
    }

    private void agregarCeldaTotal(PdfPTable table, String texto, boolean isBold) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, isBold ? BOLD_FONT : NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(4);
        table.addCell(cell);
    }

    private String formatCurrency(BigDecimal amount) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "S/. " + (amount != null ? df.format(amount) : df.format(BigDecimal.ZERO));
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date != null ? date.format(formatter) : "N/A";
    }

    private void validarPdfGenerado(ByteArrayOutputStream baos) {
        if (baos.size() == 0) throw new IllegalStateException("El PDF generado está vacío");
    }

    private String obtenerNombreProducto(DetallePedido item) {
        return (item.getProducto() != null && item.getProducto().getNombre() != null)
            ? item.getProducto().getNombre()
            : "Producto no disponible";
    }
}