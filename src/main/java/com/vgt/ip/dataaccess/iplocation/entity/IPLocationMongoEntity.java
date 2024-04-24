package com.vgt.ip.dataaccess.iplocation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@Document(collection = "ip_location")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IPLocationMongoEntity implements Serializable {
    @Id
    private String id;
    private String primaryInfoKey;
    private String ip;
    private Long version;
    private Map<String, IPLocationDetailEntity> allInfo = Collections.emptyMap();
}