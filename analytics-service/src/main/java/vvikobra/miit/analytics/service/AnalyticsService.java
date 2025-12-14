package vvikobra.miit.analytics.service;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AnalyticsService {

    private final AtomicLong totalBookings = new AtomicLong();
    private final AtomicLong paidBookings = new AtomicLong();
    private final AtomicLong totalRevenue = new AtomicLong();
    private final AtomicLong totalBookingDurationHours = new AtomicLong();

    private final Map<String, AtomicLong> equipmentPopularity = new ConcurrentHashMap<>();

    private final AtomicLong paymentEvents = new AtomicLong();
    private final AtomicLong paymentSum = new AtomicLong();

    private final AtomicLong skipassActivated = new AtomicLong();
    private final AtomicLong skipassUsed = new AtomicLong();

    private final Map<Integer, AtomicLong> skipassUsageByHour = new ConcurrentHashMap<>();

    public void onBookingCreated(double cost,
                                 LocalDateTime from,
                                 LocalDateTime to,
                                 List<String> equipmentTypes) {

        totalBookings.incrementAndGet();
        totalRevenue.addAndGet((long) cost);

        long duration = Duration.between(from, to).toHours();
        totalBookingDurationHours.addAndGet(duration);

        for (String type : equipmentTypes) {
            equipmentPopularity
                    .computeIfAbsent(type, key -> new AtomicLong())
                    .incrementAndGet();
        }
    }

    public void onBookingPaid() {
        paidBookings.incrementAndGet();
    }

    public void onPaymentCalculated(double price) {
        paymentEvents.incrementAndGet();
        paymentSum.addAndGet((long) price);
    }

    public void onSkipassActivated() {
        skipassActivated.incrementAndGet();
    }

    public void onSkipassUsed(LocalDateTime usedAt) {
        skipassUsed.incrementAndGet();

        int hour = usedAt.getHour();
        skipassUsageByHour
                .computeIfAbsent(hour, key -> new AtomicLong())
                .incrementAndGet();
    }

    public long getTotalBookings() {
        return totalBookings.get();
    }

    public long getPaidBookings() {
        return paidBookings.get();
    }

    public long getTotalRevenue() {
        return totalRevenue.get();
    }

    public long getAverageBookingDuration() {
        long count = totalBookings.get();
        if (count == 0) return 0;
        return totalBookingDurationHours.get() / count;
    }

    public Map<String, Long> getEquipmentPopularity() {
        Map<String, Long> result = new HashMap<>();
        for (Map.Entry<String, AtomicLong> e : equipmentPopularity.entrySet()) {
            result.put(e.getKey(), e.getValue().get());
        }
        return result;
    }

    public double getAveragePayment() {
        long cnt = paymentEvents.get();
        if (cnt == 0) return 0;
        return (double) paymentSum.get() / cnt;
    }

    public long getActivatedSkipasses() {
        return skipassActivated.get();
    }

    public long getUsedSkipasses() {
        return skipassUsed.get();
    }

    public Map<Integer, Long> getSkipassUsageByHour() {
        Map<Integer, Long> result = new HashMap<>();
        for (Map.Entry<Integer, AtomicLong> e : skipassUsageByHour.entrySet()) {
            result.put(e.getKey(), e.getValue().get());
        }
        return result;
    }
}

