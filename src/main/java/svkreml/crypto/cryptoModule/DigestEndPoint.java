package svkreml.crypto.cryptoModule;

import digest.crypto.svkreml.GetDigestAlgorithmsRequest;
import digest.crypto.svkreml.GetDigestAlgorithmsResponse;
import digest.crypto.svkreml.GetDigestRequest;
import digest.crypto.svkreml.GetDigestResponse;


import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
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
    public GetDigestResponse getDigestResponse(@RequestPayload GetDigestRequest request) {
        GetDigestResponse response = new  GetDigestResponse();
        MessageDigest messageDigest = digestRepository.get(request.getAlg());
        Assert.notNull(request.getInput(), "Empty input field must exist or is not Base64 Encodable");
        Assert.isTrue(Base64.isBase64(request.getInput()), "The input field must be Base64 Encodable");
        //System.out.println(Base64.getDecoder().decode(request.getInput()));
        response.setDigestValue(messageDigest.digest(request.getInput()));
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDigestAlgorithmsRequest")
    @ResponsePayload
    public GetDigestAlgorithmsResponse getDigestResponse(@RequestPayload GetDigestAlgorithmsRequest request) {
        GetDigestAlgorithmsResponse getDigestAlgorithmsResponse = new GetDigestAlgorithmsResponse();
        getDigestAlgorithmsResponse.getDigestAlg().addAll(digestRepository.getAlgorithms());
        return getDigestAlgorithmsResponse;
    }
}
