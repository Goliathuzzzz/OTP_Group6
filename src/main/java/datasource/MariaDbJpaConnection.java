package datasource;

import jakarta.persistence.*;

public class MariaDbJpaConnection {

    private static volatile EntityManagerFactory emf;
    private static volatile EntityManager em;

    public static EntityManager getInstance() {

        if (em==null) {
            synchronized (MariaDbJpaConnection.class) {
                if (em == null) {
                    if (emf == null) {
                        emf = Persistence.createEntityManagerFactory("TatskatytotMariaDbUnit");
                    }
                    em = emf.createEntityManager();
                }
            }
        }
        return em;
    }

    private MariaDbJpaConnection() {
    }
}
