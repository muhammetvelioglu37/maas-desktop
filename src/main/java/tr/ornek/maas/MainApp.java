package tr.ornek.maas;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

  private final MaasRepo repo = new MaasRepo();
  private final ObservableList<MaasKayit> data = FXCollections.observableArrayList();

  @Override
  public void start(Stage stage) {
    // Form alanları
    TextField tfSicil = new TextField();
    TextField tfAdSoyad = new TextField();
    TextField tfGun = new TextField();
    TextField tfYevmiye = new TextField();
    Label lbl = new Label();

    Button btnKaydet = new Button("Kaydet");
    btnKaydet.setOnAction(e -> {
      String sicil = tfSicil.getText().trim();
      String ad = tfAdSoyad.getText().trim();
      Integer gun = parseInt(tfGun.getText().trim());
      Double yev = parseDouble(tfYevmiye.getText().trim());

      if (sicil.isEmpty() || ad.isEmpty() || gun == null || yev == null) {
        lbl.setText("Hata: Alanları düzgün doldur.");
        return;
      }

      try {
        repo.insert(sicil, ad, gun, yev);
        lbl.setText("Kaydedildi ✅");
        tfSicil.clear(); tfAdSoyad.clear(); tfGun.clear(); tfYevmiye.clear();
        refresh();
      } catch (Exception ex) {
        lbl.setText("DB hata: " + ex.getMessage());
      }
    });

    GridPane form = new GridPane();
    form.setHgap(10); form.setVgap(10);
    form.addRow(0, new Label("Sicil:"), tfSicil);
    form.addRow(1, new Label("Ad Soyad:"), tfAdSoyad);
    form.addRow(2, new Label("Çalışma Gün:"), tfGun);
    form.addRow(3, new Label("Yevmiye:"), tfYevmiye);
    form.addRow(4, btnKaydet, lbl);

    // Tablo
    TableView<MaasKayit> table = new TableView<>(data);
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<MaasKayit, String> cTarih = new TableColumn<>("Tarih");
    cTarih.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

    TableColumn<MaasKayit, String> cSicil = new TableColumn<>("Sicil");
    cSicil.setCellValueFactory(new PropertyValueFactory<>("sicil"));

    TableColumn<MaasKayit, String> cAd = new TableColumn<>("Ad Soyad");
    cAd.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));

    TableColumn<MaasKayit, Integer> cGun = new TableColumn<>("Gün");
    cGun.setCellValueFactory(new PropertyValueFactory<>("calismaGun"));

    TableColumn<MaasKayit, Double> cYev = new TableColumn<>("Yevmiye");
    cYev.setCellValueFactory(new PropertyValueFactory<>("yevmiye"));

    TableColumn<MaasKayit, Double> cBrut = new TableColumn<>("Brüt");
    cBrut.setCellValueFactory(new PropertyValueFactory<>("brut"));

    table.getColumns().addAll(cTarih, cSicil, cAd, cGun, cYev, cBrut);

    VBox root = new VBox(12, form, new Separator(), table);
    root.setPadding(new Insets(16));

    stage.setTitle("Maaş Giriş (Çevrimdışı)");
    stage.setScene(new Scene(root, 900, 600));
    stage.show();

    refresh();
  }

  private void refresh() {
    try {
      data.setAll(repo.listAll());
    } catch (Exception e) {
      // sessiz geç
    }
  }

  private Integer parseInt(String s) {
    try { return Integer.parseInt(s); } catch (Exception e) { return null; }
  }
  private Double parseDouble(String s) {
    try { return Double.parseDouble(s.replace(",", ".")); } catch (Exception e) { return null; }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
