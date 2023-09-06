package br.ufsm.csi.cripto.SESSIONKEY;

import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Bob {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        // Gera a chave de sessão
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // Tamanho da chave, pode ser ajustado (por exemplo, 128 ou 256 bits)
        SecretKey sessionKey = keyGen.generateKey();

        // Inicia o servidor
        ServerSocket ss = new ServerSocket(5555);
        System.out.println("[BOB] Aguardando conexão na porta 5555.");
        Socket s = ss.accept();
        System.out.println("[BOB] Conexão recebida.");

        // Envia a chave de sessão para Alice
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
        objectOutputStream.writeObject(sessionKey.getEncoded());

        // Recebe a mensagem de Alice
        ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
        Message message = (Message) objectInputStream.readObject();

        // Descriptografa o arquivo usando a chave de sessão
        Cipher cipherAES = Cipher.getInstance("AES");
        cipherAES.init(Cipher.DECRYPT_MODE, sessionKey);
        byte[] textDecrypted = cipherAES.doFinal(message.getFileEncript());

        System.out.println("[BOB] Texto plano decifrado: " + new String(textDecrypted));
    }
}
