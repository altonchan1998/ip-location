package com.vgt.ip.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class IpAddressResolver {
    public static String getClientIp(Map<String, String> headers) {
        try {
            log.info("start getClientIP");
            if (headers.get("true-client-ip") != null) {
                log.info("[{}][IP] source => true-client-ip: %{}", headers.get("uuid"), headers.get("true-"));
                return getIpFromHeader(headers, "true-client-ip");

            }

            if (headers.get("x-original-forwarded-for") != null) {
                log.info("{}][IP] source =>  x-original-forwarded-for: {}", headers.get("uuid"), headers.get("x-original-forwarded-for"));
                return getIpFromHeader(headers, "x-original-forwarded-for");
            }

            if (headers.get("x-forwarded-for") != null) {
                log.info("[{}][IP] source =>  x-forwarded-for: {}", headers.get("uuid"), headers.get("x-forwarded-for"));
                return getIpFromHeader(headers, "x-forwarded-for");
            }

            return "127.0.0.1";
        } catch (Exception e) {
            log.error("[[{}][IP] getClientIP fail: {}", headers.get("uuid"), e.getMessage());
            return "127.0.0.1";
        }
    }

    private static String getIpFromHeader(Map<String, String> headers, String name) {
        String[] ipList = headers.get(name).split(",");
        return ipList[0].trim();
    }

    public static Boolean isValidIp(String ip) {
        log.info("[{}][IP] check valid ip", ip);
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}
