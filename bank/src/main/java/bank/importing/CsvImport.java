package bank.importing;

import bank.enums.CategoryType;
import bank.enums.OperationType;
import bank.services.FinanceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvImport extends AbstractImportTemplate {

    public CsvImport(FinanceService financeService) {
        super(financeService);
    }

    @Override
    protected List<CommonDTO> parseContent(String content) {
        List<CommonDTO> rows = new ArrayList<>();
        // ENTITY_TYPE, ID, NAME, BALANCE/AMOUNT, ..."
        String[] lines = content.split("\n");
        for (String line : lines) {
            String[] parts = line.split(",");
            CommonDTO row = new CommonDTO();
            row.setEntityType(parts[0]);
            row.setId(Integer.parseInt(parts[1]));
            row.setName(parts[2]);
            switch (row.getEntityType()) {
                case CommonDTO.ENTITY_TYPE_ACCOUNT:
                    row.setBalance(Double.parseDouble(parts[3]));
                    break;
                case CommonDTO.ENTITY_TYPE_CATEGORY:
                    row.setCategoryType(CategoryType.valueOf(parts[3]));
                    break;
                case CommonDTO.ENTITY_TYPE_OPERATION:
                    row.setOperationType(OperationType.valueOf(parts[3]));
                    row.setBankAccountId(Integer.parseInt(parts[4]));
                    row.setAmount(Integer.parseInt(parts[5]));
                    row.setDate(new Date(Long.parseLong(parts[6])));
                    row.setDescription(parts[7]);
                    row.setCategoryId(Integer.parseInt(parts[8]));
                    break;
            }
            rows.add(row);
        }
        return rows;
    }
}
