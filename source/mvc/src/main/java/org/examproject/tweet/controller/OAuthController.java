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

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.examproject.tweet.value.OAuthAccessorValue;
import org.examproject.tweet.value.OAuthValue;
import org.examproject.tweet.value.TweetCookie;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

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
    private final HttpServletRequest request = null;

    @Inject
    private final HttpSession session = null;

    @Inject
    private final OAuthValue authValue = null;

    ///////////////////////////////////////////////////////////////////////////
    // public methods

    @RequestMapping(
        value="/oauth",
        method=RequestMethod.GET
    )
    public String doOAuth(
        HttpServletResponse response,
        Model model
    ) {
        LOG.debug("in.");
        Twitter twitter = new TwitterFactory().getInstance();
         try {
            RequestToken requestToken = twitter.getOAuthRequestToken("http://127.0.0.1:8080/callback.html");
            session.setAttribute("requestToken_token", requestToken.getToken());
            session.setAttribute("requestToken_secret", requestToken.getTokenSecret());
            return "redirect:" + requestToken.getAuthorizationURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(
        value="/callback",
        method=RequestMethod.GET
    )
    public String doCallback(
        HttpServletResponse response,
        Model model
    ) throws TwitterException, InterruptedException {
        LOG.debug("in.");
        Thread.sleep(5000);

        // TODO: check for authentication?
        String destUrl = request.getParameter("dest");
        //String requestToken = request.getParameter(OAuth.OAUTH_TOKEN);
        String requestToken = request.getParameter("oauth_token");
        if (requestToken == null) {
            // TODO: part of the authentication check is required!
            return "redirect:" + destUrl;
        }
        //String verifire = request.getParameter(OAuth.OAUTH_VERIFIER);
        String verifire = request.getParameter("oauth_verifier");
        if (verifire == null) {
            // TODO: part of the authentication check is required!
            return "redirect:" + destUrl;
        }

        Twitter twitter = new TwitterFactory().getInstance();
        //AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifire);
        //AccessToken accessToken = twitter.getOAuthAccessToken(requestToken);
        AccessToken accessToken = twitter.getOAuthAccessToken(
            request.getParameter("oauth_token"),
            request.getParameter("oauth_verifier")
        );

//        CallbackService service = new CallbackService();
//        OAuthAccessorValue accessorValue = service.getOAuthAccessorValue(
//            request.getRequestURL().toString(),
//            requestToken,
//            verifire,
//            authValue
//        );

        // store access token and secret to cookie.
//        storeTokenToCookie(
//            response,
//            accessorValue,
//            604800
//        );

        LOG.debug("out.");

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