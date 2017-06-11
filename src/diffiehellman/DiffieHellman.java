package diffiehellman;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;


public class DiffieHellman {

    private PrivateKey privateKey;
    private PublicKey  publicKey;
    private PublicKey  receivedPublicKey;
    private byte[]     secretKey;
    private String     secretMessage;



    public byte[] encryptAndSendMessage(final String message, final DiffieHellman person) {
        try {
            final SecretKeySpec keySpec = new SecretKeySpec(secretKey, "DES");
            final Cipher        cipher  = Cipher.getInstance("DES/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            final byte[] encryptedMessage = cipher.doFinal(message.getBytes());

            return encryptedMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public void receiveAndDecryptMessage(final byte[] message) {
        try {
            final SecretKeySpec keySpec = new SecretKeySpec(secretKey, "DES");
            final Cipher        cipher  = Cipher.getInstance("DES/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            secretMessage = new String(cipher.doFinal(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void generateKeys() {
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
            keyPairGenerator.initialize(1024);

            final KeyPair keyPair = keyPairGenerator.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey  = keyPair.getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void generateCommonSecretKey() {
        try {
            final KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(receivedPublicKey, true);

            secretKey = shortenSecretKey(keyAgreement.generateSecret());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public PublicKey getPublicKey() {
        return publicKey;
    }



    public void receivePublicKeyFrom(final DiffieHellman person) {
        receivedPublicKey = person.getPublicKey();
    }



    public void tellTheMessage() {
        System.out.println(secretMessage);
    }
    
    public void tellTheMessage(final byte[] message) {
        System.out.println(message);
    }



    /**
     * 1024 bit symmetric key size is so big for DES so we must shorten the key size.
     *
     * @param   longKey
     *
     * @return
     */
    private byte[] shortenSecretKey(final byte[] longKey) {
        try {

            // We use 8 bytes (64 bits) for DES
            //final byte[] shortenedKey = new byte[8];
            //System.arraycopy(longKey, 0, shortenedKey, 0, shortenedKey.length);
        	//
            //return shortenedKey;

            // Below lines can be more secure
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            final DESKeySpec       desSpec    = new DESKeySpec(longKey);

            return keyFactory.generateSecret(desSpec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
