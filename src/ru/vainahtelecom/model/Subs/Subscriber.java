package ru.vainahtelecom.model.Subs;


public class Subscriber {

    private String name = null;
    private String oid = null;
    private String index = null;
    private SubsInfo subsInfo = null;

    public Subscriber(String name, String oid, String index) {
        this.name = name;
        this.oid = oid;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getOid() {
        return oid;
    }

    public String getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public SubsInfo getSubsInfo() {
        return subsInfo;
    }

    public void setSubsInfo(SubsInfo subsInfo) {
        this.subsInfo = subsInfo;
    }
}