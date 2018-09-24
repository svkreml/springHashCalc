package svkreml.crypto.cryptoModule;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.util.List;

public interface DigestRepository {
    @PostConstruct
    void initData();
    List<String> getAlgorithms();
    MessageDigest get(String alg);
}
