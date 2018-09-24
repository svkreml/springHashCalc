package svkreml.crypto.cryptoModule;


import org.bouncycastle.jcajce.provider.digest.GOST3411;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DigestRepositoryBC implements DigestRepository {
    private static final Map<String, MessageDigest> algs = new HashMap<>();

    private void put(MessageDigest messageDigest) {
        algs.put(messageDigest.getAlgorithm(), messageDigest);
        algorithms.add(messageDigest.getAlgorithm());
    }

    public List<String> getAlgorithms() {
        return algorithms;
    }

   private List<String> algorithms = new ArrayList<>();


    @PostConstruct
    public void initData() {
        put(new GOST3411.Digest());
        put(new GOST3411.Digest2012_256());
        put(new GOST3411.Digest2012_512());
    }

    public MessageDigest get(String alg) {
        Assert.notNull(alg, "The digestAlg's field must exist");
        MessageDigest messageDigest = algs.get(alg);
        Assert.notNull(messageDigest, "The digestAlg's do not exist here, try getDigestAlgorithmsRequest");
        messageDigest.reset();
        return messageDigest;
    }

}

