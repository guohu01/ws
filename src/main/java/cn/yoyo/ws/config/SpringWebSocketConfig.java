package cn.yoyo.ws.config;

import cn.yoyo.ws.handle.SpringWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class SpringWebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getSpringWebSocketHandler(), "/websocket/server")
                .addInterceptors(getInterceptor()).setAllowedOrigins("*");

        registry.addHandler(getSpringWebSocketHandler(), "/sockjs/server").setAllowedOrigins("*")
                .addInterceptors(getInterceptor()).withSockJS();
    }
    @Bean
    public SpringWebSocketHandler getSpringWebSocketHandler() {
        return new SpringWebSocketHandler();
    }
    @Bean
    public HandshakeInterceptor getInterceptor() {
        return new cn.yoyo.ws.web.HandshakeInterceptor();
    }
}
