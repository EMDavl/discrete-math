package ru.emdavl;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static java.math.BigInteger.ONE;

public class RSA {

    public static void main(String[] args) {
        Random rand = new Random();
        BigInteger p = BigInteger.probablePrime(300, rand);
        BigInteger q = BigInteger.probablePrime(300, rand);
        BigInteger r = BigInteger.probablePrime(300, rand);

        BigInteger n = p.multiply(q).multiply(r);
        BigInteger phiN = p.subtract(ONE).multiply(q.subtract(ONE)).multiply(r.subtract(ONE));

        BigInteger e = new BigInteger("65537");
        BigInteger d = e.modInverse(phiN);

        KeyPair keyPair = new KeyPair(new Key(n, e), new Key(n, d));
        System.out.printf("Public key: %s%nPrivate key: %s%n", keyPair.pub(), keyPair.sec());

        String toBeEncoded = "this is gonna be encoded and then decoded";
        BigInteger encoded = encode(toBeEncoded, keyPair.pub());
        System.out.println("Encoded: " + encoded);
        System.out.println("Decoded: " + decode(encoded, keyPair.sec()));

    }

    private static BigInteger encode(String msg, Key pubKey) {
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        BigInteger msgNumFromBytes = new BigInteger(msgBytes);
        return msgNumFromBytes.modPow(pubKey.second(), pubKey.first());
    }

    private static String decode(BigInteger encoded, Key secKey) {
        BigInteger bigInteger = encoded.modPow(secKey.second(), secKey.first());
        return new String(bigInteger.toByteArray());
    }
}

record KeyPair(Key pub, Key sec) {}

record Key(BigInteger first, BigInteger second) {}
