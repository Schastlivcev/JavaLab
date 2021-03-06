package ru.kpfu.itis.rodsher.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.rodsher.dto.Dto;
import ru.kpfu.itis.rodsher.dto.Status;
import ru.kpfu.itis.rodsher.models.Channel;
import ru.kpfu.itis.rodsher.models.Friends;
import ru.kpfu.itis.rodsher.models.User;
import ru.kpfu.itis.rodsher.models.Wall;
import ru.kpfu.itis.rodsher.security.details.UserDetailsImpl;
import ru.kpfu.itis.rodsher.services.ChatService;
import ru.kpfu.itis.rodsher.services.UserService;
import ru.kpfu.itis.rodsher.services.WallArticleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserPageController {
    @Autowired
    private UserService userService;

    @Autowired
    private WallArticleService wallArticleService;

    @Autowired
    private ChatService chatService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public String getPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "redirect:/user/" + userDetails.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{user-id}")
    public String getPage(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("user-id") String userId, ModelMap map) {
        try {
            long id = Long.parseLong(userId);
            if(userDetails.getId() == id) {
                map.put("user", userDetails.getUser());
            } else {
                Dto dto = userService.loadUser(id);
                if(!dto.getStatus().equals(Status.USER_LOAD_SUCCESS)) {
                    map.put("status", 404);
                    map.put("text", "Пользователя с таким id не существует.");
                    return "error_page";
                }
                map.put("user", (User) dto.get("user"));
                dto = userService.checkFriendship(userDetails.getId(), id);
                if(dto.getStatus().equals(Status.FRIENDSHIP_PRESENTED)) {
                    map.put("friends", (Friends) dto.get("friends"));
                }
                dto = chatService.checkIfChannelExistsForUsers(userDetails.getId(), id);
                if(dto.getStatus().equals(Status.CHANNEL_EXISTS_FOR_USERS)) {
                    List<Channel> channels = (List<Channel>) dto.get("channels");
                    map.put("channelId", channels.get(0).getId());
                }
            }
            map.put("me", userDetails.getUser());
            Dto dto = wallArticleService.loadArticlesByUserIdWithReplies(id);
            if(!dto.getStatus().equals(Status.ARTICLE_LOAD_BY_USER_ID_SUCCESS)) {
                map.put("status", 500);
                map.put("text", "Ошибка при загрузке страницы");
                return "error_page";
            }
            map.put("walls", (List<Wall>) dto.get("walls"));
            if(userDetails.getId() != id) {
                dto = wallArticleService.loadArticlesByUserIdBookmarks(userDetails.getId());
                List<Wall> bookmarks = (List<Wall>) dto.get("walls");
                List<Long> bookmarksId = new ArrayList<>();
                for(Wall bookmark : bookmarks) {
                    bookmarksId.add(bookmark.getArticle().getId());
                }
                map.put("bookmarksId", bookmarksId);
                dto = wallArticleService.loadArticlesByUserIdReplies(userDetails.getId());
                List<Wall> replies = (List<Wall>) dto.get("walls");
                List<Long> repliesId = new ArrayList<>();
                for(Wall reply : replies) {
                    repliesId.add(reply.getArticle().getId());
                }
                map.put("repliesId", repliesId);
            }
            return "main/user_page";
        } catch(NumberFormatException e) {
            map.put("status", 404);
            map.put("text", "Пользователя с таким id не существует.");
            return "error_page";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String getPageEditor(@AuthenticationPrincipal UserDetailsImpl userDetails, ModelMap map) {
        map.put("user", userDetails.getUser());
        return "main/user_page_edit";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit")
    public ResponseEntity<String> editPage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Map<String, String> params) {// @RequestBody String jsonBody) {
        boolean updated = false;
        if(params.get("imageUrl") != null && !params.get("imageUrl").trim().equals("")) {
            userDetails.getUser().setImage(params.get("imageUrl"));
            updated = true;
        }
        if(params.get("userStatus") != null && !params.get("userStatus").trim().equals("")) {
            userDetails.getUser().setStatus(params.get("userStatus"));
            String status = params.get("userStatus");
            if(!userDetails.getUser().getStatus().equals(status.trim())) {
                userDetails.getUser().setStatus(status.trim());
                updated = true;
            }
        }
        if(params.get("userAbout") != null && !params.get("userAbout").trim().equals("")) {
            String about = params.get("userAbout").replaceAll("\n", "<br>");
            if(!userDetails.getUser().getBio().equals(about.trim())) {
                userDetails.getUser().setBio(about.trim());
                updated = true;
            }
        }
        if(updated) {
            Dto dto = userService.updateUserInfo(userDetails.getUser());
            if(dto.getStatus().equals(Status.USER_UPDATE_SUCCESS)) {
                return new ResponseEntity<>("edited", HttpStatus.OK);
            }
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("nothing changed", HttpStatus.OK);
    }
}