package io.github.dug22.cipherlabs.utils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.IntStream;

public class MathUtils {

    public static boolean isPrime(int value) {
        boolean isPrime = value > 1;
        for (int i = 2; i * i < value; i++) {
            if (value % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }


    public static boolean isInvertible(BigInteger a, BigInteger b) {
        return a.gcd(b).equals(BigInteger.ONE);
    }

    public static double max(double[] data) {
        return Arrays.stream(data).max().getAsDouble();
    }

    public static double sumExponents(double[] data, double maxValue) {
        double sum = 0;
        for (double d : data) {
            sum += Math.exp(d - maxValue);
        }

        return sum;
    }

    public static double[] getSoftMax(double[] data) {
        int dataLength = data.length;
        double maxValue = MathUtils.max(data);
        double[] probabilities = new double[dataLength];
        double exponentSum = sumExponents(data, maxValue);
        IntStream.range(0, probabilities.length).forEach(i -> probabilities[i] = Math.exp(data[i] - maxValue) / exponentSum);
        return probabilities;
    }
}

