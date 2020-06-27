/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.examproject.tweet.controller;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.examproject.tweet.form.AuthForm;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import org.examproject.tweet.value.OAuthAccessorValue;
import org.examproject.tweet.value.OAuthValue;
import org.examproject.tweet.value.TweetCookie;

/**
 * @author h.adachi
 */
@Controller
@Scope(value="session")
public class OAuthController {

    private static final Log LOG = LogFactory.getLog(
        OAuthController.class
    );

    @Inject
    private final HttpSession session = null;

    ///////////////////////////////////////////////////////////////////////////
    // public methods

    @RequestMapping(
        value="/oauth",
        method=RequestMethod.GET
    )
    public String doRequestToken(
        HttpServletResponse response,
        Model model
    ) throws TwitterException, IOException {
        LOG.debug("in.");
        Twitter twitter = new TwitterFactory().getInstance();
         try {
            RequestToken requestToken = twitter.getOAuthRequestToken();
            if (requestToken != null) {
                // set the requestToken to session.
                session.setAttribute("requestToken", requestToken);
                // set value to the model.
                AuthForm authForm = new AuthForm();
                model.addAttribute("authForm", authForm);
                model.addAttribute("authorizationURL", requestToken.getAuthorizationURL());
                return "login";
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(
        value="/oauth",
        method=RequestMethod.POST
    )
    public String doAccessToken(
        HttpServletResponse response,
        AuthForm form,
        Model model
    ) throws TwitterException {
        LOG.debug("in.");
        RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");
        Twitter twitter = new TwitterFactory().getInstance();
        AccessToken accessToken = twitter.getOAuthAccessToken(
            requestToken,
            form.getPin()
        );
        OAuthAccessorValue accessorValue = new OAuthAccessorValue(
            "",
            accessToken.getToken(),
            accessToken.getTokenSecret(),
            String.valueOf(accessToken.getUserId()),
            accessToken.getScreenName()
        );
        // store access token and secret to cookie.
        storeTokenToCookie(
            response,
            accessorValue,
            604800
        );
        return "redirect:/index.html";
    }

    ///////////////////////////////////////////////////////////////////////////
    // private methods

    private static void storeTokenToCookie(
        HttpServletResponse response,
        OAuthAccessorValue accessorValue,
        int maxAge
    ){
        Cookie cookie = new Cookie(
            TweetCookie.REQUEST_TOKEN.getName(),
            accessorValue.getRequestToken()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

        cookie = new Cookie(
            TweetCookie.ACCESS_TOKEN.getName(),
            accessorValue.getAccessToken()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie( cookie);

        cookie = new Cookie(
            TweetCookie.TOKEN_SECRET.getName(),
            accessorValue.getTokenSecret()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

        cookie = new Cookie(
            TweetCookie.USER_ID.getName(),
            accessorValue.getId()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

        cookie = new Cookie(
            TweetCookie.SCREEN_NAME.getName(),
            accessorValue.getScreenName()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

}