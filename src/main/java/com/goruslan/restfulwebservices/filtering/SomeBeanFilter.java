package com.goruslan.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

public class SomeBeanFilter {

    /*
    // Dynamic filtering
    @GetMapping("/filtering2")
    public MappingJacksonValue retrieveSomeBean2(){
        SomeBean2 someBean = new SomeBean2("v1", "v2", "v3");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
    */
        public static MappingJacksonValue getFilter(Object bean, String... propertyArray) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(propertyArray);
            String filterName = SomeBean2.class.getAnnotation(JsonFilter.class).value();
            FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);

            MappingJacksonValue mapping = new MappingJacksonValue(bean);
            mapping.setFilters(filters);

            return mapping;
        }

}
