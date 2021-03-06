package ru.kpfu.itis.rodsher.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.rodsher.dto.Dto;
import ru.kpfu.itis.rodsher.dto.Status;
import ru.kpfu.itis.rodsher.models.RegInfo;
import ru.kpfu.itis.rodsher.security.details.UserDetailsImpl;
import ru.kpfu.itis.rodsher.services.AuthService;
import ru.kpfu.itis.rodsher.services.ContentFiller;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/signUp")
public class SignUpController {
    @Autowired
    private AuthService authService;

    @Autowired
    private ContentFiller contentFiller;

    @PreAuthorize("isAnonymous()")
    @GetMapping
    public String getForm(@AuthenticationPrincipal UserDetailsImpl userDetails, ModelMap map) {
        if(userDetails != null) {
            return "redirect:/user";
        }
        map.put("selected_country", contentFiller.EMPTY_COUNTRY_NAME);
        map.put("countries", contentFiller.getCountries());
        return "auth/sign_up";
    }

//    @PreAuthorize("isAnonymous()")
//    @PostMapping
//    public String signUp(@RequestParam Map<String, String> params, ModelMap map) {
//        Dto dto = authService.signUpFromMap(params);
//        if(dto.getStatus().equals(Status.USER_SIGN_UP_SUCCESS)) {
//            return "redirect:/signUp/success";
//        }
//        else {
//            map.put("email", params.get("email"));
//            map.put("name", params.get("name"));
//            map.put("surname", params.get("surname"));
//            map.put("sex", params.get("sex"));
//            map.put("birthday", map.get("birthday"));
//            map.put("selected_country", params.get("country"));
//            map.put("countries", contentFiller.getCountries());
//
//            map.put("errors", (List<String>) dto.get("errors"));
//            return "auth/sign_up";
//        }
//    }

    @PreAuthorize("isAnonymous()")
    @PostMapping
    public String signUp(@Valid RegInfo regInfo, BindingResult bindingResult, ModelMap map) {
        Dto dto = authService.signUpFromClass(regInfo);
        if(!bindingResult.hasErrors() && dto.getStatus().equals(Status.USER_SIGN_UP_SUCCESS)) {
                return "redirect:/signUp/success";
        }
        map.put("email", regInfo.getEmail());
        map.put("name", regInfo.getName());
        map.put("surname", regInfo.getSurname());
        map.put("sex", regInfo.isSex());
        map.put("birthday", regInfo.getBirthday());
        map.put("selected_country", regInfo.getCountry());
        map.put("countries", contentFiller.getCountries());

        map.put("errors", (List<String>) dto.get("errors"));
        return "auth/sign_up";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/success")
    public String signUpSuccess(@AuthenticationPrincipal UserDetailsImpl userDetails, ModelMap map) {
        if(userDetails != null) {
            return "redirect:/user";
        }
        return "auth/sign_up_success";
    }
}