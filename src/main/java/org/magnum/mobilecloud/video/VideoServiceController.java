/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.magnum.mobilecloud.video;


import com.google.common.collect.Lists;

import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collection;

@Controller
public class VideoServiceController {

    @Autowired
    private VideoRepository videoRepo;

    @RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideoList() {
        return Lists.newArrayList(videoRepo.findAll());
    }

    @RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}", method = RequestMethod.GET)
    public @ResponseBody Video getVideoById(@PathVariable long id, HttpServletResponse response) {
        Video v = videoRepo.findById(id);

        if (v == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return v;
    }

    @RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.POST)
    public @ResponseBody Video addVideo(@RequestBody Video v) {
        v.setLikes(0);

        return videoRepo.save(v);
    }

    @RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}/like", method = RequestMethod.POST)
    public Void likeVideo(@PathVariable long id, Principal p, HttpServletResponse response) {
        Video v = videoRepo.findById(id);

        if (v == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        if (v.getLikedBy().contains(p.getName())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        v.setLikes(v.getLikes() + 1);

        v.getLikedBy().add(p.getName());

        videoRepo.save(v);

        return null;
    }

    @RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}/unlike", method = RequestMethod.POST)
    public Void unlikeVideo(@PathVariable long id, Principal p, HttpServletResponse response) {
        Video v = videoRepo.findById(id);

        if (v == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        if (!v.getLikedBy().contains(p.getName())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        v.setLikes(v.getLikes() - 1);

        v.getLikedBy().remove(p.getName());

        videoRepo.save(v);

        return null;
    }

    @RequestMapping(value = VideoSvcApi.VIDEO_TITLE_SEARCH_PATH, method = RequestMethod.GET)
    public @ResponseBody Collection<Video> findByTitle(@RequestParam String title) {
        return videoRepo.findByName(title);
    }

    @RequestMapping(value = VideoSvcApi.VIDEO_DURATION_SEARCH_PATH, method = RequestMethod.GET)
    public @ResponseBody Collection<Video> findByDurationLessThan(@RequestParam long duration) {
        return videoRepo.findByDurationLessThan(duration);
    }

}