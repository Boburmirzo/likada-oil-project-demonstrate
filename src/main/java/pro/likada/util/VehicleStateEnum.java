package pro.likada.util;

/**
 * Created by abuca on 28.03.17.
 */
public enum VehicleStateEnum {
    PARK,MOVE,FLIGTH;


    public static VehicleStateEnum fromInteger(int i) {
        if(i == 0){
            return PARK;
        }
        if(i == 1){
            return MOVE;
        }
        if(i == 2){
            return FLIGTH;
        }
        else{
            return null;
        }
    }
}
