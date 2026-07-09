package com.training.demo;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatPresenceService {

    private static final Duration ONLINE_WINDOW = Duration.ofMinutes(3);

    private final Map<Long, PresenceEntry> activeUsers = new ConcurrentHashMap<>();

    public void markActive(User user) {
        if (user == null || user.getId() == null) {
            return;
        }
        activeUsers.put(user.getId(), new PresenceEntry(user.isAdmin(), Instant.now()));
    }

    public void markOffline(User user) {
        if (user == null || user.getId() == null) {
            return;
        }
        activeUsers.remove(user.getId());
    }

    public boolean isOtherSideOnline(User currentUser) {
        if (currentUser == null || currentUser.getId() == null) {
            return false;
        }
        purgeStaleEntries();
        boolean needAdmin = !currentUser.isAdmin();
        return activeUsers.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(currentUser.getId()))
                .map(Map.Entry::getValue)
                .anyMatch(entry -> entry.admin() == needAdmin);
    }

    public boolean isUserOnline(Long userId) {
        if (userId == null) {
            return false;
        }
        purgeStaleEntries();
        return activeUsers.containsKey(userId);
    }

    public boolean isAnyTrainerOnline() {
        purgeStaleEntries();
        return activeUsers.values().stream().anyMatch(PresenceEntry::admin);
    }

    private void purgeStaleEntries() {
        Instant cutoff = Instant.now().minus(ONLINE_WINDOW);
        activeUsers.entrySet().removeIf(entry -> entry.getValue().lastSeen().isBefore(cutoff));
    }

    private record PresenceEntry(boolean admin, Instant lastSeen) {
    }
}
