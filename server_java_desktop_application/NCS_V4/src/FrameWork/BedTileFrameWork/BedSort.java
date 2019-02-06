package FrameWork.BedTileFrameWork;

import java.util.Comparator;

public class BedSort implements Comparator<BedModel> {
    @Override
    public int compare(BedModel o1, BedModel o2) {
        return o1.getBed_no()-o2.getBed_no();
    }
}
