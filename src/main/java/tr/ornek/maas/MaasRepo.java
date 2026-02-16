package tr.ornek.maas;

import java.sql.*;
import java.util.*;

public class MaasRepo {

  public void insert(String sicil, String adSoyad, int gun, double yevmiye) throws SQLException {
    double brut = gun * yevmiye;
    String sql = "INSERT INTO maas_kayit(sicil, ad_soyad, calisma_gun, yevmiye, brut) VALUES(?,?,?,?,?)";
    try (Connection c = Db.conn(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, sicil);
      ps.setString(2, adSoyad);
      ps.setInt(3, gun);
      ps.setDouble(4, yevmiye);
      ps.setDouble(5, brut);
      ps.executeUpdate();
    }
  }

  public List<MaasKayit> listAll() throws SQLException {
    String sql = "SELECT id, sicil, ad_soyad, calisma_gun, yevmiye, brut, created_at FROM maas_kayit ORDER BY id DESC";
    List<MaasKayit> out = new ArrayList<>();
    try (Connection c = Db.conn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        out.add(new MaasKayit(
            rs.getLong("id"),
            rs.getString("sicil"),
            rs.getString("ad_soyad"),
            rs.getInt("calisma_gun"),
            rs.getDouble("yevmiye"),
            rs.getDouble("brut"),
            rs.getString("created_at")
        ));
      }
    }
    return out;
  }
}
