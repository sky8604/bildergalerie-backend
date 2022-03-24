package com.example.bildergaleriebackend;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class BildergalerieApplication extends ResourceConfig {
    public BildergalerieApplication() {
        packages("com.example.bildergaleriebackend").register(MultiPartFeature.class);
    }
}