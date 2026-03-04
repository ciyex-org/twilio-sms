package org.ciyex.sms.config;

public final class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {}

    public static void set(String orgAlias) {
        CURRENT_TENANT.set(orgAlias);
    }

    public static String get() {
        return CURRENT_TENANT.get();
    }

    public static void setSystemTenant() {
        CURRENT_TENANT.set("__SYSTEM__");
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
