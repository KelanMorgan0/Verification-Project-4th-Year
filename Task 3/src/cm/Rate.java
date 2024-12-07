package cm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Rate {
    private CarParkKind kind;
    private BigDecimal hourlyNormalRate;
    private BigDecimal hourlyReducedRate;
    private ArrayList<Period> reduced = new ArrayList<>();
    private ArrayList<Period> normal = new ArrayList<>();

    public Rate(CarParkKind kind, ArrayList<Period> reducedPeriods, ArrayList<Period> normalPeriods, BigDecimal normalRate, BigDecimal reducedRate) {
        if (kind == null) {
            throw new IllegalArgumentException("Car park kind cannot be null");
        }
        if (reducedPeriods == null || normalPeriods == null) {
            throw new IllegalArgumentException("periods cannot be null");
        }
        if (normalRate == null || reducedRate == null) {
            throw new IllegalArgumentException("The rates cannot be null");
        }
        if (normalRate.compareTo(BigDecimal.ZERO) < 0 || reducedRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("A rate cannot be negative");
        }
        if (normalRate.compareTo(reducedRate) <= 0) {
            throw new IllegalArgumentException("The normal rate cannot be less or equal to the reduced rate");
        }
        if (normalRate.compareTo(BigDecimal.TEN) > 0 || reducedRate.compareTo(BigDecimal.TEN) > 0) {
            throw new IllegalArgumentException("Rate cannot be Greater than 10");
        }
        if (!isValidPeriods(reducedPeriods) || !isValidPeriods(normalPeriods)) {
            throw new IllegalArgumentException("The periods are not valid individually");
        }
        if (!isValidPeriods(reducedPeriods, normalPeriods)) {
            throw new IllegalArgumentException("The periods overlaps");
        }
        this.kind = kind;
        this.hourlyNormalRate = normalRate;
        this.hourlyReducedRate = reducedRate;
        this.reduced = reducedPeriods;
        this.normal = normalPeriods;
    }

    /**
     * Checks if two collections of periods are valid together
     * @param periods1
     * @param periods2
     * @return true if the two collections of periods are valid together
     */
    private boolean isValidPeriods(ArrayList<Period> periods1, ArrayList<Period> periods2) {
        Boolean isValid = true;
        int i = 0;
        while (i < periods1.size() && isValid) {
            isValid = isValidPeriod(periods1.get(i), periods2);
            i++;
        }
        return isValid;
    }

    /**
     * checks if a collection of periods is valid
     * @param list the collection of periods to check
     * @return true if the periods do not overlap
     */
    private Boolean isValidPeriods(ArrayList<Period> list) {
        Boolean isValid = true;
        if (list.size() >= 2) {
            Period secondPeriod;
            int i = 0;
            int lastIndex = list.size()-1;
            while (i < lastIndex && isValid) {
                isValid = isValidPeriod(list.get(i), ((List<Period>)list).subList(i + 1, lastIndex+1));
                i++;
            }
        }
        return isValid;
    }

    /**
     * checks if a period is a valid addition to a collection of periods
     * @param period the Period addition
     * @param list the collection of periods to check
     * @return true if the period does not overlap in the collecton of periods
     */
    private Boolean isValidPeriod(Period period, List<Period> list) {
        Boolean isValid = true;
        int i = 0;
        while (i < list.size() && isValid) {
            isValid = !period.overlaps(list.get(i));
            i++;
        }
        return isValid;
    }
    public BigDecimal calculate(Period periodStay) {
        if (periodStay == null) {
            throw new IllegalArgumentException("Period cannot be null");
        }

        int normalRateHours = periodStay.occurences(normal);
        int reducedRateHours = periodStay.occurences(reduced);

        BigDecimal total = (this.hourlyNormalRate.multiply(BigDecimal.valueOf(normalRateHours))).add(
                this.hourlyReducedRate.multiply(BigDecimal.valueOf(reducedRateHours)));

        // visitors are free for first 10, then 50% off
        if (kind.equals(CarParkKind.VISITOR)){
            if (total.compareTo(BigDecimal.TEN) < 0) {
                return new BigDecimal("0.0");
            } else {
                return total.subtract(BigDecimal.TEN).multiply(BigDecimal.valueOf(0.5));
            }
        }

        // management has minimum value of 4
        if (kind.equals(CarParkKind.MANAGEMENT)){
            if (total.compareTo(BigDecimal.valueOf(4)) < 0) {
                return new BigDecimal("4.0");
            }
        }

        // students pay with 25% reduction after 5.50
        if (kind.equals(CarParkKind.STUDENT)){
            if (total.compareTo(BigDecimal.valueOf(5.50)) > 0) {
                return (((total.subtract(BigDecimal.valueOf(5.50))).multiply(BigDecimal.valueOf(0.75))).add(BigDecimal.valueOf(5.50))).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
        }

        // staff can only pay maximum 16
        if (kind.equals(CarParkKind.STAFF)) {
            if (total.compareTo(BigDecimal.valueOf(16)) > 0) {
                return new BigDecimal("16.00");
            }
        }

        return total;
    }

}
