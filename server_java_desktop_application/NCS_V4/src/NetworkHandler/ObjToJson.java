package NetworkHandler;

import FrameWork.BedTileFrameWork.BedModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ObjToJson {
    private final Gson gson=new Gson();
    private Type type=new TypeToken<BedModel>(){}.getType();

    public BedModel jsonToObj(String json){
        return gson.fromJson(json,type);

    }
    public String objToJson(BedModel bedModel){
        return gson.toJson(bedModel,type);
    }

}