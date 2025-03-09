package hse.kpo.factories;

import hse.kpo.enums.SerializeFormat;
import hse.kpo.export.transport.TransportExporter;
import hse.kpo.export.transport.impl.CsvTransportExporter;
import hse.kpo.export.transport.impl.XmlTransportExporter;
import org.springframework.stereotype.Component;

@Component
public class TransportExporterFactory {
    public TransportExporter create(SerializeFormat format) {
        return switch (format) {
            case XML -> new XmlTransportExporter();
            case CSV -> new CsvTransportExporter();
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
