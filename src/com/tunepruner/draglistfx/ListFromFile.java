package com.tunepruner.draglistfx;

import java.io.*;

public class ListFromFile{
    File file;

    public void syncFromFile(ListArea listArea) throws IOException {
        file = new File(listArea.uniqueID + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("That file already exists");
        }
        BufferedReader bufReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        String line;
        line = bufReader.readLine();

        try {
            while (line != null) {
                listArea.getList().add(line);
                line = bufReader.readLine();
            }
        }catch (IOException f) {
            f.printStackTrace();
        }

        bufReader.close();
    }
    public void handleSyncToFile(ListArea listArea){
        /*(Everything enclosed in listener to the ObservableList)
        * update file whenever list changes, but
        * add timer so it doesn't happen more than, say, 3 times a second.
        **/

        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);

        for (int i = 0; i < listArea.getList().size(); i++) {
            pw.write(listArea.getList().get(i));
            pw.write("\n");
        }

        pw.close();
    }

}
