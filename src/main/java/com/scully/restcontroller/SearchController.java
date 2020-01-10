package com.scully.restcontroller;


import com.scully.model.SearchResult;
import com.scully.search.SearchTaxis;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @RequestMapping("/dave")
    public SearchResult searchDave(
            @RequestParam(value = "pickup")                         String pickup,
            @RequestParam(value = "dropoff")                        String dropoff,
            @RequestParam(value = "passengers", defaultValue = "0") String passengers)
    {
        SearchResult result = SearchTaxis.query(SearchTaxis.SUP_DAVE, 51, 1, 52, 1);

        return result;
    }

}
