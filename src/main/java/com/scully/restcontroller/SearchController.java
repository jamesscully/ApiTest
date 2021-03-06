package com.scully.restcontroller;


import com.scully.model.Location;
import com.scully.model.SearchResult;
import com.scully.search.SearchTaxis;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @RequestMapping("/dave")
    public SearchResult searchDave(
            @RequestParam(value = "pickup")                         String pickup,
            @RequestParam(value = "dropoff")                        String dropoff,
            @RequestParam(value = "passengers", defaultValue = "1") String passengers)
    {
        return SearchTaxis.query(SearchTaxis.SUP_DAVE, new Location(pickup), new Location(dropoff), Integer.parseInt(passengers));
    }

    @RequestMapping("/eric")
    public SearchResult searchEric(
            @RequestParam(value = "pickup")                         String pickup,
            @RequestParam(value = "dropoff")                        String dropoff,
            @RequestParam(value = "passengers", defaultValue = "1") String passengers)
    {
        return SearchTaxis.query(SearchTaxis.SUP_ERIC, new Location(pickup), new Location(dropoff), Integer.parseInt(passengers));
    }

    @RequestMapping("/jeff")
    public SearchResult searchJeff(
            @RequestParam(value = "pickup")                         String pickup,
            @RequestParam(value = "dropoff")                        String dropoff,
            @RequestParam(value = "passengers", defaultValue = "1") String passengers)
    {
        return SearchTaxis.query(SearchTaxis.SUP_JEFF, new Location(pickup), new Location(dropoff), Integer.parseInt(passengers));
    }

    @RequestMapping("/search")
    public SearchResult searchAll(
            @RequestParam(value = "pickup")                         String pickup,
            @RequestParam(value = "dropoff")                        String dropoff,
            @RequestParam(value = "passengers", defaultValue = "1") String passengers)
    {
        return SearchTaxis.queryAll(new Location(pickup), new Location(dropoff), Integer.parseInt(passengers));
    }


}
