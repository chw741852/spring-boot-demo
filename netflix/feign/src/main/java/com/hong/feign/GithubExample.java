package com.hong.feign;

import feign.*;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;

import java.io.IOException;
import java.util.List;

/**
 * Created by caihongwei on 16/3/18 下午5:01.
 */
public class GithubExample {
    interface Github {
        @RequestLine("GET /repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);
    }

    static class Contributor {
        String login;
        int contributions;
    }

    static class GithubClientError extends RuntimeException {
        private String message; // parsed from json

        @Override
        public String getMessage() {
            return message;
        }
    }

    public static void main(String[] args) {
        Decoder decoder = new GsonDecoder();
        Github github = Feign.builder()
                .decoder(decoder)
                .errorDecoder(new GithubErrorDecoder(decoder))
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(Github.class, "https://api.github.com");

        System.out.println("Let's fetch and print a list of contributors to this library.");
        List<Contributor> contributors = github.contributors("netflix", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }

        System.out.println("Now, lets case an error.");
        try {
            github.contributors("netflix", "some-unknown-project");
        } catch (GithubClientError e) {
            System.out.println(e.getMessage());
        }
    }

    static class GithubErrorDecoder implements ErrorDecoder {
        final Decoder decoder;
        final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

        public GithubErrorDecoder(Decoder decoder) {
            this.decoder = decoder;
        }

        @Override
        public Exception decode(String methodKey, Response response) {
            try {
                return (Exception) decoder.decode(response, GithubClientError.class);
            } catch (IOException e) {
                return defaultDecoder.decode(methodKey, response);
            }
        }
    }
}
