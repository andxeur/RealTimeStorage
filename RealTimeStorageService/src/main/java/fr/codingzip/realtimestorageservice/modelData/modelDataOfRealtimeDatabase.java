package fr.codingzip.realtimestorageservice.modelData;

import com.google.firebase.database.Exclude;

/**
 * This class represents the template whose file information will be passed
 * to Firebase RealtimeDatabase to store
 *
 * @author Drex
 * @version 1.0
 */
public class modelDataOfRealtimeDatabase {

    private String nameOfFlile;
    private String urlOfFile;
    private String fileId;

    public modelDataOfRealtimeDatabase() {
        // Default constructor required
    }

    /**
     * This constructor designates the name and url of the file that will be transmitted
     * on Firebase RealtimeDatabase to store
     *
     * @param nameOfFile File name
     * @param urlOfFile  File url
     */
    public modelDataOfRealtimeDatabase(String nameOfFile, String urlOfFile) {
        this.nameOfFlile = nameOfFile;
        this.urlOfFile = urlOfFile;
    }

    /**
     * @return The file name
     */
    public String getNameOfFile() {
        return nameOfFlile;
    }

    /**
     * @param nameOfFile Change the name of the file
     */
    public void setNameOfFile(String nameOfFile) {
        this.nameOfFlile = nameOfFile;
    }

    /**
     * @return File url
     */
    public String getUrlOfFile() {
        return urlOfFile;
    }

    /**
     * @param urlOfFile Change file url
     */
    public void setUrlOfFile(String urlOfFile) {
        this.urlOfFile = urlOfFile;
    }

    /**
     * @return The file id
     */
    @Exclude
    public String getKeyID() {
        return fileId;
    }

    /**
     * @param fileId Change file id
     */
    @Exclude
    public void setKeyID(String fileId) {
        this.fileId = fileId;
    }


}
