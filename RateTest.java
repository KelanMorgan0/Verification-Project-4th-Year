package cm;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class RateTest {

    //Constructor Tests
    //Test 1 Valid
    @Test
    public void existingCarParkKindTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));
        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7.5), new BigDecimal(5));

        CarParkKind expected = CarParkKind.STAFF;
        CarParkKind actual = r.kind;

        assertEquals(expected, actual);
    }

    //Test 2 Invalid
    @Test
    public void nullCarParkKindTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(13, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(null, reducedPeriods, normalPeriods, new BigDecimal(7.5), new BigDecimal(5));
        });

        assertNull(thrown.getMessage());
    }

    //Test 3 Valid
    @Test
    public void reducedPeriodsNoOverlapTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(13, 20), new Period(1, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));
        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7.5), new BigDecimal(5));

        boolean expected = false;
        boolean actual = r.reducedPeriodsList.get(0).overlaps(r.reducedPeriodsList.get(1));

        assertEquals(expected, actual);
    }

    //Test 4 Invalid
    @Test
    public void reducedPeriodsOverlapTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(13, 20), new Period(10, 16)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7.5), new BigDecimal(5));
        });

        assertNull(thrown.getMessage());
    }

    //Test 5 Valid
    @Test
    public void normalPeriodsNoOverlapTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));
        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7.5), new BigDecimal(5));

        boolean expected = false;
        boolean actual = r.normalPeriodsList.get(0).overlaps(r.reducedPeriodsList.get(1));

        assertEquals(expected, actual);
    }

    //Test 6 Invalid
    @Test
    public void normalPeriodsOverlapTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 11), new Period(9, 15)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7.5), new BigDecimal(5));
        });

        assertNull(thrown.getMessage());
    }

    //Test 7 Invalid
    @Test
    public void reducedPeriodsAndNormalPeriodsOverlapTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(3, 11)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(9, 15)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7.5), new BigDecimal(5));
        });

        assertNull(thrown.getMessage());
    }

    //Test 8 Valid
    @Test
    public void normalRateGreaterOrEqualToZeroTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(3), new BigDecimal(1));

        BigDecimal expected = new BigDecimal(3);
        BigDecimal actual = r.hourlyNormalRate;

        assertEquals(expected, actual);
    }

    //Test 9 Invalid
    @Test
    public void normalRateLessThanZeroTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(-2), new BigDecimal(1));
        });

        assertNull(thrown.getMessage());
    }

    //Test 10 Invalid
    @Test
    public void normalRateGreaterThanTenTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(20), new BigDecimal(1));
        });

        assertNull(thrown.getMessage());
    }

    //Test 11 Valid
    @Test
    public void reducedRateGreaterOrEqualToZeroTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(5), new BigDecimal(3));

        BigDecimal expected = new BigDecimal(3);
        BigDecimal actual = r.hourlyReducedRate;

        assertEquals(expected, actual);
    }

    //Test 12 Invalid
    @Test
    public void reducedRateLessThanOneTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(3), new BigDecimal(-1));
        });

        assertNull(thrown.getMessage());
    }

    //Test 13 Invalid
    @Test
    public void reducedRateGreaterThanTenTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(15), new BigDecimal(13));
        });

        assertNull(thrown.getMessage());
    }

    //Test 14 Valid
    @Test
    public void normalRateGreaterThanReducedRateTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(9), new BigDecimal(7));

        BigDecimal expectedNormal = new BigDecimal(9);
        BigDecimal actual = r.hourlyNormalRate;

        assertEquals(expectedNormal, actual);
    }

    //Test 15 Invalid
    @Test
    public void normalRateLessThanReducedRateTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(5), new BigDecimal(6));
        });

        assertNull(thrown.getMessage());
    }

    //Test 16 Invalid
    @Test
    public void normalRateEqualToReducedRateTest() {
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 9)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(9, 15)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(5), new BigDecimal(5));
        });

        assertNull(thrown.getMessage());
    }

    //Calculate Tests
    //Test 1 Valid
    @Test
    public void periodWithinNormalPeriodsTest() {
        Period period = new Period(7, 10);
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 7)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(7, 15)));

        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7), new BigDecimal(5));

        BigDecimal expected = new BigDecimal(21);
        BigDecimal actual = r.calculate(period);

        assertEquals(expected, actual);
    }

    //Test 2 Valid
    @Test
    public void periodWithinReducedPeriodsTest() {
        Period period = new Period(16, 19);
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 7)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(7, 15)));

        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7), new BigDecimal(5));

        BigDecimal expected = new BigDecimal(15);
        BigDecimal actual = r.calculate(period);

        assertEquals(expected, actual);
    }

    //Test 3 Valid
    @Test
    public void periodWithinNormalAndReducedPeriodsTest() {
        Period period = new Period(13, 18);
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 7)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(7, 15)));

        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7), new BigDecimal(5));

        BigDecimal expected = new BigDecimal(29);
        BigDecimal actual = r.calculate(period);

        assertEquals(expected, actual);
    }

    //Test 4 Valid
    @Test
    public void periodOutsideNormalAndReducedPeriodsTest() {
        Period period = new Period(21, 23);
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 7)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(7, 15)));

        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7), new BigDecimal(5));

        BigDecimal expected = new BigDecimal(0);
        BigDecimal actual = r.calculate(period);

        assertEquals(expected, actual);
    }

    //Test 5 Valid
    @Test
    public void periodHasNormalReducedAndFreePeriodsTest() {
        Period period = new Period(12, 18);
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 7)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(7, 15)));

        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7), new BigDecimal(5));

        BigDecimal expected = new BigDecimal(27);
        BigDecimal actual = r.calculate(period);

        assertEquals(expected, actual);
    }

    //Test 6 Invalid
    @Test
    public void nullPeriodTest() {
        Period period = null;
        ArrayList<Period> reducedPeriods = new ArrayList<>(Arrays.asList(new Period(15, 20), new Period(6, 7)));
        ArrayList<Period> normalPeriods = new ArrayList<>(Arrays.asList(new Period(3, 6), new Period(7, 15)));

        Rate r = new Rate(CarParkKind.STAFF, reducedPeriods, normalPeriods, new BigDecimal(7), new BigDecimal(5));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            r.calculate(period);
        });

        assertNull(thrown.getMessage());
    }
}