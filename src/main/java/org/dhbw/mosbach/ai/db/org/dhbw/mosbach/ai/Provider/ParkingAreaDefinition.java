package org.dhbw.mosbach.ai.db.org.dhbw.mosbach.ai.Provider;

public class ParkingAreaDefinition {


    /*
    #       Eine Parking Area ist durch ihren Namen definiert
    #       die Anzahl in Parkplätze ist durch ein int Array beschrieben
    #       wobei die Dimension die Spalte und der Wert die Anzahl an Parkplätzen in der Spalte beschreibt
    #
    #       Parkplatz A würde dem entsprechend wie folgt aussehen :
    #
    #       [ ] [ ] [ ] [ ] [ ]
    #       [ ] [ ] [ ] [ ] [ ]
    #       [ ] [ ] [ ] [ ] [ ]
    #       [ ] [ ] [ ] [ ] [ ]
    #       [ ] [ ] [ ] [ ] [ ]
    #       [ ]     [ ]     [ ]
    #       [ ]     [ ]     [ ]
    #       [ ]     [ ]     [ ]
    #       [ ]     [ ]     [ ]
    #
    #       Die Parkplätze werden im DataProvider erstellt.
    #
    #
     */

    //Parking Area A
    public static final int[] parkingAreaA = {10,5,10,5,10};
    public static final String parkingAreaAName = "A";

    //Parking Area B
    public static final int[] parkingAreaB = {10,5,10,5,10};
    public static final String parkingAreaBName = "B";

    //Parking Area C
    public static final int[] parkingAreaC = {10,5,10,5,10};
    public static final String parkingAreaCName = "C";

    public static int[] getCorpusOfParkingArea(String parkingAreaName){

        switch (parkingAreaName){

            case parkingAreaAName:
                return parkingAreaA;
            case parkingAreaBName:
                return parkingAreaB;
            case parkingAreaCName:
                return parkingAreaC;

        }

        return new int[]{};
    }
}
