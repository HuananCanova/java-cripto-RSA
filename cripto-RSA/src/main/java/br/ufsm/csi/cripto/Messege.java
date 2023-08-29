package br.ufsm.csi.cripto;

import javax.crypto.SecretKey;
import java.io.Serializable;

public class Messege implements Serializable {
    private String name;
    private byte[] fileEncript;
    private SecretKey secretKey;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFileEncript() {
        return fileEncript;
    }

    public void setFileEncript(byte[] fileEncript) {
        this.fileEncript = fileEncript;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}
