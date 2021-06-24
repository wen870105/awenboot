package com.wen.awenboot.metric;

import com.codahale.metrics.RatioGauge;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author wen
 * @version 1.0
 * @date 2021/6/17 15:04
 */
public class RatioGaugeTest {
    @Test
    public void ratiosAreHumanReadable() {
        final RatioGauge.Ratio ratio = RatioGauge.Ratio.of(100, 200);

        assertThat(ratio.toString())
                .isEqualTo("100.0:200.0");
    }

    @Test
    public void calculatesTheRatioOfTheNumeratorToTheDenominator() {
        final RatioGauge regular = new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return RatioGauge.Ratio.of(2, 4);
            }
        };

        assertThat(regular.getValue())
                .isEqualTo(0.5);
    }

    @Test
    public void handlesDivideByZeroIssues() {
        final RatioGauge divByZero = new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(100, 0);
            }
        };

        assertThat(divByZero.getValue())
                .isNaN();
    }

    @Test
    public void handlesInfiniteDenominators() {
        final RatioGauge infinite = new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(10, Double.POSITIVE_INFINITY);
            }
        };

        assertThat(infinite.getValue())
                .isNaN();
    }

    @Test
    public void handlesNaNDenominators() {
        final RatioGauge nan = new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(10, Double.NaN);
            }
        };

        assertThat(nan.getValue())
                .isNaN();
    }
}
