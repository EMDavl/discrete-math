package ru.emdavl;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.pow;

class Runner {

    public static void main(String[] args) {

        DiffieHellman userFromKazan = new DiffieHellman();
        DiffieHellman userFromMoscow = new DiffieHellman();
        userFromMoscow.computeSharedKey(userFromKazan.getPersonalPublicKey());
        userFromKazan.computeSharedKey(userFromMoscow.getPersonalPublicKey());

        System.out.println(userFromMoscow.decrypt(userFromKazan.encrypt("Hello world!")));
    }
}

public class DiffieHellman {

    private static final BigInteger p = new BigInteger("6507145813438721725617901845087976214619118208794211680043037261795787086791243333597827058881946219");
    private static final BigInteger r = new BigInteger("3253572906719360862808950922543988107309559104397105840021518630897893543395621666798913529440973109");
    private static final BigInteger g ;
    private static final Random rand = new Random();

    static {
        BigInteger tempG = BigInteger.TWO;
        while ((tempG.modPow(r, p).equals(BigInteger.ONE) || tempG.modPow(BigInteger.TWO, p).equals(BigInteger.ONE))
                && (tempG.compareTo(BigInteger.TWO) >= 0) && tempG.compareTo(p) > 0) {
            tempG = new BigInteger(p.bitLength(), rand);
        }
        g = tempG;
    }

    private final BigInteger personalSecretKey;
    private final BigInteger personalPublicKey;
    private BigInteger sharedKey;

    public DiffieHellman() {
        BigInteger tempSec;
        do {
            tempSec = new BigInteger(r.bitLength(), rand);
        } while (tempSec.compareTo(r) > 0 && tempSec.compareTo(BigInteger.TWO) <= 0);
        this.personalSecretKey = tempSec;
        this.personalPublicKey = g.modPow(personalSecretKey, p);
    }

    public void computeSharedKey(BigInteger otherPublicKey) {
        sharedKey = otherPublicKey.modPow(personalSecretKey, p);
    }

    public BigInteger getPersonalPublicKey() {
        return personalPublicKey;
    }

    public byte[] encrypt(String stringToBeEncrypted) {
        BigInteger biFromBytes = new BigInteger(stringToBeEncrypted.getBytes());
        System.out.println("FROM BYTES ENC: " + biFromBytes);
        BigInteger xor = biFromBytes.xor(sharedKey);
        System.out.println("XOR ENC: " + xor);
        return xor.toByteArray();
    }

    public String decrypt(byte[] toBeDecrypted) {
        BigInteger biFromBytes = new BigInteger(toBeDecrypted);
        System.out.println("FROM BYTES DEC: " + biFromBytes);
        BigInteger xor = biFromBytes.xor(sharedKey);
        System.out.println("XOR DEC: " + xor);
        return new String(xor.toByteArray());
    }
}
