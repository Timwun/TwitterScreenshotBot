package de.nerdis.apis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Config {

    private String[] credentials, twitterUsers;

    public Config(String credentialsPath, String twitterUsersPath) {
        this.credentials = loadFile(credentialsPath);
        this.twitterUsers = loadFile(twitterUsersPath);
    }

    private String[] loadFile(String path) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return sc.nextLine().split(";");
    }

    public String[] getCredentials() {
        return credentials;
    }

    public String[] getTwitterUsers() {
        return twitterUsers;
    }
}
