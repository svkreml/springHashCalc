package svkreml.crypto.cryptoModule;

import digest.crypto.svkreml.GetDigestAlgorithmsRequest;
import digest.crypto.svkreml.GetDigestAlgorithmsResponse;
import digest.crypto.svkreml.GetDigestRequest;
import digest.crypto.svkreml.GetDigestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.security.MessageDigest;

@Endpoint
public class DigestEndPoint {
    private static final String NAMESPACE_URI = "svkreml.crypto.digest";

    private DigestRepository digestRepository;

    @Autowired
    public DigestEndPoint(DigestRepository digestRepository) {
        this.digestRepository = digestRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDigestRequest")
    @ResponsePayload
    public GetDigestResponse getHashResponse(@RequestPayload GetDigestRequest request) {
        GetDigestResponse response = new  GetDigestResponse();
        MessageDigest messageDigest = digestRepository.get(request.getAlg());
        Assert.notNull(request.getInput(), "The input field must exist");
        response.setDigestValue(messageDigest.digest(request.getInput()));
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDigestAlgorithmsRequest")
    @ResponsePayload
    public GetDigestAlgorithmsResponse getHashResponse(@RequestPayload GetDigestAlgorithmsRequest request) {
        GetDigestAlgorithmsResponse getDigestAlgorithmsResponse = new GetDigestAlgorithmsResponse();
        getDigestAlgorithmsResponse.getDigestAlg().addAll(digestRepository.getAlgorithms());
        return getDigestAlgorithmsResponse;
    }
}
