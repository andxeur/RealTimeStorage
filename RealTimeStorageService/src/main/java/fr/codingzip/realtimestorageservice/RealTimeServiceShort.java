package fr.codingzip.realtimestorageservice;

import android.net.Uri;

import java.util.ArrayList;

import fr.codingzip.realtimestorageservice.outil.RealtimeService;

/**
 * RealTimeServiceShort is a class providing an easier and shorter way to use the simultaneous download system
 * of class {@link RealtimeService}
 * on Firebase Storage and Firebase RealtimeDataBase
 * Use {@link RealTimeServiceShort#sendTo_FireStorage_RealtimeDatabase(String, String, Uri, String, ArrayList, ArrayList)} method
 * for simultaneous upload to Firebase Storage and Firebase RealTimeDataBase
 *
 * @author Drex
 * @version 1.0
 */
public class RealTimeServiceShort {

    /**
     * @param rootPathOnFirebaseStorage          Designate the root of your directory path on Firebase Storage
     *                                           exp: TheRootPath/...
     * @param rootPathOnFirebaseRealtimeDatabase Designate the root of your directory path on Firebase RealTimeDataBase
     *                                           exp: -TheRootPath
     *                                           -....
     *                                           -...
     * @param uriOfFile                          uri of file to upload to Firebase Storage & Firebase RealTimeDataBase
     * @param nameOfFile                         the name of the file to send
     * @param subPathForFirebaseStorage          Designates the Firebase Storage subdirectories in which the file will be stored
     *                                           {@<code>
     *                                           <p>
     *                                           ArrayList<String> paths = new ArrayList<>();
     *                                           paths.add("path1");
     *                                           paths.add("path2");
     *                                           paths.add("path3");
     *                                           <p>
     *                                           => .../path1/path2/path3
     *                                           </code>}
     * @param subPathForFirebaseRealtimeDatabase Designates the subdirectories on Firebase RealTimeDataBase in which the file will be stored
     */
    public void sendTo_FireStorage_RealtimeDatabase(String rootPathOnFirebaseStorage, String rootPathOnFirebaseRealtimeDatabase, Uri uriOfFile, String nameOfFile, ArrayList<String> subPathForFirebaseStorage, ArrayList<String> subPathForFirebaseRealtimeDatabase) {
        new RealtimeService(rootPathOnFirebaseStorage, rootPathOnFirebaseRealtimeDatabase).UploadOn_FireStorage_RealtimeDatabase(uriOfFile, nameOfFile, subPathForFirebaseStorage, subPathForFirebaseRealtimeDatabase);
    }
}
