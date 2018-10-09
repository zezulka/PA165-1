package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.ExternalServiceFailureException;
import java.math.BigDecimal;
import java.util.Currency;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class MainAnnotations {
    public static void main(String... args) throws ExternalServiceFailureException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("cz.muni.fi.pa165.currency");
        CurrencyConvertor currencyConvertor = ctx.getBean(CurrencyConvertor.class);
        System.out.println(String.format("EUR -> CZK : %s", 
                currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.ONE)));
    }
}