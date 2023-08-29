package br.ufsm.csi.cripto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Bob {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        //cria par de chaves public and private
        //public key enviar para Alice

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        //abre socket para conexão
        ServerSocket ss = new ServerSocket(5555);
        System.out.println("[BOB]listening on port 5555");
        Socket s = ss.accept();
        System.out.println("[BOB] connection accepted on port 5555");

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
        byte[] publicKeyBytes = publicKey.getEncoded();
        objectOutputStream.writeObject(publicKeyBytes);

        ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
        Messege messege = (Messege) objectInputStream.readObject();

        Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipherRSA.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] plainText = cipherRSA.doFinal(messege.getFileEncript());
        System.out.println("[BOB] Texto plano decifrado: " + new String(plainText));
    }
}
