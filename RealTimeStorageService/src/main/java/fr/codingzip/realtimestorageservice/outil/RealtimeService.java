package fr.codingzip.realtimestorageservice.outil;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import fr.codingzip.realtimestorageservice.interfaceClass.waitData;
import fr.codingzip.realtimestorageservice.modelData.modelDataOfRealtimeDatabase;

/**
 * RealtimeService is a class providing a simultaneous upload service
 * on Firebase Storage & Firebase RealtimeDataBase
 * Use {@link RealtimeService#UploadOn_FireStorage_RealtimeDatabase(Uri, String, ArrayList, ArrayList)} method
 * for simultaneous upload to Firebase Storage & Firebase RealTimeDataBase
 *
 * @author Drex
 * @version 1.0
 */
public class RealtimeService {

    //instance for firebaseStorage
    private FirebaseStorage firebaseStorage;
    //instance for firebaseDatabase
    private FirebaseDatabase firebaseDatabaseInstance;
    //reference for firebaseDatabase
    private DatabaseReference databaseReference;
    //Listener for firebaseDatabase RealtimeDataBase
    private ValueEventListener valueEventListener;
    //ArrayList to get data from firebaseDatabase (realtime-database)
    private final ArrayList<modelDataOfRealtimeDatabase> modelDataOfRealtimeDatabase = new ArrayList<modelDataOfRealtimeDatabase>();

    private String rootPathOnFirebaseStorage, rootPathOnFirebaseRealtimeDatabase;

    /**
     * Constructor for {@link RealtimeService}
     *
     * @param rootPathOnFirebaseRealtimeDatabase Designate the root of your directory path on Firebase RealTimeDataBase
     *                                           exp: -TheRootPath
     *                                           -....
     *                                           -....
     */
    public RealtimeService(String rootPathOnFirebaseRealtimeDatabase) {
        firebaseDatabaseInstance = FirebaseDatabase.getInstance();
        this.rootPathOnFirebaseRealtimeDatabase = rootPathOnFirebaseRealtimeDatabase;

    }

    /**
     * Constructor for {@link RealtimeService}
     *
     * @param rootPathOnFirebaseStorage          Designate the root of your directory path on Firebase Storage
     *                                           exp: TheRootPath/...
     * @param rootPathOnFirebaseRealtimeDatabase Designate the root of your directory path on Firebase RealTimeDataBase
     *                                           exp: -TheRootPath
     *                                           -....
     *                                           -...
     */
    public RealtimeService(String rootPathOnFirebaseStorage, String rootPathOnFirebaseRealtimeDatabase) {
        this(rootPathOnFirebaseRealtimeDatabase);
        firebaseStorage = FirebaseStorage.getInstance();
        this.rootPathOnFirebaseStorage = rootPathOnFirebaseStorage;
    }

    /**
     * Method of uploading to Firebase Storage and Firebase RealTimeDataBase simultaneously without notification
     *
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
    public void UploadOn_FireStorage_RealtimeDatabase(Uri uriOfFile, String nameOfFile, ArrayList<String> subPathForFirebaseStorage, ArrayList<String> subPathForFirebaseRealtimeDatabase) {

        String repertoryOnFirebaseStorage = String.join("/", subPathForFirebaseStorage);
        String repertoryOnFirebaseRealtimeDatabase = String.join("/", subPathForFirebaseRealtimeDatabase);

        if (uriOfFile != null) {
            // give a complete repertory on FirebaseStorage
            StorageReference storageReference = firebaseStorage.getReference(rootPathOnFirebaseStorage + "/" + repertoryOnFirebaseStorage + "/" + nameOfFile);
            // give a complete repertory on FirebaseDatabase
            databaseReference = firebaseDatabaseInstance.getReference(rootPathOnFirebaseRealtimeDatabase + "/" + repertoryOnFirebaseRealtimeDatabase);
            //put file on firebaseStorage
            storageReference.putFile(uriOfFile)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> taskUri = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!taskUri.isComplete()) ;
                                    //get url of file path on firebaseStorage
                                    Uri urlDuStockage = taskUri.getResult();
                                    modelDataOfRealtimeDatabase data = new modelDataOfRealtimeDatabase(nameOfFile, urlDuStockage.toString());
                                    //push keys to contain data on RealtimeDataBase
                                    databaseReference.child(databaseReference.push().getKey()).setValue(data);
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                }
                            });
        }
    }

    /**
     * Upload method on Firebase Storage & Firebase RealTimeDataBase simultaneously with notification
     *
     * @param ctx                                Context of the activity that calls this method
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
    public void UploadOn_FireStorage_RealtimeDatabase_Toast(Context ctx, Uri uriOfFile, String nameOfFile, ArrayList<String> subPathForFirebaseStorage, ArrayList<String> subPathForFirebaseRealtimeDatabase) {

        String repertoryOnFirebaseStorage = String.join("/", subPathForFirebaseStorage);
        String repertoryOnFirebaseRealtimeDatabase = String.join("/", subPathForFirebaseRealtimeDatabase);

        if (uriOfFile != null) {
            // give a complete repertory on FirebaseStorage
            StorageReference storageReference = firebaseStorage.getReference(rootPathOnFirebaseStorage + "/" + repertoryOnFirebaseStorage + "/" + nameOfFile);
            // give a complete repertory on FirebaseDatabase
            databaseReference = firebaseDatabaseInstance.getReference(rootPathOnFirebaseRealtimeDatabase + "/" + repertoryOnFirebaseRealtimeDatabase);
            //put file on firebaseStorage
            storageReference.putFile(uriOfFile)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> taskUri = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!taskUri.isComplete()) ;
                                    //get url of file path on firebaseStorage
                                    Uri urlDuStockage = taskUri.getResult();
                                    modelDataOfRealtimeDatabase data = new modelDataOfRealtimeDatabase(nameOfFile, urlDuStockage.toString());
                                    //push keys to contain data on RealtimeDataBase
                                    databaseReference.child(databaseReference.push().getKey()).setValue(data);

                                    Toast.makeText(ctx, "file send", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ctx, "fail file not send", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                }
                            });
        }
    }

    /**
     * Retrieve all data indicated in the subdirectory on RealtimeDatabase
     *
     * @param subPathForFirebaseRealtimeDatabase Designates the subdirectories on Firebase RealTimeDataBase in which the file will be stored
     * @param waitData                           serves as a callback to get them when they are available from asynchronous mode
     */
    public void getAllDataRealtimeDatabase(ArrayList<String> subPathForFirebaseRealtimeDatabase, waitData waitData) {

        String repertoryOnFirebaseRealtimeDatabase = String.join("/", subPathForFirebaseRealtimeDatabase);
        // give a complete repertory on FirebaseDatabase
        databaseReference = firebaseDatabaseInstance.getReference(rootPathOnFirebaseRealtimeDatabase + "/" + repertoryOnFirebaseRealtimeDatabase);
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                modelDataOfRealtimeDatabase.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    modelDataOfRealtimeDatabase data = dataSnapshot.getValue(modelDataOfRealtimeDatabase.class);
                    data.setKeyID(dataSnapshot.getKey());

                    modelDataOfRealtimeDatabase.add(data);
                }
                waitData.passData(modelDataOfRealtimeDatabase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    /**
     * @return RealtimeDatabase reference
     */
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    /**
     * remove EventListener from RealtimeDatabase
     */
    public void onDestroyEventListener() {
        databaseReference.removeEventListener(valueEventListener);
    }


}
