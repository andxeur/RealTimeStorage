package fr.codingzip.realtimestorageservice.interfaceClass;

import java.util.ArrayList;

import fr.codingzip.realtimestorageservice.modelData.modelDataOfRealtimeDatabase;

/**
 * Interface for hold data on firebase realtimeDatabase during upload process
 */
public interface waitData {

    /**
     * @param dataCollects collects all data once retrieved
     */
    public void passData(ArrayList<modelDataOfRealtimeDatabase> dataCollects);
}
