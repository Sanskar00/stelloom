package com.sanskar.project.stelloom.organization;

import com.sanskar.project.stelloom.organization.dto.OrgInviteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/org-invite")
@RequiredArgsConstructor
public class OrgInviteController {
    private OrgInviteRepository orgInviteRepository;
    private OrgInviteService orgInviteService;

//    @GetMapping(path = "/")
//
//    @PostMapping(path ="/generate-code")
//    public String post(@AuthenticationPrincipal UserDetails me, @RequestBody OrgInviteRequest request){
//        return orgInviteService.create(me, request);
//    }

}
