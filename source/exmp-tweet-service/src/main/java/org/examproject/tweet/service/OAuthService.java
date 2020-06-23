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

package org.examproject.tweet.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.URLConnectionClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.examproject.tweet.util.UrlUtil;
import org.examproject.tweet.value.OAuthValue;

/**
 * @author hiroxpepe
 */
public class OAuthService {
    
    private static final Log LOG = LogFactory.getLog(
        OAuthService.class
    );
      
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    public String getRedirectTo(
        String destUrl,
        String requestUrl,
        OAuthValue authValue
    ) {
        OAuthClient client = new OAuthClient(
            new URLConnectionClient()
        );

        OAuthServiceProvider provider = new OAuthServiceProvider(
            authValue.getRequestTokenUrl(),
            authValue.getAuthorizeUrl(),
            authValue.getAccessTokenUrl()
        );
        
        // get the site domain url.
        String siteDomain = authValue.getSiteDomain();
        if (siteDomain == null || siteDomain.isEmpty()) {
            siteDomain = UrlUtil.getDomain(requestUrl);
        }
        LOG.debug("siteDomain: " + siteDomain);
    
        OAuthConsumer consumer = new OAuthConsumer(
            siteDomain + authValue.getCallbackUrlPath(),
            authValue.getConsumerKey(),
            authValue.getConsumerSecret(),
            provider
        );

        OAuthAccessor accessor = new OAuthAccessor(consumer);

        String redirectTo = null;
        try{
            try {
                // get request token first from Twitter.com
                HashMap params = new HashMap();
                params.put(
                    "oauth_callback",
                    OAuth.addParameters(
                        accessor.consumer.callbackURL,
                        "dest",
                        destUrl
                    )
                );

                // get request token first from Twitter.com
                client.getRequestToken(
                    accessor,
                    null,
                    params.entrySet()
                );
                
            } catch (OAuthException oae) {
                throw new RuntimeException(
                    "failed to authenticate Twitter account.",
                    oae
                );
            } catch (URISyntaxException urise) {
                throw new RuntimeException(
                    "failed to authenticate Twitter account.",
                    urise
                );
            }

            // build redirect path to twitter authentication page.
            redirectTo = OAuth.addParameters(
                accessor.consumer.serviceProvider.userAuthorizationURL,
                "oauth_token",
                accessor.requestToken
            );
            
        } catch (IOException ioe) {
            throw new RuntimeException(
                "failed to authenticate Twitter account.",
                ioe
            );
        }
     
        return redirectTo;
    }
}