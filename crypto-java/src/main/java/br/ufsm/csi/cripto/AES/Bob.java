package br.ufsm.csi.cripto.AES;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Bob {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        ServerSocket ss = new ServerSocket(5555);
        System.out.println("[BOB] Aguardando conexão na porta 5555.");
        Socket s = ss.accept();
        System.out.println("[BOB] Conexão recebida.");

        ObjectInputStream oin = new ObjectInputStream(s.getInputStream());
        Message message = (Message) oin.readObject();

        Cipher cipherAES = Cipher.getInstance("AES");
        cipherAES.init(Cipher.DECRYPT_MODE, message.getSecretKey());

        byte[] textoPlano = cipherAES.doFinal(message.getFileEncript());

        System.out.println("[BOB] Texto plano decifrado: " + new String(textoPlano));


    }

}
