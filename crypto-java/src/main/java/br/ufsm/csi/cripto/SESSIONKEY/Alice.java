package br.ufsm.csi.cripto.SESSIONKEY;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Alice {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

        JFileChooser jFile = new JFileChooser("");
        System.out.println("Selecionando arquivo");
        if (jFile.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION){
            File file = jFile.getSelectedFile();
            FileInputStream fileInput = new FileInputStream(file);
            byte[] bArray = new byte[(int) fileInput.getChannel().size()];
            fileInput.read(bArray);
            System.out.println("Arquivo selecionado");

            // Estabelece conexão com Bob
            Socket s = new Socket("localhost", 5555);

            // Recebe a chave de sessão compartilhada de Bob
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            byte[] sessionKeyBytes = (byte[]) objectInputStream.readObject();
            SecretKey sessionKey = new SecretKeySpec(sessionKeyBytes, "AES");

            // Encripta o arquivo usando a chave de sessão
            Cipher cipherAES = Cipher.getInstance("AES");
            cipherAES.init(Cipher.ENCRYPT_MODE, sessionKey);
            byte[] textEncrypted = cipherAES.doFinal(bArray);

            // Cria a mensagem com o arquivo encriptado
            Message message = new Message();
            message.setFileEncript(textEncrypted);
            message.setName(file.getName());

            // Envia a mensagem para Bob
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            objectOutputStream.writeObject(message);

            s.close();
        }
    }
}
