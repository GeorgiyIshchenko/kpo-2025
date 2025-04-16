package bank.commands.impl.analytics;

import bank.services.AnalyticsService;
import bank.commands.Command;
import bank.domains.Operation;

import java.util.Date;
import java.util.List;

public class GetSortedOperationsCommand implements Command<List<Operation>> {

    private final AnalyticsService analyticsService;
    private final Date from;
    private final Date to;

    public GetSortedOperationsCommand(AnalyticsService analyticsService, Date from, Date to) {
        this.analyticsService = analyticsService;
        this.from = from;
        this.to = to;
    }

    @Override
    public List<Operation> execute() {
        return analyticsService.getOperationsSortedByDate(from, to);
    }
}
