package uk.co.hd_tech.basketdemo.data.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

/**
 * Raw rate data
 */
public class Rate {

    private Map<String, Double> other = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Double> any() {
        return other;
    }

    @JsonAnySetter
    public void set(String name, Double value) {
        other.put(name, value);
    }

}
