package br.ufsm.csi.cripto;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Alice {
    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, ClassNotFoundException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        JFileChooser jFile = new JFileChooser("");
        System.out.println("selecting file");
        if (jFile.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION){
            File file = jFile.getSelectedFile();
            FileInputStream fileInput = new FileInputStream(file);
            byte[] bArray = new byte[(int) fileInput.getChannel().size()];
            fileInput.read(bArray);
            System.out.println("file selected");

            Socket s = new Socket("localhost",5555);

            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            byte[] publicKetBytes = (byte[]) objectInputStream.readObject();
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKetBytes));


            Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipherRSA.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] textEncripted = cipherRSA.doFinal(bArray);

            Messege messege = new Messege();
            messege.setFileEncript(textEncripted);
            messege.setName(file.getName());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            objectOutputStream.writeObject(messege);
            s.close();
        }
    }
}
