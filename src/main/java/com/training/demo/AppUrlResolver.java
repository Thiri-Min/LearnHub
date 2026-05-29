package com.training.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

@Component
public class AppUrlResolver {

    public String resolvePublicBaseUrl(HttpServletRequest request, String configuredPublicUrl) {
        if (configuredPublicUrl != null && !configuredPublicUrl.isBlank()) {
            return trimTrailingSlash(configuredPublicUrl.trim());
        }

        String host = request.getServerName();
        int port = request.getServerPort();
        String scheme = request.getScheme();

        if ("localhost".equalsIgnoreCase(host) || "127.0.0.1".equals(host)) {
            String lanIp = findLanIpv4();
            if (lanIp != null) {
                host = lanIp;
            }
        }

        if (("http".equals(scheme) && port == 80) || ("https".equals(scheme) && port == 443)) {
            return scheme + "://" + host;
        }
        return scheme + "://" + host + ":" + port;
    }

    private static String findLanIpv4() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface networkInterface : Collections.list(interfaces)) {
                if (!networkInterface.isUp() || networkInterface.isLoopback() || networkInterface.isVirtual()) {
                    continue;
                }
                for (InetAddress address : Collections.list(networkInterface.getInetAddresses())) {
                    if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (Exception ignored) {
            // fall back to request host
        }
        return null;
    }

    private static String trimTrailingSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
