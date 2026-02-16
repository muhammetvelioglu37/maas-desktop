package tr.ornek.maas;

import java.sql.*;

public class Db {
  private static final String DB_URL = "jdbc:sqlite:maas.db";

  static {
    init();
  }

  public static Connection conn() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  private static void init() {
    try (Connection c = conn(); Statement st = c.createStatement()) {
      st.execute("""
        CREATE TABLE IF NOT EXISTS maas_kayit (
          id INTEGER PRIMARY KEY AUTOINCREMENT,
          sicil TEXT NOT NULL,
          ad_soyad TEXT NOT NULL,
          calisma_gun INTEGER NOT NULL,
          yevmiye REAL NOT NULL,
          brut REAL NOT NULL,
          created_at TEXT NOT NULL DEFAULT (datetime('now','localtime'))
        )
      """);
    } catch (SQLException e) {
      throw new RuntimeException("DB init error: " + e.getMessage(), e);
    }
  }
}
