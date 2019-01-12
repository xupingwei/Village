package ink.alf.village.common;

public enum CatagoryType {

    PERSON("PERSON"),
    VEHICLE("VEHICLE"),
    THING("THING");


    private String catagory;

    CatagoryType(String catagory) {
        this.catagory = catagory;
    }

    public String getCatagory() {
        return catagory;
    }
}
