package com.DOH.DOH.Patient.API.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.DOH.DOH.Patient.API.entity.Users;

public class ApplicationAuditAware implements AuditorAware<Integer> {

	@Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
            !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        Users userPrincipal = (Users) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
	
}
