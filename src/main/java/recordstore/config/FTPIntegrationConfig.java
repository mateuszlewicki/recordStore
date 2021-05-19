package recordstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ftp.dsl.Ftp;
import org.springframework.integration.ftp.dsl.FtpInboundChannelAdapterSpec;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Configuration
public class FTPIntegrationConfig {

    @Value("${upload.path}")
    String uploadPath;

    @Bean
    DefaultFtpSessionFactory defaultFtpSessionFactory(
            @Value("${ftp.username}") String username,
            @Value("${ftp.password}") String pw,
            @Value("${ftp.host}") String host,
            @Value("${ftp.port}") int port) {
        DefaultFtpSessionFactory defaultFtpSessionFactory = new DefaultFtpSessionFactory();
        defaultFtpSessionFactory.setPassword(pw);
        defaultFtpSessionFactory.setUsername(username);
        defaultFtpSessionFactory.setHost(host);
        defaultFtpSessionFactory.setPort(port);
        return defaultFtpSessionFactory;
    }

    @Bean
    IntegrationFlow inbound(DefaultFtpSessionFactory ftpSf) {
        File localDirectory = new File(uploadPath);
        FtpInboundChannelAdapterSpec spec = Ftp
                .inboundAdapter(ftpSf)
                .autoCreateLocalDirectory(true)
                .patternFilter("*.jpg")
                .localDirectory(localDirectory);
        return IntegrationFlows
                .from(spec, pc -> pc.poller(pm -> pm.fixedRate(1000, TimeUnit.MILLISECONDS)))
                .handle((file, messageHeaders) -> null).get();
    }
}