package com.my.sparta.concert.common.util

import java.util.concurrent.ConcurrentHashMap

class TokenCache<K, V>(private val maxSize: Int) {

    private val cache = ConcurrentHashMap<K, V>()
    private val keys = ArrayDeque<K>()

    fun put(key: K, value: V) {
        synchronized(this) {
            // 캐시에 값 추가
            cache[key] = value
            keys.addLast(key)

            // 크기 초과 시 가장 오래된 키 제거
            if (keys.size > maxSize) {
                val oldestKey = keys.removeFirst()
                cache.remove(oldestKey)
            }
        }
    }

    fun get(key: K): V? {
        return cache[key]
    }

    fun containsKey(key: K): Boolean {
        return cache.containsKey(key)
    }

    fun size(): Int {
        return cache.size
    }
}
