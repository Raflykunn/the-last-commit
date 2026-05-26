package the.last.commit.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DB_FILE_NAME = "game.db";

    private static Path resolveDatabasePath() {
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        
        if (Files.exists(currentDir.resolve("app"))) {
            return currentDir.resolve("app").resolve("db").resolve(DB_FILE_NAME);
        }
        return currentDir.resolve("db").resolve(DB_FILE_NAME);
    }

    private static String getJdbcUrl() {
        try {
            Path dbPath = resolveDatabasePath();
            Path parentDir = dbPath.getParent();

            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }

            return "jdbc:sqlite:" + dbPath.toAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Gagal konfigurasi folder database: " + e.getMessage(), e);
        }
    }

    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            
            Connection conn = DriverManager.getConnection(getJdbcUrl());
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            return conn;
        } catch (Exception e) {
            throw new RuntimeException("Gagal koneksi ke database", e);
        }
    }

    public static void initializeDatabase() {
        String usersTable = """
            CREATE TABLE IF NOT EXISTS users(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            );
            """;

        String progressTable = """
            CREATE TABLE IF NOT EXISTS game_progress(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                hero_name TEXT,
                current_wave INTEGER DEFAULT 1,
                gold INTEGER DEFAULT 0,
                hp INTEGER DEFAULT 100,
                mana INTEGER DEFAULT 50,
                basic_attack_damage INTEGER DEFAULT 10,
                basic_skill_damage INTEGER DEFAULT 25,
                ultimate_damage INTEGER DEFAULT 50,
                upgrade_points INTEGER DEFAULT 0,
                FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
            );
            """;

        String itemsTable = """
            CREATE TABLE IF NOT EXISTS items(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                item_name TEXT NOT NULL,
                item_type TEXT,
                effect_value INTEGER,
                price INTEGER
            );
            """;

        String inventoryTable = """
            CREATE TABLE IF NOT EXISTS inventory(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                item_id INTEGER NOT NULL,
                quantity INTEGER DEFAULT 1,
                UNIQUE(user_id, item_id),
                FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
                FOREIGN KEY(item_id) REFERENCES items(id) ON DELETE CASCADE
            );
            """;

        String defaultItems = """
            INSERT OR IGNORE INTO items (id, item_name, item_type, effect_value, price)
            VALUES
            (1, 'Potion', 'heal', 50, 100),
            (2, 'Iron Sword', 'weapon', 15, 300),
            (3, 'Shield', 'defense', 10, 250),
            (4, 'Mega Potion', 'heal', 100, 250),
            (5, 'Critical Ring', 'crit', 5, 500);
            """;

        try (
            Connection conn = connect();
            Statement stmt = conn.createStatement()
        ) {
            stmt.execute(usersTable);
            stmt.execute(progressTable);
            stmt.execute(itemsTable);
            stmt.execute(inventoryTable);
            stmt.execute(defaultItems);

            System.out.println("Database siap digunakan di: " + resolveDatabasePath().toAbsolutePath());

        } catch (Exception e) {
            throw new RuntimeException("Error Kritis: Gagal membuat skema database", e);
        }
    }
}