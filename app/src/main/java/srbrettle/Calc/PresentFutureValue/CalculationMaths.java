package srbrettle.Calc.PresentFutureValue;

public class CalculationMaths {
    //FORMULAS --------------------------------------------------------------------
    public static double FormulaVal1(double value2, double value3, double value4) {
        double dResult;
        dResult = (root((value3/value4),value2) - 1)*100; //NthRoot(F/P) - 1 [*100 to make into percentage]
        dResult = round(dResult,4);
        return dResult;
    }
    public static double FormulaVal2(double value1, double value3, double value4) {
        double dResult;
        dResult = (java.lang.Math.log(value3/value4)) / (java.lang.Math.log(1+(value1/100))); //N=ln(F/P)/ln(1+I)
        dResult = round(dResult,4);
        return dResult;
    }
    public static double FormulaVal3(double value1, double value2, double value4) {
        double dResult;
        dResult = value4 * (java.lang.Math.pow((1+(value1/100)),value2)); //F=P*(1+I)^N
        dResult = round(dResult,2);
        return dResult;
    }
    public static double FormulaVal4(double value1, double value2, double value3) {
        double dResult;
        dResult = value3 / (java.lang.Math.pow((1+(value1/100)),value2)); //P=F/(1+I)^N
        dResult = round(dResult,2);
        return dResult;
    }
    public static double root(double num, double root)
    {
        return Math.pow(Math.E, Math.log(num)/root);
    }
    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
