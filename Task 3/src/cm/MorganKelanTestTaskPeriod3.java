package cm;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class MorganKelanTestTaskPeriod3 {

    // Constructor tests
    // Test 1 Valid
    @Test
    public void startGreaterThanZeroTest() {
        Period p = new Period(2, 6);

        assertNotNull(p);
    }

    // Test 2 Valid
    @Test
    public void startLessThanTwentyFourTest() {
        Period p = new Period(23, 24);

        assertNotNull(p);
    }

    // Test 3 Valid
    @Test
    public void startEqualToZeroTest() {
        Period p = new Period(0, 10);

        assertNotNull(p);
    }

    // Test 4 Invalid
    @Test
    public void startEqualToTwentyFourTest() {
        assertThrows(IllegalArgumentException.class, () -> new Period(24, 24));
    }

    //Test 5 Invalid
    @Test
    public void startGreaterThanTwentyThreeTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Period(30, 31);
        });
    }

    //Test 6 Invalid
    @Test
    public void startLessThanZeroTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Period(-3, 5);
        });
    }

    //Test 7 Valid
    @Test
    public void endGreaterThanZeroTest() {
        Period p = new Period(3, 7);

        assertNotNull(p);
    }

    //Test 8 Valid
    @Test
    public void endEqualToTwentyFourTest() {
        Period p = new Period(3, 24);

        assertNotNull(p);
    }

    //Test 9 Valid
    @Test
    public void endLessThanTwentyFourTest() {
        Period p = new Period(3, 14);

        assertNotNull(p);
    }

    //Test 10 Invalid
    @Test
    public void endEqualToZeroTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Period(0, 0);
        });
    }

    //Test 11 Invalid
    @Test
    public void endGreaterThanTwentyFiveTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Period(15, 26);
        });
    }

    //Test 12 Invalid
    @Test
    public void endLessThanOneTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Period(-2, -1);
        });
    }

    //Test 13 Invalid
    @Test
    public void endLessThanStartTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Period(10, 5);
        });
    }

    //Test 14 Invalid
    @Test
    public void endEqualToStartTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Period(10, 10);
        });
    }

    //Test 15 Valid
    @Test
    public void endGreaterThanStartTest() {
        Period p = new Period(10, 15);

        assertNotNull(p);
    }

    //Duration Tests
    //Test 1 Valid
    @Test
    public void anyRangeDurationTest() {
        Period p = new Period(10, 15);
        int expected = 5;
        int actual = p.duration();

        assertEquals(expected, actual);
    }

    //Test 2 Valid
    @Test
    public void maxRangeDurationTest() {
        Period p = new Period(0, 24);
        int expected = 24;
        int actual = p.duration();

        assertEquals(expected, actual);
    }

    //Test 3 Valid
    @Test
    public void minRangeDurationTest() {
        Period p = new Period(10, 11);
        int expected = 1;
        int actual = p.duration();

        assertEquals(expected, actual);
    }

    //Overlaps Tests
    //Test 1 Valid
    @Test
    public void noOverlapTest() {
        Period p = new Period(10, 12);
        Period p2 = new Period(15, 17);
        boolean expected = false;
        boolean actual = p.overlaps(p2);

        assertEquals(expected, actual);
    }

    //Test 2 Valid
    @Test
    public void overlapTest() {
        Period p = new Period(10, 12);
        Period p2 = new Period(11, 16);
        boolean expected = true;
        boolean actual = p.overlaps(p2);

        assertEquals(expected, actual);
    }

    //Test 3 Valid
    @Test
    public void closeOverlapTest() {
        Period p = new Period(10, 12);
        Period p2 = new Period(12, 15);
        boolean expected = false;
        boolean actual = p.overlaps(p2);

        assertEquals(expected, actual);
    }
}