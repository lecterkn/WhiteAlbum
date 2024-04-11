# WhiteAlbum - Valorant store checker

## Install
1. install [Java 17 JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. download WhiteAlbum from [Releases](https://github.com/lecterkn/WhiteAlbum/releases)

## Usage
1. run jar file or batch/shell file

```sh
java -jar WhiteAlbum-0.0.1-SNAPSHOT.jar
```

2. connect to [http://localhost:8100](http://localhost:8100)

```
http://localhost:8100
```
   
## CloudFlare 1020 Bypass
just use this🧑‍🦯

```java
private static SSLConnectionSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException {
    return new SSLConnectionSocketFactory(SSLContext.getDefault(), new String[]{"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"}, new String[]{"TLS_CHACHA20_POLY1305_SHA256", "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384"}, new DefaultHostnameVerifier());
}
```