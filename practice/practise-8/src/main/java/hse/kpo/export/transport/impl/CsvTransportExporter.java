package hse.kpo.export.transport.impl;

import hse.kpo.export.transport.TransportExporter;
import hse.kpo.interfaces.Transport;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CsvTransportExporter implements TransportExporter {

    @Override
    public void export(List<Transport> transports, Writer writer) throws IOException {
        writer.write("VIN,Engine Type,Transport Type\n"); // Заголовки CSV

        for (Transport transport : transports) {
            writer.write(transport.getVin() + "," +
                    transport.getEngineType() + "," +
                    transport.getTransportType() + "\n");
        }
    }

}
