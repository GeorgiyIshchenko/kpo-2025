package bank.commands.impl.analytics;

import bank.commands.Command;
import bank.domains.Operation;
import bank.services.AnalyticsService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GroupOperationsByCategoryCommand implements Command<Map<Integer, List<Operation>>> {


    private final AnalyticsService analyticsService;
    private final Date from;
    private final Date to;

    public GroupOperationsByCategoryCommand(AnalyticsService analyticsService, Date from, Date to) {
        this.analyticsService = analyticsService;
        this.from = from;
        this.to = to;
    }

    @Override
    public Map<Integer, List<Operation>> execute() {
        return analyticsService.groupOperationsByCategoryId(from, to);
    }

}
