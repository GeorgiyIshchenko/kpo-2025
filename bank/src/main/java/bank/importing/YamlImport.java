package bank.importing;

import bank.services.FinanceService;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlImport extends AbstractImportTemplate {

    public YamlImport(FinanceService financeService) {
        super(financeService);
    }

    @Override
    protected List<CommonDTO> parseContent(String content) {
        Yaml yaml = new Yaml();
        Object object = yaml.load(content);

        List<Map<String, Object>> rawData = (List<Map<String, Object>>) object; // pohui na warning

        List<CommonDTO> rows = new ArrayList<>();
        for (Map<String, Object> map : rawData) {
            CommonDTO row = new CommonDTO();
            row.setEntityType((String) map.get("entityType"));
            row.setId(((Number) map.get("id")).intValue());
            row.setName((String) map.get("name"));
            rows.add(row);
        }

        return rows;
    }
}
