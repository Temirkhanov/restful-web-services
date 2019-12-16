package com.goruslan.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    // Static filtering
    @GetMapping("/filtering")
    public SomeBean retrieveSomeBean(){
        return new SomeBean("v1", "v2", "v3");
    }

    @GetMapping("/filtering-list")
    public List<SomeBean> retrieveListOfSomeBeans(){
        return Arrays.asList(new SomeBean("vv1", "vv2", "vv3"), new SomeBean("vv11", "vv22", "vv33"));
    }


    // Dynamic filtering
    @GetMapping("/filtering2")
    public MappingJacksonValue retrieveSomeBean2() {
        SomeBean2 someBean2 = new SomeBean2("v1", "v2", "v3");
        return SomeBeanFilter.getFilter(someBean2, "field1", "field2");
    }

    @GetMapping("/filtering2-list")
    public MappingJacksonValue retrieveListOfSomeBeans2(){
        List<SomeBean2> list = Arrays.asList(new SomeBean2("vv1", "vv2", "vv3"), new SomeBean2("vv11", "vv22", "vv33"));

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }



}
