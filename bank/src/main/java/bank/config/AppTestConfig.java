package bank.config;

import bank.factories.BankAccountFactory;
import bank.factories.CategoryFactory;
import bank.factories.OperationFactory;
import bank.factories.interfaces.InOperationFactory;
import bank.services.AnalyticsService;
import bank.services.DataManagementService;
import bank.services.FinanceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppTestConfig {

    @Bean
    public BankAccountFactory bankAccountFactory() {
        return new BankAccountFactory();
    }

    @Bean
    public CategoryFactory categoryFactory() {
        return new CategoryFactory();
    }

    @Bean
    public OperationFactory operationFactory() {
        return new OperationFactory();
    }

    @Bean
    public FinanceService financeService() {
        return new FinanceService(bankAccountFactory(), categoryFactory(), operationFactory());
    }

    @Bean
    public AnalyticsService analyticsService() {
        return new AnalyticsService(financeService());
    }

    @Bean
    public DataManagementService dataManagementService() {
        return new DataManagementService(financeService());
    }

}
