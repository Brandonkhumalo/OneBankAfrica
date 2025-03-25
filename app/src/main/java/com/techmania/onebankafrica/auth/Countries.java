package com.techmania.onebankafrica.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Countries {
    private static final HashMap<String, String> sadcCountries = new HashMap<>();

    static {
        sadcCountries.put("🇿🇦 South Africa", "ZA");
        sadcCountries.put("🇧🇼 Botswana", "BW");
        sadcCountries.put("🇲🇿 Mozambique", "MZ");
        sadcCountries.put("🇳🇦 Namibia", "NA");
        sadcCountries.put("🇿🇲 Zambia", "ZM");
        sadcCountries.put("🇿🇼 Zimbabwe", "ZW");
        sadcCountries.put("🇱🇸 Lesotho", "LS");
        sadcCountries.put("🇲🇼 Malawi", "MW");
        sadcCountries.put("🇲🇺 Mauritius", "MU");
        sadcCountries.put("🇸🇿 Eswatini", "SZ");
        sadcCountries.put("🇦🇴 Angola", "AO");
        sadcCountries.put("🇨🇩 DRC", "CD");
        sadcCountries.put("🇸🇨 Seychelles", "SC");
        sadcCountries.put("🇹🇿 Tanzania", "TZ");
    }

    public static HashMap<String, String> getCountryCodes() {
        return sadcCountries;
    }

    public static List<String> getCountryNames() {
        return new ArrayList<>(sadcCountries.keySet());
    }

    public static String getCode(String countryName) {
        return sadcCountries.getOrDefault(countryName, "South Africa"); // Default to SA
    }
}
