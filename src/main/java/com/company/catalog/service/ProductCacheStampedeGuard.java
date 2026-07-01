package com.company.catalog.service;

import com.company.catalog.dto.ProductResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProductCacheStampedeGuard {
    private final StringRedisTemplate redisTemplate;
    private final ProductService productService; // fuente de verdad (BD)
    private final ObjectMapperPort objectMapper;  // wrapper de serialización

    public ProductResponse getProduct(String id) {
        String cacheKey = "product:" + id;
        String cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return objectMapper.readValue(cached, ProductResponse.class);
        }

        String lockKey = "lock:product:" + id;
        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, "1", Duration.ofSeconds(5));

        if (Boolean.TRUE.equals(acquired)) {
            try {
                ProductResponse fresh = productService.findById(id);
                long jitterSeconds = ThreadLocalRandom.current().nextInt(0, 300);
                redisTemplate.opsForValue().set(
                        cacheKey,
                        objectMapper.writeValueAsString(fresh),
                        Duration.ofMinutes(60).plusSeconds(jitterSeconds));
                return fresh;
            } finally {
                redisTemplate.delete(lockKey);
            }
        } else {
            // Otro hilo ya está recalculando: pequeño backoff y reintento de lectura de caché
            sleepQuietly(50);
            String retryCached = redisTemplate.opsForValue().get(cacheKey);
            return retryCached != null
                    ? objectMapper.readValue(retryCached, ProductResponse.class)
                    : productService.findById(id); // fallback directo a BD si el retry falla
        }
    }

    private void sleepQuietly(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    interface ObjectMapperPort {
        String writeValueAsString(Object value);
        <T> T readValue(String json, Class<T> type);
    }
}
