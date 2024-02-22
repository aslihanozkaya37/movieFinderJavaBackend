package MovieFinder.MovieFinder.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Root{
    @JsonProperty("Search") 
    public ArrayList<Search> search;
    public String totalResults;
    @JsonProperty("Response") 
    public String response;
}

