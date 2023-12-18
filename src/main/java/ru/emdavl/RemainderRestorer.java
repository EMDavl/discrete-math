package ru.emdavl;

public class RemainderRestorer {

    public static void main(String[] args) {
        long[] moduli = new long[]{13, 23, 27};
        long[] remainders = new long[]{4, 9, 3};

        long restored = restoreRemainder(moduli, remainders);
        System.out.println("Восстановленный остаток: " + restored);
    }

    public static long[] extendedGCD(long a, long b) {
        if (a == 0) {
            return new long[]{b, 0, 1};
        } else {
            long[] res = extendedGCD(b % a, a);
            return new long[]{res[0], res[2] - (b / a) * res[1], res[1]};
        }
    }

    public static long modInverse(long a, long m) {
        long[] res = extendedGCD(a, m);
        if (res[0] != 1) {
            throw new IllegalArgumentException("Числа не взаимно просты");
        } else {
            return res[1] % m;
        }
    }

    public static long restoreRemainder(long[] moduli, long[] remainders) {
        long product = 1;
        for (long modulus : moduli) {
            product *= modulus;
        }
        long result = 0;
        for (int i = 0; i < moduli.length; i++) {
            long m = product / moduli[i];
            long inversed = modInverse(m, moduli[i]);
            result += remainders[i] * m * inversed;
        }
        return result % product;
    }

}
