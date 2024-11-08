# RealTimeStorage

__RealTimeStorage is an Android online or file recovery library on [Firebase](https://firebase.google.com).
This library is based on services provided by firabase such as Firestore and RealTime-database.__

## Why this library?

It was developed to synchronize the download of a file on Firestore and RealTime-database simultaneously
While getting the download URL of the file.

Its purpose is to help Android developers set up online storage systems (such as pdf, image...)
for their users on [firebase] (https://firebase.google.com) via their mobile app.

This is practically useful for solving problems like:
* [How to get the Firebase Storage getDownloadURL URL](https://stackoverflow.com/questions/37374868/how-to-get-url-from-firebase-storage-getdownloadurl)
* [How do I return the DataSnapshot value after a method? ](https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method/47853774#47853774)
* [How do I manage an asynchronous database with Firebase? ](https://stackoverflow.com/questions/48720701/how-to-handle-asynchronous-database-with-firebase)
* [How do I return the DataSnapshot value after a method? ](https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method/47853774#47853774)

__Note: *Currently version 1.0 only supports the uriOfFile of the file stored on the user’s device for sending to Firestore and synchronizing to RealTime-database. *__

## In short, what does it allow you to do?

It allows you to send a file to Firestore by synchronizing it with RealTime-database.
to get the URL of the file storage for download or either the file name or the key (identifier) on which it is stored on RealTime-database to possibly delete it

## Installation?

Please follow the steps below.

1. First, please connect your application to firebase if it has not been done

2. Then integrate the sdk of the following services as a dependency on your application if you have not already done so:
3. 
```gradle
implementation 'com.google.firebase:firebase-storage:20.1.0' //Firestore
implementation 'com.google.firebase:firebase-database:20.1.0' //RealTime-database
```
*Note: Remember to provide internet access in your application.
Because sending to Firestore and RealTime-database is of course done with an Internet connection.*

3. Add a dependency below to use the RealTimeStorage library
4. 
```gradle
implementation 'com.github.Drex-xdev:RealTimeStorage:1.0' //RealTimeStorage
```
*For your information, RealTimeStorage version 1.0 uses the following firebase sdk version:*

```gradle
implementation 'com.google.firebase:firebase-storage:20.1.0' //Firestore
implementation 'com.google.firebase:firebase-database:20.1.0' //RealTime-database
```
Yours may be superior to ours, but don’t worry.

## using the RealTimeStorage library

1. Sending a file:

``` java
//exp:equivalent to the following subdirectory -> .../path1/path2/path3
ArrayList<String> subPathForFirebaseStorage = new ArrayList<>();
cheminsFirestore.add(chemin1);
cheminsFirestore.add(chemin2);
cheminsFirestore.add(chemin3);

//exp:equivalent to the following subdirectory -> .../path1/path2/path3
ArrayList<String> subPathForFirebaseRealtimeDatabase = new ArrayList<>();
cheminsReatimeDatabase.add(chemin1);
cheminsReatimeDatabase.add(chemin2);
cheminsReatimeDatabase.add(chemin2);

RealtimeService rts = new RealtimeService("rootPathOnFirebaseStorage", "rootPathOnFirebaseRealtimeDatabase");
rts.UploadOn_FireStorage_RealtimeDatabase(uriOfFile,nameOfFile,subPathForFirebaseStorage,subPathForFirebaseRealtimeDatabase);

//or more simply 
new RealtimeService("rootPathOnFirebaseStorage","rootPathOnFirebaseRealtimeDatabase").UploadOn_FireStorage_RealtimeDatabase(uriOfFile,nameOfFile,subPathForFirebaseStorage,subPathForFirebaseRealtimeDatabase);
```
or you can use the __RealTimeServiceShort__ class and pass all information to its “sendTo_FireStorage_RealtimeDatabase("pdf", "pdf ufr",uriOfFile,nameDuPdf,cheminsportout,cheminsportout) method for faster use.

``` java
new RealTimeServiceShort().sendTo_FireStorage_RealtimeDatabase("rootPathOnFirebaseStorage", "rootPathOnFirebaseRealtimeDatabase",uriOfFile,nameOfFile,subPathForFirebaseStorage,subPathForFirebaseRealtimeDatabase);
```
2. Send with notification:

```java
RealtimeService rts = new RealtimeService("rootPathOnFirebaseStorage", "rootPathOnFirebaseRealtimeDatabase");
rts.UploadOn_FireStorage_RealtimeDatabase_Toast(uriOfFile,nameOfFile,subPathForFirebaseStorage,subPathForFirebaseRealtimeDatabase);
```

3. File Recovery:

``` java
//exp:equivalent to the following subdirectory -> .../path1/path2/path3
ArrayList<String> subPathForFirebaseRealtimeDatabase = new ArrayList<>();
cheminsReatimeDatabase.add(chemin1);
cheminsReatimeDatabase.add(chemin2);
cheminsReatimeDatabase.add(chemin2);

RealtimeService rts = new RealtimeService("rootPathOnFirebaseRealtimeDatabase");

rts.getAllDataRealtimeDatabase(subPathForFirebaseRealtimeDatabase, new waitData() {
    @Override
    public void passData(ArrayList<modelDataOfRealtimeDatabase> dataCollects) {
    //dataCollects collects data from the subdirectories (location) passed as the first parameter of the function
    //dataCollects is a modelDataOfRealtimeDatabase object
    
    //get file name
    dataCollects.get(position).getNameOfFile()
    //get file url
    dataCollects.get(position).getUrlOfFile()
    //obtain file id
    dataCollects.get(position).getKeyID()
    
    //change file name
    dataCollects.get(position).setKeyID();
    //change the file url
    dataCollects.get(position).setNameOfFile();
    //change file id
    dataCollects.get(position).setUrlOfFile();
     }
});
```

__modelDataOfRealtimeDatabase__ is an interface to structure data based on a model before sending or retrieving from the RealTime database.

4. How to simultaneously delete the file stored on Firestore and its data on RealTime-database?

You can use the following function:

```java

public void suppDansRealtimeDataBaseEtFireStorage(String idFile, String urlFile) {

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference stRef = storage.getReferenceFromUrl(urlFile);
        stRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void vv) {
            
            //You can get an instance on RealTimeDataBase of the recover file as a result 
            rts.getDatabaseReference().child(idFile).removeValue();
                     
            }
        });
}
    
```

and call it after:

```java
suppDansRealtimeDataBaseEtFireStorage(dataCollects.get(position).getKeyID(), dataCollects.get(position).getUrlOfFile());
```

5. Remove or delete the earphone from the RealTime database

If you want to remove or delete the earphone from the RealTime database when you close your application activity
use the following method:

```java
rts.onDestroyEventListener();
```

If for any reason you want to access the RealTime-database instance, you can do so as a follow-up :

```java
rts.getDatabaseReference();
```

## One more thing

If you have any suggestions to improve this freedom, write to me.

## Licence 
created by [andxeur](https://github.com/andxeur)
