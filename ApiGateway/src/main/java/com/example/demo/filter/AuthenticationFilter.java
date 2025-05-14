package com.example.demo.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.example.demo.util.JwtUtil;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
   
	@Autowired
    private RouteValidator validator;

	@Autowired
    private JwtUtil util;

    public static class Config {
    }

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return handleUnauthorized(exchange.getResponse(), "Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    String role = util.extractRolesFromToken(authHeader);
                    String requestedPath = exchange.getRequest().getPath().toString();
                    String method = exchange.getRequest().getMethod().name();

                    if (!isAuthorized(role, requestedPath, method)) {
                        return handleUnauthorized(exchange.getResponse(), "Unauthorized access");
                    }

                } catch (Exception e) {
                    return handleUnauthorized(exchange.getResponse(), "Invalid token");
                }
            }
            return chain.filter(exchange);
        };
    }
    
    private boolean isAuthorized(String role, String path, String method) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            // admin has full access
            return true;
        } else if ("ORGANISER".equalsIgnoreCase(role)) {
            // organiser can access event, user, booking, notifications, and feedback paths
            return path.startsWith("/event") || 
                   path.startsWith("/user/add") || 
                   path.startsWith("/user/getUserById") || 
                   path.startsWith("/user/getAllUsers") || 
                   path.startsWith("/booking/getTicketById") || 
                   path.startsWith("/booking/getAllTickets") || 
                   path.startsWith("/booking/getBookingsByEventId") || 
                   path.startsWith("/notifications/getAll") || 
                   path.startsWith("/notifications/add") || 
                   path.startsWith("/feedback");
        } else if ("USER".equalsIgnoreCase(role)) {
            // User-specific access with certain actions allowed
            return (path.startsWith("/event/getAllEvents") || 
                    path.startsWith("/event/getEventById") || 
                    path.startsWith("/user/add") || 
                    path.startsWith("/user/update") || 
                    path.startsWith("/user/getUserById") || 
                    path.startsWith("/user/removeUserById") || 
                    path.startsWith("/booking/bookTicket") || 
                    path.startsWith("/booking/cancelTicket") || 
                    path.startsWith("/booking/getTicketById") || 
                    path.startsWith("/notifications/getById") || 
                    path.startsWith("/notification/delete") || 
                    path.startsWith("/feedback"));
        }
        return false;
    }


    private Mono<Void> handleUnauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}

