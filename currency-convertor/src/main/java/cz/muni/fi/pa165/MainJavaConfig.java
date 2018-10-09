package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import java.math.BigDecimal;
import java.util.Currency;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainJavaConfig {
    public static void main(String... args) {
        // NOTE: the application will not run with the CurrencyConvertorImpl
        // tagged with @Named as Spring wouldn't know which implementation
        // to choose!
        
        ApplicationContext ctx
                = new AnnotationConfigApplicationContext(SpringJavaConfig.class);
        CurrencyConvertor currencyConvertor = ctx.getBean(CurrencyConvertor.class);
        System.out.println(String.format("EUR -> CZK : %s", 
                currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.ONE)));
    }
}