package tr.ornek.maas;

public record MaasKayit(
    long id,
    String sicil,
    String adSoyad,
    int calismaGun,
    double yevmiye,
    double brut,
    String createdAt
) {}
