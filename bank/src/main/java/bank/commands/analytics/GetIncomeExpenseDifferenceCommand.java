package bank.commands.analytics;

import bank.commands.Command;
import bank.services.AnalyticsService;

import java.util.Date;

public class GetIncomeExpenseDifferenceCommand implements Command<Double> {

    private final AnalyticsService analyticsService;
    private final Date from;
    private final Date to;


    public GetIncomeExpenseDifferenceCommand(AnalyticsService analyticsService, Date from, Date to) {
        this.analyticsService = analyticsService;
        this.from = from;
        this.to = to;
    }

    @Override
    public Double execute() {
        return analyticsService.getIncomeExpenseDifference(from, to);
    }

}
