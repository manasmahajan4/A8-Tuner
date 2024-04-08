package adnyey.a8tuner;

/**
 * Created by Mahajan-PC on 2017-11-02.
 */

public class GridItem {

    int ID;
    String name;

    GridItem(){}

    GridItem(int ID, String name)
    {
        this.ID=ID;
        this.name=name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
