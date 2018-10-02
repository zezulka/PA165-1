package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        if(sourceCurrency == null) {
            throw new IllegalArgumentException("source currency cannot be null");
        }
        if(targetCurrency == null) {
            throw new IllegalArgumentException("target currency cannot be null");
        }
        if(sourceAmount == null) {
            throw new IllegalArgumentException("source amount cannot be null");
        }
        logger.trace(String.format("calling convert with params %s -> %s, amount %s", sourceCurrency, targetCurrency, sourceAmount));
        try {
            BigDecimal rate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if(rate == null) {
                String m = String.format("The conversion %s -> %s not known.", sourceCurrency, targetCurrency);
                logger.warn(m);
                throw new UnknownExchangeRateException(m);
            }
            return rate.multiply(sourceAmount);
        } catch (ExternalServiceFailureException e) {
            String m = String.format("Could not retrieve exchange rate for %s -> %s", 
                            sourceCurrency, targetCurrency);
            logger.error(m);
            throw new UnknownExchangeRateException(m, e);
        }
    }

}
