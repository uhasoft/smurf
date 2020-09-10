package com.uhasoft.smurf.registry.consul;

import com.uhasoft.registry.core.MetaCustomizer;
import com.uhasoft.registry.core.TagCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class RegistrationCustomizer implements ConsulRegistrationCustomizer {

    @Autowired(required = false)
    private List<MetaCustomizer> metaCustomizers = Collections.EMPTY_LIST;

    @Autowired(required = false)
    private List<TagCustomizer> tagCustomizers = Collections.EMPTY_LIST;

    public void customize(ConsulRegistration registration) {
        List<String> tags = registration.getService().getTags();
        if(tags == null){
            tags = new ArrayList<>();
        }
        for(TagCustomizer customizer : tagCustomizers){
            tags.addAll(customizer.customize());
        }
        registration.getService().setTags(tags);

        Map<String, String> meta = registration.getService().getMeta();
        if(meta == null){
            meta = new HashMap<>();
        }
        for(MetaCustomizer customizer : metaCustomizers){
            meta.putAll(customizer.customize());
        }
        registration.getService().setMeta(meta);
    }
}
