package cz.muni.fi.pa165.currency;
import java.math.BigDecimal;
import java.util.Currency;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    private static final Currency CZK = Currency.getInstance("CZK");
    private static final Currency EUR = Currency.getInstance("EUR");

    @Mock
    private ExchangeRateTable table;
    private CurrencyConvertor convertor;
    
    @Before
    public void init() {
        convertor = new CurrencyConvertorImpl(table);
    }
    
    @Test
    public void testConvertHappyScenario() throws ExternalServiceFailureException {
        BigDecimal convertRate = BigDecimal.TEN;
        when(table.getExchangeRate(EUR, CZK)).thenReturn(convertRate);
        assertThat(convertor.convert(EUR, CZK, BigDecimal.ONE)).isEqualTo(convertRate);
        assertThat(convertor.convert(EUR, CZK, BigDecimal.TEN)).isEqualTo(new BigDecimal("100"));
        assertThat(convertor.convert(EUR, CZK, new BigDecimal("1.1"))).isEqualTo(new BigDecimal("11.0"));
        assertThat(convertor.convert(EUR, CZK, new BigDecimal("1.123456789"))).isEqualByComparingTo(new BigDecimal("11.23456789"));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        assertThatThrownBy(() -> convertor.convert(null, CZK, BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        assertThatThrownBy(() -> convertor.convert(CZK, null, BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        assertThatThrownBy(() -> convertor.convert(CZK, EUR, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(table.getExchangeRate(EUR, CZK)).thenReturn(null);
        assertThatThrownBy(() -> convertor.convert(EUR, CZK, BigDecimal.ONE))
                .isInstanceOf(UnknownExchangeRateException.class);
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(table.getExchangeRate(EUR, CZK)).thenThrow(ExternalServiceFailureException.class);
        assertThatThrownBy(() -> convertor.convert(EUR, CZK, BigDecimal.ONE))
                .isInstanceOf(UnknownExchangeRateException.class);
    }

}
