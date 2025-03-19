package bank.importing;

import bank.enums.CategoryType;
import bank.enums.OperationType;
import bank.services.FinanceService;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class YamlImport extends AbstractImportTemplate {

    public YamlImport(FinanceService financeService) {
        super(financeService);
    }

    @java.lang.SuppressWarnings("unchecked")
    @Override
    protected List<CommonDTO> parseContent(String content) {
        Yaml yaml = new Yaml();
        Object object = yaml.load(content);

        List<Map<String, Object>> rawData = (List<Map<String, Object>>) object;

        List<CommonDTO> rows = new ArrayList<>();
        if (rawData != null){
            for (Map<String, Object> map : rawData) {
                CommonDTO row = new CommonDTO();
                row.setEntityType((String) map.get("entityType"));
                if (row.getEntityType().equals("ACCOUNT") ||
                        row.getEntityType().equals("OPERATION") ||
                        row.getEntityType().equals("CATEGORY")) {
                    row.setId(((Number) map.get("id")).intValue());
                    switch (row.getEntityType()) {
                        case CommonDTO.ENTITY_TYPE_ACCOUNT:
                            row.setName(map.get("name").toString());
                            row.setBalance((Double) map.get("balance"));
                            break;
                        case CommonDTO.ENTITY_TYPE_CATEGORY:
                            row.setName(map.get("name").toString());
                            row.setCategoryType(CategoryType.valueOf(map.get("type").toString()));
                            break;
                        case CommonDTO.ENTITY_TYPE_OPERATION:
                            row.setOperationType(OperationType.valueOf(map.get("operationType").toString()));
                            row.setBankAccountId(Integer.parseInt(map.get("bankAccountId").toString()));
                            row.setAmount((Integer) map.get("amount"));
                            row.setDate(new Date((Long) map.get("date")));
                            row.setDescription(map.get("description").toString());
                            row.setCategoryId((Integer) map.get("categoryId"));
                            break;
                    }
                    rows.add(row);
                }
            }
        }

        return rows;
    }
}
