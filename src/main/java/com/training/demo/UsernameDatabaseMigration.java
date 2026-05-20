package com.training.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Ensures the {@code users.username} column exists when upgrading an existing MySQL database.
 * Safe on H2 (Render prod): failures are logged and ignored — Hibernate ddl-auto handles schema.
 */
@Component
@Order(0)
public class UsernameDatabaseMigration implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(UsernameDatabaseMigration.class);

    private final JdbcTemplate jdbcTemplate;

    public UsernameDatabaseMigration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            if (usernameColumnExists()) {
                return;
            }
            jdbcTemplate.execute("ALTER TABLE users ADD COLUMN username VARCHAR(50) NULL");
            log.info("Added users.username column");
        } catch (Exception e) {
            log.warn("Username column migration skipped (Hibernate may still add it): {}", e.getMessage());
        }
    }

    private boolean usernameColumnExists() {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    """
                    SELECT COUNT(*)
                    FROM information_schema.columns
                    WHERE table_schema = DATABASE()
                      AND LOWER(table_name) = 'users'
                      AND LOWER(column_name) = 'username'
                    """,
                    Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            try {
                Integer h2Count = jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
                        WHERE LOWER(TABLE_NAME) = 'users' AND LOWER(COLUMN_NAME) = 'username'
                        """,
                        Integer.class);
                return h2Count != null && h2Count > 0;
            } catch (Exception ignored) {
                return false;
            }
        }
    }
}
