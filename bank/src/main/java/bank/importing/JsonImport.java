package bank.importing;

import bank.services.FinanceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JsonImport extends AbstractImportTemplate {

    public JsonImport(FinanceService financeService) {
        super(financeService);
    }

    @Override
    protected List<CommonDTO> parseContent(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<List<CommonDTO>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Ошибка парсинга JSON", e);
        }
    }

}
