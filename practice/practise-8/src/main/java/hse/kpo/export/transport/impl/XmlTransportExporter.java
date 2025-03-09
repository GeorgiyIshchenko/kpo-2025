package hse.kpo.export.transport.impl;

import hse.kpo.export.transport.TransportExporter;
import hse.kpo.interfaces.Transport;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class XmlTransportExporter implements TransportExporter {

    public void export(List<Transport> transports, Writer writer) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<transports>\n");

        for (Transport transport : transports) {
            writer.write("  <transport>\n");
            writer.write("    <vin>" + transport.getVin() + "</vin>\n");
            writer.write("    <engineType>" + transport.getEngineType() + "</engineType>\n");
            writer.write("    <transportType>" + transport.getTransportType() + "</transportType>\n");
            writer.write("  </transport>\n");
        }

        writer.write("</transports>\n");
    }
}
