package br.ufsm.csi.cripto.AES;

import javax.crypto.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Alice {
    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        JFileChooser fc = new JFileChooser("");
        System.out.println("Selecionando arquivo");
        if (fc.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            FileInputStream fin = new FileInputStream(f);
            byte[] bArray = new byte[(int) fin.getChannel().size()];
            fin.read(bArray);
            System.out.println("Arquivo selecionado");

            Cipher cipherAES = Cipher.getInstance("AES");
            SecretKey keyAES = KeyGenerator.getInstance("AES").generateKey();
            cipherAES.init(Cipher.ENCRYPT_MODE, keyAES);

            byte[] textoCifrado = cipherAES.doFinal(bArray);

            Socket s = new Socket("localhost", 5555);
            Message message = new Message();
            message.setFileEncript(textoCifrado);
            message.setSecretKey(keyAES);
            message.setName(f.getName());

            ObjectOutputStream oout = new ObjectOutputStream(s.getOutputStream());
            oout.writeObject(message);
            s.close();


        }
    }
}
