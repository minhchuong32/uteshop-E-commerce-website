package ute.shop.adapter;

import ute.shop.config.VnPayConfig;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class VnPayAdapter implements IPaymentGateway {

    @Override
    public String buildPaymentUrl(BigDecimal amount,
                                  String orderRef,
                                  String orderInfo,
                                  String ipAddress) {

        // Toàn bộ chi tiết VNPay bị giấu trong đây
        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version",    "2.1.0");
        params.put("vnp_Command",    "pay");
        params.put("vnp_TmnCode",    VnPayConfig.getTmnCode());
        params.put("vnp_Amount",     String.valueOf(amount.multiply(BigDecimal.valueOf(100)).longValue()));
        params.put("vnp_CurrCode",   "VND");
        params.put("vnp_TxnRef",     orderRef.replace(",", "-"));
        params.put("vnp_OrderInfo",  orderInfo);
        params.put("vnp_OrderType",  "other");
        params.put("vnp_Locale",     "vn");
        params.put("vnp_ReturnUrl",  VnPayConfig.getReturnUrl());
        params.put("vnp_IpAddr",     ipAddress);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put("vnp_CreateDate", fmt.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        params.put("vnp_ExpireDate", fmt.format(cld.getTime()));

        // Build query & hash
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder query = new StringBuilder();
        for (Iterator<String> itr = keys.iterator(); itr.hasNext();) {
            String k = itr.next();
            query.append(URLEncoder.encode(k, StandardCharsets.US_ASCII))
                 .append('=')
                 .append(URLEncoder.encode(params.get(k), StandardCharsets.US_ASCII));
            if (itr.hasNext()) query.append('&');
        }
        query.append("&vnp_SecureHash=").append(VnPayConfig.hashAllFields(params));

        return VnPayConfig.getVnpUrl() + "?" + query;
    }
}