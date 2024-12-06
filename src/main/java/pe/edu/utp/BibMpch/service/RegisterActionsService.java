package pe.edu.utp.BibMpch.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.utp.BibMpch.model.RegisterActions;
import pe.edu.utp.BibMpch.model.User;
import pe.edu.utp.BibMpch.repository.RegisterActionsRepository;
import pe.edu.utp.BibMpch.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterActionsService {

    @Autowired
    private final RegisterActionsRepository registerActionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    public RegisterActions newRegisterAction (String detail) {
        RegisterActions registerActions = RegisterActions.builder()
                .userId(getUser())
                .detail("[%s] %s".formatted(request.getMethod(), detail))
                .fec(OffsetDateTime.now())
                .ip(getClientIpAddress())
                .build();
        return registerActionsRepository.save(registerActions);
    }

    public RegisterActions newRegisterAction (String detail, String userDocument) {
        Optional<User> userOptional = userRepository.findByDocument(userDocument);

        if (userOptional.isEmpty()) return null;

        RegisterActions registerActions = RegisterActions.builder()
                .userId(userOptional.get().getUserId())
                .detail("[%s] %s".formatted(request.getMethod(), detail))
                .fec(OffsetDateTime.now())
                .ip(getClientIpAddress())
                .build();

        return registerActionsRepository.save(registerActions);
    }

    private Long getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getUserId();
        }
        return null;
    }

    private String getClientIpAddress() {
        String[] headerCandidates = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_CLIENT_IP",
                "HTTP_X_CLUSTER_CLIENT_IP"
        };

        for (String header : headerCandidates) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim();
            }
        }

        String remoteAddr = request.getRemoteAddr();
        return "0:0:0:0:0:0:0:1".equals(remoteAddr) ? "LOCALHOST" :
                remoteAddr != null ? remoteAddr : "IP_NO_IDENTIFICADA";
    }
}
