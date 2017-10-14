package co.japo.myrssreader.model;

/**
 * Created by japodeveloper on 10/14/17.
 */

public class Feed {

    private String name;
    private String url;

    public Feed(){

    }

    public Feed(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
