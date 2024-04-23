package com.vgt.ip.dataaccess.iplocation.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@Document("ip_location_v4")
@Data
public class IPLocationV4MongoEntity implements Serializable {
    private String primaryInfo;
    private String ip;
    private Long timestamp;
    private Map<String, IPLocationDetailEntity> allInfo = Collections.emptyMap();

    private static class IPLocationDetailEntity {
        private String country;
        private String province;
        private String distinct;
        private String city;
        private String lang;
        private String isp;
        private String description;
        private String isChinaRegion;
        private String region;
        private String isoCode;
        private String timeZone;
        private String ip;
        private String source;
    }

}