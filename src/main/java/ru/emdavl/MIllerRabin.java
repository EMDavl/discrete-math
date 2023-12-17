package ru.emdavl;

import java.math.BigInteger;
import java.util.Random;

public class MIllerRabin {

    private static final Random rnd = new Random();

    public static void main(String[] args) {
        System.out.println(millerRabin(new BigInteger("39")));
    }

    public static boolean millerRabin(BigInteger n) {
        for (int repeat = 0; repeat < 20; repeat++) {
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), rnd);
            } while (a.equals(BigInteger.ZERO));
            if (!millerRabinPass(a, n)) {
                return false;
            }
        }
        return true;
    }

    private static boolean millerRabinPass(BigInteger a, BigInteger n) {
        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        BigInteger d = nMinusOne;

        int s = d.getLowestSetBit();
        d = d.shiftRight(s);

        BigInteger poweredA = a.modPow(d, n);

        if (poweredA.equals(BigInteger.ONE)) {
            return true;
        }
        for (int i = 0; i < s - 1; i++) {
            if (poweredA.equals(nMinusOne)) {
                return true;
            }
            poweredA = poweredA.multiply(poweredA).mod(n);
        }
        return poweredA.equals(nMinusOne);
    }
}