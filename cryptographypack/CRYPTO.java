package cryptographypack;

import transactionspack.*;
import javax.crypto.Cipher;
import java.io.*;
import java.security.*;

import static javax.crypto.Cipher.ENCRYPT_MODE;

public interface CRYPTO {
    static KeyPair constructkeys() throws NoSuchAlgorithmException {
            // Generate a key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024); // Set the key size
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }
    static byte[] encrypt(TransactionData transactiondata, PrivateKey PrivateK) throws Exception {
        Cipher encryptionGenerator = Cipher.getInstance("RSA");
        encryptionGenerator.init(ENCRYPT_MODE, PrivateK);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(transactiondata);
        oos.close();
        return encryptionGenerator.doFinal(baos.toByteArray());
    }
    static TransactionData decrypt(byte[] byteArray, PublicKey publickey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publickey);
        byte[] decryptedBytes = cipher.doFinal(byteArray);
        ByteArrayInputStream bais = new ByteArrayInputStream(decryptedBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        TransactionData decryptedTransaction = (TransactionData) ois.readObject();
        ois.close();
        return decryptedTransaction;
    }
    static String createHash(String prevHash, Transaction[] transactions, int nonce) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder input = new StringBuilder();
            input.append(prevHash);
            input.append(transactions.toString());
            input.append(nonce);
            byte[] hashBytes = digest.digest(input.toString().getBytes());
            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception if the SHA-256 algorithm is not available
            e.printStackTrace();
        }
        return null; // Return null if an error occurs
    }
}