package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.example.expert.domain.user.service.UserAdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class UserAdminController {

    private final UserAdminService userAdminService;

    @PatchMapping("/{userId}")
    public void changeUserRole(@PathVariable long userId, @RequestBody UserRoleChangeRequest userRoleChangeRequest) {
        userAdminService.changeUserRole(userId, userRoleChangeRequest);
    }
}
